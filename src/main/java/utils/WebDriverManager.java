package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

                // More robust directory creation
                String userDataDir = createUniqueUserDataDir();
                options.addArguments("user-data-dir=" + userDataDir);

                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                // Add more options as needed (e.g., headless mode for CI)
                // options.addArguments("--headless=new"); // For headless mode

                driver.set(new ChromeDriver(options));
                System.out.println("Chrome User Data Directory: " + userDataDir);
                break;
        }

        driver.get().manage().window().maximize();
    }

    private static String createUniqueUserDataDir() {
        String baseDir = System.getProperty("java.io.tmpdir");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        LocalDateTime now = LocalDateTime.now();
        String timestamp = dtf.format(now);
        String uniqueDirName = "chrome_user_data_" + timestamp;
        Path userDataPath = Paths.get(baseDir, uniqueDirName);

        try {
            Files.createDirectories(userDataPath); // Ensure parent directories are created
            return userDataPath.toString();
        } catch (IOException e) {
            System.err.println("Error creating user data directory: " + e.getMessage());
            // Fallback to a less robust method (less ideal, but prevents complete failure)
            return baseDir + File.separator + "chrome_user_data_fallback_" + System.currentTimeMillis();
        }
    }


    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            try {
                // Attempt to delete the user data directory after the test
                String userDataDir = ((ChromeDriver) driver.get()).getCapabilities().getCapability("goog:chromeOptions").toString();
                if(userDataDir.contains("user-data-dir=")) {
                    userDataDir = userDataDir.substring(userDataDir.indexOf("user-data-dir=") + 16, userDataDir.indexOf(", "));

                    Path path = Paths.get(userDataDir);
                    Files.walk(path)
                        .sorted(java.util.Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
                    System.out.println("Deleted User Data Directory: " + userDataDir);

                }
            } catch (Exception e) {
                System.err.println("Error deleting user data directory: " + e.getMessage());
            }
            driver.remove();
        }
    }
}
