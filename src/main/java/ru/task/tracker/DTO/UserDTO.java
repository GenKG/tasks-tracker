package ru.task.tracker.DTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class UserDTO {
    @NotBlank
    @NotEmpty
    private String login;
    @NotBlank
    @NotEmpty
    private String password;
    @NotBlank
    @NotEmpty
    private String email;

    public UserDTO() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
