package jp.co.stex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;

/**
 * <p>株価情報の分析、自動取引を行うシステムトレードアプリケーションです。</p>
 *
 * @author t.nemoto.x
 * @version 1.0.0
 */
@SpringBootApplication
@EnableCaching
@EnableScheduling
public class StexApplication {

    /**
     * <p>キャッシュマネージャを有効にする。</p>
     *
     * @return キャッシュマネージャ
     */
    @Bean
    CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("stocks"), new ConcurrentMapCache("finances")));
        return cacheManager;
    }

    /**
     * <p>アプリケーションを起動する。</p>
     *
     * @param args 引数
     */
    public static void main(String[] args) {
        SpringApplication.run(StexApplication.class, args);
    }
}
