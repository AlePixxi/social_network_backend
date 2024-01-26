package com.social.controller;

import com.social.dto.RegistrazioneUserDTO;
import com.social.dto.UserDTO;
import com.social.exception.UtenteEsistenteException;
import com.social.service.LoginService;
import com.social.service.RegistrazioneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("rest/api/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

    private final LoginService loginService;
    private final RegistrazioneService registrazioneService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginuser(@Valid @RequestBody UserDTO userDTO) {
        return loginService.login(userDTO);
    }

    @PostMapping("/nuovo-utente")
    public ResponseEntity<Map<String, String>> registrazioneUser(@Valid @RequestBody RegistrazioneUserDTO user)
            throws UtenteEsistenteException {

        return registrazioneService.registraUtente(user);
    }

}
