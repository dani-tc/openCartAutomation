package patterns.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.*;

import java.time.Duration;
import java.util.Random;

public class CheckoutPage extends homePageHeader {

    //First Name Field
    @FindBy(css ="#checkout-register")
    private WebElement title;

    //First Name Field
    @FindBy(css ="#input-firstname")
    private WebElement firstNameInput;

    //Last Name Field
    @FindBy(css ="#input-lastname")
    private WebElement lastNameInput;

    //Email Field
    @FindBy(css ="#input-email")
    private WebElement emailInput;

    //Address Field
    @FindBy(css ="#input-payment-address-1")
    private WebElement addressInput;

    //City Field
    @FindBy(css ="#input-payment-city")
    private WebElement cityInput;

    //Postcode Field
    @FindBy(css ="#input-payment-postcode")
    private WebElement postcodeInput;

    //State Dropdown
    @FindBy(css ="#input-payment-zone")
    private WebElement stateDrop;

    //Password Field
    @FindBy(css ="#input-password")
    private WebElement passwordInput;

    //Privacy Policy Checkbox
    @FindBy(css ="#input-register-agree")
    private WebElement policyCheckbox;

    //Continue Button
    @FindBy(css ="#button-register")
    private WebElement continueButton;

    private WebDriver driver;

    // Constructor
    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    public void registerCredentials(String firstName, String lastName, String address, String postcode, String city, String state, String password) {

        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#checkout-register")));
        
        String randomEmail = generateRandomEmail();
        
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        emailInput.sendKeys(randomEmail);
        addressInput.sendKeys(address);
        postcodeInput.sendKeys(postcode);
        cityInput.sendKeys(city);
        stateDrop.sendKeys(state);
        passwordInput.sendKeys(password);
        
        explicitWait.until(ExpectedConditions.elementToBeClickable(policyCheckbox));
        new Actions (driver)
            .moveToElement(policyCheckbox)
            .click(policyCheckbox)
            .perform();

        explicitWait.until(ExpectedConditions.elementToBeClickable(continueButton));
        new Actions (driver)
            .moveToElement(continueButton)
            .click(continueButton)
            .perform();

        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#alert div.alert-success")));
        
    }

    private static String generateRandomEmail() {
        String allowedChars = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder email = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(allowedChars.length());
            char randomChar = allowedChars.charAt(index);
            email.append(randomChar);
        }

        email.append("@testing.com");
        return email.toString();
    }

}
