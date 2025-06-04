package com.alen.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtService {
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    public String generateToken(UserDetails user){
        return getToken(user);
    }
    private String getToken(UserDetails user){
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("roles",
                        user.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)// Convert authorities to strings
                                .collect(Collectors.toList()))// Collect them into a list
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private SecretKey getKey(){
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private Claims getAllClaimsFromJwt(String token){
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    private <T> T getClaim(Function<Claims,T> claimProvider, String token){
        Claims claims = getAllClaimsFromJwt(token);
        return claimProvider.apply(claims);
    }
    //public ones
    public String getSubject(String token){
        return getClaim(Claims::getSubject,token);
    }
    public Date getExpiration(String token){
        return getClaim(Claims::getExpiration,token);
    }
    public Boolean isTokenExpired(String token){return getExpiration(token).before(new Date());}

    public Boolean isTokenValid(UserDetails userDetails, String token){
        String user=userDetails.getUsername();
        return !isTokenExpired(token) && user.equals(getSubject(token));//If token is not expired and the userDetails matches the user token->true
    }

}