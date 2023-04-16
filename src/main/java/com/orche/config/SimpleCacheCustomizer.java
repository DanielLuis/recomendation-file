package com.orche.config;

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.stereotype.Component;

import static com.orche.config.CachingConfig.PRODUCT_CUSTOMERS;
import static com.orche.config.CachingConfig.PRODUCT_QUANTITY;
import static com.orche.config.CachingConfig.RECOMMENDATION_REPORT;
import static java.util.Arrays.asList;

@Component
public class SimpleCacheCustomizer implements CacheManagerCustomizer<ConcurrentMapCacheManager> {

    @Override
    public void customize(ConcurrentMapCacheManager cacheManager) {
        cacheManager.setCacheNames(asList(PRODUCT_CUSTOMERS, RECOMMENDATION_REPORT,PRODUCT_QUANTITY));
    }
}