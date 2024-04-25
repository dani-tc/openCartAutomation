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
import patterns.pageobject.HomePage;
import patterns.pageobject.shoppingCartPage;
import utilities.Utils;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class VerifyOptionModifyQuantityForItemInCartPage {

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
    public void eclat_303() {
        manageCaptcha();

        shoppingCartPage header = new shoppingCartPage(driver);
        HomePage home = new HomePage(driver);

        driver.navigate().refresh();
        manageCaptcha();

        WebElement macbook = home.getAddToCartButtonMacbook();
        WebElement iphone = home.getAddToCartButtonIphone();
        new Actions(driver)
                .moveToElement(macbook)
                .perform();

        macbook.click();
        iphone.click();

        WebElement closeButton = header.getCloseButton();

        //let's wait until label advertisement appears on upper-right corner
        String closeButtonSelector = closeButton.toString().split(": ")[2].replace("'","");
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(8));

        //We need to wait twice because we added two items.
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(closeButtonSelector)));
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(closeButtonSelector)));
        closeButton.click();

        WebElement cartLink = header.getShoppingCartLink();
        new Actions(driver)
                .moveToElement(cartLink)
                .perform();
        cartLink.click();

        WebElement quantityInput = header.getQuantityInput();
        Assert.assertTrue(quantityInput.isEnabled());

        Utils.takeSnapShot(driver, "src/resources/VerifyOptionModifyQuantityForItemInCartPage/eclat_303.png");


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
