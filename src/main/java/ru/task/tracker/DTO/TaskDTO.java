package ru.task.tracker.DTO;

import java.util.UUID;

public class TaskDTO {
    private String name;
    private UUID code;
    private StatusDTO status;
    private String responsibleUser;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }

    public String getResponsibleUser() {
        return responsibleUser;
    }

    public void setResponsibleUser(String responsibleUser) {
        this.responsibleUser = responsibleUser;
    }

    public UUID getCode() {
        return code;
    }

    public void setCode(UUID code) {
        this.code = code;
    }
}
