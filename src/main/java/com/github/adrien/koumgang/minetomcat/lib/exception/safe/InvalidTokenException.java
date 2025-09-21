package com.github.adrien.koumgang.minetomcat.lib.exception.safe;

import com.github.adrien.koumgang.minetomcat.lib.exception.SafeException;

public class InvalidTokenException extends SafeException {

    public InvalidTokenException() {
        super();
    }

    public InvalidTokenException(String errorMessage) {
        super(errorMessage);
    }

}
