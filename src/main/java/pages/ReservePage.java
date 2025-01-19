package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ReservePage {
    private WebDriver driver;
    
    @FindBy(xpath = "//input[@value='Choose This Flight'][1]")
    private WebElement firstFlightButton;
    
    @FindBy(xpath = "//h3[contains(text(),'Flights from')]")
    private WebElement reservePageHeader;
    
    public ReservePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public void selectFirstFlight() {
        firstFlightButton.click();
    }
    
    public boolean isOnReservePage() {
        return reservePageHeader.isDisplayed();
    }
    
    public String getPageTitle() {
        return driver.getTitle();
    }
}