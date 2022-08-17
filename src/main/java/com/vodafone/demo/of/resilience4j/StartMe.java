package com.vodafone.demo.of.resilience4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class StartMe implements CommandLineRunner {

    @Autowired
    private FaultyServiceCaller faultyServiceCaller;

    public static void main(String[] args) {
        new SpringApplicationBuilder(StartMe.class).build().run(args);
    }

    @Override
    public void run(String... args) throws Exception {
            //faultyServiceCaller.backendWhichThrowsExceptionAfterGivenCount();
    }
}
