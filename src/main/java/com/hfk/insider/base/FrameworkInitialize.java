package com.hfk.insider.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class FrameworkInitialize extends TestBase {

    private static final Logger frameworkInitializeLogger = LogManager.getLogger(FrameworkInitialize.class);

    private static void chmodChange(String filePath) {
        try {
            Set<PosixFilePermission> permissions = new HashSet<>();
            permissions.add(PosixFilePermission.OWNER_READ);
            permissions.add(PosixFilePermission.OWNER_WRITE);
            permissions.add(PosixFilePermission.OWNER_EXECUTE);
            permissions.add(PosixFilePermission.GROUP_EXECUTE);
            permissions.add(PosixFilePermission.GROUP_WRITE);
            permissions.add(PosixFilePermission.GROUP_READ);
            permissions.add(PosixFilePermission.OTHERS_EXECUTE);
            permissions.add(PosixFilePermission.OTHERS_WRITE);
            permissions.add(PosixFilePermission.OTHERS_READ);
            Files.setPosixFilePermissions(Paths.get(filePath), permissions);
            frameworkInitializeLogger.info("Local Driver CHMOD applied... File Path : " + filePath);
        } catch (Throwable t) {
            frameworkInitializeLogger.error(ExceptionUtils.getMessage(t));
            Assert.fail("Error occured on chmodChange..");
        }
    }

    public static Properties readBrowserConfig(String configFileName) throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/" + configFileName + ".properties");
        properties.load(inputStream);
        frameworkInitializeLogger.info(configFileName + ".properties reading is successfully..");
        return properties;
    }

    public static WebDriver InitializeBrowser(BrowserType browserType) throws Throwable {

        RemoteWebDriver driver = null;
        Properties configProperties = readBrowserConfig("frameworkConfig");
        Properties browserConfigProperties = readBrowserConfig(browserType.toString().toLowerCase());

        String os = System.getProperty("os.name").toLowerCase();
        frameworkInitializeLogger.info("Operating System: " + os);
        String driverPath = System.getProperty("user.home") + "/documents/automationDrivers/";

        switch (browserType) {
            case FIREFOX: {
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                FirefoxProfile firefoxProfile = new FirefoxProfile();
                if (browserConfigProperties.isEmpty()) {
                    frameworkInitializeLogger.info(browserType.toString().toLowerCase() + " properties does not contains capabilities..");
                } else {
                    for (String key : browserConfigProperties.stringPropertyNames()) {
                        String value = browserConfigProperties.getProperty(key);
                        firefoxProfile.setPreference(key, value);
                    }
                    firefoxProfile.setAssumeUntrustedCertificateIssuer(false);
                    firefoxOptions.setProfile(firefoxProfile);
                    firefoxOptions.setAcceptInsecureCerts(true);
                }
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver(firefoxOptions);
                frameworkInitializeLogger.info("Firefox driver successfully initialized in LOCAL...");
                break;
            }
            default: {
                ChromeOptions chromeOptions = new ChromeOptions();
                if (browserConfigProperties.isEmpty()) {
                    frameworkInitializeLogger.info(browserType.toString().toLowerCase() + " properties does not contains capabilities..");
                } else {
                    for (String key : browserConfigProperties.stringPropertyNames()) {
                        String value = browserConfigProperties.getProperty(key);
                        if (value == null || value.isEmpty()) {
                            chromeOptions.addArguments(key);
                        } else {
                            chromeOptions.setCapability(key, value);
                        }
                    }
                }
                chromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(chromeOptions);
                frameworkInitializeLogger.info("Chrome driver successfully initialized in LOCAL...");

                break;
            }
        }
        LocalDriverContext.setWebDriver(driver);
        return driver;
    }
}
