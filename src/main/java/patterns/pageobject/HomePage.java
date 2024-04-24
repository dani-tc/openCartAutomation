package patterns.pageobject;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.*;

import java.time.Duration;


public class HomePage extends homePageHeader {
    

    //MacBook product
    @FindBy(css ="div.product-thumb button[aria-label='Add to Cart']")
    private WebElement macBook;

    //Cart Button
    @FindBy(css ="div#header-cart button.btn-inverse")
    private WebElement cartBtn;

    //Checkout Button
    @FindBy(css ="p.text-end a:nth-child(2)")
    private WebElement checkoutBtn;

    //Alert Popup
    @FindBy(className ="alert-success")
    private WebElement alert;

    //Cart Dropdown
    @FindBy(css ="div.d-grid ul.dropdown-menu-right")
    private WebElement cartDropdown;

    private WebDriver driver;

    // Constructor
    public HomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void openProductPage() {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        explicitWait.until(ExpectedConditions.elementToBeClickable(macBook));
        int MAX_ATTEMPTS = 10;
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++){
            try{
            macBook.click();
            explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success")));
            try {
                Thread.sleep(1000);
                } catch (InterruptedException e) {
                e.printStackTrace();
                }
            break;
            } catch (Exception e){
                System.out.println("Attempt " + (attempt + 1) + " failed");
                if (attempt == MAX_ATTEMPTS - 1) {
                    // If this was the last attempt, rethrow the exception
                    throw e;
                }
            }
        }

    }

    public void openCartPage() {

        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        explicitWait.until(ExpectedConditions.elementToBeClickable(cartBtn));
        int MAX_ATTEMPTS = 10;
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++){
            try{
            cartBtn.click();
            explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.d-grid ul.dropdown-menu-right")));
            try {
                Thread.sleep(1000);
                } catch (InterruptedException e) {
                e.printStackTrace();
                }
            break;
            } catch (Exception e){
                System.out.println("Attempt " + (attempt + 1) + " failed");
                if (attempt == MAX_ATTEMPTS - 1) {
                    // If this was the last attempt, rethrow the exception
                    throw e;
                }
            }
        }
        explicitWait.until(ExpectedConditions.elementToBeClickable(checkoutBtn));
        new Actions(driver)
            .moveToElement(checkoutBtn)
            .click(checkoutBtn)
            .perform();

    }

}
