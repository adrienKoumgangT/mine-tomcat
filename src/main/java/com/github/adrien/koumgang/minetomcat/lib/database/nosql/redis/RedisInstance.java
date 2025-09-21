package com.github.adrien.koumgang.minetomcat.lib.database.nosql.redis;

import com.github.adrien.koumgang.minetomcat.lib.configuration.service.RedisConfiguration;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.redis.provider.RedisClusterProvider;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.redis.provider.RedisProvider;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.redis.provider.RedisProviderInterface;
import com.github.adrien.koumgang.minetomcat.lib.database.nosql.redis.serializer.RedisSerializer;

public class RedisInstance {

    private static final String redisType;

    static {
        String rt;
        try {
            RedisConfiguration redisConfiguration = new RedisConfiguration();
            rt = redisConfiguration.getRedisType();
        } catch (Exception e) {
            rt = "sentinel";
        }
        redisType = rt;
    }


    private static volatile RedisProviderInterface<String> instance;

    /** Thread-safe lazy singleton */
    public static RedisProviderInterface<String> getInstance() throws Exception {
        RedisProviderInterface<String> local = instance;
        if (local == null) {
            synchronized (RedisProviderInterface.class) {
                local = instance;
                if (local == null) {
                    if(redisType.equals("cluster")) {
                        instance = local = new RedisClusterProvider(new RedisSerializer());
                    } else {
                        instance = local = new RedisProvider(new RedisSerializer());
                    }
                }
            }
        }

        return local;
    }

}
