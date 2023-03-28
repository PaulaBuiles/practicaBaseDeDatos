package org.example.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class Product {
    Long id;
    String name;
    Double price;
    LocalDate date_register;

    Categorie categorie;

}
