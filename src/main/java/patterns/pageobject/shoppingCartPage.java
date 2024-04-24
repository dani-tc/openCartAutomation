package patterns.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
//import patterns.DriverManager;


public class shoppingCartPage extends PageHeader {
    //Shopping Cart page title
    @FindBy(css="#content h1")
    private WebElement shoppingCartTitle;

    // Constructor


    public shoppingCartPage(WebDriver driver) {
        super(driver);
    }

    public String getCartPageTitleText (){return shoppingCartTitle.getText();}
    public WebElement getCartPageTitle (){return shoppingCartTitle;}
}