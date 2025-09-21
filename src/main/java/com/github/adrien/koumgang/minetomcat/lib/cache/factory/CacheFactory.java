package com.github.adrien.koumgang.minetomcat.lib.cache.factory;

import com.github.adrien.koumgang.minetomcat.lib.cache.impl.CacheHandlerInterface;
import com.github.adrien.koumgang.minetomcat.lib.cache.impl.CacheHandlerMap;
import com.github.adrien.koumgang.minetomcat.lib.cache.impl.CacheHandlerRedis;
import com.github.adrien.koumgang.minetomcat.lib.cache.impl.CacheHandlerRedisCluster;
import com.github.adrien.koumgang.minetomcat.lib.cache.serializer.CacheSerializer;
import com.github.adrien.koumgang.minetomcat.lib.cache.serializer.GsonSerializer;
import com.github.adrien.koumgang.minetomcat.lib.cache.serializer.JacksonSerializer;
import com.github.adrien.koumgang.minetomcat.lib.configuration.service.CacheConfiguration;
import redis.clients.jedis.HostAndPort;

import java.util.HashSet;
import java.util.Set;

public class CacheFactory {

    public static CacheHandlerInterface<String, Object> getHandler() throws Exception {
        CacheConfiguration cacheConfiguration = new CacheConfiguration();

        CacheSerializer serializer = getSerializer();

        String cacheHandler = cacheConfiguration.getCacheHandler();

        return switch (cacheHandler.toLowerCase()) {
            case "map" -> new CacheHandlerMap<>(cacheConfiguration.getCacheHandlerMapPersistencePath(), serializer);
            case "redis" -> new CacheHandlerRedis<>(cacheConfiguration.getCacheHandlerRedisHost(), cacheConfiguration.getCacheHandlerRedisPort(), serializer);
            case "redis-cluster" -> {
                int port = cacheConfiguration.getCacheHandlerRedisClusterPort();
                String[] hosts = cacheConfiguration.getCacheHandlerRedisClusterHost().split(",");
                Set<HostAndPort> clusterNodes = new HashSet<>();
                for (String host : hosts) {
                    clusterNodes.add(new HostAndPort(host, port));
                }
                yield new CacheHandlerRedisCluster<>(clusterNodes, serializer);
            }
            default -> throw new IllegalArgumentException("Unknown cache handler: " + cacheHandler);
        };
    }

    public static CacheSerializer getSerializer() throws Exception {
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        String serializerType = cacheConfiguration.getCacheSerializerType();

        return switch (serializerType.toLowerCase()) {
            case "gson" -> new GsonSerializer();
            case "jackson" -> new JacksonSerializer();
            default -> throw new IllegalArgumentException("Unknown serializer: " + serializerType);
        };
    }
}

