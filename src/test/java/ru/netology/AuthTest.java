package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.DataGenerator.Registration.getRandomUser;
import static ru.netology.DataGenerator.getRandomLogin;
import static ru.netology.DataGenerator.getRandomPassword;
import org.junit.jupiter.api.BeforeEach;

public class AuthTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful registration")
    void shouldSuccessfulRegistration() {
        var registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button ").click();
        $$("h2").findBy(Condition.text("Личный кабинет")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should blocked user")
    void shouldBlockedUser() {
        var blockedUser = getRegisteredUser ("blocked");
        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $("button ").click();
        $$("[data-test-id='error-notification'] .notification__content")
                .findBy(Condition.text("Пользователь заблокирован")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should unregistered user")
    void shouldUnregisteredUser() {
        var unregisteredUser = getRandomUser("active");
        $("[data-test-id='login'] input").setValue(unregisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(unregisteredUser.getPassword());
        $("button ").click();
        $$("[data-test-id='error-notification'] .notification__content")
                .findBy(Condition.text("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should incorrect login")
    void shouldIncorrectLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[data-test-id='login'] input").setValue(wrongLogin);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("button ").click();
        $$("[data-test-id='error-notification'] .notification__content")
                .findBy(Condition.text("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Should incorrect password")
    void shouldIncorrectPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $("button ").click();
        $$("[data-test-id='error-notification'] .notification__content")
                .findBy(Condition.text("Неверно указан логин или пароль")).shouldBe(Condition.visible);
    }
}
