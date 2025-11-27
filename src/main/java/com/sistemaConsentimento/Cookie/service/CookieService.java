package com.sistemaConsentimento.Cookie.service;

import com.sistemaConsentimento.Cookie.contract.CookieSmartContract;
import com.sistemaConsentimento.Cookie.model.Block;
import com.sistemaConsentimento.Cookie.model.Blockchain;
import com.sistemaConsentimento.Cookie.model.CookieData;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CookieService {

    @Getter
    private final Blockchain blockchain;
    private final CookieSmartContract contract;

    public CookieService(){
        this.blockchain = new Blockchain(4);
        this.contract = new CookieSmartContract();
    }


    public boolean processCookie(CookieData cookie) {
        if (!contract.validateConsent(cookie)) return false;
        blockchain.addBlock(cookie);
        return true;
    }

    public boolean revokeConsent(CookieData cookie) {
        cookie.setConsentGiven(false);
        if (!contract.validateConsent(cookie)) return false;
        blockchain.addBlock(cookie);
        return true;
    }

    public Blockchain getBlockchain() {
        return blockchain;
    }

    public List<CookieData> getAllConsents() {
        return blockchain.getChain().stream()
                .map(Block::getCookieData)
                .collect(Collectors.toList());
    }


    public List<CookieData> getConsentsByName(String name) {
        return blockchain.getChain().stream()
                .map(Block::getCookieData)
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    public boolean isConsentActive(String name) {
        return blockchain.latestByName(name)
                .map(CookieData::isConsentGiven)
                .orElse(false);
    }

    public boolean isChainValid() {
        return blockchain.isChainValid();


    }
}

