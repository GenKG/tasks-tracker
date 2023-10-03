package ru.task.tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.task.tracker.DTO.TaskDTO;
import ru.task.tracker.exceptions.TaskNotFoundException;
import ru.task.tracker.config.PathConstants;
import ru.task.tracker.service.TaskService;

import java.util.List;
import java.util.UUID;

@RestController
public class TaskController {
    private final TaskService tasksService;

    @Autowired
    public TaskController(TaskService tasksService) {
        this.tasksService = tasksService;
    }

    /**
     * Получить все задачи указанного пользователя
     * @param login логин пользователя по которому необходима задача
     * @return Список задач закрепленных за пользователем
     * @throws TaskNotFoundException ошибка если такой пользователь не найден
     */
    @GetMapping(value = PathConstants.V_1 + PathConstants.TASKS + "/{userLogin}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TaskDTO>> getTasks(@PathVariable("userLogin") String login) throws TaskNotFoundException {
        List<TaskDTO> allTasksForUser = tasksService.getAllTasksForUser(login);
        return ResponseEntity
                .ok()
                .body(allTasksForUser);
    }

    /**
     * Создает задачу и присваивает ей уникальный номер
     * @param tasksDto информация о задаче
     * @return уникальный код созданной задачи
     */
    @PostMapping(value = PathConstants.V_1 + PathConstants.TASKS + "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createTasks(@RequestBody TaskDTO tasksDto) {
        UUID taskCode = tasksService.createTask(tasksDto);
        return ResponseEntity
                .ok()
                .body(taskCode.toString());
    }

    /**
     * Редактирует уже созданную задачу. Для изменения необходимо передать уникальный код задачи
     * @param taskCode уникальный код задачи для которой будет осуществленно редактирование
     * @param updateTask обновленная задача
     * @return результат обновления
     * @throws TaskNotFoundException ошибка если задача с таким кодом не найдена
     */
    @PutMapping(value = PathConstants.V_1 + PathConstants.TASKS + "/{taskCode}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateTask(@PathVariable("taskCode") UUID taskCode, @RequestBody TaskDTO updateTask) throws TaskNotFoundException {
        tasksService.updateTask(taskCode, updateTask);
        return ResponseEntity
                .ok()
                .body("task updated");
    }

    /**
     * Удаляет задачу по коду.
     * @param taskCode Уникальный код задачи, которую необходимо удалить
     * @return возвращает результат удаления
     */
    @DeleteMapping(value = PathConstants.V_1 + PathConstants.TASKS + "/{taskCode}")
    public ResponseEntity<String> deleteTasks(@PathVariable("taskCode") UUID taskCode) {
        tasksService.deleteTaskByCode(taskCode);
        return ResponseEntity
                .ok()
                .body(String.format("task with code %s was deleted", taskCode.toString()));
    }

    /**
     * Обработка кастомной ошибки при которой задача не найдена
     * @param exception кастомное исключение
     * @return возвращает статус 404 и сообщение об ошибке
     */
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<String> handleException(TaskNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)

                .body(exception.getMessage());
    }
}
