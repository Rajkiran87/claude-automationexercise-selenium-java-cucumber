package com.automationexercise.pages;

import com.automationexercise.models.PaymentCard;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Payment page and the "Order Placed!" confirmation that follows it.
 */
public class PaymentPage extends BasePage {

    private final By nameOnCard = By.cssSelector("input[data-qa='name-on-card']");
    private final By cardNumber = By.cssSelector("input[data-qa='card-number']");
    private final By cvc = By.cssSelector("input[data-qa='cvc']");
    private final By expiryMonth = By.cssSelector("input[data-qa='expiry-month']");
    private final By expiryYear = By.cssSelector("input[data-qa='expiry-year']");
    private final By payButton = By.cssSelector("button[data-qa='pay-button']");
    private final By orderPlacedHeading = By.cssSelector("h2[data-qa='order-placed']");

    public PaymentPage(WebDriver driver) {
        super(driver);
    }

    public void payWith(PaymentCard card) {
        type(nameOnCard, card.nameOnCard());
        type(cardNumber, card.cardNumber());
        type(cvc, card.cvc());
        type(expiryMonth, card.expiryMonth());
        type(expiryYear, card.expiryYear());
        click(payButton);
    }

    public String getOrderPlacedHeading() {
        return getText(orderPlacedHeading);
    }
}
