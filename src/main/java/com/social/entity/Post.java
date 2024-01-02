package com.social.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post implements Serializable {

    @Id
    @UuidGenerator
    private String id;

    @Lob
    @Column(columnDefinition="LONGBLOB")
    private byte[] immagine;

    private String contenuto;

    @Column(name = "data_di_creazione", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataDiCreazione;

    @JsonIgnore
    @ManyToOne
    private User user;



}
