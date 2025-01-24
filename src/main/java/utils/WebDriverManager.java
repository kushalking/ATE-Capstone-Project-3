package utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import java.io.File;
import java.util.UUID;

public class WebDriverManager {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    // Declare userDataDir as a class variable
    private static String userDataDir;
    
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
                
                // Generate a truly unique directory using UUID
                userDataDir = System.getProperty("java.io.tmpdir") + 
                File.separator + "chrome_user_data_" + UUID.randomUUID().toString() + 
                "_" + System.currentTimeMillis();
                
                options.addArguments("user-data-dir=" + userDataDir);
                
                // Additional options to prevent session conflicts
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                
                driver.set(new ChromeDriver(options));
                break;
        }
        
        driver.get().manage().window().maximize();
        System.out.println("Chrome User Data Directory: " + userDataDir);
    }
    
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
