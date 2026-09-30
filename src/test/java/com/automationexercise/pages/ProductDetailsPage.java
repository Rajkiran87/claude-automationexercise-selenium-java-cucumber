package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductDetailsPage extends BasePage {

    private final By productName = By.cssSelector(".product-information h2");
    private final By quantityInput = By.id("quantity");
    private final By addToCartButton = By.cssSelector(".product-information button.cart");

    public ProductDetailsPage(WebDriver driver) {
        super(driver);
    }

    public String getProductName() {
        return getText(productName);
    }

    public void addToCart(int quantity) {
        type(quantityInput, String.valueOf(quantity));
        click(addToCartButton);
    }
}
