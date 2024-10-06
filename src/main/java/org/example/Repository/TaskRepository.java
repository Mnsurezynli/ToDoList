package org.example.Repository;

import org.example.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface TaskRepository extends JpaRepository<Task,Long> {

    Optional<Task> findById();


}
