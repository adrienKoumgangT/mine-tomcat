package com.github.adrien.koumgang.minetomcat.lib.exception.unsafe;

import com.github.adrien.koumgang.minetomcat.lib.exception.UnsafeException;

public class CacheContainerException extends UnsafeException {
    public CacheContainerException() {
        super();
    }

    public CacheContainerException(String errorMessage) {
        super(errorMessage);
    }
}
