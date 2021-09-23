package com.lukkoc.users.infrastructure.config.feign;

import com.lukkoc.users.infrastructure.config.logging.FeignClientLogger;
import feign.slf4j.Slf4jLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfiguration {

    @Bean
    Slf4jLogger customFeignLogging(){
        return new FeignClientLogger();
    }
}
