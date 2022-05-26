package com.hfk.insider.pages;

import com.hfk.insider.base.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;

public class HomePage extends BasePage {
    private static final Logger log = LogManager.getLogger(HomePage.class);
    Base base = new Base();

    @FindBy(how = How.XPATH, using = "//nav[@id='navigation']//a[@href='/']/img")
    public WebElement insiderLogo;

    @FindBy(how = How.XPATH, using = "//div[@id='navbarNavDropdown']//span[.='More']")
    public WebElement moreBtn;

    @FindBy(how = How.XPATH, using = "//div[@id='navbarNavDropdown']//h5[.='Careers']")
    public WebElement careerBtn;

    @FindBy(how = How.XPATH, using = "//a[contains(@id,'accept-all-btn')]")
    public WebElement acceptAllCookies;


    public void clickAcceptAllCookiesBtn(){
        acceptAllCookies.click();
        log.info("Accept all cookies btn tiklandi");
    }

    public void checkIfHomePageOpened(){
        DriverMethods.WaitForElementClickable(insiderLogo);
        if (insiderLogo.isDisplayed()){
            log.info("Insider logo is displayed, therefore the page is opened");
        }else {
            Assert.fail("Insider logo is not displayed, therefore the page is not opened");
        }
    }

    public void clickMoreBtn(){
        moreBtn.click();
        log.info("More btn is clicked");
    }

    public void clickCareerBtn(){
        if (careerBtn.isDisplayed()){
            careerBtn.click();
            log.info("Career btn is clicked");
        }else {
            log.info("Career btn did not appear on the screen");
        }
    }
}
