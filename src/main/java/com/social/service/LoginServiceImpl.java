package com.social.service;

import com.social.config.JwtService;
import com.social.dto.UserDTO;
import com.social.entity.User;
import com.social.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    public Map<String, String> login(UserDTO request) {

        Map<String, String> response = new HashMap<>();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User utente = repository.findById(request.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(utente);

        response.put("token", jwtToken);

        return response;
    }
}
