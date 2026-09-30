package com.automationexercise.models;

/**
 * Dummy payment details. Automation Exercise is a practice site and never charges a card.
 */
public record PaymentCard(
        String nameOnCard,
        String cardNumber,
        String cvc,
        String expiryMonth,
        String expiryYear
) {
}
