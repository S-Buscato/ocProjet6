package com.paymybuddy.paymybuddy.repository;

import com.paymybuddy.paymybuddy.models.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<Users, Long> {

    @Query(" select u from Users u where u.email = :email")
    Optional<Users> findByEmail(String email);
}
