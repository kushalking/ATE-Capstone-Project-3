package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ConfirmationPage {
    private WebDriver driver;
    
    @FindBy(xpath = "//h1[contains(text(),'Thank you for your purchase today!')]")
    private WebElement confirmationMessage;
    
    public ConfirmationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public boolean isConfirmationMessageDisplayed() {
        return confirmationMessage.isDisplayed();
    }
    
    public String getConfirmationMessage() {
        return confirmationMessage.getText();
    }
}
