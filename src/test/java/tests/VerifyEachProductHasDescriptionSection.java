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
import patterns.pageobject.*;
import reports.ReportMethods;
import utilities.Utils;
import java.time.Duration;


public class VerifyEachProductHasDescriptionSection {

    private WebDriver driver = null;
    ReportMethods report = new ReportMethods();

    @BeforeTest
    @Parameters({"browser"})
    public void beforeTest(String browser){
        driver = DriverManager.getDriver(DriverManager.BrowserType.valueOf(browser));
        String browserName = driver.getClass().getSimpleName();
        report.setupReport(browserName,"VerifyEachProductHasDescriptionSection.html",
                "Verify each product has a description",
                "Verify that each product has a description with relevant information");
    }

    @Test
    public void eclat_265() {
        HomePage home = new HomePage(driver);
        MacDesktopPage macDesktop = new MacDesktopPage(driver);
        PhonesPage phonesSection = new PhonesPage(driver);
        ProductsPage product = new ProductsPage(driver);

        //Refresh and manage captcha
        manageCaptcha();
        driver.navigate().refresh();
        manageCaptcha();

        //Hover element
        WebElement desktopsCategory = home.getDesktopsCategory();
        new Actions(driver)
                .moveToElement(desktopsCategory)
                .perform();

        //Let's go to Desktops Mac section
        WebElement macOption = home.getMacSubcategory();
        macOption.click();
        manageCaptcha();

        //click on first element
        WebElement productImage = macDesktop.getMacDesktopImg();
        productImage.click();
        manageCaptcha();

        //Let's find the description of item
        WebElement descriptionTab = product.getDescriptionTab();
        Assert.assertEquals(descriptionTab.getText(),"Description");
        new Actions(driver)
                .moveToElement(descriptionTab)
                .perform();
        descriptionTab.click();                               //Click on element in case it is not selected.

        WebElement descriptionText = product.getDescriptionText();
        Assert.assertTrue(descriptionText.isDisplayed());

        // -----------------------------

        //Let's go to phones section
        WebElement phones = product.getPhonesCategory();
        new Actions(driver)
                .moveToElement(phones)
                .perform();
        phones.click();
        manageCaptcha();

        //Click on first element
        WebElement productImage2 = phonesSection.getPhoneDesktopImg();
        productImage2.click();
        manageCaptcha();

        //Let's find the description of item
        WebElement descriptionTab2 = product.getDescriptionTab();
        Assert.assertEquals(descriptionTab2.getText(),"Description");
        new Actions(driver)
                .moveToElement(descriptionTab2)
                .perform();
        descriptionTab.click();                                 //Click on element in case it is not selected.

        WebElement descriptionText2 = product.getDescriptionText();
        Assert.assertTrue(descriptionText2.isDisplayed());

        Utils.takeSnapShot(driver, "src/resources/VerifyEachProductHasDescriptionSection/eclat_265.png");

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
