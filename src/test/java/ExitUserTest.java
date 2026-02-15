import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pageobject.LoginPage;
import pageobject.MainPage;
import pageobject.ProfilePage;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertTrue;

// Выход из аккаунта.

public class ExitUserTest extends BaseTest {

    int random = ThreadLocalRandom.current().nextInt(100,100_000);
    private final String email = "test" + random + "@yandex.ru";
    private final String name = "Ivan";
    private final String password = "te4567";

    private final User apiHelper = new User();

    private MainPage mainPage;
    private LoginPage loginPage;
    private ProfilePage profilePage;

    @Before
    public void setUpPage() {
        apiHelper.createUser(email, password, name);
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);

        // Вход
        mainPage.open();
        mainPage.clickLoginButton();
        loginPage.login(email, password);
    }

    @After
    public void deleteUser() {
        apiHelper.deleteUser(email, password);
    }

    @Test
    @DisplayName("Проверить выход по кнопке «Выйти» в личном кабинете.")
    @Description("Проверка выхода из аккаунта через Личный кабинет")
    public void userCanLogoutSuccessfullyTest() {
        mainPage.clickPersonalAccount();
        profilePage.clickLogoutButton();

        // Проверка, что переход снова на странице логина
        assertTrue(driver.findElement(loginPage.getLoginHeader()).isDisplayed());
    }
}