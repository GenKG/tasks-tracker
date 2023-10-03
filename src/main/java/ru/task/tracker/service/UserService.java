package ru.task.tracker.service;

import org.springframework.stereotype.Service;
import ru.task.tracker.DTO.UserDTO;
import ru.task.tracker.exceptions.UserAlreadyExistException;
import ru.task.tracker.model.User;

@Service
public interface UserService {
    User registerNewUserAccount(UserDTO userDTO) throws UserAlreadyExistException;
}
