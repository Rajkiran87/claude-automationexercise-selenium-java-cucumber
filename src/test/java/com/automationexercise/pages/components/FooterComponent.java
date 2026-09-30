package com.automationexercise.pages.components;

import com.automationexercise.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * The newsletter subscription box in the footer of every page.
 * (The id "susbscribe_email" is misspelled on the real website, so we copy it exactly.)
 */
public class FooterComponent extends BasePage {

    private final By emailInput = By.id("susbscribe_email");
    private final By subscribeButton = By.id("subscribe");
    private final By successMessage = By.cssSelector("#success-subscribe .alert-success");

    public FooterComponent(WebDriver driver) {
        super(driver);
    }

    public void subscribe(String email) {
        type(emailInput, email);
        click(subscribeButton);
    }

    public String getSuccessMessage() {
        return getText(successMessage);
    }

    /**
     * The browser's own HTML5 validation message, e.g. "Please include an '@' in the email address."
     * It is empty when the email is valid.
     */
    public String getEmailValidationMessage() {
        return waitForVisible(emailInput).getDomProperty("validationMessage");
    }
}
