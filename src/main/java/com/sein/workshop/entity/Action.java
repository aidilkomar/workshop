package com.sein.workshop.entity;

public enum Action {
    READ,
    WRITE,
    UPDATE,
    DELETE,
    EXPORT,
    APPROVE;

    public String asAuthority(String featureCode) {
        return featureCode + ":" +this.name();
    }
}
