package patterns.pageobject;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductsPage extends PageHeader{
    
    @FindBy(className = "col-12")
    private WebElement productItem;

    @FindBy(id = "button-cart")
    private WebElement addToCart;

    @FindBy(id = "input-quantity")
    private WebElement productQuantity;

    @FindBy(tagName = "footer")
    private WebElement footer;

    @FindBy(tagName = "header")
    private WebElement header;

    @FindBy(className = "alert-success")
    private WebElement confirmProductAdded;

    @FindBy(css = ".nav-tabs li:nth-child(1) a")
    private WebElement descriptionTab;

    @FindBy(id = "tab-description")
    private WebElement descriptionText;

    public ProductsPage(WebDriver driver){
        super(driver);
    }

    public void openProduct(){
        productItem.click();
    }

    public void addProductToCart(){

        WebDriverWait explicitWait = new WebDriverWait(this.getDriver(), Duration.ofSeconds(10));
        
        int MAX_ATTEMPTS = 10;
        for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++){
            try{
                addToCart.click();
                explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("alert-success")));
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

    public WebElement getProductItem() {
        return productItem;
    }

    public WebElement getAddToCart() {
        return addToCart;
    }

    public WebElement getProductQuantity() {
        return productQuantity;
    }

    public WebElement getFooter() {
        return footer;
    }

    public WebElement getHeader() {
        return header;
    }

    public WebElement getConfirmProductAdded() {
        return confirmProductAdded;
    }

    public WebElement getDescriptionTab(){return descriptionTab;}

    public WebElement getDescriptionText(){return descriptionText;}
}
