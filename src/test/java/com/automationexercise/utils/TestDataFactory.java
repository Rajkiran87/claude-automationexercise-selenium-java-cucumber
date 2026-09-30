package com.automationexercise.utils;

import com.automationexercise.models.PaymentCard;
import com.automationexercise.models.User;

import java.util.UUID;

/**
 * Builds test data. Every user gets a unique email, so tests never clash with each other
 * or with data left over from an earlier run.
 */
public final class TestDataFactory {

    public static final String DEFAULT_PASSWORD = "Test@12345";

    private TestDataFactory() {
    }

    /** Example: autotest_3f9a1c2b@example.com */
    public static String uniqueEmail() {
        String shortId = UUID.randomUUID().toString().substring(0, 8);
        return "autotest_" + shortId + "@example.com";
    }

    public static User newUser() {
        return new User(
                "QA Tester",
                uniqueEmail(),
                DEFAULT_PASSWORD,
                "Mr",
                "15",
                "6",
                "1995",
                "Qa",
                "Tester",
                "Portfolio Testing Ltd",
                "221B Test Street",
                "Near Automation Park",
                "India",
                "Karnataka",
                "Bengaluru",
                "560001",
                "9876543210"
        );
    }

    public static PaymentCard testCard() {
        return new PaymentCard("QA Tester", "4111111111111111", "123", "12", "2030");
    }
}
