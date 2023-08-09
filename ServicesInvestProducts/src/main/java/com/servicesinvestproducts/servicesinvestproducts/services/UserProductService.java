package com.servicesinvestproducts.servicesinvestproducts.services;

import com.servicesinvestproducts.servicesinvestproducts.model.UserProduct;
import com.servicesinvestproducts.servicesinvestproducts.repos.UserProductRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class UserProductService
{
    private final UserProductRepository userProductRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public UserProductService(final UserProductRepository userProductRepository)
    {
        this.userProductRepository = userProductRepository;
    }

    public List<UserProduct> findAll() {
        return userProductRepository.findAll();
    }


    public List<Object[]> findProductByuid(String uid)
    {
        List<Object[]> userProductDetail = userProductRepository.findProductByuid(uid);
        return userProductDetail;
    }

    public Integer create(final UserProduct product)
    {
        return userProductRepository.save(product).getId();
    }

    public void update(final Integer id, final UserProduct product) {
        final UserProduct existingReservation = userProductRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        userProductRepository.save(product);
    }

    public void delete(final Integer id) {
        userProductRepository.deleteById(id);
    }

}
