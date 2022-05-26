package com.hfk.insider;

import com.hfk.insider.base.Base;
import com.hfk.insider.base.CurrentPageContext;
import com.hfk.insider.base.FrameworkInitialize;
import com.hfk.insider.pages.CareerPage;
import com.hfk.insider.pages.HomePage;
import com.hfk.insider.pages.QualityAssurancePage;
import org.testng.annotations.Test;

public class InsiderTestCase extends FrameworkInitialize {

    @Test
    public void applyQAJob() throws InterruptedException {
        Base base = new Base();



        CurrentPageContext.setCurrentPage(base.GetInstance(HomePage.class));
        CurrentPageContext.getCurrentPage().As(HomePage.class).checkIfHomePageOpened();

        CurrentPageContext.getCurrentPage().As(HomePage.class).clickAcceptAllCookiesBtn();

        CurrentPageContext.getCurrentPage().As(HomePage.class).clickMoreBtn();

        CurrentPageContext.getCurrentPage().As(HomePage.class).clickCareerBtn();

        CurrentPageContext.setCurrentPage(base.GetInstance(CareerPage.class));

        CurrentPageContext.getCurrentPage().As(CareerPage.class).clickSeeAllTeamsBtn();

        CurrentPageContext.getCurrentPage().As(CareerPage.class).clickQualityAssurance();

        CurrentPageContext.setCurrentPage(base.GetInstance(QualityAssurancePage.class));

        CurrentPageContext.getCurrentPage().As(QualityAssurancePage.class).clickSeeAllQAJobs();

        CurrentPageContext.getCurrentPage().As(QualityAssurancePage.class).setLocationFilter();

        CurrentPageContext.getCurrentPage().As(QualityAssurancePage.class).getTheNumberResults();

        CurrentPageContext.getCurrentPage().As(QualityAssurancePage.class).checkIfJobsContainsRequiredTexts();

        CurrentPageContext.getCurrentPage().As(QualityAssurancePage.class).clickApplyNowBtn();

        CurrentPageContext.getCurrentPage().As(QualityAssurancePage.class).checkIfRedirectedToLeverApplicationPage();

    }
}
