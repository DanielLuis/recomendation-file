package com.ochre.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import static com.ochre.config.CachingConfig.PRODUCT_CUSTOMERS;
import static com.ochre.config.CachingConfig.PRODUCT_QUANTITY;
import static com.ochre.config.CachingConfig.RECOMMENDATION_REPORT;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest("spring.cache.type=simple")
class SimpleCacheCustomizerTest {
    @Autowired
    private CacheManager cacheManager;

    @Test
    public void givenCacheManagerCustomizerWhenBootstrappedThenCacheManagerCustomized() {
        assertThat(cacheManager.getCacheNames())
                .containsOnly(PRODUCT_CUSTOMERS, RECOMMENDATION_REPORT, PRODUCT_QUANTITY);
    }
}