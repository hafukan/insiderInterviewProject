package com.hfk.insider.base;

import org.openqa.selenium.WebDriver;

public class LocalDriverContext {
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    public static WebDriver getWebDriver() {
        return driverThread.get();
    }

    public static void setWebDriver(WebDriver driver) {
        driverThread.set(driver);
    }
}
