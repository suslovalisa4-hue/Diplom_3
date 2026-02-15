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

// Переход из личного кабинета в конструктор.

public class ConstructorTest extends BaseTest {

    int random = ThreadLocalRandom.current().nextInt(100,100_000);
    private final String email = "test" + random + "@yandex.ru";
    private final String name = "Trata";
    private final String password = "test456";

    private final User apiHelper = new User();

    private MainPage mainPage;
    private LoginPage loginPage;
    private ProfilePage profilePage;

    @Before // переход в профиль
    public void setUpPage() {
        apiHelper.createUser(email, password, name);
        mainPage = new MainPage(driver);
        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);

        mainPage.open();
        mainPage.clickLoginButton();
        loginPage.login(email, password);

        mainPage.clickPersonalAccount();
    }

    @After
    public void deleteUser() {
        apiHelper.deleteUser(email, password);
    }

    @Test
    @DisplayName("Проверь переход по клику на «Конструктор»")
    @Description("Проверка перехода в конструктор по кнопке 'Конструктор' в личном кабинете")
    public void userCanNavigateToConstructorViaButtonTest() {
        profilePage.clickConstructorButton();
        assertTrue("Не отобразилась кнопка 'Оформить заказ'",
                mainPage.isOrderButtonVisible());
    }

    @Test
    @DisplayName("Проверка перехода в конструктор по клику на логотип 'Stellar Burgers'")
    @Description("Проверка перехода в конструктор по клику на логотип 'Stellar Burgers' в личном кабинете")
    public void userCanNavigateToConstructorViaLogoTest() {
        profilePage.clickLogo();
        assertTrue("Не отобразилась кнопка 'Оформить заказ'",
                mainPage.isOrderButtonVisible());
    }
}
