package tests;

import org.openqa.selenium.*;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import patterns.DriverManager;
import patterns.pageobject.HomePage;
import patterns.pageobject.ProductsPage;
import reports.ReportMethods;
import utilities.Utils;

import java.time.Duration;


public class VerifyEachDescriptionIsResponsiveDifferentScreenSizes {
    private WebDriver driver = null;
    ReportMethods report = new ReportMethods();

    @BeforeTest
    @Parameters({"browser"})
    public void beforeTest(String browser){
        driver = DriverManager.getDriver(DriverManager.BrowserType.valueOf(browser));
        String browserName = driver.getClass().getSimpleName();
        report.setupReport(browserName,"VerifyEachDescriptionIsResponsiveDifferentScreenSizes.html",
                "Verify each product has a description with responsive design",
                "Verify that each product has a description section which responsively changes to different " +
                        "screen sizes");
    }

    @Test
    public void eclat_269() {
        driver.manage().window().setSize(new Dimension(390,800));      //Simulate cellphone
        HomePage home = new HomePage(driver);
        ProductsPage product = new ProductsPage(driver);

        //Refresh and manage captcha
        manageCaptcha();
        driver.navigate().refresh();
        manageCaptcha();

        //Move to element and click on it
        WebElement appleCinemaImg = home.getImageAppleCinema();
        new Actions(driver)
                .moveToElement(appleCinemaImg)
                .perform();
        appleCinemaImg.click();
        manageCaptcha();

        //Move to element
        WebElement description = product.getDescriptionText();
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, arguments[0]-window.innerHeight/2);", description.getLocation().getY());

        //Assertions
        Assert.assertTrue(description.isEnabled());
        boolean smaller = description.getSize().getWidth() < 1000;             //Width of element must be smaller
        Assert.assertTrue(smaller);
        sleep(1500);
        Utils.takeSnapShot(driver, "src/resources/VerifyEachDescriptionIsResponsiveDifferentScreenSizes/eclat_269.png");


    }

    public void manageCaptcha(){
        boolean pass = true;
        String title = null;
        String pathYourSystem = System.getProperty("user.dir") + "\\";
        Pattern image = new Pattern(pathYourSystem + "src\\resources\\cloudflare.png");
        Screen screen = new Screen();
        do {
            try {
                sleep(2000);
                title = driver.getTitle();
                if(title.contains("moment")) {
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
