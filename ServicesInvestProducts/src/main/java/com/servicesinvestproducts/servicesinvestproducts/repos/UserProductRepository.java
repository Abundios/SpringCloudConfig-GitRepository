package com.servicesinvestproducts.servicesinvestproducts.repos;

import com.servicesinvestproducts.servicesinvestproducts.model.UserProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface UserProductRepository  extends JpaRepository<UserProduct, Integer>
{
    @Query("SELECT up.product, up FROM UserProduct up WHERE up.user.uid = :uid")
    List<Object[]> findProductByuid(@Param("uid") String uid);

}