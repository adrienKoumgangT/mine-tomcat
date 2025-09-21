package com.github.adrien.koumgang.minetomcat.lib.cache.factory;


import com.github.adrien.koumgang.minetomcat.lib.cache.serializer.CacheSerializer;
import com.github.adrien.koumgang.minetomcat.lib.cache.serializer.GsonSerializer;
import com.github.adrien.koumgang.minetomcat.lib.cache.serializer.JacksonSerializer;

public class SerializerFactory {

    public static CacheSerializer get(String type) {
        return switch (type.toLowerCase()) {
            case "gson" -> new GsonSerializer();
            case "jackson" -> new JacksonSerializer();
            default -> throw new IllegalArgumentException("Unknown serializer: " + type);
        };
    }

}
