package com.github.adrien.koumgang.minetomcat.lib.exception.unsafe;

import com.github.adrien.koumgang.minetomcat.lib.exception.UnsafeException;

public class UnauthorizedException extends UnsafeException {
    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String errorMessage) {
        super(errorMessage);
    }
}
