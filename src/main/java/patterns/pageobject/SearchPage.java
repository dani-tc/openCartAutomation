package patterns.pageobject;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchPage extends PageHeader {

    //Title of the page
    @FindBy(css ="div#content h1")
    private WebElement title;

    //Filter category dropdown
    @FindBy(css="select#input-category")
    private WebElement filterSelect;

    private WebDriver driver;

    // Constructor
    public SearchPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public WebElement getTitle() {
        return title;
    }

    public WebElement getFilterSelect() {
        return filterSelect;
    }

    public void openSearchPage() {
        
        int MAX_ATTEMPTS = 5;
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++){
            try{
                clickSearchBarButton();
                explicitWait.until(ExpectedConditions.visibilityOf(title));
            break;
            } catch (Exception e){
                System.out.println("Attempt " + (attempt + 1) + " failed");
                if (attempt == MAX_ATTEMPTS - 1) {
                    throw e;
                }
            }
        }

    }

    public void clickFilterCategory() {
        
        int MAX_ATTEMPTS = 5;
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++){
            try{
                explicitWait.until(ExpectedConditions.elementToBeClickable(filterSelect));
                new Actions (driver)
                    .moveToElement(filterSelect)
                    .click(filterSelect)
                    .perform();
            break;
            } catch (Exception e){
                System.out.println("Attempt " + (attempt + 1) + " failed");
                if (attempt == MAX_ATTEMPTS - 1) {
                    throw e;
                }
            }
        }

    }

    public void selectDesktopsCategory(String category) {
        
        int MAX_ATTEMPTS = 5;
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++){
            try{
                explicitWait.until(ExpectedConditions.elementToBeClickable(filterSelect));
                explicitWait.until(ExpectedConditions.textToBePresentInElement(filterSelect, category));
                Select categories = new Select(filterSelect);
                categories.selectByVisibleText(category);
            break;
            } catch (Exception e){
                System.out.println("Attempt " + (attempt + 1) + " failed");
                if (attempt == MAX_ATTEMPTS - 1) {
                    throw e;
                }
            }
        }

    }

}
