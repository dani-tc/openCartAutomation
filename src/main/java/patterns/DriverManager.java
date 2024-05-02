package patterns;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.text.SimpleDateFormat;
import java.util.Date;

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
                    chromeOptions.addArguments("--disable-extensions");
                    chromeOptions.addArguments("--disable-popup-blocking");
                    chromeOptions.addArguments("--disable-notifications");
                    driver = new ChromeDriver(chromeOptions);
                    //Login as admin to unlock functionalities
                    Date todayChrome = new Date();
                    SimpleDateFormat formatChrome = new SimpleDateFormat("yyyyMMddHHmmss");
                    String formatedDateChrome = formatChrome.format(todayChrome);
                    driver.get("https://demo.opencart.com/");
                    driver.manage().addCookie(new Cookie("OCSESSID","11c0f931cf"+formatedDateChrome+"ec"));
                    driver.manage().addCookie(new Cookie("_ga","GA1.1.2123778129.1713796835"));
                    driver.manage().addCookie(new Cookie("_ga_X8G0BRFSDF","GS1.1.1713796835.1.0.1713796835.0.0.0"));
                    driver.manage().addCookie(new Cookie("_gcl_au","1.1.534898992.1713796834"));
                    driver.manage().addCookie(new Cookie("_gid","GA1.2.438931849.1713796835"));
                    driver.manage().addCookie(new Cookie("cf_clearance","zJ9wxfXGd6JiMI3czkXFs4.kzRi6IqvPGPR1BaphLjM-1713852454-1.0.1.1-XKiVE5CVgEaZJ6pwxaPFZvAbzObkzBLWVzgfCCZoPHgWbHPgp6V.HROlod2Rr0jRzg2O5vNoDLVqbRP0JC8Gnw"));
                    driver.manage().addCookie(new Cookie("currency","USD"));
                    break;
                case FIREFOX:
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    driver = new FirefoxDriver(firefoxOptions);
                    driver.manage().window().maximize();
                    //Login as admin to unlock functionalities
                    Date todayFirefox = new Date();
                    SimpleDateFormat formatFirefox = new SimpleDateFormat("yyyyMMddHHmmss");
                    String formatedDateFirefox = formatFirefox.format(todayFirefox);
                    driver.get("https://demo.opencart.com/");
                    driver.manage().addCookie(new Cookie("OCSESSID","11c0f931cf"+formatedDateFirefox+"ec"));
                    driver.manage().addCookie(new Cookie("_ga","GA1.1.2123778129.1713796835"));
                    driver.manage().addCookie(new Cookie("_ga_X8G0BRFSDF","GS1.1.1713796835.1.0.1713796835.0.0.0"));
                    driver.manage().addCookie(new Cookie("_gcl_au","1.1.534898992.1713796834"));
                    driver.manage().addCookie(new Cookie("_gid","GA1.2.438931849.1713796835"));
                    driver.manage().addCookie(new Cookie("cf_clearance","zJ9wxfXGd6JiMI3czkXFs4.kzRi6IqvPGPR1BaphLjM-1713852454-1.0.1.1-XKiVE5CVgEaZJ6pwxaPFZvAbzObkzBLWVzgfCCZoPHgWbHPgp6V.HROlod2Rr0jRzg2O5vNoDLVqbRP0JC8Gnw"));
                    driver.manage().addCookie(new Cookie("currency","USD"));
                    break;
                case EDGE:
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("start-maximized -disable-notifications -disable-infobars");
                    driver = new EdgeDriver(edgeOptions);
                    //Login as admin to unlock functionalities
                    Date todayEdge = new Date();
                    SimpleDateFormat formatEdge = new SimpleDateFormat("yyyyMMddHHmmss");
                    String formatedDateEdge = formatEdge.format(todayEdge);
                    driver.get("https://demo.opencart.com/");
                    driver.manage().addCookie(new Cookie("OCSESSID","11c0f931cf"+formatedDateEdge+"ec"));
                    driver.manage().addCookie(new Cookie("_ga","GA1.1.2123778129.1713796835"));
                    driver.manage().addCookie(new Cookie("_ga_X8G0BRFSDF","GS1.1.1713796835.1.0.1713796835.0.0.0"));
                    driver.manage().addCookie(new Cookie("_gcl_au","1.1.534898992.1713796834"));
                    driver.manage().addCookie(new Cookie("_gid","GA1.2.438931849.1713796835"));
                    driver.manage().addCookie(new Cookie("cf_clearance","zJ9wxfXGd6JiMI3czkXFs4.kzRi6IqvPGPR1BaphLjM-1713852454-1.0.1.1-XKiVE5CVgEaZJ6pwxaPFZvAbzObkzBLWVzgfCCZoPHgWbHPgp6V.HROlod2Rr0jRzg2O5vNoDLVqbRP0JC8Gnw"));
                    driver.manage().addCookie(new Cookie("currency","USD"));
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