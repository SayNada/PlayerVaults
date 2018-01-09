package com.drtshock.playervaults.translations;

public enum Language {
    ENGLISH("english"),
    BULGARIAN("english"),
    DUTCH("english");
    private String friendlyName;

    Language(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getFriendlyName() {
        return this.friendlyName;
    }
}
