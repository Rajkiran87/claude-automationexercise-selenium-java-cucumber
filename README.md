# Automation Exercise – Selenium + Java + Cucumber (BDD)

UI test automation framework for the practice e-commerce site **[automationexercise.com](https://automationexercise.com)**, written in **Java** with **Selenium WebDriver 4** and **Cucumber (BDD)**.

It is one of four portfolio projects that automate **the same Gherkin scenarios** with different tool stacks:

| Project | Stack |
|---|---|
| **selenium-java-cucumber** (this repo) | Java · Selenium · Cucumber · JUnit 5 |
| selenium-csharp-reqnroll | C# · Selenium · Reqnroll · NUnit |
| playwright-java-cucumber | Java · Playwright · Cucumber · JUnit 5 |
| playwright-typescript-bdd | TypeScript · Playwright Test · playwright-bdd |

---

## What is tested

| Feature | Scenarios | Tags |
|---|---|---|
| Login | valid login, wrong password, unregistered email (2 data rows), logout | `@login` |
| Registration | register a new user, duplicate email is rejected | `@registration` |
| Product search | matching results, no results | `@search` |
| Shopping cart | add one product, add several (data table), set quantity on details page, remove product | `@cart` |
| Checkout (end-to-end) | logged-in user pays and sees *Order Placed!* | `@checkout @e2e` |
| Newsletter | valid subscription, invalid emails blocked (3 data rows) | `@subscription` |

Each scenario also has `@smoke` or `@regression`. Negative tests carry `@negative`.

---

## Tech stack

| Tool | Why |
|---|---|
| Java 17+ | Language |
| Selenium 4 | Browser automation. **Selenium Manager** downloads the browser driver automatically |
| Cucumber 7 | Runs Gherkin `.feature` files (BDD) |
| cucumber-picocontainer | Shares one `TestContext` between hooks and step classes (dependency injection) |
| JUnit 5 Platform | Test runner and assertions |
| Maven | Build and dependency management |
| GitHub Actions | Runs the suite on every push (headless Chrome) |

---

## Project structure

```
selenium-java-cucumber
├── pom.xml                                  # dependencies and versions
├── .github/workflows/tests.yml              # CI pipeline
└── src/test
    ├── java/com/automationexercise
    │   ├── runners/TestRunner.java          # starts Cucumber
    │   ├── hooks/Hooks.java                 # open browser before / screenshot + close + cleanup after
    │   ├── context/TestContext.java         # data shared inside one scenario (driver, user)
    │   ├── stepdefinitions/                 # Gherkin step  ->  Java method
    │   │   ├── NavigationSteps.java
    │   │   ├── LoginSteps.java
    │   │   ├── RegistrationSteps.java
    │   │   ├── SearchSteps.java
    │   │   ├── CartSteps.java
    │   │   ├── CheckoutSteps.java
    │   │   └── SubscriptionSteps.java
    │   ├── pages/                           # Page Object Model
    │   │   ├── BasePage.java                # waits + common actions for every page
    │   │   ├── components/                  # parts shared by many pages
    │   │   │   ├── HeaderComponent.java
    │   │   │   ├── FooterComponent.java
    │   │   │   └── CartModalComponent.java
    │   │   ├── HomePage.java, LoginPage.java, SignupPage.java, AccountCreatedPage.java
    │   │   ├── ProductsPage.java, ProductDetailsPage.java, CartPage.java
    │   │   └── CheckoutPage.java, PaymentPage.java
    │   ├── models/                          # simple data holders (User, PaymentCard)
    │   └── utils/
    │       ├── ConfigReader.java            # reads config.properties (+ -D overrides)
    │       ├── DriverFactory.java           # creates Chrome / Firefox / Edge
    │       ├── ApiClient.java               # creates/deletes test users through the site's API
    │       └── TestDataFactory.java         # unique emails, test user, test card
    └── resources
        ├── features/*.feature               # the BDD scenarios
        ├── config.properties                # base URL, browser, headless, timeout
        └── junit-platform.properties        # Cucumber report settings
```

### How a test flows

```
login.feature  ──►  LoginSteps.java  ──►  LoginPage.java  ──►  Selenium  ──►  Browser
  (what)              (glue)               (how)
```

* **Feature files** describe behaviour in plain English. No code, no locators.
* **Step definitions** connect each sentence to Java. They contain the assertions.
* **Page objects** hold locators and actions. If the website changes, only the page class changes.

---

## Prerequisites

* Java **17 or newer** (`java -version`)
* Maven **3.9+** (`mvn -version`)
* Google Chrome (or Firefox / Edge)
* An IDE: IntelliJ IDEA Community with the *Cucumber for Java* plugin is recommended

## How to run

```bash
# all scenarios, Chrome with a visible window
mvn clean test

# only smoke tests
mvn clean test -Dcucumber.filter.tags="@smoke"

# everything except the end-to-end checkout
mvn clean test -Dcucumber.filter.tags="not @e2e"

# another browser, without a window
mvn clean test -Dbrowser=firefox -Dheadless=true
```

In IntelliJ you can also right-click a `.feature` file (or `TestRunner.java`) and choose **Run**.

## Reports

After a run, open **`target/cucumber-reports/cucumber-report.html`** in a browser.
Failed scenarios include a screenshot taken at the moment of failure.
A JSON report (`cucumber.json`) is also produced for CI tools.

---

## Design decisions

| Decision | Reason |
|---|---|
| Page Object Model + components | Locators live in one place; header/footer/cart pop-up are reused |
| Explicit waits only (`BasePage`) | Tests wait for the exact condition they need, which removes flaky `Thread.sleep` |
| `data-qa` locators first | The site adds these attributes for testing, so they rarely change |
| Test users created and deleted through the **API** | Fast, independent scenarios with no leftover data |
| Unique email per run | Tests can run repeatedly and in any order |
| Ads blocked at DNS level (Chrome/Edge) | Google ads on the site sometimes cover buttons |
| Screenshot on failure | Faster defect analysis |
| Config file + `-D` overrides | Same code runs locally and in CI |

## Known limitations / ideas for next steps

* Automation Exercise is a shared public demo site. It can be slow, and ads can still appear in Firefox. Re-run before logging a defect.
* Add parallel execution (`cucumber.execution.parallel.enabled=true`) together with a thread-safe driver.
* Add Allure or ExtentReports for richer dashboards.
* Add API test scenarios for the site's public endpoints.

---

**Author:** Rajkiran G R · [LinkedIn](https://www.linkedin.com/in/grkrajkiran)
