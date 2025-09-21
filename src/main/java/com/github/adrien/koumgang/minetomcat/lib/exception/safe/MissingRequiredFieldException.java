package com.github.adrien.koumgang.minetomcat.lib.exception.safe;

import com.github.adrien.koumgang.minetomcat.lib.exception.SafeException;

public class MissingRequiredFieldException extends SafeException {

    public MissingRequiredFieldException() {
        super();
    }

    public MissingRequiredFieldException(String errorMessage) {
        super(errorMessage);
    }

}
