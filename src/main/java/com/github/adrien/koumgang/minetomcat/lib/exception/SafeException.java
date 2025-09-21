package com.github.adrien.koumgang.minetomcat.lib.exception;

public class SafeException extends Exception {

    public SafeException() {
        super();
    }

    public SafeException(String errorMessage) {
        super(errorMessage);
    }

}
