package com.github.adrien.koumgang.minetomcat.lib.cache.impl;


public interface CacheHandlerInterface <K, V> {
    V get(K key);
    void set(K key, V value);
    void set(K key, V value, long ttlInSeconds); // optional TTL support
    void delete(K key);
    boolean contains(K key);
}
