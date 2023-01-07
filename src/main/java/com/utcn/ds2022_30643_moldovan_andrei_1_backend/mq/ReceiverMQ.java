package com.utcn.ds2022_30643_moldovan_andrei_1_backend.mq;

import com.rabbitmq.client.*;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.config.NotificationEndpoint;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.dto.EnergyDeviceDto;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.exception.EnergyDeviceNotFoundException;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.api.EnergyDeviceRepository;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.api.MeasurementRepository;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.jpa.HibernateRepositoryFactory;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.service.EnergyDeviceService;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.service.MeasurementService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.SimpleMessage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@Component
@CrossOrigin(origins = {"*"})
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ReceiverMQ implements CommandLineRunner{
    private final EnergyDeviceService deviceService;

    private final SimpMessagingTemplate template;

    public void run(String... args) {
        String uri = "amqps://pxujpuqb:0QNynshiRNg12vK9kM785aqE05h2y66l@goose.rmq2.cloudamqp.com/pxujpuqb";

        ConnectionFactory connectionFactory = new ConnectionFactory();

        try{
            connectionFactory.setUri(uri);
        } catch (URISyntaxException | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }

        connectionFactory.setVirtualHost("pxujpuqb");
        connectionFactory.setUsername("pxujpuqb");
        connectionFactory.setPassword("0QNynshiRNg12vK9kM785aqE05h2y66l");

        try{
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();

            String QUEUE_NAME = "measurements_queue";
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages.");
            channel.basicConsume(QUEUE_NAME, true, (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                JSONParser parser = new JSONParser();
                try{
                    Object obj = parser.parse(message);
                    JSONObject jsonObject = (JSONObject) obj;
                    System.out.println("New Measurement: ");
                    System.out.println("  id: " + jsonObject.get("id").toString());
                    System.out.println("  measurement: " + jsonObject.get("measurement").toString());
                    System.out.println();
                    try {
                        EnergyDeviceDto dto = deviceService.findById(Integer.parseInt(jsonObject.get("id").toString()));
                        Double measurement = Double.parseDouble(jsonObject.get("measurement").toString());
                        if(dto.getThreshold() < measurement)
                        {
                            //send notification to front
                            System.out.println("Threshold is exceeded for the device " + dto.getId());
                            template.convertAndSend(NotificationEndpoint.EXCEEDED, "Threshold is exceeded for the device " + dto.getId());
                            //send(new SimpleMessage("Threshold is exceeded for the device " + dto.getId()));
                        } else {
                            deviceService.addMeasurement(dto, measurement);
                            // template.convertAndSend(NotificationEndpoint.EXCEEDED, "Device measurement added for the ID: " + dto.getId());
                            //send(new SimpleMessage("Device measurement added for the ID: " + dto.getId()));
                        }
                    } catch(EnergyDeviceNotFoundException e){
                        System.out.println("Device not found: " + e.getMessage());
                    }

                } catch(ParseException pe) {

                    System.out.println("position: " + pe.getPosition());
                    System.out.println(pe.getMessage());
                }
            }, consumerTag -> {});
        } catch (IOException | TimeoutException e){
            e.printStackTrace();
        }
    }
}
