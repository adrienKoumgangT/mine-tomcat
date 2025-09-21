package com.github.adrien.koumgang.minetomcat.lib.cache;


import com.github.adrien.koumgang.minetomcat.lib.cache.factory.CacheFactory;
import com.github.adrien.koumgang.minetomcat.lib.cache.impl.CacheHandlerInterface;

public class CacheManager {

    private static CacheManager instance;

    public static CacheManager getInstance() throws Exception {
        if(instance == null) {
            instance = new CacheManager();
        }
        return instance;
    }




    CacheHandlerInterface<String, Object> cacheHandler;

    public CacheManager() throws Exception {
        this.cacheHandler = CacheFactory.getHandler();
    }

    public CacheHandlerInterface<String, Object> getHandler() {
        return cacheHandler;
    }
}
