package com.example.microservices.todomicroservices.utilities;

public enum Priority {

    LOW("low"),
    HIGH("high");

    private final String level;

    Priority(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

}
