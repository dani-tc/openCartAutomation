package patterns.pageobject;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;


public class HomePage extends PageHeader{

    @FindBy(css=".row:nth-child(4) .col:nth-child(1) button:nth-child(1)")
    private WebElement addToCartButtonMacbook;

    @FindBy(css=".row:nth-child(4) .col:nth-child(2) button:nth-child(1)")
    private WebElement addToCartButtonIphone;

    @FindBy(css=".row:nth-child(4) .col:nth-child(3) button:nth-child(1)")
    private WebElement addToCartButtonCinema;

    @FindBy(css=".row:nth-child(4) .col:nth-child(4) button:nth-child(1)")
    private WebElement addToCartButtonCanon;




    public HomePage(WebDriver driver) {
        super(driver);
    }

    public WebElement getAddToCartButtonMacbook(){return addToCartButtonMacbook;}
    public WebElement getAddToCartButtonIphone(){return addToCartButtonIphone;}
    public WebElement getAddToCartButtonCinema(){return addToCartButtonCinema;}
    public WebElement getAddToCartButtonCanon(){return addToCartButtonCanon;}
}
