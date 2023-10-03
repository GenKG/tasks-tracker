package ru.task.tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.task.tracker.DAO.UserRepository;
import ru.task.tracker.model.User;

import java.util.Collections;

/**
 * Класс авторизирует пользователя в системе
 */
@Service
public class UserDetail implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetail(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not exists by Username");
        }
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), Collections.emptyList());
    }
}
