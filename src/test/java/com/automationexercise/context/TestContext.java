package com.automationexercise.context;

import com.automationexercise.models.User;
import org.openqa.selenium.WebDriver;

/**
 * Holds everything that must be shared between Hooks and step definition classes
 * during ONE scenario: the browser and the test user.
 *
 * Cucumber's PicoContainer creates a fresh TestContext for every scenario and passes
 * the same object to every class that asks for it in its constructor.
 * This is called "dependency injection" - no static variables needed.
 */
public class TestContext {

    private WebDriver driver;
    private User user;
    private boolean userNeedsCleanup;

    public WebDriver getDriver() {
        if (driver == null) {
            throw new IllegalStateException("The browser has not been started. Check the @Before hook.");
        }
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public boolean hasDriver() {
        return driver != null;
    }

    public User getUser() {
        if (user == null) {
            throw new IllegalStateException("No test user yet. Add the step 'Given a registered user exists' first.");
        }
        return user;
    }

    /**
     * Remembers the user for later steps and marks it for deletion after the scenario.
     */
    public void setUser(User user) {
        this.user = user;
        this.userNeedsCleanup = true;
    }

    public boolean userNeedsCleanup() {
        return user != null && userNeedsCleanup;
    }
}
