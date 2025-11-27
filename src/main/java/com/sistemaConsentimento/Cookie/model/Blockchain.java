package com.sistemaConsentimento.Cookie.model;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class Blockchain {
    private List<Block> chain;
    private int dificuldade;

    public Blockchain(int dificuldade) {
        this.dificuldade = dificuldade;
        this.chain = new ArrayList<>();
        chain.add(createGenesisBlock());
    }

    private Block createGenesisBlock() {
        CookieData genesisData = new CookieData(
                "Genesis", "Init", "Genesis Consent",
                "Inicialização", "indefinido",
                true
        );
        return new Block(0, "0", Instant.now(), genesisData);
    }

    public void addBlock(CookieData data) {
        Block previous = chain.get(chain.size() - 1);
        Block newBlock = new Block(
                chain.size(),
                previous.getHash(),
                Instant.now(),
                data
        );
        newBlock.mineBlock(dificuldade);
        chain.add(newBlock);
    }

    public boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block current = chain.get(i);
            Block previous = chain.get(i - 1);
            if (!current.getHash().equals(current.calculateHash())) return false;
            if (!current.getHashAnterior().equals(previous.getHash())) return false;
        }
        return true;
    }

    public Optional<CookieData> latestByName(String name) {
        for (int i = chain.size() - 1; i >= 0; i--) {
            CookieData data = chain.get(i).getCookieData();
            if (data.getName().equalsIgnoreCase(name)) return Optional.of(data);
        }
        return Optional.empty();
    }

}
