package com.astrapay.starter.service.string;

import com.astrapay.starter.service.model.AstrapaySignature;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class SignatureGeneratorServiceTest {

    @Test
    public void testGenerateSignature() throws NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        AstrapaySignature astrapaySignature = AstrapaySignature.builder()
                .url("http://localhost:8020/disbursement-service/inquiries")
                .key("eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJlVm9kSlYzeVN6aUlyLW9aZjZCQzJtUVVaTmJFLWFaQndETjFUUmxmZE1jIn0.eyJleHAiOjE2Mzk0NDY3NzksImlhdCI6MTYzOTQ0NjQ3OSwianRpIjoiMzUxYWVlYTAtNTE2Ny00OTVkLWJhNTYtYWY5NmRhOTg2ZTFjIiwiaXNzIjoiaHR0cDovLzE3Mi4yMC4zLjEyNDo4MDgwL2F1dGgvcmVhbG1zL2FzdHJhcGF5LWJ1c2luZXNzIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjJlNjJmNzVhLTA2Y2UtNGQ5ZC04NTRkLTdiYWYxYjk0OWJiOSIsInR5cCI6IkJlYXJlciIsImF6cCI6IjYwMDY4YzlmLTkyMGMtNGVlMy05M2Q5LWQ0NWRlMzcxZjJlMSIsImFjciI6IjEiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1hc3RyYXBheS1idXNpbmVzcyIsIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6InByb2ZpbGUgZW1haWwiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImNsaWVudEhvc3QiOiIxNzIuMjAuMy4xMjMiLCJjbGllbnRJZCI6IjYwMDY4YzlmLTkyMGMtNGVlMy05M2Q5LWQ0NWRlMzcxZjJlMSIsInByZWZlcnJlZF91c2VybmFtZSI6InNlcnZpY2UtYWNjb3VudC02MDA2OGM5Zi05MjBjLTRlZTMtOTNkOS1kNDVkZTM3MWYyZTEiLCJjbGllbnRBZGRyZXNzIjoiMTcyLjIwLjMuMTIzIn0.THD7R67KhJu93x4pBzQUSbxb4riGTROdPCFiVTGhz9v1sYmo7Ku1rDNrRw2hZcnnIxrXMPlVsHeLSrjS_gpXFUTSibqGlpqKpQ-niHxWnzHns-IbUAZF0Us0pj_7Yx3g0Q7huGwyV-ZyMXYrgZFCul4IKloUgEkYASznGGM2dCuH9kp90tGwXfQwLsgmq570mtE9LpSS0lIg92uLd8H7nDTCAhQVgG-9r5RkuxJv7GZqcajD2G4hrZgkR8FrtY5gnMu-mA4GzWGfpVwFWqRY_i9y2_nP5attQ07IsbFaARR6SKnMUAVG11mtRopDn8b4MTLpcAJ_IpVYRaQ8KcrZ_Q")
                .httpMethod(HttpMethod.POST)
                .body("{\n" +
                        "\n" +
                        "    \"phoneNumber\": \"085225843522\",\n" +
                        "    \"amount\": 12000\n" +
                        "\n" +
                        "}")
                .timestamp("2019-06-21T11:37:51.436Z")
                .build();

        String sign = astrapaySignature.toSign() + "zst3x80085760spyhn5eab";
        SecretKeySpec secretKeySpec = new SecretKeySpec(sign.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        SignatureGeneratorService signatureGeneratorService = new SignatureGeneratorService();

        Mac sha256HMAC = Mac.getInstance("HmacSHA256");
        sha256HMAC.init(secretKeySpec);
        signatureGeneratorService.setSha256HMAC(sha256HMAC);

        Assertions.assertEquals("d953f15d1300e3c19dc5964f46c2129157a3ab2e9822763825aedefaba6322b1",signatureGeneratorService.createAstraPaySignature(astrapaySignature, "zst3x80085760spyhn5eab"));
    }
}
