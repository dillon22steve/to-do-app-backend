package com.to_do.backend.repository;

import java.util.List;
import com.to_do.backend.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {

    List<ToDo> findByUserUsername(String username);

    List<ToDo> findByUserId(Long userId);

}
