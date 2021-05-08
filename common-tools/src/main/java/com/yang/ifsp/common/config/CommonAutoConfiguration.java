//package com.yang.ifsp.common.config;
//
//import com.yang.ifsp.common.redis.RedisClient;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import javax.validation.Valid;
//
//@Configuration
//public class CommonAutoConfiguration {
//
//    @Bean({"mainRedisClient"})
//    @ConditionalOnProperty(
//            name = {"ifsp.redis.enable"},
//            havingValue = "true",
//            matchIfMissing = false
//    )
//    @Primary
//    public RedisClient redisClient(@Value("${ifsp.redis.maxIdle:20"}) int maxIdle,)
//}
