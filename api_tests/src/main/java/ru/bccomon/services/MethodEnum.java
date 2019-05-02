package ru.bccomon.services;

public enum MethodEnum {
    GET_SINGLE_USER("/api/users/{userId}"), //getAllSalesAreas
    POST_USER("/api/users"); //createSalesArea

    private final String key;

    MethodEnum(final String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
