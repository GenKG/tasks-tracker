package ru.task.tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.task.tracker.DTO.LoginDTO;
import ru.task.tracker.DTO.UserDTO;
import ru.task.tracker.config.PathConstants;
import ru.task.tracker.exceptions.UserAlreadyExistException;
import ru.task.tracker.service.UserService;

@RestController
public class AuthorizationController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthorizationController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Аутентифицирует пользователя
     *
     * @param loginDto учетные данные для авторизации
     * @return возвращает результат авторизации
     */
    @PostMapping(value = PathConstants.V_1 + PathConstants.AUTH + "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDTO loginDto) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return ResponseEntity
                .ok()
                .body("User login successfully!");
    }

    /**
     * Регистрирует нового пользователя. Выбрасывает оишбку, если такой пользователь (или email) уже существует
     *
     * @param userDTO учетные данные для нового пользователя
     * @return результат регистрации
     * @throws UserAlreadyExistException ошибка если такой пользователь уже есть
     */
    @PostMapping(value = PathConstants.V_1 + PathConstants.AUTH + "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> signIn(@Validated @RequestBody UserDTO userDTO) throws UserAlreadyExistException {
        userService.registerNewUserAccount(userDTO);
        return ResponseEntity
                .ok()
                .body("User create");
    }

    /**
     * Обработка исключения. Выдает ошибку и соответствующий ответ
     * @param exception если такой пользователь уже существует то вылетает исключение
     * @return ошибка 409 и сообщение
     */
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<String> handleException(UserAlreadyExistException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }
}
