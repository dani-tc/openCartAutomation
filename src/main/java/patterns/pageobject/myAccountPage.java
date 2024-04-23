package patterns.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class myAccountPage extends homePageHeader {
    //Email Input
    @FindBy(name="email")
    private WebElement emailInput;
    //Password Input
    @FindBy(id="input-password")
    private WebElement passwordInput;
    //Login button
    @FindBy(css=".mb-3 + button")
    private WebElement loginButton;
    //Delete address button
    @FindBy(css="#content tr:nth-of-type(2) a[href*='delete']")
    private WebElement deleteAddressButton;
    //All addresses
    @FindBy(css="#address .text-start")
    private List<WebElement> allAddresses;


    private WebDriver driver;

    // Constructor
    public myAccountPage(WebDriver driver) {
        super(driver);
    }

    public boolean login (String email, String password){
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        loginButton.click();
        return true;
    }

    public void deleteAddress (){
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        explicitWait.until(ExpectedConditions.elementToBeClickable(deleteAddressButton));
        deleteAddressButton.click();
    }

    public List<WebElement> getAllAddressesLength() {
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        explicitWait.until(ExpectedConditions.visibilityOfAllElements(allAddresses));
        return allAddresses;
    }
}
