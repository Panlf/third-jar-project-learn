package com.plf.learn.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author panlf
 * @date 2023/1/31
 */
@Slf4j
public class GuavaCacheTest {
    public static void main(String[] args) throws ExecutionException {
        Cache<String, String> cache = CacheBuilder.newBuilder()
                .initialCapacity(5)  // 初始容量
                .maximumSize(10)     // 最大缓存数，超出淘汰
                .expireAfterWrite(5, TimeUnit.SECONDS) // 过期时间
                .build();

        String name = "壮壮";
        // 获取orderInfo，如果key不存在，callable中调用getInfo方法返回数据
        String userInfo = cache.get(name, () -> getInfo(name));
        log.info("userInfo = {}", userInfo);

    }

    private static String getInfo(String name) {
        String info = "";
        // 先查询redis缓存
        log.info("get data from redis");

        // 当redis缓存不存在查db
        log.info("get data from mysql");
        info = String.format("{name=%s}", name);
        return info;
    }
}
