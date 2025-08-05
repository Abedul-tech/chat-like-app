package com.alen.mcsv_websocket.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.io.Decoders;
import javax.crypto.SecretKey;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private SecretKey getKey(){
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private Claims getAllClaimsFromJwt(String token){
        try{
            return Jwts
                    .parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException e){
            //Here we can handle an expired, invalid token or whatever
            System.err.println("Invalid JWT: " + e.getMessage());
            throw new RuntimeException("JWT validation failed", e);
        }
    }
    //claimProvider is a function we can execute within the Claims Object defined in the method above.
    private <T> T getClaim(Function<Claims,T> claimProvider, String token){
        Claims claims = getAllClaimsFromJwt(token);
        return claimProvider.apply(claims);
    }
    //public ones
    public String getSubject(String token){
        return getClaim(Claims::getSubject,token);
    }
    public String getUserId(String token){ return getClaim(claims -> claims.get("userId",String.class),token);}
    public List<GrantedAuthority> getRoles(String token){
        List<String> roles = getClaim(claims->claims.get("roles",List.class), token);
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
