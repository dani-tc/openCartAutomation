package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import patterns.DriverManager;

import patterns.pageobject.*;
import reports.ReportMethods;
import utilities.Utils;

import java.time.Duration;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VerifyUserCanAddProductsToShoppingCart {

    private WebDriver driver = null;
    ReportMethods report = new ReportMethods();

    @BeforeTest
    public void beforeTest(){
        driver = DriverManager.getDriver(DriverManager.BrowserType.EDGE); // replace with your desired browser
        String browserName = driver.getClass().getSimpleName();
        report.setupReport(browserName,"VerifyUserCanAddProductsToShoppingCart.html","Verify users can add products to the shopping cart", "Verify that users can add products to the shopping cart when clicking on the add to cart button.");

    }

    @Test
    public void eclat_273(){
        manageCaptcha();

        driver.navigate().refresh();
        manageCaptcha();

        ShoppingCartPage header = new ShoppingCartPage(driver);
        MonitorsPage monitorsPage = new MonitorsPage(driver);
        WebElement components = header.getComponents();
        WebElement monitors = header.getMonitors();
        WebElement addToCartButton = monitorsPage.getAddToCartButton();
        WebElement productTitle = monitorsPage.getProductTitle();
        WebElement closeButton = header.getCloseButton();
        WebElement cartLink = header.getShoppingCartLink();
        WebElement productName = header.getProductName();

        new Actions(driver)                     //hover to components menu on header
                .moveToElement(components)
                .perform();

        monitors.click();

        manageCaptcha();

        new Actions(driver)
                .moveToElement(addToCartButton)
                .perform();
        addToCartButton.click();              //Simulate click on "add to cart" button

        String title = productTitle.getText();             //let's save the name of product for later assertions

        //let's wait until label advertisement appears on upper-right corner
        String closeButtonSelector = closeButton.toString().split(": ")[2].replace("'","");
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(8));
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(closeButtonSelector)));

        closeButton.click();

        new Actions(driver)
                .moveToElement(cartLink)
                .perform();
        cartLink.click();

        manageCaptcha();

        Assert.assertEquals(title,productName.getAttribute("title"));

        Utils.takeSnapShot(driver, "src/resources/VerifyUserCanAddProductsToShoppingCart/eclat_273.png");
    }

    public void manageCaptcha(){
        boolean pass = true;
        String title = null;
        do {
            try {
                sleep(2000);
                title = driver.getTitle();
                if(title.contains("moment")) {
                    Screen screen = new Screen();
                    String pathYourSystem = System.getProperty("user.dir") + "\\";
                    Pattern image = new Pattern(pathYourSystem + "src\\resources\\cloudflare.png");

                    screen.wait(image, 5);
                    screen.click(image);
                    try {
                        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
                        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logo")));
                    } catch (TimeoutException e) {
                    }
                }
                else{
                    pass = false;
                }

            } catch (FindFailed e) {
                //pass = false;
            }
        }while(pass);
    }

    public void sleep(int minSecond){
        try {
            Thread.sleep(minSecond);
        } catch (InterruptedException e) {
        }
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        report.afterMethodReport(result);
    }

    @AfterTest
    public void afterTest(){
        // Writing everything to report
        report.writeReport();
        DriverManager.quitDriver();
    }
}
