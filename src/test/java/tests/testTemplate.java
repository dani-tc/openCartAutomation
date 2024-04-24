package tests;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import utilities.*;
import patterns.pageobject.*;
//import patterns.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;
import org.testng.Assert;

import java.time.Duration;

public class testTemplate {
    private WebDriver driver = null;

    @BeforeTest
    public void beforeTest(){
        //driver = DriverManager.getDriver(DriverManager.BrowserType.EDGE); // replace with your desired browser
        //Login as admin to unlock functionalities
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        driver = new ChromeDriver(options);
        driver.get("https://demo.opencart.com/admin/");
        WebElement inputName = driver.findElement(By.id("input-username"));
        inputName.sendKeys("demo");
        WebElement inputPassword = driver.findElement(By.id("input-password"));
        inputPassword.sendKeys("demo");
        WebElement login = driver.findElement(By.cssSelector(".btn"));
        login.click();

    }

    @Test
    public void test1() {
        driver.get("https://demo.opencart.com/");
        ShoppingCartPage cartPage = new ShoppingCartPage(driver);
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(60));
        explicitWait.until(ExpectedConditions.elementToBeClickable(cartPage.getNavShoppingCart()));
        cartPage.clickNavShoppingCart();
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        //new WebDriverWait(driver, Duration.ofSeconds(120)).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));
        //new WebDriverWait(driver, Duration.ofSeconds(120)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='challenge-stage']/div/label/input"))).click();
        //WebElement captcha = driver.findElement(By.xpath("/html/body/div/div/div[1]/div/label/input"));
        //captcha.click();
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(60));
        explicitWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".float-end .btn")));
        Utils.takeSnapShot(driver, "src/resources/test1.png");
        WebElement button = driver.findElement(By.cssSelector(".float-end .btn"));
        Assert.assertEquals(button.getText(),"Continue");
    }

    @Test
    public void test2() throws FindFailed {
        driver.get("https://demo.opencart.com/");
        ShoppingCartPage cartPage = new ShoppingCartPage(driver);

        Screen screen = new Screen();
        String pathYourSystem = System.getProperty("user.dir") + "\\";
        Pattern image = new Pattern(pathYourSystem+"src\\resources\\cloudflare.png");

        screen.wait(image, 5);
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
        driver.quit();
        //DriverManager.quitDriver();
    }
}

