package com.mitienda.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Configuración para evitar problemas con tus Records
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT) // Coincidencia exacta de nombres
                .setSkipNullEnabled(true) // Ignora campos nulos
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        // Conversión explícita para BigDecimal (evita problemas de precisión)
        modelMapper.createTypeMap(String.class, BigDecimal.class)
                .setConverter(ctx -> new BigDecimal(ctx.getSource().trim()));

        return modelMapper;
    }
}