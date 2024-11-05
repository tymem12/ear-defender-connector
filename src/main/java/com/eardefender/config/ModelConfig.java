package com.eardefender.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Getter
public class ModelConfig {

    @Value("${model.api.config.models}")
    private List<String> models;
}
