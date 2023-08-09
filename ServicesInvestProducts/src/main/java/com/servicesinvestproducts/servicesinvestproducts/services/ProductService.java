package com.servicesinvestproducts.servicesinvestproducts.services;

import com.servicesinvestproducts.servicesinvestproducts.model.Product;
import com.servicesinvestproducts.servicesinvestproducts.model.User;
import com.servicesinvestproducts.servicesinvestproducts.repos.ProductRepository;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService
{
    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository)
    {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findByIsin(String isin) {
        return productRepository.findByIsin(isin);
    }

    public Product findByDescription(String description) {
        return productRepository.findByDescription(description);
    }

    public Integer create(final Product product)
    {
        return productRepository.save(product).getId();
    }

    public void update(final Integer id, final Product product) {
        final Product existingReservation = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        productRepository.save(product);
    }

    public void update(final Product product)
    {
        productRepository.save(product);
    }
    public void delete(Product product)
    {
        productRepository.delete(product);
    }

}
