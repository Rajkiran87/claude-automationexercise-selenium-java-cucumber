package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    private final By emptyCartMessage = By.id("empty_cart");
    private final By proceedToCheckoutButton = By.cssSelector("a.check_out");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openPath("/view_cart");
    }

    public boolean containsProduct(String productName) {
        return isDisplayed(By.xpath(rowXpath(productName)));
    }

    /** Reads the number shown in the Quantity column for this product. */
    public int getQuantity(String productName) {
        String quantity = getText(By.xpath(rowXpath(productName) + "//td[contains(@class, 'cart_quantity')]/button"));
        return Integer.parseInt(quantity);
    }

    /** Clicks the X button of the product and waits until its row disappears. */
    public void removeProduct(String productName) {
        click(By.xpath(rowXpath(productName) + "//a[contains(@class, 'cart_quantity_delete')]"));
        waitForInvisible(By.xpath(rowXpath(productName)));
    }

    public boolean isEmptyMessageDisplayed() {
        return waitForVisible(emptyCartMessage).isDisplayed();
    }

    public void proceedToCheckout() {
        click(proceedToCheckoutButton);
    }

    /** Dynamic locator: the table row whose product name link has exactly this text. */
    private String rowXpath(String productName) {
        return "//table[@id='cart_info_table']//tbody/tr"
                + "[.//td[contains(@class, 'cart_description')]//a[normalize-space()='" + productName + "']]";
    }
}
