package ru.netology.testmode.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.testmode.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.testmode.data.DataGenerator.Registration.getUser;
import static ru.netology.testmode.data.DataGenerator.getRandomLogin;
import static ru.netology.testmode.data.DataGenerator.getRandomPassword;

class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = getRegisteredUser("active");
        $x("//input[@class='input__control'] [@name='login']").setValue(registeredUser.getLogin());
        $x("//input[@class='input__control'] [@name='password']").setValue(registeredUser.getPassword());
        $x("//button[@data-test-id='action-login']").click();
        $x("//div[@id='root']//h2").shouldHave(Condition.exactText("  Личный кабинет"));
        // TODO: добавить логику теста, в рамках которого будет выполнена попытка входа в личный кабинет с учётными
        //  данными зарегистрированного активного пользователя, для заполнения полей формы используйте
        //  пользователя registeredUser
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = getUser("active");
        $x("//input[@class='input__control'] [@name='login']").setValue(notRegisteredUser.getLogin());
        $x("//input[@class='input__control'] [@name='password']")
                .setValue(notRegisteredUser.getPassword());
        $x("//button[@data-test-id='action-login']").click();
        $x("//div[@class='notification__content']")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"));
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет
        //  незарегистрированного пользователя, для заполнения полей формы используйте пользователя notRegisteredUser
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = getRegisteredUser("blocked");
        $x("//input[@class='input__control'] [@name='login']").setValue(blockedUser.getLogin());
        $x("//input[@class='input__control'] [@name='password']")
                .setValue(blockedUser.getPassword());
        $x("//button[@data-test-id='action-login']").click();
        $x("//div[@class='notification__content']")
                .shouldHave(Condition.exactText("Ошибка! Пользователь заблокирован"));
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет,
        //  заблокированного пользователя, для заполнения полей формы используйте пользователя blockedUser
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $x("//input[@class='input__control'] [@name='login']").setValue(wrongLogin);
        $x("//input[@class='input__control'] [@name='password']")
                .setValue(registeredUser.getPassword());
        $x("//button[@data-test-id='action-login']").click();
        $x("//div[@class='notification__content']")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"));
        System.out.println();
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  логином, для заполнения поля формы "Логин" используйте переменную wrongLogin,
        //  "Пароль" - пользователя registeredUser
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $x("//input[@class='input__control'] [@name='login']").setValue(registeredUser.getLogin());
        $x("//input[@class='input__control'] [@name='password']")
                .setValue(wrongPassword);
        $x("//button[@data-test-id='action-login']").click();
        $x("//div[@class='notification__content']")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"));
        // TODO: добавить логику теста в рамках которого будет выполнена попытка входа в личный кабинет с неверным
        //  паролем, для заполнения поля формы "Логин" используйте пользователя registeredUser,
        //  "Пароль" - переменную wrongPassword
    }
}
