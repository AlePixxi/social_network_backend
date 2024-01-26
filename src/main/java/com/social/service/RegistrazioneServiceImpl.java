package com.social.service;

import com.social.dto.RegistrazioneUserDTO;
import com.social.entity.User;
import com.social.exception.UtenteEsistenteException;
import com.social.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RegistrazioneServiceImpl implements RegistrazioneService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    @Override
    public ResponseEntity<Map<String, String>> registraUtente(RegistrazioneUserDTO userDTO) throws UtenteEsistenteException {
        Map<String, String> response = new HashMap<>();
        User utenteDaSalvare = new User();

        try {
            if (repository.findById(userDTO.getEmail()).isPresent()) {
                throw new UtenteEsistenteException("Dipendente esistente");
            }

            utenteDaSalvare.setEmail(userDTO.getEmail());
            utenteDaSalvare.setPassword(passwordEncoder.encode(userDTO.getPassword()));

            utenteDaSalvare.setNome(userDTO.getNome());
            utenteDaSalvare.setCognome(userDTO.getCognome());
            utenteDaSalvare.setDataDiNascita(userDTO.getDataDiNascita());
            utenteDaSalvare.setNazionalita(userDTO.getNazionalita());

            repository.save(utenteDaSalvare);

            response.put("messaggio", "Registrato correttamente");
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();

            response.put("messaggio", "Qualcosa è andato storto");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
