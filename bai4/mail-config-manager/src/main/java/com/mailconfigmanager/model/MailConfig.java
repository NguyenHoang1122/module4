package com.mailconfigmanager.model;

import java.awt.*;

public class MailConfig {
    private int id;
    private String language;
    private int size;
    private boolean spamFitter;
    private String signature;

    public MailConfig() {
    }

    public MailConfig(int id, String language, int size, boolean spamFitter, String signature) {
        this.id = id;
        this.language = language;
        this.size = size;
        this.spamFitter = spamFitter;
        this.signature = signature;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isSpamFitter() {
        return spamFitter;
    }

    public void setSpamFitter(boolean spamFitter) {
        this.spamFitter = spamFitter;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "MailConfig{" +
                "id=" + id +
                ", language='" + language + '\'' +
                ", size=" + size +
                ", spamFitter=" + spamFitter +
                ", signature='" + signature + '\'' +
                '}';
    }
}
