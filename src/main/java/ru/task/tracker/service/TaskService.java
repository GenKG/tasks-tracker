package ru.task.tracker.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.task.tracker.DAO.TaskRepository;
import ru.task.tracker.DTO.TaskDTO;
import ru.task.tracker.DTO.mapper.TaskDtoToEntity;
import ru.task.tracker.exceptions.TaskNotFoundException;
import ru.task.tracker.model.Task;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Сервис занимается менеджментом задач пользователя
 */
@Service
public class TaskService {
    private static final Logger log = LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Получает всю информацию из бд по задачам указанного пользователя
     *
     * @param userLogin логин пользователя для которого необходимо найти задачи
     * @return список задач
     * @throws TaskNotFoundException если задач для такого пользователя не найдено то возвращает исключение
     */
    @Transactional
    public List<TaskDTO> getAllTasksForUser(String userLogin) throws TaskNotFoundException {
        List<Task> tasks = taskRepository.findByResponsibleUser(userLogin).orElseThrow(() -> new TaskNotFoundException(String.format("Task with user %s not found", userLogin)));
        return tasks.stream()
                .map(TaskDtoToEntity.INSTANCE::EntityToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Создает задачу назначив ответственного пользователя
     *
     * @param task информация о задаче
     * @return возврает уникальный код созданной задачи
     */
    @Transactional
    public UUID createTask(TaskDTO task) {
        Task taskEntity = TaskDtoToEntity.INSTANCE.taskDtoToEntity(task);
        Task savedTask = taskRepository.save(taskEntity);
        log.info("task for user {}  with id {} was saved. Task code: {}", savedTask.getResponsibleUser(), savedTask.getId(), savedTask.getCode());
        return savedTask.getCode();
    }

    /**
     * Обновляет задачу по коду
     *
     * @param taskCode   код задачи для которой необходимо обновить информацию
     * @param updateTask информация о задаче(обновленная)
     * @throws TaskNotFoundException исключение если такой задачи не найдено
     */
    @Transactional
    public void updateTask(UUID taskCode, TaskDTO updateTask) throws TaskNotFoundException {
        Task oldTask = taskRepository.findByCode(taskCode).orElseThrow(() -> new TaskNotFoundException(String.format("Task with code %s not found", taskCode)));
        Task newTask = TaskDtoToEntity.INSTANCE.taskDtoToEntity(updateTask);
        Task modifyTask = TaskDtoToEntity.INSTANCE.updateExistingTask(oldTask, newTask);
        taskRepository.save(modifyTask);
        log.info("task by code {} was update", taskCode);
    }

    /**
     * Удалить задачу по коду
     *
     * @param taskCode код задачи для удалаения
     */
    @Transactional
    public void deleteTaskByCode(UUID taskCode) {
        taskRepository.deleteByCode(taskCode);
        log.info("task by code {} was deleted", taskCode);
    }
}
