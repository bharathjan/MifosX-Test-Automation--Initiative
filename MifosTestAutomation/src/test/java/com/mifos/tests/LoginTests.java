package com.mifos.tests;
import org.junit.runner.RunWith;

import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)
@Cucumber.Options(format = { "pretty", "html:target/cucumber-html-report",
		"json-pretty:target/cucumber-json-report.json" },
		features = { "src/test/resources/features/AccountVerify.feature"},
	//	 tags={"@loanproductcreation"},
		 glue = { "com.mifos.steps" })

public class LoginTests {
	
}

	
