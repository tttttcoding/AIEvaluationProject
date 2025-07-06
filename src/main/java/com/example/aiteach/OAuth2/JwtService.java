package com.example.aiteach.OAuth2;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;

    public JwtService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(String subject) {
        Instant now = Instant.now();
        Instant expiry = now.plus(1, ChronoUnit.HOURS); // 设置Token有效期为1小时

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("http://127.0.0.1:8080") // issuer 应该是合法的 URL
                .issuedAt(now)
                .expiresAt(expiry)
                .subject(subject)
                .claim("custom_claim", "custom_value")
                .build();

        JwtEncoderParameters parameters = JwtEncoderParameters.from(claims);

        return jwtEncoder.encode(parameters).getTokenValue();
    }
}
