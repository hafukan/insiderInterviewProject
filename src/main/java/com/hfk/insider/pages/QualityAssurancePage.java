package com.hfk.insider.pages;

import com.hfk.insider.base.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

import static com.hfk.insider.base.LocalDriverContext.getWebDriver;

public class QualityAssurancePage extends BasePage {
    private static final Logger log = LogManager.getLogger(QualityAssurancePage.class);
    int number;
    String results;

    @FindBy(how = How.XPATH, using = "//div/a[text()='See all QA jobs']")
    public WebElement seeAllQAJobs;

    @FindBy(how = How.XPATH, using = "//span[contains(@id,'filter-by-location')]")
    public WebElement locationFilter;

    @FindBy(how = How.XPATH, using = "//li[text()='Istanbul, Turkey']")
    public WebElement istanbulTurkeyOption;

    @FindBy(how = How.XPATH, using = "//p[@id='resultCounter']/span[@class='totalResult']")
    public WebElement totalResult;

    @FindBy(how = How.XPATH, using = "(//a[text()='Apply Now'])[1]")
    public WebElement applyNow;

    @FindBy(how = How.XPATH, using = "")
    public WebElement applyForThisJob;







    public void clickSeeAllQAJobs(){
        DriverMethods.WaitForElementClickable(seeAllQAJobs);
        if (seeAllQAJobs.isDisplayed()){
            seeAllQAJobs.click();
            log.info("See all QA jobs is clicked");
        }else {
            log.info("See all QA jobs could not be clicked");
        }
    }

    public void setLocationFilter(){
        DriverMethods.WaitForElementClickable(locationFilter);
        locationFilter.click();
        istanbulTurkeyOption.click();
    }

    public void getTheNumberResults() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        results = totalResult.getText();
        number = Integer.parseInt(results);

    }

    public void checkIfJobsContainsRequiredTexts(){
        for (int i = 1; i < number + 1; i++) {
            if (DriverMethods.isElementPresent(By.xpath("(//div[contains(@class,'position-list') and @data-location='istanbul-turkey' and @data-team='qualityassurance'])["+i+"]"), 5)){
                log.info(i+"th element has the required texts");
            }else {
                Assert.fail(i+"th element does not have the required texts");
            }
        }
    }

    public void clickApplyNowBtn(){
        JavascriptExecutor executor = (JavascriptExecutor)getWebDriver();
        executor.executeScript("arguments[0].click();", applyNow);
    }

    public void checkIfRedirectedToLeverApplicationPage(){
        DriverMethods.WaitForPageToLoad();
        DriverMethods.switchTab("Insider. -");
        String url = getWebDriver().getCurrentUrl();
        if (url.contains("jobs.lever")){
            log.info("Redirected to Lever Application Form Page");
        }else {
            Assert.fail("Did not redirect");
        }
    }
}
