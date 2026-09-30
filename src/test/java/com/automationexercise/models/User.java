package com.automationexercise.models;

/**
 * All the details needed to register a user on Automation Exercise.
 * A "record" is a short way to write a class that only holds data:
 * Java creates the constructor and the getter methods (user.email(), user.password() ...) for us.
 */
public record User(
        String name,
        String email,
        String password,
        String title,          // "Mr" or "Mrs"
        String birthDay,       // "1" to "31"
        String birthMonth,     // "1" to "12"
        String birthYear,      // e.g. "1995"
        String firstName,
        String lastName,
        String company,
        String address1,
        String address2,
        String country,        // must be one of the options in the site's dropdown
        String state,
        String city,
        String zipcode,
        String mobileNumber
) {
}
