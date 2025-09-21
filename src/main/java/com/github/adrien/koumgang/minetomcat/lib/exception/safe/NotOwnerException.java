package com.github.adrien.koumgang.minetomcat.lib.exception.safe;

import com.github.adrien.koumgang.minetomcat.lib.exception.SafeException;

public class NotOwnerException extends SafeException {
    public NotOwnerException() {
        super();
    }

    public NotOwnerException(String errorMessage) {
        super(errorMessage);
    }
}
