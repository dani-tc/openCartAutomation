package patterns.pageobject;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class addressBookPage extends myAccountPage {
    //Manage address book
    @FindBy(css="#content > ul:nth-of-type(1) > li:nth-of-type(3) > a")
    private WebElement addressBookLink;
    //Create new address
    @FindBy(css=".d-inline-block .float-end a")
    private WebElement createNewAddressButton;
    //First Name
    @FindBy(id="input-firstname")
    private WebElement addressFirstName;
    //Last Name
    @FindBy(id="input-lastname")
    private WebElement addressLastName;
    //Address 1
    @FindBy(id="input-address-1")
    private WebElement address1Input;
    //City
    @FindBy(id="input-city")
    private WebElement addressCity;
    //City
    @FindBy(id="input-postcode")
    private WebElement addressPostCode;
    //Country
    @FindBy(css="#input-country")
    private WebElement addressCountry;
    //Country
    @FindBy(css="#input-country option[value='222']")
    private WebElement addressCountryOption;
    //Region
    @FindBy(css="#input-zone")
    private WebElement addressRegion;
    //Region
    @FindBy(css="#input-zone option:nth-of-type(2)")
    private WebElement addressRegionOption;
    //Click outside
    @FindBy(tagName="footer")
    private WebElement body;
    //Continue Button
    @FindBy(css=".float-end button")
    private WebElement addressContinueButton;


    private WebDriver driver;

    // Constructor
    public addressBookPage(WebDriver driver) {
        super(driver);
    }

    public void clickAddressBookLink(){
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        explicitWait.until(ExpectedConditions.visibilityOf(addressBookLink));
        addressBookLink.click();
    }
    public void createNewAddress(String firstName, String lastName, String address1,String city,String postalCode){
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        explicitWait.until(ExpectedConditions.elementToBeClickable(addressBookLink));
        addressBookLink.click();
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        explicitWait.until(ExpectedConditions.elementToBeClickable(createNewAddressButton));
        createNewAddressButton.click();
        addressFirstName.sendKeys(firstName);
        addressLastName.sendKeys(lastName);
        address1Input.sendKeys(address1);
        addressCity.sendKeys(city);
        addressPostCode.sendKeys(postalCode);
        addressCountry.click();
        addressCountryOption.click();
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        explicitWait.until(ExpectedConditions.elementToBeClickable(addressRegion));
        addressRegion.click();
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        explicitWait.until(ExpectedConditions.elementToBeClickable(addressRegionOption));
        addressRegionOption.click();
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        explicitWait.until(ExpectedConditions.elementToBeClickable(body));
        body.click();
        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        explicitWait.until(ExpectedConditions.elementToBeClickable(addressContinueButton));
        addressContinueButton.click();
    }
}
