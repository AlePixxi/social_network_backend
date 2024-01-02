package com.social.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Classe che si occupa di definire dei metodi utilizzati per
 * effettuare operazioni sull JWT(token).
 */
@Service
public class JwtService {

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
    public <T>T extractClaim(String token , Function <Claims,T> claimsResolver){
        final Claims  claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Metodo che richiama il metodo generateToken, ma passandogli
     * una mappa per i Claims extra vuota.
     * Questo metodo viene utilizzato, appunto, se non ci sono Claimsd extra
     * da aggiungere al token.
     *
     * @param userDetails Dettagli dell'utente
     * @return
     */

    public String generateToken( UserDetails userDetails) {
        return generateToken(new HashMap <>(), userDetails);
    }
    /**
     * Metodo che prendendo una mappa di Claims aggiuntivi e
     * i dettagli dell'utente (username), genera un token JWT.
     *
     * @param //extraClaims Claims aggiuntivi pe ril token
     * @param userDetails Dettagli dell'utente per prendere l'username
     * @return String che rappresenta il token
     */

    public String generateToken(Map<String , Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date (System.currentTimeMillis() +1000*60*60*24*7))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
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
     * Metodo che controlla se il token è valido.
     * Prima estrai il nome utente dal token e lo confronta con
     * l'UserDetail dell utente nel database. Inoltre controlla anche
     * che il token non sia scaduto tramite il metodo isTokenExpired().
     * @param token da controllare
     * @param userDetails da confrontare con il subject del token
     * @return boolean
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    /**
     * Metodo che controlla che il token non sia scaduto.
     * Per fare ciò v erifica che la data di scadenza del token sia
     * antecedente alla data odierna.
     * @param token da controllare
     * @return boolean
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    /**
     * Metodo che ritorna la data di scadenza del token.
     * @param token da cui ricavare la data
     * @return data
     */
    private Date extractExpiration(String token) {
        // TODO Auto-generated method stub
        return extractClaim(token, Claims::getExpiration);
    }
    /**
     * metodo che si occupa di generare una chiave di firma
     * tramite algorimo hmac.
     * @return Key
     *
     **/
    private Key getSignInKey() {
        // prende la chiave segreta e la decodifica ritornando un array di byte
        String SECRET_KEY = "dc6748dbc046a939ee02ba4511b04f76fe796e8f537e60fec4e36b22a08e929f";
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        // tramite l'array di byte genera una Key con codifica hmac
        return Keys.hmacShaKeyFor(keyBytes);
    }

}