package com.automationexercise.stepdefinitions;

import com.automationexercise.context.TestContext;
import com.automationexercise.pages.CartPage;
import com.automationexercise.pages.ProductDetailsPage;
import com.automationexercise.pages.ProductsPage;
import com.automationexercise.pages.components.CartModalComponent;
import com.automationexercise.pages.components.HeaderComponent;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartSteps {

    private final TestContext context;

    public CartSteps(TestContext context) {
        this.context = context;
    }

    private CartPage cartPage() {
        return new CartPage(context.getDriver());
    }

    @When("I add the product {string} to the cart")
    public void iAddTheProductToTheCart(String productName) {
        new ProductsPage(context.getDriver()).addToCart(productName);
        new CartModalComponent(context.getDriver()).continueShopping();
    }

    /**
     * The feature file passes a table with a "product" header:
     *   | product    |
     *   | Blue Top   |
     *   | Men Tshirt |
     * Cucumber turns each row into a Map, e.g. {product=Blue Top}.
     */
    @When("I add these products to the cart:")
    public void iAddTheseProductsToTheCart(List<Map<String, String>> rows) {
        for (Map<String, String> row : rows) {
            iAddTheProductToTheCart(row.get("product"));
        }
    }

    @When("I open the product details for {string}")
    public void iOpenTheProductDetailsFor(String productName) {
        new ProductsPage(context.getDriver()).openProductDetails(productName);
        assertEquals(productName, new ProductDetailsPage(context.getDriver()).getProductName());
    }

    @When("I add it to the cart with quantity {int}")
    public void iAddItToTheCartWithQuantity(int quantity) {
        new ProductDetailsPage(context.getDriver()).addToCart(quantity);
        new CartModalComponent(context.getDriver()).continueShopping();
    }

    @When("I open the cart")
    public void iOpenTheCart() {
        new HeaderComponent(context.getDriver()).openCart();
    }

    @When("I remove {string} from the cart")
    public void iRemoveFromTheCart(String productName) {
        cartPage().removeProduct(productName);
    }

    @Then("the cart should contain {string} with quantity {int}")
    public void theCartShouldContainWithQuantity(String productName, int expectedQuantity) {
        CartPage cartPage = cartPage();
        assertTrue(cartPage.containsProduct(productName), productName + " is not in the cart");
        assertEquals(expectedQuantity, cartPage.getQuantity(productName),
                "Wrong quantity for " + productName);
    }

    @Then("the cart should be empty")
    public void theCartShouldBeEmpty() {
        assertTrue(cartPage().isEmptyMessageDisplayed(), "The 'Cart is empty!' message is not shown");
    }
}
