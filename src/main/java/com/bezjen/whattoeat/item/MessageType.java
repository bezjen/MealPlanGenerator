package com.bezjen.whattoeat.item;

public enum MessageType {
    ERROR("error"),
    WARNING("warning"),
    INFO("info");

    private String name;

    MessageType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
