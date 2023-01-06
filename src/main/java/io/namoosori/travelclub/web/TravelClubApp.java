package io.namoosori.travelclub.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TravelClubApp {

    public static void main(String[] args) {
        SpringApplication.run(TravelClubApp.class);
    }
}
