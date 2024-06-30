package com.emeff23.stc.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(tags = "", features = {"src/test/resources/features/02-OrangeHRMSearchUsers.feature"},
        glue = {"com.emeff23.stc.cucumbertests.definitions"},
        plugin = { "pretty", "html:target/cucumber-reports.html" },
        monochrome = true)

public class RunCucumberTests extends AbstractTestNGCucumberTests{

}
