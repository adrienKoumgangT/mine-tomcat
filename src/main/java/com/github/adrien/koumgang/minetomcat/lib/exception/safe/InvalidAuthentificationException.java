package com.github.adrien.koumgang.minetomcat.lib.exception.safe;

import com.github.adrien.koumgang.minetomcat.lib.exception.SafeException;

public class InvalidAuthentificationException extends SafeException {

    public InvalidAuthentificationException() {
        super();
    }

    public InvalidAuthentificationException(String errorMessage) {
        super(errorMessage);
    }

}
