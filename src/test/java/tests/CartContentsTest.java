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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import patterns.pageobject.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.annotations.*;
import org.testng.Assert;

import java.time.Duration;

public class CartContentsTest {

    private WebDriver driver;
    private ProductsPage productsPage;
    private ShoppingCartPage cartPage;

    @BeforeTest
    public void beforeTest() {
        driver = DriverManager.getDriver(BrowserType.EDGE);
        productsPage = new ProductsPage(driver);

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

        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(5));

        final int MAX_ATTEMPTS = 20;
        Screen screen = new Screen();
        Pattern image = new Pattern(
                "C:\\Users\\sanrocha\\Documents\\Project\\openCartAutomation\\src\\resources\\cloudflare.png");

        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            try {
                driver.get("https://demo.opencart.com/");
                explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logo")));
                productsPage.openProduct();

                explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logo")));
                productsPage.addProductToCart();

                break;
            } catch (WebDriverException e) {

                System.out.println(e.getMessage());
                System.out.println("Attempt " + (attempt + 1) + " failed. Retrying...");
                try {
                    screen.wait(image, 5);
                    screen.click(image);
                } catch (FindFailed e1) {
                    Assert.fail();
                }

                if (attempt == MAX_ATTEMPTS - 1) {
                    // If this was the last attempt, rethrow the exception
                    throw e;
                }

            }
        }
    }

    @Test(priority = 1, description = "Verify cart page elements")
    public void verifyCartPageElements() {
        cartPage = new ShoppingCartPage(driver);

        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.get("https://demo.opencart.com/");
        WebElement cart = driver.findElement(By.cssSelector("a[title=\"Shopping Cart\"]"));
        cart.sendKeys(Keys.ENTER);
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logo")));
        Utils.takeSnapShot(driver, "src/resources/cartContentsTest/1-checkingCart.png");

        Assert.assertTrue(cartPage.getCartPageTitle().isDisplayed());
        Assert.assertTrue(cartPage.getCartPageTitle().getText().contains("Shopping Cart"));

        Assert.assertTrue(cartPage.getContentsTable().isDisplayed());
        Assert.assertTrue(cartPage.getUpdateButton().isDisplayed());
        Assert.assertTrue(cartPage.getTotalTable().isDisplayed());

        Assert.assertTrue(cartPage.getShoppingCartSubtitle().isDisplayed());
        Assert.assertEquals(cartPage.getShoppingCartSubtitle().getText(), "What would you like to do next?");
    }

    @Test(priority = 2, description = "Verify cart information section")
    public void verifyCartInformationSection() {
        cartPage = new ShoppingCartPage(driver);

        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.get("https://demo.opencart.com/");
        WebElement cart = driver.findElement(By.cssSelector("a[title=\"Shopping Cart\"]"));
        cart.sendKeys(Keys.ENTER);
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logo")));

        new Actions(driver)
                .moveToElement(cartPage.getFooter())
                .perform();
        Utils.takeSnapShot(driver, "src/resources/cartContentsTest/2-checkingCartInformation.png");
        Assert.assertTrue(cartPage.getAccordion().isDisplayed());
    }

    @Test(priority = 3, description = "Verify estimate shipping functionality")
    public void verifyEstimateShippingFunctionality() {
        cartPage = new ShoppingCartPage(driver);

        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.get("https://demo.opencart.com/");
        WebElement cart = driver.findElement(By.cssSelector("a[title=\"Shopping Cart\"]"));
        cart.sendKeys(Keys.ENTER);
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logo")));

        new Actions(driver)
            .moveToElement(cartPage.getEstimateShippingButton())
            .click()
            .perform();
        explicitWait.until(ExpectedConditions.visibilityOf(cartPage.getPostCodeInput()));
        Utils.takeSnapShot(driver, "src/resources/cartContentsTest/3-checkingShippingAndTaxes.png");

        Assert.assertTrue(cartPage.getCountryInput().isDisplayed());
        Assert.assertTrue(cartPage.getCountryInput().getText().contains("Colombia"));

        Assert.assertTrue(cartPage.getZoneInput().isDisplayed());
        Assert.assertFalse(cartPage.getZoneInput().getText().contains("Bogota D.C."));

        Assert.assertTrue(cartPage.getPostCodeInput().isDisplayed());
        cartPage.getPostCodeInput().sendKeys("1234");
        cartPage.getPostCodeInput().click();
        cartPage.getPostCodeInput().sendKeys(Keys.ENTER);
        Assert.assertNotEquals(cartPage.getPostCodeInput().getText(), "1234");

        Utils.takeSnapShot(driver, "src/resources/cartContentsTest/4-checkingFilledShippingAndTaxes.png");
    }

    @AfterTest
    public void afterTest() {
        DriverManager.quitDriver();
    }
}
