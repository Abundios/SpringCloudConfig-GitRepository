package com.investproducts.appinvestproducts.model;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User
{
    private int id;
    private String name;
    private String forename;
    private String password;
}


