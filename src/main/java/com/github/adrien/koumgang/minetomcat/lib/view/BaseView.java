package com.github.adrien.koumgang.minetomcat.lib.view;

import com.github.adrien.koumgang.minetomcat.lib.exception.safe.MissingRequiredFieldException;
import com.google.gson.GsonBuilder;

import java.util.List;

public abstract class BaseView {

    public BaseView() {}

    public void checkIfValid() throws MissingRequiredFieldException {
        List<String> missing = RequiredValidator.validateRequiredFields(this);

        if (!missing.isEmpty()) {
            throw new MissingRequiredFieldException("Missing required fields: " + missing);
        }
    }


    @Override
    public String toString() {
        try {
            return new GsonBuilder().serializeNulls().create().toJson(this);
        } catch (Exception ignored) {}

        return super.toString();
    }

}
