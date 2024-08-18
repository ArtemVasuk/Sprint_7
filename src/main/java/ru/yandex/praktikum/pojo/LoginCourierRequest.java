package ru.yandex.praktikum.pojo;

public class LoginCourierRequest {
    private String login;
    private String password;

    private LoginCourierRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static LoginCourierRequest from(CreateCourierRequest createCourierRequest) {
        return new LoginCourierRequest(createCourierRequest.getLogin(), createCourierRequest.getPassword());
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
}
