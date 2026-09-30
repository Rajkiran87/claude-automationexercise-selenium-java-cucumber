package com.automationexercise.runners;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

/**
 * Entry point for the test run.
 *
 * - @SelectClasspathResource("features") -> run every .feature file in src/test/resources/features
 * - GLUE -> the Java package that holds step definitions and hooks
 *
 * Run only some scenarios by tag:
 *   mvn test -Dcucumber.filter.tags="@smoke"
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.automationexercise")
public class TestRunner {
}
