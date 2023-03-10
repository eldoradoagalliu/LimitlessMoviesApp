package com.limitlessmoviesapp.repositories;

import com.limitlessmoviesapp.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();
    User findByIdIs(Long id);
    User findByEmail(String email);
}