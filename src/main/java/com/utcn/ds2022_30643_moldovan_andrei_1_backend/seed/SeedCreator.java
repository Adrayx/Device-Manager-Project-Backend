package com.utcn.ds2022_30643_moldovan_andrei_1_backend.seed;

import com.utcn.ds2022_30643_moldovan_andrei_1_backend.Ds202230643MoldovanAndrei1BackendApplication;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.EnergyDevice;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.entity.User;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.api.EnergyDeviceRepository;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.api.MeasurementRepository;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.api.RepositoryFactory;
import com.utcn.ds2022_30643_moldovan_andrei_1_backend.persistance.api.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.lang.Math.random;

@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnProperty(name = "create-seed", havingValue = "TRUE")
public class SeedCreator implements CommandLineRunner {
    private final RepositoryFactory factory;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args){
        if(Ds202230643MoldovanAndrei1BackendApplication.create_seed == 1) {
            System.out.println("Initialized creating devices!");
            createDevices();

            System.out.println("Initialized creating measurements!");
            createMeasurements();

            System.out.println("Initialized creating users!");
            createUsers();

            System.out.println("Completed");

            System.out.println("Users:");
            System.out.println(factory.createUserRepository().findAll());
            System.out.println("Devices:");
            System.out.println(factory.createEnergyDeviceRepository().findAll());
            System.out.println("Measurements:");
            System.out.println(factory.createMeasurementRepository().findAll());
        }
    }

    private void createDevices(){
        EnergyDeviceRepository repository = factory.createEnergyDeviceRepository();

        for(int i = 0; i < 15; i++){
            EnergyDevice device = new EnergyDevice("description" + i, "address" + i, random() * 1000);
            repository.save(device);
        }
    }

    private void createMeasurements(){
        EnergyDeviceRepository repositoryDevices = factory.createEnergyDeviceRepository();
        MeasurementRepository measurementRepository = factory.createMeasurementRepository();
        List<EnergyDevice> deviceList = repositoryDevices.findAll();

        for (EnergyDevice device : deviceList) {
            int rand = 15;
            for (int j = 0; j < rand; j++) {
                Double consumption = random() * 100;
                measurementRepository.save(device.createMeasurement(consumption));
            }
        }
    }

    private void createUsers(){
        UserRepository repository = factory.createUserRepository();
        List<EnergyDevice> devices = factory.createEnergyDeviceRepository().findAll();
        int ind = 0;
        for(int i = 0; i < 5; i++) {
            User user = new User("username" + i, "password" + i, false);
            int k = 2;
            for(int j = 0; j < k; j++){
                EnergyDevice device = devices.get(ind++);
                user.addDevice(device);
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            repository.save(user);
        }

        User admin = new User("admin", "parola", true);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        repository.save(admin);
    }
}
