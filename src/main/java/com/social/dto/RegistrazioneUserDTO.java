package com.social.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrazioneUserDTO {

    private String email;
    private String password;
    private String nome;
    private String cognome;
    private Date dataDiNascita;
    private String nazionalita;

}
