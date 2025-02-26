package com.example.spring_relationships_project.LibUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibUserRepository
    extends JpaRepository<LibUser, Long> {
  Optional<LibUser> findUserByEmail(String email);
}
