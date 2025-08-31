package ru.sbercources.filmography;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class FilmographyApplication {

    public static void main(String[] args) {
        log.info("swagger is running on http://localhost:9090/swagger-ui/index.html#/");
        SpringApplication.run(FilmographyApplication.class, args);
    }
}