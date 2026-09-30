package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * The "Signup / Login" page. It has two forms: Login (left) and New User Signup (right).
 * Locators use the site's data-qa attributes, which were added for testing and rarely change.
 */
public class LoginPage extends BasePage {

    // Login form
    private final By loginEmail = By.cssSelector("input[data-qa='login-email']");
    private final By loginPassword = By.cssSelector("input[data-qa='login-password']");
    private final By loginButton = By.cssSelector("button[data-qa='login-button']");
    private final By loginError = By.cssSelector("form[action='/login'] p");

    // Signup form
    private final By signupName = By.cssSelector("input[data-qa='signup-name']");
    private final By signupEmail = By.cssSelector("input[data-qa='signup-email']");
    private final By signupButton = By.cssSelector("button[data-qa='signup-button']");
    private final By signupError = By.cssSelector("form[action='/signup'] p");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openPath("/login");
    }

    public boolean isLoginFormDisplayed() {
        return waitForVisible(loginEmail).isDisplayed();
    }

    public void login(String email, String password) {
        type(loginEmail, email);
        type(loginPassword, password);
        click(loginButton);
    }

    public String getLoginError() {
        return getText(loginError);
    }

    public void startSignup(String name, String email) {
        type(signupName, name);
        type(signupEmail, email);
        click(signupButton);
    }

    public String getSignupError() {
        return getText(signupError);
    }
}
