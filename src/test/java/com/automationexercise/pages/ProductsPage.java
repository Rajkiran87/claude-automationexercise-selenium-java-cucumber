package com.automationexercise.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

/**
 * "All Products" page with the search box and the product grid.
 */
public class ProductsPage extends BasePage {

    private final By searchInput = By.id("search_product");
    private final By searchButton = By.id("submit_search");
    private final By sectionTitle = By.cssSelector(".features_items h2.title");
    private final By productNames = By.cssSelector(".features_items .productinfo p");

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        openPath("/products");
    }

    public void search(String searchTerm) {
        type(searchInput, searchTerm);
        click(searchButton);
    }

    /** Title above the grid, e.g. "ALL PRODUCTS" or "SEARCHED PRODUCTS". */
    public String getSectionTitle() {
        return getText(sectionTitle);
    }

    public List<String> getProductNames() {
        return getAllTexts(productNames);
    }

    /** Clicks the "Add to cart" button on the card of the product with this exact name. */
    public void addToCart(String productName) {
        click(By.xpath(productCardXpath(productName) + "//a[contains(@class, 'add-to-cart')]"));
    }

    /** Clicks the "View Product" link of the product with this exact name. */
    public void openProductDetails(String productName) {
        click(By.xpath(productCardXpath(productName) + "//a[contains(@href, '/product_details/')]"));
    }

    /**
     * Dynamic locator: the product card that contains a name paragraph with exactly this text.
     * Example result for "Blue Top":
     * //div[contains(@class,'product-image-wrapper')][.//div[contains(@class,'productinfo')]/p[normalize-space()='Blue Top']]
     */
    private String productCardXpath(String productName) {
        return "//div[contains(@class, 'product-image-wrapper')]"
                + "[.//div[contains(@class, 'productinfo')]/p[normalize-space()='" + productName + "']]";
    }
}
