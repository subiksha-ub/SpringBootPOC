/*
package com.example.employeeManagement.CacheConfig;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import reactor.core.publisher.Mono;

public class RedisConfig {
    private RedisReactiveCommands<String, String> reactiveCommands;

//    public RedisConfig() {
////        RedisURI redisURI = RedisURI.Builder.redis("localhost", 6379).build();
////        RedisClient redisClient = RedisClient.create(redisURI);
////        StatefulRedisConnection<String, String> connection = redisClient.connect();
////        reactiveCommands = connection.reactive();
//    }

    public void saveToRedis(String key, String value) {
        reactiveCommands.set(key, value);
    }

    public Mono<String> fetchFromRedis(String key) {
        return reactiveCommands.get(key);
    }

    // Other Redis operations and utility methods

//    public void closeConnection() {
//        reactiveCommands.getStatefulConnection().close();
//    }
}

*/
