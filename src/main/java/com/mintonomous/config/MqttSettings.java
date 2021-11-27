package com.mintonomous.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import lombok.Data;

@Primary
@Data
@Configuration
@ConfigurationProperties(prefix = "mqtt")
public class MqttSettings {

    private String hostname;
    private int port;
    private String username;
    private String password;
    private String topic;
}
