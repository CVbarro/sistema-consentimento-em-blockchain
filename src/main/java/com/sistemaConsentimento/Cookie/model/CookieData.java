package com.sistemaConsentimento.Cookie.model;

import lombok.Data;

@Data
public class CookieData {

    private String name;
    private String value;
    private String clientData;
    private String purpose;
    private String retention;
    private boolean consentGiven;
    private boolean revoked = false;

    public CookieData() {}

    public CookieData(String name, String value, String clientData, String purpose, String retention, boolean consentGiven, boolean revoked) {
        this.name = name;
        this.value = value;
        this.clientData = clientData;
        this.purpose = purpose;
        this.retention = retention;
        this.consentGiven = consentGiven;
        this.revoked = revoked;
    }


    @Override
    public String toString() {
        return "name=" + name +
                "|clientData=" + clientData +
                "|purpose=" + purpose +
                "|retention=" + retention +
                "|consentGiven=" + consentGiven +
                "|revoked=" + revoked;
    }
}
