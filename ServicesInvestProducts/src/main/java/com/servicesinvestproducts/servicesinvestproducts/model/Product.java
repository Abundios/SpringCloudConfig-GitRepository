package com.servicesinvestproducts.servicesinvestproducts.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "product", schema="public")
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceProduct")
    @SequenceGenerator(name = "SequenceProduct", sequenceName = "SequenceProduct",
            allocationSize = 1,initialValue=1)
    private int id;

    @Column(name = "isin")
    private String isin;

    @Column(name = "currentvalue")
    private float currentValue;

    @Column(name = "link")
    private String link;

    @Column(name = "description")
    private String description;

    @Column(name = "ter")
    private float ter;

    //https://stackoverflow.com/questions/46893664/failed-to-extract-resultset#:~:text=You%20can%27t%20convert%20a%20bidirectional%20relation%20of%20an,setUdsaconnex%20%28Udsaconnex%20udsaconnex%29%20%7B%20this.udsaconnex%20%3D%20udsaconnex%3B%20%7D
    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserProduct> userProduct = new HashSet<>();


    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public float getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(float currentValue) {
        this.currentValue = currentValue;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String url) {
        this.link = link;
    }

}
