package ru.task.tracker.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.task.tracker.DAO.UserRepository;
import ru.task.tracker.DTO.UserDTO;
import ru.task.tracker.exceptions.UserAlreadyExistException;
import ru.task.tracker.model.User;

import javax.transaction.Transactional;

/**
 * Сервис занимается менеджментом пользователя решившего воспользоваться tasks-service
 */
@Service
@Transactional
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DefaultUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Регистрирует нового пользователя в системе и сохраняет его данные в бд.
     * Пароль пользователя хешируется через bcrypt шифрование
     *
     * @param userDto данные о пользователе
     * @return возвращает результат сохранения
     * @throws UserAlreadyExistException исключение если такой пользователь уже есть в системе
     */
    @Override
    public User registerNewUserAccount(UserDTO userDto) throws UserAlreadyExistException {
        if (emailExists(userDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: "
                    + userDto.getEmail());
        }
        if (loginExists(userDto.getLogin())) {
            throw new UserAlreadyExistException("There is an account with that login: " + userDto.getLogin());
        }

        User user = new User();
        user.setLogin(userDto.getLogin());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());

        return userRepository.save(user);
    }

    /**
     * Проверяет что такой логин уже есть в системе
     *
     * @param login логин пользователя
     * @return true - пользователь найден, false - пользователь отсуствует
     */
    private boolean loginExists(String login) {
        return userRepository.findByLogin(login) != null;
    }

    /**
     * Проверяет что такая почта уже есть в системе
     *
     * @param email почта пользователя
     * @return true - пользователь найден, false - пользователь отсуствует
     */
    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
