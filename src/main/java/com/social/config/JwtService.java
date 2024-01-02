package com.social.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.function.Function;

@Service
public class JwtService {

    /**
     * metodo che si occupa di generare una chiave di firma
     * tramite algorimo hmac.
     * @return Key
     *
     **/
    private Key getSignInKey() {
        // prende la chiave segreta e la decodifica ritornando un array di byte
        String SECRET_KEY = "4c2c09ce8f58b6181661507c49554774a12ea3b83de16f590b407aca9579feba";
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        // tramite l'array di byte genera una Key con codifica hmac
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Metodo che prendendo in input un token JWT estrae
     * da quest'ultimo tutti i Claims contenuti nel corpo
     * del token.
     * @param token JWT
     * @return Claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts.
                parserBuilder().
                setSigningKey(getSignInKey()).
                build().
                parseClaimsJws(token).
                getBody();
    }

    /**
     * Metodo che si occupa di estrarre l'username (email)
     * dell'utente associato al token.
     * @param token
     * @return String: username dell'utente
     */
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * metodo che permette di estrarre un Claim di tipo generico
     * in base alle necessità dell applicazione.
     * @param token token JWT da cui estrarre i Claims
     * @param claimsResolver funzione da passare per specificare cosa e come estrarre dal token
     * @return Un Claim in base al tipo generico
     * @param <T>
     */
    public <T>T extractClaim(String token , Function<Claims,T> claimsResolver){
        final Claims  claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        return false;
    }
}
