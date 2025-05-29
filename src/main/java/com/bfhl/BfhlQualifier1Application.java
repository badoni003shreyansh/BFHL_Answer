package com.bfhl;

import com.bfhl.service.BfhlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BfhlQualifier1Application implements CommandLineRunner {

    @Autowired
    private BfhlService service;

    public static void main(String[] args) {
        SpringApplication.run(BfhlQualifier1Application.class, args);
    }

    @Override
    public void run(String... args) {
        service.execute();
    }
}
