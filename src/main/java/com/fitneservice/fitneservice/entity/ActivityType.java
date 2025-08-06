package com.fitneservice.fitneservice.entity;

public enum ActivityType {
    RUNNING("Correr"),
    WALKING("Caminar"),
    CYCLING("Ciclismo"),
    HIKING("Senderismo"),
    DANCING("Baile"),
    SOCCER("Fútbol"),
    BASKETBALL("Baloncesto"),
    TENNIS("Tenis"),
    BOXING("Boxeo"),
    CLIMBING("Escalada"),
    SKIING("Esquí"),
    SURFING("Surf");

    private final String displayName;

    ActivityType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}