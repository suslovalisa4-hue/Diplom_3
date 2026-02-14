import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobject.LoginPage;
import pageobject.RegistrationPage;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertTrue;

//* Регистрация

public class RegistrationTest extends BaseTest {

    private RegistrationPage registrationPage;
    private LoginPage loginPage;
    private final User apiHelper = new User();
    int random = ThreadLocalRandom.current().nextInt(100,100_000);
    private final String email = "test" + random + "@yandex.ru";
    private final String name = "Petr";
    private final String password = "test3456";
    private boolean userWasCreated = false;

    @Before
    public void setUpPage() {
        registrationPage = new RegistrationPage(driver);
        loginPage = new LoginPage(driver);
        registrationPage.open();
    }

    @After
    public void deleteUser() {
        if (userWasCreated) {
            apiHelper.deleteUser(email, password);
        }
    }

    @Test
    @DisplayName("Успешная регистрастрация с валидными данными")
    @Description("Проверка успешной регистрации пользователя с валидными данными")
    public void RegisterSuccessfullyTest() {
        registrationPage.register(name, email, password);
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(loginPage.getLoginHeader()));

        userWasCreated = true;

        // проверка наличие текста заголовка 'Вход'
        assertTrue(driver.findElement(loginPage.getLoginHeader()).isDisplayed());
    }

    @Test
    @DisplayName("Неуспешная регистрация при некорректном пароле")
    @Description("Проверка ошибки при регистрации с паролем короче 6 символов")
    public void userCannotRegisterWithShortPasswordTest() {
        registrationPage.register(name, email, "12345");
        assertTrue(driver.getPageSource().contains("Некорректный пароль"));
    }
}
