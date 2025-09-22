package com.github.adrien.koumgang.minetomcat.lib.view;

import com.github.adrien.koumgang.minetomcat.lib.exception.safe.MissingRequiredFieldException;
import com.github.adrien.koumgang.minetomcat.lib.model.BaseModel;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.List;

public abstract class BaseView {

    private Date createdAt;

    private Date updatedAt;

    protected BaseView() {}

    protected BaseView(BaseModel model) {
        this.createdAt = model.getCreatedAt();
        this.updatedAt = model.getUpdatedAt();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

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
