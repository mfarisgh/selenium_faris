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
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
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
                    //WebDriverManager.chromedriver().setup();
                    if (PropFileMgmt.getPropertyValue(CentralVars.PropNameCustomWebDriver).equalsIgnoreCase("true")) {
                        System.setProperty("webdriver.chrome.driver", PropFileMgmt.getPropertyValue(CentralVars.PropNameCustomChromeDriverPath));
                    }
                    ChromeOptions options = new ChromeOptions();

                    options.addArguments("--window-size=1920,1080");
                    //options.addArguments("--start-maximized");

                    if (PropFileMgmt.getPropertyValue(CentralVars.PropNameBrowserMode).equalsIgnoreCase("headless")) {
                        options.addArguments("--headless");
                    }

                    options.addArguments("--no-sandbox");
                    //options.addArguments("--remote-allow-origins=*");
                    //options.addArguments("--ignore-ssl-errors=yes");
                    //options.addArguments("--ignore-certificate-errors");

                    if (PropFileMgmt.getPropertyValue(CentralVars.PropNameLocation).equalsIgnoreCase("lambdatest")) {

                        options.setPlatformName("Windows 10");
                        options.setBrowserVersion("latest");
                        Map<String, Object> ltOptions = configLambdaTestCapabilities();
                        options.setCapability("LT:Options", ltOptions);

                        String gridURL = configLambdaTestUrl();

                        driver = new RemoteWebDriver(new URL(gridURL), options);

                    } else {

                        driver = new ChromeDriver(options);

                    }
                    break;
                case "firefox":
                    //WebDriverManager.firefoxdriver().clearDriverCache().setup();
                    if (PropFileMgmt.getPropertyValue(CentralVars.PropNameCustomWebDriver).equalsIgnoreCase("true")) {
                        System.setProperty("webdriver.gecko.driver", PropFileMgmt.getPropertyValue(CentralVars.PropNameCustomFirefoxDriverPath));
                    }
                    FirefoxOptions firefoxOptions = new FirefoxOptions();

                    //firefoxOptions.addPreference("dom.block_external_protocol_navigation_from_sandbox", "false");
                    //firefoxOptions.addPreference("media.cubeb.sandbox", "false");
                    //firefoxOptions.addPreference("security.sandbox.content.headless", "false");
                    //firefoxOptions.addPreference("security.sandbox.content.level", "0");
                    //firefoxOptions.addPreference("security.sandbox.socket.process.level", "0");

                    firefoxOptions.addArguments("--width=1920");
                    firefoxOptions.addArguments("--height=1080");

                    if (PropFileMgmt.getPropertyValue(CentralVars.PropNameBrowserMode).equalsIgnoreCase("headless")) {
                        firefoxOptions.addArguments("-headless");
                    }

                    if (PropFileMgmt.getPropertyValue(CentralVars.PropNameLocation).equalsIgnoreCase("lambdatest")) {

                        firefoxOptions.setPlatformName("Windows 10");
                        firefoxOptions.setBrowserVersion("latest");
                        Map<String, Object> ltOptions = configLambdaTestCapabilities();
                        firefoxOptions.setCapability("LT:Options", ltOptions);

                        String gridURL = configLambdaTestUrl();

                        driver = new RemoteWebDriver(new URL(gridURL), firefoxOptions);

                    } else {

                        driver = new FirefoxDriver(firefoxOptions);

                    }
                    break;
                default:
                    break;
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT));

        } catch (Exception e) {
            LogThis.error("Exception e msg = " + e.getMessage());
            LogThis.error("Exception e  = " + e);
        }

    }

    private Map<String, Object> configLambdaTestCapabilities() {
        Map<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("build", "Selenium Cucumber Sample Build");
        ltOptions.put("name", "Selenium Test");

        return ltOptions;
    }

    private String configLambdaTestUrl() {
        String gridURL = "https://"
                + PropFileMgmt.getPropertyValue(CentralVars.PropNameLtUsername)
                + ":"
                + PropFileMgmt.getPropertyValue(CentralVars.PropNameLtAccesskey)
                + "@hub.lambdatest.com/wd/hub";

        //LogThis.info("LambdaTest API : " + gridURL);

        return gridURL;
    }

    public static void openPage(String url) {

        try {
            driver.get(url);

            Thread.sleep(Duration.ofSeconds(2).toMillis());
        } catch (Exception e) {
            LogThis.error("Exception e = " + e.getMessage());
        }
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
