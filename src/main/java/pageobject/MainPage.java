package pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage {

    private final WebDriver driver;

    private static final String URL = "https://stellarburgers.education-services.ru/";

    // Локаторы
    private final By LOGIN_BTN = By.xpath("//button[text()='Войти в аккаунт']");
    private final By PERSONAL_ACCOUNT_BTN = By.xpath("//p[text()='Личный Кабинет']");
    private final By ORDER_BTN = By.xpath("//button[text()='Оформить заказ']");
    private final By BUN_TAB = By.xpath(".//div[span[text()='Булки']]");
    private final By SAUSE_TAB = By.xpath(".//div[span[text()='Соусы']]");
    private final By FILLING_TAB = By.xpath(".//div[span[text()='Начинки']]");
    private final By ACTIVE_TAB = By.xpath("//div[contains(@class,'tab_tab_type_current')]");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Открыть главную страницу")
    public void open() {
        driver.get(URL);
    }

    @Step("Нажать кнопку 'Войти в аккаунт'")
    public void clickLoginButton() {
        driver.findElement(LOGIN_BTN).click();
    }

    @Step("Нажать на 'Личный кабинет'")
    public void clickPersonalAccount() {
        driver.findElement(PERSONAL_ACCOUNT_BTN).click();
    }

    @Step("Проверка: отображается кнопка 'Оформить заказ'")
    public boolean isOrderButtonVisible() {
        return driver.findElement(ORDER_BTN).isDisplayed();
    }

    @Step("Клик на вкладку 'Булки'")
    public void clickBunTab() {
        driver.findElement(BUN_TAB).click();
    }

    @Step("Клик на вкладку 'Соусы'")
    public void clickSauceTab() {
        driver.findElement(SAUSE_TAB).click();
    }

    @Step("Клик на вкладку 'Начинки'")
    public void clickFillingTab() {
        driver.findElement(FILLING_TAB).click();
    }

    @Step("Получить текст активной вкладки")
    public String getActiveTabText() {
        return driver.findElement(ACTIVE_TAB).getText();
    }
}
