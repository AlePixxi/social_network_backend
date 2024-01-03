package com.social.service;

import com.social.config.JwtService;
import com.social.dto.UserDTO;
import com.social.entity.User;
import com.social.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{

    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<Map<String, String>> login(UserDTO request) {

        Map<String, String> response = new HashMap<>();

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            User utente = repository.findById(request.getEmail()).orElseThrow();
            String jwtToken = jwtService.generateToken(utente);

            response.put("token", jwtToken);
            return ResponseEntity.ok(response);


        } catch (AuthenticationException e) {
            response.put("errore", "login non effettuato");
            return ResponseEntity.badRequest().body(response);
        }

    }
}
