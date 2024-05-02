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
import patterns.pageobject.MonitorsPage;
import patterns.pageobject.ShoppingCartPage;
import reports.ReportMethods;
import utilities.Utils;
import java.time.Duration;

public class VerifyShoppingCartIconUpdatesItemCount {

    private WebDriver driver = null;
    ReportMethods report = new ReportMethods();

    @BeforeTest
    @Parameters({"browser"})
    public void beforeTest(String browser){
        driver = DriverManager.getDriver(DriverManager.BrowserType.valueOf(browser));
        String browserName = driver.getClass().getSimpleName();
        report.setupReport(browserName,"VerifyShoppingCartIconUpdatesItemCount.html",
                "Verify cart item count updates",
                "Verify that the cart item count is updated when adding an item to the cart.");

    }

    @Test
    public void eclat_275() {
        manageCaptcha();
        driver.navigate().refresh();
        manageCaptcha();

        ShoppingCartPage header = new ShoppingCartPage(driver);
        MonitorsPage monitorsPage = new MonitorsPage(driver);
        WebElement components = header.getComponents();
        WebElement monitors = header.getMonitors();
        WebElement addToCartButton = monitorsPage.getAddToCartButton();
        WebElement itemsButton = header.getCartDropdownButton();

        new Actions(driver)                     //hover to components menu on header
                .moveToElement(components)
                .perform();

        monitors.click();

        manageCaptcha();

        new Actions(driver)
                .moveToElement(addToCartButton)
                .perform();
        addToCartButton.sendKeys(Keys.ENTER);              //Simulate click on "add to cart" button

        sleep(1000);

        Assert.assertEquals("1",itemsButton.getText().charAt(0)+"");

        Utils.takeSnapShot(driver, "src/resources/VerifyShoppingCartIconUpdatesItemCount/eclat_275.png");

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
