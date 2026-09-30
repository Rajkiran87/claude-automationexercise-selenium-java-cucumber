package com.automationexercise.stepdefinitions;

import com.automationexercise.context.TestContext;
import com.automationexercise.models.User;
import com.automationexercise.pages.LoginPage;
import com.automationexercise.pages.components.HeaderComponent;
import com.automationexercise.utils.ApiClient;
import com.automationexercise.utils.TestDataFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginSteps {

    private final TestContext context;

    public LoginSteps(TestContext context) {
        this.context = context;
    }

    // Small helpers so each step reads like plain English
    private LoginPage loginPage() {
        return new LoginPage(context.getDriver());
    }

    private HeaderComponent header() {
        return new HeaderComponent(context.getDriver());
    }

    @Given("a registered user exists")
    public void aRegisteredUserExists() {
        User user = TestDataFactory.newUser();
        ApiClient.createAccount(user);   // fast set-up through the API instead of the UI
        context.setUser(user);           // remembered for later steps and deleted after the scenario
    }

    @When("I log in with the registered user's credentials")
    public void iLogInWithTheRegisteredUsersCredentials() {
        User user = context.getUser();
        loginPage().login(user.email(), user.password());
    }

    @When("I log in with the registered user's email and a wrong password")
    public void iLogInWithTheRegisteredUsersEmailAndAWrongPassword() {
        loginPage().login(context.getUser().email(), "Wrong@Password1");
    }

    @When("I log in with email {string} and password {string}")
    public void iLogInWithEmailAndPassword(String email, String password) {
        loginPage().login(email, password);
    }

    @When("I log out")
    public void iLogOut() {
        header().logout();
    }

    @Then("I should see that I am logged in")
    public void iShouldSeeThatIAmLoggedIn() {
        assertEquals(context.getUser().name(), header().getLoggedInUsername(),
                "The header should show the logged-in user's name");
    }

    @Then("I should see the login error {string}")
    public void iShouldSeeTheLoginError(String expectedError) {
        assertEquals(expectedError, loginPage().getLoginError());
    }

    @Then("I should be on the login page")
    public void iShouldBeOnTheLoginPage() {
        LoginPage loginPage = loginPage();
        assertTrue(loginPage.isLoginFormDisplayed(), "The login form should be visible");
        assertTrue(loginPage.getCurrentUrl().endsWith("/login"),
                "Expected the /login URL but was " + loginPage.getCurrentUrl());
    }
}
