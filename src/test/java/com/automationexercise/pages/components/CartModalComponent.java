package com.automationexercise.pages.components;

import com.automationexercise.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * The "Added!" pop-up that appears after clicking "Add to cart".
 */
public class CartModalComponent extends BasePage {

    private final By modal = By.id("cartModal");
    private final By continueShoppingButton = By.cssSelector("#cartModal button.close-modal");

    public CartModalComponent(WebDriver driver) {
        super(driver);
    }

    /** Waits for the pop-up, closes it and waits until it is gone. */
    public void continueShopping() {
        waitForVisible(modal);
        click(continueShoppingButton);
        waitForInvisible(modal);
    }
}
