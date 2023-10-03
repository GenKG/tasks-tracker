package ru.task.tracker.DTO;

public enum StatusDTO {
    NEW("Новая"),
    IN_WORK("В работе"),
    COMPLETE("Завершена");

    private final String description;

    StatusDTO(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
