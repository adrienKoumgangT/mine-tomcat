package com.github.adrien.koumgang.minetomcat.lib.exception.safe;

import com.github.adrien.koumgang.minetomcat.lib.exception.SafeException;

public class InvalidParamException extends SafeException {

    public InvalidParamException() {
        super();
    }

    public InvalidParamException(String errorMessage) {
        super(errorMessage);
    }
}
