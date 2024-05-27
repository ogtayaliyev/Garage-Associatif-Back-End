package com.example.garage2.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class ModelDTO {
    private int id;
    private String modelNom;
    private LocalDate miseCirculation;
    private String moteureType;
    private int moteurePuissance;


}
