package patterns.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PhonesPage extends PageHeader {

    @FindBy(css="#content .col:nth-child(1) button:nth-child(1)")
    private WebElement addToCartButtonHtc;

    @FindBy(css="#content .col:nth-child(2) button:nth-child(1)")
    private WebElement addToCartButtonIphone;

    @FindBy(css="#content .col:nth-child(3) button:nth-child(1)")
    private WebElement addToCartButtonPalm;

    public PhonesPage(WebDriver driver){
        super(driver);
    }

    public WebElement getAddToCartButtonHtc(){return addToCartButtonHtc;}
    public WebElement getAddToCartButtonIphone(){return addToCartButtonIphone;}
    public WebElement getAddToCartButtonPalm(){return addToCartButtonPalm;}
}
