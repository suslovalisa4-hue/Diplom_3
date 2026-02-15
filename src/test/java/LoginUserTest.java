import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pageobject.LoginPage;
import pageobject.MainPage;
import pageobject.RegistrationPage;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertTrue;

// Тесты на Вход

public class LoginUserTest extends BaseTest {

    private final User apiHelper = new User();
    private MainPage mainPage;
    private LoginPage loginPage;
    int random = ThreadLocalRandom.current().nextInt(100,100_000);
    private final String email = "test" + random + "@yandex.ru";
    private final String name = "Petr";
    private final String password = "tes456";
    @Before
    public void setUpPage() {
        apiHelper.createUser(email, password, name);
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
    }

    @After
    public void deleteUser() {
        apiHelper.deleteUser(email, password);
    }

    @Test
    @DisplayName("Вход в ЛК по кнопке «Войти в аккаунт» на главной странице")
    @Description("Проверка входа через кнопку 'Войти в аккаунт' на главной странице")
    public void loginMainPageTest() {
        mainPage.open();
        mainPage.clickLoginButton();
        loginPage.login(email, password);
        assertTrue(mainPage.isOrderButtonVisible());
    }

    @Test
    @DisplayName("Вход в ЛК через кнопку «Личный кабинет»")
    @Description("Проверка входа через кнопку 'Личный кабинет' на главной странице")
    public void loginPersonalAccountTest() {
        mainPage.open();
        mainPage.clickPersonalAccount();
        loginPage.login(email, password);
        assertTrue(mainPage.isOrderButtonVisible());
    }

    @Test
    @DisplayName("Вход в ЛК через кнопку в форме регистрации")
    @Description("Проверка входа по ссылке из формы регистрации")
    public void loginRegistrationFormTest() {
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.open();
        registrationPage.clickLoginLink();
        loginPage.login(email, password);
        assertTrue(mainPage.isOrderButtonVisible());
    }

    @Test
    @DisplayName("Вход в ЛК через кнопку в форме восстановления пароля")
    @Description("Проверка входа по ссылке из формы восстановления пароля")
    public void loginForgotPasswordTest() {
        loginPage.openForgotPasswordPage();
        loginPage.clickLoginFromForgotPassword();
        loginPage.login(email, password);
        assertTrue(mainPage.isOrderButtonVisible());
    }
}
