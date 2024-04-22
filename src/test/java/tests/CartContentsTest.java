package tests;

import patterns.DriverManager;
import patterns.DriverManager.BrowserType;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import patterns.pageobject.*;
//import patterns.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import org.testng.Assert;

import java.time.Duration;

public class CartContentsTest {

    private WebDriver driver;

    @BeforeTest
    public void beforeTest(){

        driver = DriverManager.getDriver(BrowserType.CHROME);

        driver.get("https://demo.opencart.com/admin/");
        WebElement inputName = driver.findElement(By.id("input-username"));
        inputName.sendKeys("demo");
        WebElement inputPassword = driver.findElement(By.id("input-password"));
        inputPassword.sendKeys("demo");
        WebElement login = driver.findElement(By.cssSelector(".btn"));
        login.click();

    }

    @Test
    public void verifyContentsTest() throws FindFailed {
        driver.get("https://demo.opencart.com/");
        shoppingCartPage cartPage = new shoppingCartPage(driver);

        Screen screen = new Screen();
        Pattern image = new Pattern("C:\\Users\\sanrocha\\Documents\\Project\\openCartAutomation\\src\\resources\\cloudflare.png");

        screen.wait(image, 10);
        screen.click(image);

        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logo")));

        WebElement cart = driver.findElement(By.cssSelector("a[title=\"Shopping Cart\"]"));
        cart.sendKeys(Keys.ENTER);

        screen.wait(image, 5);
        screen.click(image);

        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#content h1")));
        Assert.assertEquals(cartPage.getCartPageTitleText(),"Shopping Cart");
    }

    @AfterTest
    public void afterTest(){
        DriverManager.quitDriver();
    }
}
