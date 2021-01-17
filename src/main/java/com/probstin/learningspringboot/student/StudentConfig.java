package com.probstin.learningspringboot.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.JANUARY;


@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student mariam = new Student(
                    "Mariam",
                    "mar@gmail.com",
                    LocalDate.of(1990, JANUARY, 5)
            );

            Student todd = new Student(
                    "todd",
                    "todd@gmail.com",
                    LocalDate.of(2000, JANUARY, 5)
            );

            studentRepository.saveAll(List.of(mariam, todd));
        };
    }
}
