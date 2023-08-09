package com.servicesinvestproducts.servicesinvestproducts.model;



import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "userproduct", schema="public")
public class UserProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceUserProduct")
    @SequenceGenerator(name = "SequenceUserProduct", sequenceName = "SequenceUserProduct",
            allocationSize = 1,initialValue=1)
    private int id;

    @Column(name = "investedmount")
    private float investedMount;

    @Column(name = "productvalue")
    private float productValue;

    @Column(name = "participations")
    private float participations;


    public UserProduct(Integer id, float investedMount, float productValue) {
        this.id = id;
        this.investedMount = investedMount;
        this.productValue = productValue;
    }

    //https://stackoverflow.com/questions/46893664/failed-to-extract-resultset#:~:text=You%20can%27t%20convert%20a%20bidirectional%20relation%20of%20an,setUdsaconnex%20%28Udsaconnex%20udsaconnex%29%20%7B%20this.udsaconnex%20%3D%20udsaconnex%3B%20%7D
    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "productid", nullable = false)
    private Product product;

    //https://stackoverflow.com/questions/46893664/failed-to-extract-resultset#:~:text=You%20can%27t%20convert%20a%20bidirectional%20relation%20of%20an,setUdsaconnex%20%28Udsaconnex%20udsaconnex%29%20%7B%20this.udsaconnex%20%3D%20udsaconnex%3B%20%7D
    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "userid", nullable = false)
    private User user;


}
