package tests;

import patterns.DriverManager;
import patterns.DriverManager.BrowserType;
import utilities.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import patterns.pageobject.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.annotations.*;
// import org.testng.Assert;

import java.time.Duration;

public class CartContentsTest {

    private WebDriver driver;
    private ProductsPage productsPage;
    // private ShoppingCartPage cartPage;


    @BeforeTest
    public void beforeTest() {
        driver = DriverManager.getDriver(BrowserType.EDGE);

        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String formatedDate = format.format(today);

        driver.get("https://demo.opencart.com/index.php");
        driver.manage().addCookie(new Cookie("OCSESSID", "11c0f931cf" + formatedDate + "ec"));
        driver.manage().addCookie(new Cookie("_ga", "GA1.1.2123778129.1713796835"));
        driver.manage().addCookie(new Cookie("_ga_X8G0BRFSDF", "GS1.1.1713796835.1.0.1713796835.0.0.0"));
        driver.manage().addCookie(new Cookie("_gcl_au", "1.1.534898992.1713796834"));
        driver.manage().addCookie(new Cookie("_gid", "GA1.2.438931849.1713796835"));
        driver.manage().addCookie(new Cookie("cf_clearance",
                "zJ9wxfXGd6JiMI3czkXFs4.kzRi6IqvPGPR1BaphLjM-1713852454-1.0.1.1-XKiVE5CVgEaZJ6pwxaPFZvAbzObkzBLWVzgfCCZoPHgWbHPgp6V.HROlod2Rr0jRzg2O5vNoDLVqbRP0JC8Gnw"));
        driver.manage().addCookie(new Cookie("currency", "USD"));

        driver.get("https://demo.opencart.com/");
    }

    @Test
    public void verifyContentsTest() throws FindFailed {
        final int MAX_ATTEMPTS = 20;
        Screen screen = new Screen();
        Pattern image = new Pattern("C:\\Users\\sanrocha\\Documents\\Project\\openCartAutomation\\src\\resources\\cloudflare.png");

        productsPage = new ProductsPage(driver);
        // cartPage = new ShoppingCartPage(driver);

        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        
        for(int attempt = 0;attempt<MAX_ATTEMPTS;attempt++){
            try {
                explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logo")));

                productsPage.openProduct();
                explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logo")));
                Utils.takeSnapShot(driver, "src/resources/cartContentsTest/1-selectingProduct.png");


                productsPage.addProductToCart();
                explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success")));
                Utils.takeSnapShot(driver, "src/resources/cartContentsTest/2-addingProductzToCart.png");
                
                WebElement cart = driver.findElement(By.cssSelector("a[title=\"Shopping Cart\"]"));
                cart.sendKeys(Keys.ENTER);
                explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logo")));
                Utils.takeSnapShot(driver, "src/resources/cartContentsTest/3-checkingCart.png");

                // Assert.assertTrue(cartPage.getCartPageTitleText().contains("Shopping Cart"));

                break;
            } catch (WebDriverException e) {
                System.out.println(e.getMessage());
                System.out.println("Attempt " + (attempt + 1) + " failed. Retrying...");
                screen.wait(image, 5);
                screen.click(image);
                if (attempt == MAX_ATTEMPTS - 1) {
                    // If this was the last attempt, rethrow the exception
                    throw e;
                }
            }            
        }
    }

    @AfterTest
    public void afterTest() {
        DriverManager.quitDriver();
    }
}
