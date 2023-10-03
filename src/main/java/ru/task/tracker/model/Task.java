package ru.task.tracker.model;


import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Task {
    @Id
    @SequenceGenerator(name = "tasks_id_seq", sequenceName = "tasks_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tasks_id_seq")
    private Long id;

    private UUID code;

    private String name;

    private String responsibleUser;

    private String status;

    public Task(Long id, UUID code, String name, String responsibleUser, String status) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.responsibleUser = responsibleUser;
        this.status = status;
    }

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getCode() {
        return code;
    }

    public void setCode(UUID code) {
        this.code = code;
    }

    public String getResponsibleUser() {
        return responsibleUser;
    }

    public void setResponsibleUser(String responsibleUser) {
        this.responsibleUser = responsibleUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(code, task.code) && Objects.equals(name, task.name) && Objects.equals(responsibleUser, task.responsibleUser) && Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, responsibleUser, status);
    }
}




