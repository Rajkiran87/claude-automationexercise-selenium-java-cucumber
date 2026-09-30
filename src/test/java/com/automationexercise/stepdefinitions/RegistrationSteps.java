package com.automationexercise.stepdefinitions;

import com.automationexercise.context.TestContext;
import com.automationexercise.models.User;
import com.automationexercise.pages.AccountCreatedPage;
import com.automationexercise.pages.LoginPage;
import com.automationexercise.pages.SignupPage;
import com.automationexercise.utils.TestDataFactory;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationSteps {

    private final TestContext context;

    public RegistrationSteps(TestContext context) {
        this.context = context;
    }

    @When("I sign up with a new name and a unique email")
    public void iSignUpWithANewNameAndAUniqueEmail() {
        User newUser = TestDataFactory.newUser();
        // Saved now, so the @After hook deletes the account even if a later step fails
        context.setUser(newUser);
        new LoginPage(context.getDriver()).startSignup(newUser.name(), newUser.email());
    }

    @When("I sign up with the registered user's email")
    public void iSignUpWithTheRegisteredUsersEmail() {
        User existingUser = context.getUser();
        new LoginPage(context.getDriver()).startSignup(existingUser.name(), existingUser.email());
    }

    @When("I fill in the account information form")
    public void iFillInTheAccountInformationForm() {
        SignupPage signupPage = new SignupPage(context.getDriver());
        signupPage.fillAccountInformation(context.getUser());
        signupPage.createAccount();
    }

    @Then("I should see the account created message")
    public void iShouldSeeTheAccountCreatedMessage() {
        String heading = new AccountCreatedPage(context.getDriver()).getHeading();
        // The site shows this heading in capitals with CSS, so compare without caring about case
        assertTrue(heading.equalsIgnoreCase("Account Created!"),
                "Expected 'Account Created!' but was '" + heading + "'");
    }

    @When("I continue to the home page")
    public void iContinueToTheHomePage() {
        new AccountCreatedPage(context.getDriver()).clickContinue();
    }

    @Then("I should see the signup error {string}")
    public void iShouldSeeTheSignupError(String expectedError) {
        assertEquals(expectedError, new LoginPage(context.getDriver()).getSignupError());
    }
}
