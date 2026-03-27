package com.aceward.usermanagement.bean;

import java.time.Clock;
import java.time.ZoneId;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClockのBean定義クラス。
 * 
 * @author h-suyama
 * @version 1.0.0
 */
@Configuration
public class ClockConfig {

    /**
     * ClockのBean定義。
     * 
     * @return Clock
     */
    @Bean
    public Clock clock() {
        return Clock.system(ZoneId.of("Asia/Tokyo"));
    }
}
