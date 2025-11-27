package com.sistemaConsentimento.Cookie.contract;

import com.sistemaConsentimento.Cookie.model.CookieData;

public class CookieSmartContract {

    public boolean validateConsent(CookieData cookie) {
        return notBlank(cookie.getName())
                && notBlank(cookie.getValue())
                && notBlank(cookie.getClientData())
                && notBlank(cookie.getPurpose())
                && notBlank(cookie.getRetention());
    }

    private boolean notBlank(String s) {
        return s != null && !s.trim().isEmpty();
    }

}
