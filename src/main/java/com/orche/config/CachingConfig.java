package com.orche.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CachingConfig {

    public static final String PRODUCT_CUSTOMERS = "productCustomers";
    public static final String RECOMMENDATION_REPORT = "recommendationReport";

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(PRODUCT_CUSTOMERS, RECOMMENDATION_REPORT);
    }
}