package com.sistemaConsentimento.Cookie.model;

import lombok.Data;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;

@Data
public class Block {
    private int index;
    private String hash;
    private String hashAnterior;
    private Instant timestamp;
    private CookieData cookieData;
    private int nonce;

    public Block(int index, String hashAnterior, Instant timestamp, CookieData cookieData) {
        this.index = index;
        this.cookieData = cookieData;
        this.hashAnterior = hashAnterior == null ? "0" : hashAnterior;
        this.timestamp = timestamp == null ? Instant.now() : timestamp;
        this.hash = calculateHash();
        this.nonce = 0;
    }

    public String calculateHash(){
        String input = index + timestamp.toString() + cookieData.toString() + hashAnterior + nonce;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao calcular hash", e);
        }
    }

    public void mineBlock(int difficulty) {
        if (difficulty < 0 || difficulty > 30) throw new IllegalArgumentException("Dificuldade inválida");
        String targetPrefix = "0".repeat(difficulty);
        // Garante que o hash seja recalculado se dificuldade mudar
        this.hash = calculateHash();
        while (!hash.startsWith(targetPrefix)) {
            nonce++;
            hash = calculateHash();
        }
    }
}
