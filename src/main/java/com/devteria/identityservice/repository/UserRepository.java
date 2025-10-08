package com.devteria.identityservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devteria.identityservice.entity.UsersEntity;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity, String> {
    boolean existsByUsername(String username);

    Optional<UsersEntity> findByUsername(String username);
}
