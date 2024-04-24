package patterns;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverManager {
    private static WebDriver driver;

    public enum BrowserType {
        CHROME,
        FIREFOX,
        EDGE
    }

    public DriverManager() {}

    public static WebDriver getDriver(BrowserType type) {
        if (driver == null) {
            switch (type) {
                case CHROME:
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("start-maximized");
                    driver = new ChromeDriver(chromeOptions);
                    break;
                case FIREFOX:
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("start-maximized");
                    driver = new FirefoxDriver(firefoxOptions);
                    break;
                case EDGE:
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("start-maximized");
                    driver = new EdgeDriver(edgeOptions);
                    break;
            }
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}