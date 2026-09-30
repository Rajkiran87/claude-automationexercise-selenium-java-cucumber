package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Checkout page: shows the delivery address and order review, with a "Place Order" button.
 */
public class CheckoutPage extends BasePage {

    private final By placeOrderButton = By.cssSelector("a[href='/payment']");

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void placeOrder() {
        click(placeOrderButton);
    }
}
