package com.automationexercise.pages;

import com.automationexercise.models.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * "Enter Account Information" page, shown after the first signup step.
 * Name and email are already filled in from the previous page.
 */
public class SignupPage extends BasePage {

    private final By titleMr = By.id("id_gender1");
    private final By titleMrs = By.id("id_gender2");
    private final By password = By.cssSelector("input[data-qa='password']");
    private final By birthDay = By.cssSelector("select[data-qa='days']");
    private final By birthMonth = By.cssSelector("select[data-qa='months']");
    private final By birthYear = By.cssSelector("select[data-qa='years']");
    private final By firstName = By.cssSelector("input[data-qa='first_name']");
    private final By lastName = By.cssSelector("input[data-qa='last_name']");
    private final By company = By.cssSelector("input[data-qa='company']");
    private final By address1 = By.cssSelector("input[data-qa='address']");
    private final By address2 = By.cssSelector("input[data-qa='address2']");
    private final By country = By.cssSelector("select[data-qa='country']");
    private final By state = By.cssSelector("input[data-qa='state']");
    private final By city = By.cssSelector("input[data-qa='city']");
    private final By zipcode = By.cssSelector("input[data-qa='zipcode']");
    private final By mobileNumber = By.cssSelector("input[data-qa='mobile_number']");
    private final By createAccountButton = By.cssSelector("button[data-qa='create-account']");

    public SignupPage(WebDriver driver) {
        super(driver);
    }

    public void fillAccountInformation(User user) {
        click("Mrs".equals(user.title()) ? titleMrs : titleMr);
        type(password, user.password());
        selectByValue(birthDay, user.birthDay());
        selectByValue(birthMonth, user.birthMonth());
        selectByValue(birthYear, user.birthYear());
        type(firstName, user.firstName());
        type(lastName, user.lastName());
        type(company, user.company());
        type(address1, user.address1());
        type(address2, user.address2());
        selectByVisibleText(country, user.country());
        type(state, user.state());
        type(city, user.city());
        type(zipcode, user.zipcode());
        type(mobileNumber, user.mobileNumber());
    }

    public void createAccount() {
        click(createAccountButton);
    }
}
