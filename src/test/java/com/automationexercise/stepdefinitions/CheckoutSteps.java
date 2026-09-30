package com.automationexercise.stepdefinitions;

import com.automationexercise.context.TestContext;
import com.automationexercise.pages.CartPage;
import com.automationexercise.pages.CheckoutPage;
import com.automationexercise.pages.PaymentPage;
import com.automationexercise.utils.TestDataFactory;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutSteps {

    private final TestContext context;

    public CheckoutSteps(TestContext context) {
        this.context = context;
    }

    @When("I proceed to checkout")
    public void iProceedToCheckout() {
        new CartPage(context.getDriver()).proceedToCheckout();
    }

    @When("I place the order")
    public void iPlaceTheOrder() {
        new CheckoutPage(context.getDriver()).placeOrder();
    }

    @When("I pay with the test card details")
    public void iPayWithTheTestCardDetails() {
        new PaymentPage(context.getDriver()).payWith(TestDataFactory.testCard());
    }

    @Then("I should see the order placed confirmation")
    public void iShouldSeeTheOrderPlacedConfirmation() {
        String heading = new PaymentPage(context.getDriver()).getOrderPlacedHeading();
        assertTrue(heading.equalsIgnoreCase("Order Placed!"),
                "Expected 'Order Placed!' but was '" + heading + "'");
    }
}
