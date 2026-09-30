package com.automationexercise.stepdefinitions;

import com.automationexercise.context.TestContext;
import com.automationexercise.pages.components.FooterComponent;
import com.automationexercise.utils.TestDataFactory;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SubscriptionSteps {

    private final TestContext context;

    public SubscriptionSteps(TestContext context) {
        this.context = context;
    }

    private FooterComponent footer() {
        return new FooterComponent(context.getDriver());
    }

    @When("I subscribe with a unique email")
    public void iSubscribeWithAUniqueEmail() {
        footer().subscribe(TestDataFactory.uniqueEmail());
    }

    @When("I subscribe with the email {string}")
    public void iSubscribeWithTheEmail(String email) {
        footer().subscribe(email);
    }

    @Then("I should see the subscription success message")
    public void iShouldSeeTheSubscriptionSuccessMessage() {
        assertEquals("You have been successfully subscribed!", footer().getSuccessMessage());
    }

    @Then("the subscription email field should show a validation error")
    public void theSubscriptionEmailFieldShouldShowAValidationError() {
        String message = footer().getEmailValidationMessage();
        assertFalse(message == null || message.isBlank(),
                "The browser should block an invalid email with a validation message");
    }
}
