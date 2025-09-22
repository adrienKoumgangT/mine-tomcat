package com.github.adrien.koumgang.minetomcat.lib.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class BaseService<T> implements ServiceInterface<T> {

    protected static final Gson gson = new GsonBuilder().serializeNulls().create();

    protected static final long CACHE_TTL = 60 * 60L;  // 30 minutes

}
