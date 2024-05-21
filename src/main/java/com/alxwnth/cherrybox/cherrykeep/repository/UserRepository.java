package com.alxwnth.cherrybox.cherrykeep.repository;

import com.alxwnth.cherrybox.cherrykeep.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
