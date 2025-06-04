package com.alen.mcsv_websocket.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.List;
import java.util.function.Function;

@Component
public class JwtUtil {
    public static final String SECRET_KEY= "NTFjNWJlNDM4ZWFhYWUwNzE4NmZlMDQzNQ==NTFjNWJlNDM4ZWFhYWUwNzE4NmZlMDQzNQ==";
    private Claims getAllClaimsFromJwt(final String token){
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    private SecretKey getKey(){
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private <T> T getClaim(Function<Claims,T> claimProvider, String token){
        Claims claims = getAllClaimsFromJwt(token);
        return claimProvider.apply(claims);
    }
    //.-----------------------------------------------------YOU CAN DO IT
    //public ones
    public List<String> getRoles(String token){
        return getClaim(claims -> claims.get("roles", List.class),token);
    }
    public String getSubject(String token){
        return getClaim(Claims::getSubject,token);
    }
}
