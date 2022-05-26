package com.hfk.insider.base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

import static com.hfk.insider.base.LocalDriverContext.getWebDriver;

public class DriverMethods {

    static long timeOutInSeconds = 30L;
    private static ThreadLocal<WebDriver> driverThread = new ThreadLocal();

    public static synchronized void WaitForElementClickable(final WebElement elementFindBy) {
        try {
            FluentWait<WebDriver> wait = new WebDriverWait(getWebDriver(), Duration.ofSeconds(timeOutInSeconds))
                    .pollingEvery(Duration.ofSeconds(5))
                    .withTimeout(Duration.ofSeconds(timeOutInSeconds))
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(NoSuchElementException.class);
            wait.until(ExpectedConditions.elementToBeClickable(elementFindBy));
        } catch (Throwable t) {
            Assert.fail("Element: " + elementFindBy + " WebElement is not clickable !!");
        }
    }


    public static synchronized boolean waitUntilUrlContains(final String expectedUrl) {
        try {
            FluentWait<WebDriver> wait = new WebDriverWait(getWebDriver(), Duration.ofSeconds(30))
                    .pollingEvery(Duration.ofSeconds(5))
                    .withTimeout(Duration.ofSeconds(30))
                    .ignoring(StaleElementReferenceException.class)
                    .ignoring(NoSuchElementException.class);
            return wait.until(ExpectedConditions.urlContains(expectedUrl));
        } catch (Throwable t) {
            Assert.fail("Expected URL: " + expectedUrl + " Driver URL: " + getWebDriver().getCurrentUrl() + "!!");
            return false;
        }
    }

    public static synchronized void WaitForPageToLoad() {
        try {
            WebDriverWait wait = new WebDriverWait(getWebDriver(), 30);
            JavascriptExecutor jsExecutor = (JavascriptExecutor) getWebDriver();

            ExpectedCondition<Boolean> jsLoad = webDriver -> (jsExecutor).executeScript("return document.readyState").toString().equals("complete");

            //Get JS Ready
            boolean jsReady = jsExecutor.executeScript("return document.readyState").toString().equals("complete");

            if (!jsReady)
                wait.until(jsLoad);
        } catch (Throwable t) {
            Assert.fail("Error occured in DriverContext waitForPageToLoad !!");
        }
    }

    public static synchronized boolean isElementPresent(By by, int timeOutInSeconds) {
        FluentWait wait = (new FluentWait(getWebDriver())).withTimeout(Duration.ofSeconds((long)timeOutInSeconds)).pollingEvery(Duration.ofMillis(500L)).ignoring(NotFoundException.class).ignoring(NoSuchElementException.class);

        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            return true;
        } catch (Exception var5) {
            return false;
        }
    }

    public static void switchTab(String title) {
        Set<String> allTabs = getWebDriver().getWindowHandles();
        Iterator var3 = allTabs.iterator();

        while (var3.hasNext()) {
            String eachTab = (String) var3.next();
            getWebDriver().switchTo().window(eachTab);
            if (getWebDriver().getTitle().contains(title)) {
                break;
            }
        }
    }

}
