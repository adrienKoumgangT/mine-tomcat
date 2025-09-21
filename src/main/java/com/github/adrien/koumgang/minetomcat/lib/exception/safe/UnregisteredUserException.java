package com.github.adrien.koumgang.minetomcat.lib.exception.safe;

import com.github.adrien.koumgang.minetomcat.lib.exception.SafeException;

public class UnregisteredUserException extends SafeException {

    public UnregisteredUserException() {
        super();
    }

    public UnregisteredUserException(String errorMessage) {
        super(errorMessage);
    }

}
