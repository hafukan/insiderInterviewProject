package com.hfk.insider.listeners;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.util.Objects;

import static com.hfk.insider.base.LocalDriverContext.getWebDriver;

public class ReportListener implements ITestListener {

    @Override
    public synchronized void onTestStart(ITestResult iTestResult) {
        System.out.println("On Test Start");
    }

    @Override
    public synchronized void onTestSuccess(ITestResult iTestResult) {
        System.out.println("On Test Sucess");
    }

    @SneakyThrows
    @Override
    public synchronized void onTestFailure(ITestResult iTestResult) {
        System.out.println("On Test Failure");
        Object testClass = iTestResult.getInstance();
        WebDriver driver = getWebDriver();
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("PUT THE SCREENSHOT PACKAGE PATH HERE/screenShots/failSS.png"));
    }

    @Override
    public synchronized void onTestSkipped(ITestResult iTestResult) {
        System.out.println("On Test Skipped");
    }

    @Override
    public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        System.out.println(" Test Percentage");
    }

    @Override
    public synchronized void onStart(ITestContext iTestContext) {
        System.out.println("On Test Start");
    }

    @Override
    public synchronized void onFinish(ITestContext iTestContext) {
        System.out.println("On Test Finish");
    }
}
