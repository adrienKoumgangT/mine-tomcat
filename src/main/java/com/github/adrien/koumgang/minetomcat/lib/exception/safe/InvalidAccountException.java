package com.github.adrien.koumgang.minetomcat.lib.exception.safe;

import com.github.adrien.koumgang.minetomcat.lib.exception.SafeException;

public class InvalidAccountException extends SafeException {

    public InvalidAccountException() {
        super();
    }

    public InvalidAccountException(String errorMessage) {
        super(errorMessage);
    }

}
