package com.hfk.insider.base;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Properties;

import static com.hfk.insider.base.FrameworkInitialize.readBrowserConfig;

public class TestBase {


    private static final Logger testBaseLogger = LogManager.getLogger(TestBase.class);

    public static BrowserType BROWSERTYPE = null;
    public String GRIDURL = null;
    public static String DEVICENAME = null;

    public void initializeConfig(String suiteName, String browser) {
        try {
            Properties configProperties = readBrowserConfig("frameworkConfig");
            BROWSERTYPE = BrowserType.valueOf(browser.toUpperCase());
            GRIDURL = configProperties.getProperty("gridUrl");
            DEVICENAME = configProperties.getProperty("deviceName");
            testBaseLogger.info("TestNG XML values are initialized successfully..");
            testBaseLogger.info("GRIDURL: " + GRIDURL);
            testBaseLogger.info("BROWSERTYPE: " + BROWSERTYPE);
            testBaseLogger.info("SUITE NAME : " + suiteName);
        } catch (Throwable t) {
            testBaseLogger.error(ExceptionUtils.getMessage(t));
            Assert.fail("Error occurred while initializing settings.. Parameters: " + "GridUrl: " + GRIDURL + " BrowserType: " + BROWSERTYPE);
        }
    }

    @BeforeSuite(alwaysRun = true)
    @Parameters(value = {"browser"})
    public void beforeSuite(ITestContext context, String browser) {
        try {
            testBaseLogger.info("Running Before Suite.. Setting XML and FrameworkConfig values..");
            initializeConfig(context.getCurrentXmlTest().getSuite().getName(), browser);
            for (ITestNGMethod method : context.getSuite().getAllMethods()) {
                method.setRetryAnalyzerClass(Retry.class);
            }
            testBaseLogger.info("Before Suite ended successfully...");
        } catch (Throwable t) {
            testBaseLogger.error(ExceptionUtils.getMessage(t));
            Assert.fail("Error occured in Before Suite !!");
        }
    }


    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method) {
        try {
            testBaseLogger.info("Running Before Method..");
            LocalDriverContext.setWebDriver(FrameworkInitialize.InitializeBrowser(BROWSERTYPE));
            LocalDriverContext.getWebDriver().navigate().to("https://useinsider.com/");
            testBaseLogger.info("Driver navigate To URL: https://useinsider.com/");
            LocalDriverContext.getWebDriver().manage().window().maximize();
            LocalDriverContext.getWebDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30L));
            testBaseLogger.info("Before Method ended successfully...");
        } catch (Throwable t) {
            testBaseLogger.error(ExceptionUtils.getMessage(t));
            Assert.fail("Error occured in Before Method !!");
        }
    }


    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        testBaseLogger.info("Running After Method.. Preparing reports..");
        try {
            if (LocalDriverContext.getWebDriver() != null) {
                LocalDriverContext.getWebDriver().quit();
                testBaseLogger.info("Successfully quit Webdriver..");
            }
            testBaseLogger.info("Report is prepared and ready to check results..");
        } catch (Throwable t) {
            testBaseLogger.error(ExceptionUtils.getMessage(t));
            Assert.fail("Error occured in After Method !!");
        }
    }
}
