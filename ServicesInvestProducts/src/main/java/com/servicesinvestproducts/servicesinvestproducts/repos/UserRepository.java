package com.servicesinvestproducts.servicesinvestproducts.repos;

import com.servicesinvestproducts.servicesinvestproducts.model.User;
//import org.springframework.data.repository.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Integer>
{
    User findByForename(String forename);

    User findByUid(String uid);

}
