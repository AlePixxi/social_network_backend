package com.social.service;

import com.social.dto.RegistrazioneUserDTO;
import com.social.exception.UtenteEsistenteException;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface RegistrazioneService {

    public ResponseEntity<Map<String, String>> registraUtente(RegistrazioneUserDTO userDTO) throws UtenteEsistenteException;


    }
