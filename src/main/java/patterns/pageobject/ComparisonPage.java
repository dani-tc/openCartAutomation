package patterns.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import patterns.DriverManager;

import java.time.Duration;
import java.util.List;

public class ComparisonPage extends PageHeader {

    //Comparison Page Header
    @FindBy(css = "h1")
    private WebElement productComparisonHeader;

    @FindBy(css = "p")
    private WebElement productComparisonDescription;

    //Comparison tool table
    @FindBy(css="#content table")
    private WebElement comparisonToolTable;

    //Comparison tool product titles
    @FindBy(css="#content table tbody td strong")
    private List<WebElement> comparisonToolProductTitles;

    //Comparison tool key features and specification titles
    @FindBy(css="#content table tbody tr td:nth-of-type(1)")
    private List<WebElement> comparisonToolFeaturesTitles;

    //Comparison tool key features and specification first product
    @FindBy(css=" #content table tbody tr td:nth-of-type(2)")
    private List<WebElement> comparisonToolFeaturesContentFirstProduct;

    //Comparison tool key features and specification second product
    @FindBy(css=" #content table tbody tr td:nth-of-type(3)")
    private List<WebElement> comparisonToolFeaturesContentSecondProduct;

    private WebDriver driver;
    public ComparisonPage(WebDriver driver) {
        super(driver);
        this.driver = DriverManager.getDriver(DriverManager.BrowserType.EDGE); // replace with desired browser (CHROME, EDGE, FIREFOX)
        PageFactory.initElements(driver, this);
    }

    public boolean isComparisonToolTableDisplayed() {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        return explicitWait.until(ExpectedConditions.visibilityOf(comparisonToolTable)).isDisplayed();
    }

    public String getComparisonToolProductTitlesText() {
        String productTitles = "";
        for (WebElement element : comparisonToolProductTitles) {
            productTitles = productTitles + element.getText() + " ";
        }
        return productTitles;
    }

    public String getComparisonToolFeaturesTitlesText() {
        String featuresTitles = "";
        for (WebElement element : comparisonToolFeaturesTitles) {
            featuresTitles = featuresTitles + element.getText() + " ";
        }
        return featuresTitles;
    }

    public String getComparisonToolFeaturesContentFirstProductText() {
        String featuresContentFirstProduct = "";
        for (WebElement element : comparisonToolFeaturesContentFirstProduct) {
            featuresContentFirstProduct = featuresContentFirstProduct + element.getText() + " ";
        }
        return featuresContentFirstProduct;
    }

    public String getComparisonToolFeaturesContentSecondProductText() {
        String featuresContentSecondProduct = "";
        for (WebElement element : comparisonToolFeaturesContentSecondProduct) {
            featuresContentSecondProduct = featuresContentSecondProduct +element.getText() + " ";
        }
        return featuresContentSecondProduct;
    }

    public WebElement getProductComparisonHeader() {
        return productComparisonHeader;
    }

    public WebElement getProductComparisonDescription() {
        return productComparisonDescription;
    }

    public WebElement getComparisonToolTable() {
        return comparisonToolTable;
    }

    public List<WebElement> getComparisonToolProductTitles() {
        return comparisonToolProductTitles;
    }

    public List<WebElement> getComparisonToolFeaturesTitles() {
        return comparisonToolFeaturesTitles;
    }

    public List<WebElement> getComparisonToolFeaturesContentFirstProduct() {
        return comparisonToolFeaturesContentFirstProduct;
    }

    public List<WebElement> getComparisonToolFeaturesContentSecondProduct() {
        return comparisonToolFeaturesContentSecondProduct;
    }

    public WebDriver getDriver() {
        return driver;
    }

    
}
