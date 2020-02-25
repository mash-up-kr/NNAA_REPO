package com.na.backend.service;

public enum Type {
    SUBJECTIVE("주관식"),
    OBJECTIVE("객관식"),
    OX("OX");

    String value;

    Type(String value) {
        this.value = value;
    }

    public String value() { return value; }
}