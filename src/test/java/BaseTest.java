import org.junit.Before;
import org.junit.After;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.File;
import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;

    @Before
    public void setUp() {
        String browser = System.getProperty("browser", "chrome");
        System.out.println("Запуск браузера: " + browser);

        if (browser.equalsIgnoreCase("yandex")) {
            String yandexPath = "C:\\Users\\lisa9\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe";

            File yandexFile = new File(yandexPath);
            if (!yandexFile.exists()) {
                throw new RuntimeException("Яндекс браузер не найден по пути: " + yandexPath);
            }

            // Используем рабочую версию ChromeDriver
            WebDriverManager.chromedriver()
                    .driverVersion("142.0.7444.0")
                    .setup();

            ChromeOptions options = new ChromeOptions();
            options.setBinary(yandexFile);

            // ВАЖНЫЕ ОПЦИИ для стабильности
            options.addArguments("--remote-allow-origins=*");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--start-maximized"); // Вместо driver.manage().window().maximize()

            // Отключаем автоматические закрытия
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

            // Дополнительные опции для стабильности
            options.addArguments("--disable-gpu");
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-infobars");

            driver = new ChromeDriver(options);
            System.out.println("Яндекс.Браузер успешно запущен");

        } else {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }

        // Устанавливаем неявные ожидания
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Увеличил до 10 секунд
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        // НЕ вызываем maximize здесь, если уже использовали --start-maximized
        if (!browser.equalsIgnoreCase("yandex")) {
            driver.manage().window().maximize();
        }
    }

    @After
    public void tearDown() {
        if (driver != null) {
            try {
                // Небольшая задержка перед закрытием
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.quit();
            System.out.println("Браузер закрыт");
        }
    }
}
