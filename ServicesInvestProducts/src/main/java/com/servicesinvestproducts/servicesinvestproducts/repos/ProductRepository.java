package com.servicesinvestproducts.servicesinvestproducts.repos;

import com.servicesinvestproducts.servicesinvestproducts.model.Product;
//import org.springframework.data.repository.Repository;
import com.servicesinvestproducts.servicesinvestproducts.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository  extends JpaRepository<Product, Integer>
{
    Product findByIsin(String isin);
    Product findByDescription(String description);

    Product findById(int id);

}

