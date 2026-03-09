package com.to_do.backend.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.to_do.backend.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Used to look up the user object during Task creation
    Optional<User> findByUsername(String username);
}
