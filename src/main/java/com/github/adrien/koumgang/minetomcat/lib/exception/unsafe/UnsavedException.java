package com.github.adrien.koumgang.minetomcat.lib.exception.unsafe;

import com.github.adrien.koumgang.minetomcat.lib.exception.UnsafeException;

public class UnsavedException extends UnsafeException {
    public UnsavedException() {
        super();
    }

    public UnsavedException(String errorMessage) {
        super(errorMessage);
    }
}
