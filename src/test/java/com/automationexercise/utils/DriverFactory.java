package com.automationexercise.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.List;

/**
 * Creates a WebDriver for the browser named in config.properties.
 * Selenium Manager (built into Selenium 4) downloads the matching driver automatically,
 * so there is no need for chromedriver.exe or WebDriverManager.
 */
public final class DriverFactory {

    /**
     * Automation Exercise shows Google ads that sometimes cover buttons and make tests flaky.
     * For Chrome/Edge we point the ad domains at 127.0.0.1 so the ads never load.
     */
    private static final String BLOCK_ADS =
            "--host-resolver-rules="
                    + "MAP *.doubleclick.net 127.0.0.1, "
                    + "MAP *.googlesyndication.com 127.0.0.1, "
                    + "MAP *.googleadservices.com 127.0.0.1, "
                    + "MAP adservice.google.com 127.0.0.1, "
                    + "MAP fundingchoicesmessages.google.com 127.0.0.1";

    private static final List<String> COMMON_CHROMIUM_ARGS = List.of(
            "--window-size=1920,1080",
            "--disable-notifications",
            BLOCK_ADS
    );

    private DriverFactory() {
    }

    public static WebDriver createDriver() {
        String browser = ConfigReader.getBrowser();
        boolean headless = ConfigReader.isHeadless();

        WebDriver driver = switch (browser) {
            case "chrome" -> {
                ChromeOptions options = new ChromeOptions();
                options.addArguments(COMMON_CHROMIUM_ARGS);
                if (headless) {
                    options.addArguments("--headless=new");
                }
                yield new ChromeDriver(options);
            }
            case "edge" -> {
                EdgeOptions options = new EdgeOptions();
                options.addArguments(COMMON_CHROMIUM_ARGS);
                if (headless) {
                    options.addArguments("--headless=new");
                }
                yield new EdgeDriver(options);
            }
            case "firefox" -> {
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments("--width=1920", "--height=1080");
                if (headless) {
                    options.addArguments("-headless");
                }
                yield new FirefoxDriver(options);
            }
            default -> throw new IllegalArgumentException(
                    "Unsupported browser '" + browser + "'. Use chrome, firefox or edge.");
        };

        // We use explicit waits only (see BasePage), so the implicit wait stays at 0.
        if (!headless) {
            driver.manage().window().maximize();
        }
        return driver;
    }
}
