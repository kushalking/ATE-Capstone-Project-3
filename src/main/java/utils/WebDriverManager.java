package utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import java.io.File;

public class WebDriverManager {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    
    public static WebDriver getDriver() {
        if (driver.get() == null) {
            initializeDriver();
        }
        return driver.get();
    }
    
    private static void initializeDriver() {
        String browserType = System.getProperty("browser", "chrome");
        
        switch (browserType.toLowerCase()) {
            case "firefox":
                driver.set(new FirefoxDriver());
                break;
            case "edge":
                driver.set(new EdgeDriver());
                break;
            default:
                ChromeOptions options = new ChromeOptions();
                
                // Create a unique user data directory
                String userDataDir = System.getProperty("java.io.tmpdir") + 
                    File.separator + "chrome_user_data_" + Thread.currentThread().getId();
                
                options.addArguments("user-data-dir=" + userDataDir);
                driver.set(new ChromeDriver(options));
                break;
        }
        
        driver.get().manage().window().maximize();
    }
    
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
