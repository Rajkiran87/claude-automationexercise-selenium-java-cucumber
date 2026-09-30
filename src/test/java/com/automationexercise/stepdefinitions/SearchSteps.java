package com.automationexercise.stepdefinitions;

import com.automationexercise.context.TestContext;
import com.automationexercise.pages.ProductsPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchSteps {

    private final TestContext context;

    public SearchSteps(TestContext context) {
        this.context = context;
    }

    private ProductsPage productsPage() {
        return new ProductsPage(context.getDriver());
    }

    @When("I search for {string}")
    public void iSearchFor(String searchTerm) {
        productsPage().search(searchTerm);
    }

    @Then("the searched products section should be displayed")
    public void theSearchedProductsSectionShouldBeDisplayed() {
        String title = productsPage().getSectionTitle();
        assertTrue(title.equalsIgnoreCase("Searched Products"),
                "Expected the 'Searched Products' title but was '" + title + "'");
    }

    @Then("every product in the results should contain {string}")
    public void everyProductInTheResultsShouldContain(String expectedText) {
        List<String> names = productsPage().getProductNames();

        assertFalse(names.isEmpty(), "The search returned no products");
        for (String name : names) {
            // Case-insensitive: "jeans" matches "Soft Stretch Jeans"
            assertTrue(name.toLowerCase().contains(expectedText.toLowerCase()),
                    "Product '" + name + "' does not contain '" + expectedText + "'");
        }
    }

    @Then("no products should be displayed")
    public void noProductsShouldBeDisplayed() {
        List<String> names = productsPage().getProductNames();
        assertTrue(names.isEmpty(), "Expected no products but found: " + names);
    }
}
