package com.sistemaConsentimento.Cookie.contract;

import com.sistemaConsentimento.Cookie.model.CookieData;

public class CookieSmartContract {

    public boolean validateConsent(CookieData cookie) {
        return notBlank(cookie.getName())
                && notBlank(cookie.getValue())
                && notBlank(cookie.getClientData())
                && notBlank(cookie.getPurpose())
                && notBlank(cookie.getRetention())
                && cookie.isConsentGiven()
                && !cookie.isRevoked();
    }

    public boolean validateRevocation(CookieData cookie, CookieData previousState) {
        // previousState é o último registro desse cookie na cadeia
        return previousState != null
                && previousState.isConsentGiven()
                && !previousState.isRevoked()
                && notBlank(cookie.getName())
                && cookie.isConsentGiven() // revoga um consentimento previamente dado
                && cookie.isRevoked();
    }

    private boolean notBlank(String s) {
        return s != null && !s.trim().isEmpty();
    }

}
