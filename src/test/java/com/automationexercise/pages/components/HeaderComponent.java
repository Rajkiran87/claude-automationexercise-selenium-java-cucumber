package com.automationexercise.pages.components;

import com.automationexercise.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * The navigation bar at the top of every page.
 * Shared parts of a page are modelled as "components" and reused by many pages.
 */
public class HeaderComponent extends BasePage {

    private final By cartLink = By.cssSelector(".shop-menu a[href='/view_cart']");
    private final By logoutLink = By.cssSelector(".shop-menu a[href='/logout']");
    // Renders as: "Logged in as <b>QA Tester</b>"
    private final By loggedInUsername = By.xpath("//a[contains(., 'Logged in as')]/b");

    public HeaderComponent(WebDriver driver) {
        super(driver);
    }

    public void openCart() {
        click(cartLink);
    }

    public void logout() {
        click(logoutLink);
    }

    public String getLoggedInUsername() {
        return getText(loggedInUsername);
    }
}
