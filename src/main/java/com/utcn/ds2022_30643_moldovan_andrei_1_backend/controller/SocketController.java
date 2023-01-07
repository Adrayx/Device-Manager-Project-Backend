package com.utcn.ds2022_30643_moldovan_andrei_1_backend.controller;

import org.apache.logging.log4j.message.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {

    @SendTo("/all/notification")
    public Message send(final Message message){
        return message;
    }
}
