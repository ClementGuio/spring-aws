package com.example.jppaws.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.jppaws.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
