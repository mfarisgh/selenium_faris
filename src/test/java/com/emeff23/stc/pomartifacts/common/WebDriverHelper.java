package com.emeff23.stc.pomartifacts.common;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.emeff23.stc.common.CentralVars;
import com.emeff23.stc.common.PropFileMgmt;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WebDriverHelper {

    private static final Logger LogThis = LogManager.getLogger(WebDriverHelper.class.getName());

    private static WebDriverHelper helperClass;

    private static WebDriver driver;
    private static ThreadLocal<WebDriver> threadedDriver = new ThreadLocal<>();

    public final static int TIMEOUT = 5;

    private WebDriverHelper() {

        try {

            switch (PropFileMgmt.getPropertyValue(CentralVars.PropNameBrowser)) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();

                    options.addArguments("--window-size=1920,1080");
                    options.addArguments("--start-maximized");

                    if (PropFileMgmt.getPropertyValue(CentralVars.PropNameBrowserMode).equalsIgnoreCase("headless")) {
                        options.addArguments("--headless");
                    }

                    options.addArguments("--remote-allow-origins=*");
                    options.addArguments("--ignore-ssl-errors=yes");
                    options.addArguments("--ignore-certificate-errors");

                    if (PropFileMgmt.getPropertyValue(CentralVars.PropNameLocation).equalsIgnoreCase("lambdatest")) {

                        options.setPlatformName("Windows 10");
                        options.setBrowserVersion("latest");
                        Map<String, Object> ltOptions = new HashMap<>();
                        ltOptions.put("build", "Selenium Cucumber Sample Build");
                        ltOptions.put("name", "Selenium Test");
                        options.setCapability("LT:Options", ltOptions);

                        String gridURL = "https://"
                                + PropFileMgmt.getPropertyValue(CentralVars.PropNameLtUsername)
                                + ":"
                                + PropFileMgmt.getPropertyValue(CentralVars.PropNameLtAccesskey)
                                + "@hub.lambdatest.com/wd/hub";

                        LogThis.info("LambdaTest API : " + gridURL);

                        driver = new RemoteWebDriver(new URL(gridURL), options);

                    } else {

                        driver = new ChromeDriver(options);

                    }

                default:
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT));

        } catch (Exception e) {
            LogThis.error("Exception e msg = " + e.getMessage());
            LogThis.error("Exception e  = " + e);
        }

    }

    public static void openPage(String url) {

        driver.get(url);

    }

    public static synchronized WebDriver getDriver() {

        if (Objects.nonNull(driver)) {
            threadedDriver.set(driver);
        }
        return threadedDriver.get();

    }

    public static void setUpDriver() {

        if (helperClass == null) {

            helperClass = new WebDriverHelper();
        }
    }

    public static void tearDown() {

        if (driver != null) {
            driver.close();
            //driver.quit();
        }

        helperClass = null;
    }
}
