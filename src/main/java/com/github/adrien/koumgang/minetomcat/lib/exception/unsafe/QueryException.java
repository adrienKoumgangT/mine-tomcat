package com.github.adrien.koumgang.minetomcat.lib.exception.unsafe;

import com.github.adrien.koumgang.minetomcat.lib.exception.UnsafeException;

public class QueryException extends UnsafeException {

    public QueryException() {
        super();
    }

    public QueryException(String errorMessage) {
        super(errorMessage);
    }

}
