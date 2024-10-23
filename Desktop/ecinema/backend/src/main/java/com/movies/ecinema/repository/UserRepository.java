package com.movies.ecinema.repository;

import com.movies.ecinema.entity.User;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //Optional<User> findByEmail(String email);
    //List<User> findUserByRole(Role role);
}
