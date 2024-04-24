package tests;

import patterns.DriverManager;
import patterns.DriverManager.BrowserType;
import utilities.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;

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
import org.testng.Assert;

import java.time.Duration;

public class CartItemsContentsTest {

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

                WebElement cart = driver.findElement(By.cssSelector("a[title=\"Shopping Cart\"]"));
                cart.sendKeys(Keys.ENTER);

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

    @Test(priority = 1, description = "Verify product table exists and has a product")
    public void cartProductTableTest() {
        cartPage = new ShoppingCartPage(driver);

        Assert.assertTrue(cartPage.getContentsTable().getText().contains("Image"));
        Assert.assertTrue(cartPage.getContentsTable().getText().contains("Product Name"));
        Assert.assertTrue(cartPage.getContentsTable().getText().contains("Model"));
        Assert.assertTrue(cartPage.getContentsTable().getText().contains("Quantity"));
        Assert.assertTrue(cartPage.getContentsTable().getText().contains("Unit Price"));
        Assert.assertTrue(cartPage.getContentsTable().getText().contains("Total"));

        Assert.assertTrue(cartPage.getContentsTable().getText().contains("Sub-Total"));
        Assert.assertTrue(cartPage.getContentsTable().getText().contains("Eco Tax"));
        Assert.assertTrue(cartPage.getContentsTable().getText().contains("VAT"));

        Utils.takeSnapShot(driver, "src/resources/cartItemsContentsTest/1-checkingCart.png");

    }

    @Test(priority = 2, description = "Verify product image is visible in the cart")
    public void productImageTest() {
        cartPage = new ShoppingCartPage(driver);

        Assert.assertTrue(cartPage.getImageLink().isDisplayed());

        Utils.takeSnapShot(driver, "src/resources/cartItemsContentsTest/2-checkingProductImage.png");

    }

    @Test(priority = 3, description = "Verify product name is visible in the cart")
    public void productNameTest() {
        cartPage = new ShoppingCartPage(driver);

        Assert.assertTrue(cartPage.getProductName().isEnabled());

        Utils.takeSnapShot(driver, "src/resources/cartItemsContentsTest/3-checkingProductName.png");

    }

    @Test(priority = 3, description = "Verify product name is visible in the cart")
    public void productModelTest() {
        cartPage = new ShoppingCartPage(driver);

        Assert.assertTrue(cartPage.getProductModel().isDisplayed());
        Assert.assertEquals(cartPage.getProductModel().getText(), "SAM1");

        Utils.takeSnapShot(driver, "src/resources/cartItemsContentsTest/4-checkingProductModel.png");

    }

    @Test(priority = 5, description = "Verify product quantity is visible in the cart")
    public void productQuantityTest() {
        cartPage = new ShoppingCartPage(driver);

        Assert.assertTrue(cartPage.getUpdateButton().isDisplayed());
        Assert.assertTrue(cartPage.getRemoveButton().isEnabled());
        Assert.assertTrue(cartPage.getProductQuantity().isDisplayed());
        Assert.assertEquals(cartPage.getProductQuantity().getAttribute("value"), "1");

        Utils.takeSnapShot(driver, "src/resources/cartItemsContentsTest/5-checkingProductQuantity.png");

    }

    @AfterTest
    public void afterTest() {
        DriverManager.quitDriver();
    }
}
