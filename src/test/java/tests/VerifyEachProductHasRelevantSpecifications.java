package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

public class VerifyEachProductHasRelevantSpecifications {

    private WebDriver driver = null;
    ReportMethods report = new ReportMethods();

    @BeforeTest
    @Parameters({"browser"})
    public void beforeTest(String browser){
        driver = DriverManager.getDriver(DriverManager.BrowserType.valueOf(browser));
        String browserName = driver.getClass().getSimpleName();
        report.setupReport(browserName,"VerifyEachProductHasRelevantSpecifications.html",
                "Verify each product description has relevant specifications",
                "Verify that each product has a description with relevant specifications");
    }

    @Test
    public void eclat_267() {
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

        WebElement specificationsSection = product.getSpecificationsInDescription();
        Assert.assertEquals(specificationsSection.getText(),"Technical specifications");

        Utils.takeSnapShot(driver, "src/resources/VerifyEachProductHasRelevantSpecifications/eclat_267.png");
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
