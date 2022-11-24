package com.utcn.ds2022_30643_moldovan_andrei_1_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class Ds202230643MoldovanAndrei1BackendApplication {
    public static Integer create_seed = 0;
    public static void main(String[] args) {
        if(Arrays.asList(args).contains("CREATE_SEED")){
            create_seed = 1;
        }
        SpringApplication.run(Ds202230643MoldovanAndrei1BackendApplication.class, args);
    }
}
