package com.investproducts.appinvestproducts.model;

import lombok.*;



@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product
{
    private int id;
    private String isin;
    private float currentValue;
    private String link;
    private String description;
    private float ter;
}
