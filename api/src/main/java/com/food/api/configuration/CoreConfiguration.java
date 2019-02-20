package com.food.api.configuration;

import com.food.core.processor.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfiguration {

    @Bean
    public ItemProcessor itemProcessor(){
        return new ItemProcessor();
    }

}
