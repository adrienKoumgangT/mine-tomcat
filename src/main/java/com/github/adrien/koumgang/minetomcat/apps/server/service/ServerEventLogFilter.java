package com.github.adrien.koumgang.minetomcat.apps.server.service;

public enum ServerEventLogFilter {

    NAME("name"),
    EVENT("event"),

    ;

    private final String value;

    ServerEventLogFilter(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ServerEventLogFilter getByValue(String value) {
        for (ServerEventLogFilter filter : ServerEventLogFilter.values()) {
            if (filter.getValue().equals(value)) {
                return filter;
            }
        }
        return null;
    }
}
