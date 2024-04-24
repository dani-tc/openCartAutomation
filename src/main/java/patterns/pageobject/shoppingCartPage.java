package patterns.pageobject;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class ShoppingCartPage extends PageHeader{

    @FindBy(css = "#content h1")
    private WebElement shoppingCartTitle;

    @FindBy(css = "#content h2")
    private WebElement shoppingCartSubtitle;

    @FindBy(css = "#shopping-cart")
    private WebElement contentsTable;

    @FindBy(css = "#checkout-total")
    private WebElement totalTable;

    @FindBy(css = "button[data-bs-original-title='Update']")
    private WebElement updateButton;

    @FindBy(css = "a[href='https://demo.opencart.com/index.php?route=product/product&amp;language=en-gb&amp;product_id=49']>img")
    WebElement imageLink;

    @FindBy(css = "#accordion.accordion")
    private WebElement accordion;

    @FindBy(css = "button[data-bs-target='#collapse-shipping']")
    private WebElement estimateShippingButton;

    @FindBy(css = "#input-country")
    private WebElement countryInput;

    @FindBy(css = "#input-zone")
    private WebElement zoneInput;

    @FindBy(css = "#input-postcode")
    private WebElement postCodeInput;

    @FindBy(tagName = "footer")
    private WebElement footer;

    @FindBy(tagName = "header")
    private WebElement header;

    public ShoppingCartPage(WebDriver driver) {
        super(driver);
    }

    public String getCartPageTitleText() {
        return shoppingCartTitle.getText();
    }

    public WebElement getCartPageTitle() {
        return shoppingCartTitle;
    }

    public WebElement getShoppingCartTitle() {
        return shoppingCartTitle;
    }

    public WebElement getShoppingCartSubtitle() {
        return shoppingCartSubtitle;
    }

    public WebElement getContentsTable() {
        return contentsTable;
    }

    public WebElement getTotalTable() {
        return totalTable;
    }

    public WebElement getUpdateButton() {
        return updateButton;
    }

    public WebElement getImageLink() {
        return imageLink;
    }

    public WebElement getAccordion() {
        return accordion;
    }

    public WebElement getEstimateShippingButton() {
        return estimateShippingButton;
    }

    public WebElement getCountryInput() {
        return countryInput;
    }

    public WebElement getZoneInput() {
        return zoneInput;
    }

    public WebElement getPostCodeInput() {
        return postCodeInput;
    }

    public WebElement getFooter() {
        return footer;
    }

    public WebElement getHeader() {
        return header;
    }
    
}