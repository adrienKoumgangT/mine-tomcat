package com.github.adrien.koumgang.minetomcat.apps.account.service;

public enum AccountFilter {

    NAME("name"),
    IS_ARCHIVED("isArchived"),
    ;

    private final String value;

    AccountFilter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static AccountFilter getByValue(String value) {
        for (AccountFilter filter : AccountFilter.values()) {
            if (filter.value.equals(value)) {
                return filter;
            }
        }
        return null;
    }
}
