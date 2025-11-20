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

    public boolean revokeConsent(CookieData revocation) {
        // Busca estado anterior
        CookieData previous = blockchain.latestByName(revocation.getName()).orElse(null);
        // Sinaliza revogação explicitamente
        revocation.setRevoked(true);
        revocation.setConsentGiven(true); // estamos revogando um consentimento previamente dado
        if (!contract.validateRevocation(revocation, previous)) return false;
        blockchain.addBlock(revocation);
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

    public List<CookieData> getConsentsByStatus(boolean revoked) {
        return blockchain.allByRevoked(revoked);
    }

    public List<CookieData> getConsentsByName(String name) {
        return blockchain.getChain().stream()
                .map(Block::getCookieData)
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    public boolean isChainValid() {
        return blockchain.isChainValid();


    }
}

