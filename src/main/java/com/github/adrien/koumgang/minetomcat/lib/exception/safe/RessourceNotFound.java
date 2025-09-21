package com.github.adrien.koumgang.minetomcat.lib.exception.safe;

import com.github.adrien.koumgang.minetomcat.lib.exception.SafeException;

public class RessourceNotFound extends SafeException {
    public RessourceNotFound() {
        super();
    }

    public RessourceNotFound(String errorMessage) {
        super(errorMessage);
    }
}
