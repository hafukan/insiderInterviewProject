package com.hfk.insider.pages;

import com.hfk.insider.base.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import static com.hfk.insider.base.LocalDriverContext.getWebDriver;

public class CareerPage extends BasePage {

    JavascriptExecutor executor = (JavascriptExecutor)getWebDriver();

    private static final Logger log = LogManager.getLogger(CareerPage.class);
    Base base = new Base();

    @FindBy(how = How.XPATH, using = "//div/a[text()='See all teams']")
    public WebElement seeAllTeamsBtn;

    @FindBy(how = How.XPATH, using = "//div/a/h3[text()='Quality Assurance']")
    public WebElement qualityAssurance;





    public void clickSeeAllTeamsBtn(){
        if (seeAllTeamsBtn.isDisplayed()){
            executor.executeScript("arguments[0].click();", seeAllTeamsBtn);
            log.info("See all teams btn is clicked");
        }else {
            log.info("See all teams btn could not be clicked");
        }
    }

    public void clickQualityAssurance(){
        try {
            DriverMethods.WaitForElementClickable(qualityAssurance);
            executor.executeScript("arguments[0].click();", qualityAssurance);
            log.info("Quality Assurance is clicked");
        }catch (Exception e){
            log.info("Quality Assurance could not be clicked");
        }
    }
}
