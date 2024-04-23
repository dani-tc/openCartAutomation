package tests;

import patterns.DriverManager;
import patterns.DriverManager.BrowserType;
import utilities.Utils;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import patterns.pageobject.*;
//import patterns.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.testng.annotations.*;
import org.testng.Assert;

import java.time.Duration;

public class CartContentsTest {

    private WebDriver driver;

    @BeforeTest
    public void beforeTest(){

        driver = DriverManager.getDriver(BrowserType.EDGE);

        driver.get("https://demo.opencart.com/admin/");
        WebElement inputName = driver.findElement(By.id("input-username"));
        inputName.sendKeys("demo");
        WebElement inputPassword = driver.findElement(By.id("input-password"));
        inputPassword.sendKeys("demo");
        WebElement login = driver.findElement(By.cssSelector(".btn"));
        login.click();
        Utils.takeSnapShot(driver, "src/resources/cartContentsTest/1-loginSuccesfully.png");
        driver.get("https://demo.opencart.com/");
    }

    @Test
    public void verifyContentsTest() throws FindFailed {
        driver.get("https://demo.opencart.com/");
        ProductsPage productsPage = new ProductsPage(driver);
        ShoppingCartPage cartPage = new ShoppingCartPage(driver);

        Screen screen = new Screen();
        Pattern image = new Pattern("C:\\Users\\sanrocha\\Documents\\Project\\openCartAutomation\\src\\resources\\cloudflare.png");

        screen.wait(image, 10);
        screen.click(image);

        try{
            screen.wait(image, 10);
            screen.click(image);
        } catch (FindFailed e){
            System.out.println("Element not found");
        }

        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logo")));

        try{
            screen.wait(image, 10);
            screen.click(image);
        } catch (FindFailed e){
            System.out.println("Element not found");
        }

        WebElement item = driver.findElement(By.className("col-12"));
        item.click();
        Utils.takeSnapShot(driver, "src/resources/cartContentsTest/2-selectingProduct.png");

        try{
            screen.wait(image, 10);
            screen.click(image);
        } catch (FindFailed e){
            System.out.println("Element not found");
        }

        WebElement addToCart = driver.findElement(By.id("button-cart"));
        addToCart.click();
        try{
            screen.wait(image, 10);
            screen.click(image);
        } catch (FindFailed e){
            System.out.println("Element not found");
        }
        Utils.takeSnapShot(driver, "src/resources/cartContentsTest/3-addingProductToCart.png");

        WebElement cart = driver.findElement(By.cssSelector("a[title=\"Shopping Cart\"]"));
        cart.sendKeys(Keys.ENTER);
        Utils.takeSnapShot(driver, "src/resources/cartContentsTest/4-checkingCart.png");
        
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#content h1")));
        Assert.assertTrue(cartPage.getCartPageTitleText().contains("Shopping Cart"));
    }

    @AfterTest
    public void afterTest(){
        DriverManager.quitDriver();
    }
}
