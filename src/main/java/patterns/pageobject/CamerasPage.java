package patterns.pageobject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CamerasPage extends PageHeader {
    //Compare product button
    @FindBy(css=".button-group button[aria-label='Compare this Product']")
    private List<WebElement> compareThisProductButtonsCameras;

    //Comparison tool button
    @FindBy(css="#display-control a")
    private WebElement comparisonToolButtonCameras;

    //Comparison tool alert pop up
    @FindBy(css=".alert")
    private WebElement comparisonAlertPopUp;

    @FindBy(css="#content .col:nth-child(1) button:nth-child(1)")
    private WebElement addToCartButtonCanon;

    @FindBy(css="#content .col:nth-child(2) button:nth-child(1)")
    private WebElement addToCartButtonNykon;

    public CamerasPage(WebDriver driver){
        super(driver);
    }

    public WebElement getAddToCartButtonCanon(){return addToCartButtonCanon;}
    public WebElement getAddToCartButtonNykon(){return addToCartButtonNykon;}

    public List<WebElement> getCompareThisProductButtonsCameras() {
        return compareThisProductButtonsCameras;
    }

    public void comparisonToolForOneProduct(){
        int tries = 0;
        boolean passedComparison = false;
        while (!passedComparison && tries<10) {
            try {
                WebDriverWait explicitWait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
                explicitWait.until(ExpectedConditions.visibilityOfAllElements(getCompareThisProductButtonsCameras()));
                getCompareThisProductButtonsCameras().get(0).click();
                explicitWait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
                explicitWait.until(ExpectedConditions.invisibilityOf(comparisonAlertPopUp));
                passedComparison = true;
            } catch (Exception e) {
                System.out.println("error:" + e);
                tries = tries + 1;
                ((JavascriptExecutor) getDriver()).executeScript("location.reload()");
            }
        }
    }

    public void comparisonToolForTwoProducts(){
        int tries = 0;
        boolean passedComparison = false;
        while (!passedComparison && tries<5) {
            try {
                WebDriverWait explicitWait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
                explicitWait.until(ExpectedConditions.visibilityOfAllElements(getCompareThisProductButtonsCameras()));
                getCompareThisProductButtonsCameras().get(0).click();
                explicitWait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
                explicitWait.until(ExpectedConditions.invisibilityOf(comparisonAlertPopUp));
                getCompareThisProductButtonsCameras().get(1).click();
                explicitWait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
                explicitWait.until(ExpectedConditions.invisibilityOf(comparisonAlertPopUp));
                passedComparison = true;
            } catch (Exception e) {
                System.out.println("error:" + e);
                tries = tries + 1;
                ((JavascriptExecutor) getDriver()).executeScript("location.reload()");
            }
        }
    }

    public void clickComparisonToolButton(){
        WebDriverWait explicitWait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
        explicitWait.until(ExpectedConditions.visibilityOf(comparisonAlertPopUp));
        comparisonToolButtonCameras.click();
    }
}
