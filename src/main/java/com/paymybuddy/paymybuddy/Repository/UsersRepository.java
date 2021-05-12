package com.paymybuddy.paymybuddy.Repository;

import com.paymybuddy.paymybuddy.Models.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends CrudRepository<Users, Long> {

}
