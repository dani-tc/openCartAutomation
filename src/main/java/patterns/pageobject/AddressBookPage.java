package patterns.pageobject;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import patterns.DriverManager;

import java.time.Duration;
import java.util.List;

public class AddressBookPage extends AccountPage {
    //Create new address
    @FindBy(css=".d-inline-block .float-end a")
    private WebElement createNewAddressButton;
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
    //Title
    @FindBy(css="#content h1")
    private WebElement pageTitle;
    //Delete address button
    @FindBy(css="#content tr:nth-of-type(2) a[href*='delete']")
    private WebElement deleteAddressButton;
    //Default address
    @FindBy(css="#address tr:nth-of-type(1) td:nth-of-type(1)")
    private WebElement defaultAddress;
    //Edit address button
    @FindBy(css="#content tr:nth-of-type(1) a:nth-of-type(1)")
    private WebElement editDefaultAddressButton;
    //All addresses
    @FindBy(css="#address .text-start")
    private List<WebElement> allAddresses;
    //Account Created Title
    @FindBy(css="#content h1")
    private WebElement accountCreatedMessage;
    //body
    @FindBy(tagName="body")
    private WebElement body;



    private WebDriver driver;

    // Constructor
    public AddressBookPage(WebDriver driver) {
        super(driver);
        this.driver = DriverManager.getDriver(DriverManager.BrowserType.EDGE); // replace with desired browser (CHROME, EDGE, FIREFOX)
        PageFactory.initElements(driver, this);
    }

    public WebElement getAccountCreatedMessage() {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        explicitWait.until(ExpectedConditions.visibilityOf(accountCreatedMessage));
        return accountCreatedMessage;
    }

    public void deleteAddress (){
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        explicitWait.until(ExpectedConditions.elementToBeClickable(deleteAddressButton));
        deleteAddressButton.click();
        ((JavascriptExecutor)driver).executeScript("location.reload()");
    }

    public WebElement getDefaultAddress() {
        return defaultAddress;
    }

    public void editDefaultAddress (){
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        explicitWait.until(ExpectedConditions.elementToBeClickable(editDefaultAddressButton));
        editDefaultAddressButton.click();
    }

    public List<WebElement> getAllAddressesLength() {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        explicitWait.until(ExpectedConditions.visibilityOfAllElements(allAddresses));
        return allAddresses;
    }

    public void clickCreateNewAddress(){
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        explicitWait.until(ExpectedConditions.visibilityOf(createNewAddressButton));
        createNewAddressButton.sendKeys(Keys.ENTER);
    }

    public void createNewAddress(String firstName, String lastName, String address1,String city,String postalCode, String countryValue, String regionValue){
        boolean passedCreateAddress = false;
        int tries = 0;
        while (!passedCreateAddress && tries<5) {
            try {
                WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
                explicitWait.until(ExpectedConditions.visibilityOf(getFirstName()));
                getFirstName().sendKeys(firstName);
                getLastName().sendKeys(lastName);
                address1Input.sendKeys(address1);
                addressCity.sendKeys(city);
                addressPostCode.sendKeys(postalCode);
                addressCountry.click();
                driver.findElement(By.cssSelector("#input-country option[value='" + countryValue + "']")).click();
                explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
                explicitWait.until(ExpectedConditions.elementToBeClickable(addressRegion));
                addressRegion.click();
                driver.findElement(By.cssSelector("#input-zone option[value='" + regionValue + "']")).click();
                explicitWait.until(ExpectedConditions.elementToBeSelected(By.cssSelector("#input-zone option[value='"+regionValue+"']")));
                body.click();
                new Actions (driver)
                        .moveToElement(getContinueButton())
                        .perform();
                explicitWait.until(ExpectedConditions.elementToBeClickable(getContinueButton()));
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", getContinueButton());
                explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
                explicitWait.until(ExpectedConditions.textToBePresentInElement(pageTitle, "Address Book Entries"));
                passedCreateAddress = true;
            } catch (Exception e) {
                System.out.println("error:" + e);
                tries = tries+1;
                ((JavascriptExecutor) driver).executeScript("location.reload()");
            }
        }
    }

    public void updateDefaultAddress(String firstNameUpdate, String lastNameUpdate, String address1Update, String cityUpdate, String postalCodeUpdate,String countryValueUpdate, String regionValueUpdate) {
        boolean passUpdate = false;
        int tries = 0;
        while (!passUpdate && tries < 5) {
            try {
                WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
                explicitWait.until(ExpectedConditions.visibilityOf(getFirstName()));
                getFirstName().clear();
                getFirstName().sendKeys(firstNameUpdate);
                getLastName().clear();
                getLastName().sendKeys(lastNameUpdate);
                address1Input.clear();
                address1Input.sendKeys(address1Update);
                addressCity.clear();
                addressCity.sendKeys(cityUpdate);
                addressPostCode.clear();
                addressPostCode.sendKeys(postalCodeUpdate);
                addressCountry.click();
                driver.findElement(By.cssSelector("#input-country option[value='" + countryValueUpdate + "']")).click();
                explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
                explicitWait.until(ExpectedConditions.elementToBeClickable(addressRegion));
                addressRegion.click();
                explicitWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#input-zone option[value='" + regionValueUpdate + "']")));
                driver.findElement(By.cssSelector("#input-zone option[value='" + regionValueUpdate + "']")).click();
                explicitWait.until(ExpectedConditions.elementToBeSelected(By.cssSelector("#input-zone option[value='" + regionValueUpdate + "']")));
                explicitWait.until(ExpectedConditions.elementToBeClickable(getContinueButton()));
                new Actions (driver)
                        .moveToElement(getContinueButton())
                        .click(getContinueButton())
                        .perform();
                explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
                explicitWait.until(ExpectedConditions.textToBePresentInElement(pageTitle, "Address Book Entries"));
                passUpdate = true;
            } catch (Exception e) {
                System.out.println("error:" + e);
                tries = tries+1;
                ((JavascriptExecutor) driver).executeScript("location.reload()");
            }
        }
    }
}
