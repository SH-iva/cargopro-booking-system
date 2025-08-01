package com.cargopro.linternship_project.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // This is the important part: we are registering the new module.
        // This teaches the JSON converter how to handle the special "proxy"
        // objects that Hibernate uses for lazy loading.
        objectMapper.registerModule(new Hibernate6Module());

        return objectMapper;
    }
}