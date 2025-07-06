package com.example.aiteach.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;

@Configuration
public class JwtConfig {

    @Bean
    public JwtEncoder jwtEncoder() throws IOException {
        // 从文件中加载密钥
        byte[] keyBytes = Files.readAllBytes(Paths.get("jwt.key"));
        Key key = new SecretKeySpec(keyBytes, "HmacSHA256");

        // 创建一个JWK
        OctetSequenceKey octetSequenceKey = new OctetSequenceKey.Builder((SecretKey) key).build();

        // 创建一个JWKSet并添加JWK
        JWKSet jwkSet = new JWKSet(octetSequenceKey);

        // 创建一个JWKSource
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(jwkSet);

        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public JwtDecoder jwtDecoder() throws IOException {
        // 从文件中加载密钥
        byte[] keyBytes = Files.readAllBytes(Paths.get("jwt.key"));
        Key key = new SecretKeySpec(keyBytes, "HmacSHA256");

        return NimbusJwtDecoder.withSecretKey((SecretKey) key).build();
    }
}