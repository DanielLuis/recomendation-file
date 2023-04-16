package com.orche.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest("spring.cache.type=simple")
class SimpleCacheCustomizerTest {
    @Autowired
    private CacheManager cacheManager;

    @Test
    public void givenCacheManagerCustomizerWhenBootstrappedThenCacheManagerCustomized() {
        assertThat(cacheManager.getCacheNames())
                .containsOnly(CachingConfig.PRODUCT_CUSTOMERS, CachingConfig.RECOMMENDATION_REPORT);
    }
}