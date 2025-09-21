package com.github.adrien.koumgang.minetomcat.lib.exception.unsafe;

import com.github.adrien.koumgang.minetomcat.lib.exception.UnsafeException;

public class BadColorException extends UnsafeException {
    public BadColorException() {
        super();
    }

    public BadColorException(String errorMessage) {
        super(errorMessage);
    }

}
