import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageobject.MainPage;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

//Раздел «Конструктор», переходы к разделам булки, соусы, начинка

public class ConstructorTabTest extends BaseTest {

    private MainPage mainPage;

    @Before
    public void setUpPage() {
        mainPage = new MainPage(driver);
        mainPage.open();
    }

    @Test
    @DisplayName("Переход к разделу Соусы")
    @Description("Проверка, что при клике на 'Соусы' открывается соответствующая вкладка")
    public void userCanSwitchToSauceTabTest() {
        mainPage.clickSauceTab();
        assertEquals("Соусы", mainPage.getActiveTabText());
    }

    @Test
    @DisplayName("Переход к разделу Начики")
    @Description("Проверка, что при клике на 'Начинки' открывается соответствующая вкладка")
    public void userCanSwitchToFillingTabTest() {
        mainPage.clickFillingTab();
        assertEquals("Начинки", mainPage.getActiveTabText());
    }

    @Test
    @DisplayName("Переход к разделу Булки")
    @Description("Проверка, что при клике на 'Булки' открывается соответствующая вкладка")
    public void userCanSwitchToBunTabTest() {
        mainPage.clickSauceTab();
        mainPage.clickBunTab();

        // Добавляем небольшую задержку
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.textToBePresentInElementLocated(
                        By.xpath("//div[contains(@class, 'tab_tab_type_current')]//span"),
                        "Булки"));

        assertEquals("Булки", mainPage.getActiveTabText());
    }
}