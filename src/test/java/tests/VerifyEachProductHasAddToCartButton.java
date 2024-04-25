package tests;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import patterns.DriverManager;
import patterns.pageobject.CamerasPage;
import patterns.pageobject.HomePage;
import patterns.pageobject.PhonesPage;
import utilities.Utils;


import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class VerifyEachProductHasAddToCartButton {

    private WebDriver driver = null;

    @BeforeTest
    public void beforeTest(){
        driver = DriverManager.getDriver(DriverManager.BrowserType.EDGE); // replace with your desired browser

        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String formatedDate = format.format(today);

        driver.get("https://demo.opencart.com/index.php");
        driver.manage().addCookie(new Cookie("OCSESSID","11c0f931cf"+formatedDate+"ec"));
        driver.manage().addCookie(new Cookie("_ga","GA1.1.2123778129.1713796835"));
        driver.manage().addCookie(new Cookie("_ga_X8G0BRFSDF","GS1.1.1713796835.1.0.1713796835.0.0.0"));
        driver.manage().addCookie(new Cookie("_gcl_au","1.1.534898992.1713796834"));
        driver.manage().addCookie(new Cookie("_gid","GA1.2.438931849.1713796835"));
        driver.manage().addCookie(new Cookie("cf_clearance","zJ9wxfXGd6JiMI3czkXFs4.kzRi6IqvPGPR1BaphLjM-1713852454-1.0.1.1-XKiVE5CVgEaZJ6pwxaPFZvAbzObkzBLWVzgfCCZoPHgWbHPgp6V.HROlod2Rr0jRzg2O5vNoDLVqbRP0JC8Gnw"));
        driver.manage().addCookie(new Cookie("currency","USD"));
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

    @AfterTest
    public void afterTest(){
        DriverManager.quitDriver();
    }
}
