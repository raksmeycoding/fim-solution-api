package com.fimsolution.group.app.security;

import com.fimsolution.group.app.model.security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;


@Service
public class JwtServiceImpl implements JwtService {
    private final Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);


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


    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }


    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        Instant instant = Instant.now();
        return Jwts.builder()
                .setClaims(extraClaims)
                .setIssuedAt(Date.from(instant))
                .setExpiration(Date.from(instant.plus(30, ChronoUnit.DAYS)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
//        return Jwts.builder()
//                .setClaims(extraClaims)
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
//                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    public String genJwtFromUser(String username) {
        Instant instant = Instant.now();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(instant))
                .setExpiration(Date.from(instant.plus(30, ChronoUnit.DAYS)))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    public String generateRefreshToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(30, ChronoUnit.DAYS)))
                .signWith(SignatureAlgorithm.HS512, getSigningKey())
                .compact();
    }

//    public String extractUsername(String token) {
//        Claims claims = Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(token).getBody();
//        return claims.getSubject();
//    }


    private ResponseCookie genCookie(String name, String value) {
        return ResponseCookie.fromClientResponse(name, value)
                .path("/")
                .maxAge(jwtCookieAccessExpiration)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .build();
    }

    public ResponseCookie genJwtCookie(UserDetailsImpl userDetailsImpl) {
        String jwt = genJwtFromUser(userDetailsImpl.getUsername());
        return genCookie(jwtCookieName, jwt);

    }

//    public ResponseCookie genJwtCookie2(UserDetailsImpl userDetailsImpl) {
//        String jwt = genJwtFromUser(userDetailsImpl.getUsername());
//        return genCookie(jwtCookie + "2", jwt);
//
//    }


//    Not My Algorithm

//    public String generateTokenFromUsername(String username) {
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
//                .signWith(SignatureAlgorithm.HS512, jwtSigningKey)
//                .compact();
//    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
        } catch (JwtException e) {
            // Log the error and throw a custom exception or return null
            logger.error("Invalid JWT token: {}", e.getMessage());
            throw new RuntimeException("Invalid JWT token");
        }
    }


    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }


//    Generate refresh token

    private ResponseCookie generateCookie(String name, String value) {

        return ResponseCookie.fromClientResponse(name, value)
                .path("/")
                .maxAge(jwtCookieRefreshExpiration)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .build();
    }

    public ResponseCookie generateJwtCookie(UserDetailsImpl userDetailsImpl) {
        String jwt = genJwtFromUser(userDetailsImpl.getUsername());
        return generateCookie(jwtCookieName, jwt);

    }


    public ResponseCookie generateJwtCookie(UserDetails userDetails) {
        String jwt = genJwtFromUser(userDetails.getUsername());
        return generateCookie(jwtCookieName, jwt);

    }

    public ResponseCookie generateRefreshJwtCookie(String refreshToken) {
        return generateCookie(jwtRefreshCookieName, refreshToken);
    }

    public String getJwtFromCookies(HttpServletRequest httpServletRequest) {
        return getCookieValueByName(httpServletRequest, jwtCookieName);
    }


    public String getJwtRefreshFromCookie(HttpServletRequest httpServletRequest) {
        return getCookieValueByName(httpServletRequest, jwtRefreshCookieName);
    }


    public Optional<String> getJwtRefreshFromCookieOptional(HttpServletRequest httpServletRequest) {
        return Optional.ofNullable(getCookieValueByName(httpServletRequest, jwtRefreshCookieName));
    }


    private String getCookieValueByName(HttpServletRequest httpServletRequest, String name) {
        Cookie cookie = WebUtils.getCookie(httpServletRequest, name);
        if (cookie != null) {
            return cookie.getValue();
        }

        return null;
    }


    public ResponseCookie getCleanJwtCookie() {
        return ResponseCookie.from(jwtCookieName, null).path("/").build();
    }

    public ResponseCookie getCleanJwtRefreshCookie() {
        return ResponseCookie.from(jwtRefreshCookieName, null).path("/").build();
    }
}
