package ru.task.tracker.DTO.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import ru.task.tracker.DTO.StatusDTO;
import ru.task.tracker.DTO.TaskDTO;
import ru.task.tracker.model.Task;

import java.util.UUID;

/**
 * Маппинг дто сущности задач на entity.
 * Используется библиотека mapStruct
 */
@Mapper
public interface TaskDtoToEntity {
    TaskDtoToEntity INSTANCE = Mappers.getMapper(TaskDtoToEntity.class);

    @Mapping(target = "code", expression = "java(generateCode())")
    @Mapping(target = "status", source = "status.description")
    Task taskDtoToEntity(TaskDTO taskDto);

    @Mapping(target = "id", ignore = true)
    Task updateExistingTask(@MappingTarget Task oldTask, Task newTask);

    @Mapping(target = "status", expression = "java(convertStatus(task.getStatus()))")
    TaskDTO EntityToDTO(Task task);

    default StatusDTO convertStatus(String dbStatus) {
        StatusDTO status = null;
        switch (dbStatus) {
            case "Новая":
                status = StatusDTO.NEW;
                break;
            case "В работе":
                status = StatusDTO.IN_WORK;
                break;
            case "Завершена":
                status = StatusDTO.COMPLETE;
                break;
        }
        return status;
    }

    default UUID generateCode() {
        return UUID.randomUUID();
    }
}
