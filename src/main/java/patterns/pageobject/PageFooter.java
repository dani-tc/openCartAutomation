package patterns.pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import patterns.DriverManager;

import java.time.Duration;

public class PageFooter {
    //MP3 show all
    @FindBy(css="footer div:nth-child(4)  ul > li:nth-child(1) > a")
    private WebElement myAccountFooter;

    private WebDriver driver;

    public PageFooter(WebDriver driver){
        this.driver = driver;
        this.driver = DriverManager.getDriver(DriverManager.BrowserType.EDGE); // replace with desired browser (CHROME, EDGE, FIREFOX)
        PageFactory.initElements(driver, this);
    }

    public void clickMyAccountFooter(){
        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        explicitWait.until(ExpectedConditions.elementToBeClickable(myAccountFooter));
        new Actions(driver)
                .moveToElement(myAccountFooter)
                .click(myAccountFooter)
                .perform();
    }
}
