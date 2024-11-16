package com.fimsolution.group.app.utils;


import com.fimsolution.group.app.model.security.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    private Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${token.signing.key}")
    private String jwtSigningKey;

    @Value("${jwt.cookieName}")
    private String jwtCookieName;

    @Value("${jwt.refreshCookieName}")
    private String jwtRefreshCookieName;
    @Value("${jwt.cookieRefreshExpiration}")
    private int jwtCookieRefreshExpiration;


    @Value("${jwt.cookieAccessExpiration}")
    private int jwtCookieAccessExpiration;


//    private Key getSigningKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(this.jwtSigningKey);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }

//    private Key getSigningKey() {
//        byte[] keyBytes = this.jwtSigningKey.getBytes(StandardCharsets.UTF_8);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }


//    SecretKey getSigningKey() {
//        return Jwts.SIG.HS256.key().build();
//    }


    SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String generateJwtToken(UserDetailsImpl userDetails) {
        logger.info(":::generateJwtToken:::");

        LocalDateTime now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime nowPlusExpire = now.plusDays(1).atZone(ZoneId.systemDefault()).toLocalDateTime();
        logger.info(":::Now:::{}, To Date:{}", now.toString(), convertToDateViaInstant(now));
        logger.info(":::NowPlusExpire:::{}, :::ToDate:{}", nowPlusExpire.toString(), convertToDateViaInstant(nowPlusExpire));

        return Jwts.builder()
                .subject((userDetails.getUsername()))
                .issuedAt(convertToDateViaInstant(now))
                .expiration(convertToDateViaInstant(nowPlusExpire))
                .claim("roles", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority
                                ::getAuthority) // Extract authority as a string
                        .collect(Collectors.toList()))
                .signWith(getSigningKey())
                .compact();
    }

    public void test() {
//        ZoneId.SHORT_IDS
    }


    public String generateRefreshToken(UserDetailsImpl userDetails) {
        logger.info(":::generateRefreshToken:::");

        LocalDateTime now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime nowPlusExpire = now.plusDays(30).atZone(ZoneId.systemDefault()).toLocalDateTime();
        logger.info(":::Now:::{}, To Date:{}", now.toString(), convertToDateViaInstant(now));
        logger.info(":::NowPlusExpire:::{}, :::ToDate:{}", nowPlusExpire.toString(), convertToDateViaInstant(nowPlusExpire));

        return Jwts.builder()
                .subject((userDetails.getUsername()))
                .issuedAt(convertToDateViaInstant(now))
                .expiration(convertToDateViaInstant(nowPlusExpire))
                .claim("roles", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority
                                ::getAuthority) // Extract authority as a string
                        .collect(Collectors.toList()))
                .signWith(getSigningKey())
                .compact();

    }

//    private boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {

        try {

            return Jwts
                    .parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

        } catch (ExpiredJwtException e) {
            // If the token is expired, return true to indicate expiration
            logger.error("Token expired: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());


        } catch (SignatureException e) {
            // If the signature is invalid, return false indicating invalid token
            logger.error("Invalid token signature: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());


        } catch (MalformedJwtException e) {
            // If the token is malformed, return false indicating invalid token
            logger.error("Malformed token: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());


        } catch (JwtException e) {
            // Handle other JWT exceptions
            logger.error("JWT parsing error: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());


        } catch (Exception e) {
            // Catch any other unforeseen exceptions
            logger.error("Unknown error during token verification: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());


        }

    }


    boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    public boolean isValidToken(String token, UserDetails userDetails) {
        return (extractSubject(token).equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    public boolean isNotExpiredReToken(String refreshToken) {
        return extractExpiration(toString()).before(new Date());
    }


    private Date convertToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
        return java.sql.Timestamp.valueOf(dateToConvert);
    }

    private Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }
}
