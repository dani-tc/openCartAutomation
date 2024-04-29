package tests;

import patterns.DriverManager;
import patterns.DriverManager.BrowserType;
import utilities.Utils;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;
import patterns.pageobject.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.Assert;

public class ComparisonPageExistenceTest {

    private WebDriver driver;
    private WebDriverWait explicitWait;

    Screen screen = new Screen();
    String pathYourSystem = System.getProperty("user.dir") + "\\";
    Pattern image = new Pattern(pathYourSystem+"src\\resources\\cloudflare.png");

    @BeforeTest
    @Parameters("browserType")
    public void beforeTest(String browserType) {
        driver = DriverManager.getDriver(BrowserType.valueOf(browserType));

        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));

        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String formatedDate = format.format(today);
        driver.get("https://demo.opencart.com/");
        driver.manage().addCookie(new Cookie("OCSESSID","11c0f931cf"+formatedDate+"ec"));
        driver.manage().addCookie(new Cookie("_ga","GA1.1.2123778129.1713796835"));
        driver.manage().addCookie(new Cookie("_ga_X8G0BRFSDF","GS1.1.1713796835.1.0.1713796835.0.0.0"));
        driver.manage().addCookie(new Cookie("_gcl_au","1.1.534898992.1713796834"));
        driver.manage().addCookie(new Cookie("_gid","GA1.2.438931849.1713796835"));
        driver.manage().addCookie(new Cookie("cf_clearance","zJ9wxfXGd6JiMI3czkXFs4.kzRi6IqvPGPR1BaphLjM-1713852454-1.0.1.1-XKiVE5CVgEaZJ6pwxaPFZvAbzObkzBLWVzgfCCZoPHgWbHPgp6V.HROlod2Rr0jRzg2O5vNoDLVqbRP0JC8Gnw"));
        driver.manage().addCookie(new Cookie("currency","USD"));
    }

    @Test(priority = 1, description = "Verify product comparison tool exists")
    public void comparisonPageElementsTest() throws FindFailed {
        ComparisonPage comparisonPage = new ComparisonPage(driver);

        String comparisonToolURL = "https://demo.opencart.com/index.php?route=product/compare";
        driver.get(comparisonToolURL);

        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logo")));
        Utils.takeSnapShot(driver, "src/resources/ComparisonPageTest/1-ComparisonPageExistence.png");
        Assert.assertEquals(driver.getCurrentUrl(), comparisonToolURL);

        String header = comparisonPage.getProductComparisonHeader().getText();
        String expectedHeader = "Product Comparison";
        Assert.assertEquals(header, expectedHeader);


        String desc = comparisonPage.getProductComparisonDescription().getAttribute("textContent");
        String expectedDesc = "Your shopping cart is empty!";
        Assert.assertEquals(desc, expectedDesc);
        
    }

    @Test(priority = 2, description = "Verify product comparison tool working for a single product")
    @Parameters({"productOneName", "productsFeatures", "productOneFeatures"})
    public void comparisonToolForASingleProduct(String productOneName, String productsFeatures, String productOneFeatures) throws FindFailed {

        CamerasPage camerasPage = new CamerasPage(driver);
        ComparisonPage comparisonPage = new ComparisonPage(driver);

        camerasPage.goToCamerasCategory();
        camerasPage.comparisonToolForOneProduct();
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logo")));
        Utils.takeSnapShot(driver, "src/resources/ComparisonPageTest/2-AddAProductToComparisonTable.png");
        
        camerasPage.clickComparisonToolButton();
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logo")));
        Utils.takeSnapShot(driver, "src/resources/ComparisonPageTest/3-SendingProductToComparisonPage.png");
        
        Assert.assertTrue(comparisonPage.isComparisonToolTableDisplayed());
        Assert.assertTrue(comparisonPage.getComparisonToolProductTitlesText().contains(productOneName));
        Assert.assertEquals(comparisonPage.getComparisonToolFeaturesTitlesText(),productsFeatures);
        Assert.assertTrue(comparisonPage.getComparisonToolFeaturesContentFirstProductText().contains(productOneFeatures));

    }

    @Test(priority = 3, description = "Verify product comparison tool working for multiple products")
    @Parameters({"productOneName", "productTwoName", "productsFeatures", "productOneFeatures", "productTwoFeatures"})
    public void comparisonToolForMultipleProducts(String productOneName, String productTwoName, String productsFeatures, String productOneFeatures, String productTwoFeatures) throws FindFailed {

        CamerasPage camerasPage = new CamerasPage(driver);
        ComparisonPage comparisonPage = new ComparisonPage(driver);

        camerasPage.goToCamerasCategory();
        camerasPage.comparisonToolForTwoProducts();
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logo")));
        Utils.takeSnapShot(driver, "src/resources/ComparisonPageTest/4-AddTwoProductsToComparisonTable.png");
        
        camerasPage.clickComparisonToolButton();
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logo")));
        Utils.takeSnapShot(driver, "src/resources/ComparisonPageTest/5-SendingMultipleProductsToComparisonPage.png");

        Assert.assertTrue(comparisonPage.isComparisonToolTableDisplayed());
        Assert.assertTrue(comparisonPage.getComparisonToolProductTitlesText().contains(productOneName + " " + productTwoName));
        Assert.assertEquals(comparisonPage.getComparisonToolFeaturesTitlesText(), productsFeatures);
        Assert.assertTrue(comparisonPage.getComparisonToolFeaturesContentFirstProductText().contains(productOneFeatures));
        Assert.assertTrue(comparisonPage.getComparisonToolFeaturesContentSecondProductText().contains(productTwoFeatures));

    }

    @AfterTest
    public void afterTest() {
        DriverManager.quitDriver();
    }
}

