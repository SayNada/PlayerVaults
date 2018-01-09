package com.drtshock.playervaults.translations;

public enum Language {
    ENGLISH("english"),

    private String friendlyName;

    Language(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getFriendlyName() {
        return this.friendlyName;
    }
}
