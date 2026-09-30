package com.automationexercise.stepdefinitions;

import com.automationexercise.context.TestContext;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.LoginPage;
import com.automationexercise.pages.ProductsPage;
import io.cucumber.java.en.Given;

/**
 * Steps that simply open a page. Used by the Background of most feature files.
 */
public class NavigationSteps {

    private final TestContext context;

    public NavigationSteps(TestContext context) {
        this.context = context;
    }

    @Given("I am on the home page")
    public void iAmOnTheHomePage() {
        new HomePage(context.getDriver()).open();
    }

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        new LoginPage(context.getDriver()).open();
    }

    @Given("I am on the products page")
    public void iAmOnTheProductsPage() {
        new ProductsPage(context.getDriver()).open();
    }
}
