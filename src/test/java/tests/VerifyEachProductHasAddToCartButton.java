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
import patterns.pageobject.CamerasPage;
import patterns.pageobject.HomePage;
import patterns.pageobject.PhonesPage;
import reports.ReportMethods;
import utilities.Utils;
import java.time.Duration;

public class VerifyEachProductHasAddToCartButton {

    private WebDriver driver = null;
    ReportMethods report = new ReportMethods();

    @BeforeTest
    @Parameters({"browser"})
    public void beforeTest(String browser){
        driver = DriverManager.getDriver(DriverManager.BrowserType.valueOf(browser));
        String browserName = driver.getClass().getSimpleName();
        report.setupReport(browserName,"VerifyEachProductHasAddToCartButton.html",
                "Verify each product has add to cart button",
                "Verify each product has a add to cart button");
    }

    @Test
    public void eclat_274() {
        manageCaptcha();

        driver.navigate().refresh();
        manageCaptcha();

        HomePage home = new HomePage(driver);
        PhonesPage phonesPage = new PhonesPage(driver);
        CamerasPage camerasPage = new CamerasPage(driver);

        WebElement iphone = home.getAddToCartButtonIphone();
        WebElement cinema = home.getAddToCartButtonCinema();
        WebElement canon = home.getAddToCartButtonCanon();
        WebElement macbook = home.getAddToCartButtonMacbook();

        new Actions(driver)
                .moveToElement(macbook)
                .perform();

        Assert.assertTrue(macbook.isEnabled());
        Assert.assertTrue(iphone.isEnabled());
        Assert.assertTrue(cinema.isEnabled());
        Assert.assertTrue(canon.isEnabled());

        WebElement phones = home.getPhonesCategory();
        new Actions(driver)
                .moveToElement(phones)
                .perform();
        phones.click();
        driver.navigate().refresh();
        manageCaptcha();

        WebElement iphoneButton = phonesPage.getAddToCartButtonIphone();
        WebElement HtcButton = phonesPage.getAddToCartButtonHtc();
        WebElement PalmButton = phonesPage.getAddToCartButtonPalm();

        new Actions(driver)
                .moveToElement(HtcButton)
                .perform();

        Assert.assertTrue(iphoneButton.isEnabled());
        Assert.assertTrue(HtcButton.isEnabled());
        Assert.assertTrue(PalmButton.isEnabled());

        WebElement cameras = phonesPage.getCamerasCategory();
        new Actions(driver)
                .moveToElement(cameras)
                .perform();
        cameras.click();
        driver.navigate().refresh();
        manageCaptcha();

        WebElement canonButton = camerasPage.getAddToCartButtonCanon();
        WebElement nykonButton = camerasPage.getAddToCartButtonNykon();

        new Actions(driver)
                .moveToElement(canonButton)
                .perform();

        Assert.assertTrue(canonButton.isEnabled());
        Assert.assertTrue(nykonButton.isEnabled());

        Utils.takeSnapShot(driver, "src/resources/VerifyEachProductHasAddToCartButton/eclat_274.png");
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
        report.writeReport();
        DriverManager.quitDriver();
    }
}
