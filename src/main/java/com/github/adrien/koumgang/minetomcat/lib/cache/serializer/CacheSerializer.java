package com.github.adrien.koumgang.minetomcat.lib.cache.serializer;

public interface CacheSerializer {

    <T> String serialize(T value);

    <T> T deserialize(String data, Class<T> type);

}
