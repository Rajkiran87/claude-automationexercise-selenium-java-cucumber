package com.automationexercise.hooks;

import com.automationexercise.context.TestContext;
import com.automationexercise.models.User;
import com.automationexercise.utils.ApiClient;
import com.automationexercise.utils.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

/**
 * Hooks run automatically before and after EVERY scenario.
 */
public class Hooks {

    private final TestContext context;

    public Hooks(TestContext context) {
        this.context = context;
    }

    @Before
    public void startBrowser() {
        context.setDriver(DriverFactory.createDriver());
    }

    @After
    public void tearDown(Scenario scenario) {
        try {
            if (scenario.isFailed() && context.hasDriver()) {
                // The screenshot is embedded in target/cucumber-reports/cucumber-report.html
                byte[] screenshot = ((TakesScreenshot) context.getDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Screenshot at failure");
            }
        } finally {
            if (context.hasDriver()) {
                context.getDriver().quit();
            }
            deleteTestUser();
        }
    }

    /** Removes any account this scenario created, so the site does not fill up with test users. */
    private void deleteTestUser() {
        if (!context.userNeedsCleanup()) {
            return;
        }
        User user = context.getUser();
        try {
            ApiClient.deleteAccount(user.email(), user.password());
        } catch (Exception e) {
            // Cleanup problems should not hide the real test result.
            System.out.println("Warning: could not delete test user " + user.email() + ": " + e.getMessage());
        }
    }
}
