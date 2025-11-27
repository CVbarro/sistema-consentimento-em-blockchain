package com.sistemaConsentimento.Cookie.controller;


import com.sistemaConsentimento.Cookie.model.Blockchain;
import com.sistemaConsentimento.Cookie.model.CookieData;
import com.sistemaConsentimento.Cookie.service.CookieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/api/cookies")
public class CookieController {
    @Autowired
    private CookieService service;

    @PostMapping("/set-cookie")
    public ResponseEntity<String> setCookieAndRegister(@RequestBody CookieData cookie) {
        if (!service.processCookie(cookie)) {
            return ResponseEntity.badRequest().body("Dados inválidos para consentimento.");
        }

        // Ajuste de retenção simples: se contiver "30", usa 30 dias; pode evoluir para ISO-8601 (PT30D)
        long days = cookie.getRetention().contains("30") ? 30 : 7;

        ResponseCookie httpCookie = ResponseCookie.from(cookie.getName(), cookie.getValue())
                .maxAge(Duration.ofDays(days))
                .path("/")
                .httpOnly(true)
                .secure(true)            // usar HTTPS em produção
                .sameSite("Lax")         // "Strict" para maior proteção, "None" exige Secure
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, httpCookie.toString())
                .body("Cookie enviado e registrado na blockchain.");
    }

    @PostMapping("/revoke")
    public ResponseEntity<String> revokeCookie(@RequestBody CookieData cookie) {
        boolean revoked = service.revokeConsent(cookie);
        if (revoked) {
            return ResponseEntity.ok("Consentimento revogado e registrado na blockchain.");
        } else {
            return ResponseEntity.badRequest().body("Revogação inválida.");
        }
    }

    @GetMapping("/blockchain")
    public Blockchain getBlockchain() {
        return service.getBlockchain();
    }

    @GetMapping("/audit/all")
    public List<CookieData> auditAll() {
        return service.getAllConsents();
    }


    @GetMapping("/status")
    public ResponseEntity<String> checkConsentStatus(@RequestParam String name) {
        boolean active = service.isConsentActive(name);
        return ResponseEntity.ok(active ? "Consentimento ativo." : "Consentimento revogado.");
    }

    @GetMapping("/audit/name")
    public List<CookieData> auditByName(@RequestParam String name) {
        return service.getConsentsByName(name);
    }

    @GetMapping("/integrity")
    public ResponseEntity<String> checkIntegrity() {
        return ResponseEntity.ok(service.isChainValid() ? "Blockchain válida." : "Blockchain inválida!");
    }



}
