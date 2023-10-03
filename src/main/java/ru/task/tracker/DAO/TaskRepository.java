package ru.task.tracker.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.task.tracker.model.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findByCode(UUID taskCode);

    void deleteByCode(UUID taskCode);

    Optional<List<Task>> findByResponsibleUser(String userName);
}
