package com.amadeus.selenium.sqmobile.test;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.amadeus.selenium.common.PageFactory;
import com.amadeus.selenium.sqmobile.helper.HelperWelcome;
import com.amadeus.selenium.sqmobile.page.availabililty.OutBoundAvailPage;
import com.amadeus.selenium.sqmobile.page.confirmation.ConfirmationPage;
import com.amadeus.selenium.sqmobile.page.home.HomePage;
import com.amadeus.selenium.sqmobile.page.paxinfo.customers.EgyptAirPaxInfoPage;
import com.amadeus.selenium.sqmobile.page.payment.customers.EgyptAirPaymentPage;
import com.amadeus.selenium.sqmobile.page.review.FareReviewPage;
import com.amadeus.selenium.sqmobile.page.search.SearchPage;

/**
 * A sample test for Booking Flow
 *
 * @author bsingh
 *
 */
public class Test_EgyptAir_Sample_Booking_Flow_OW extends SeleniumSQTest {

  // Local Setup -- Required only during maintenance
  @Override
  public void localSetUp() throws Exception {
    // comment this once coding is done
    setDriverClass(FirefoxDriver.class);
    setBaseUrl("http://nceetvqanlb24.dev.amadeus.net");
    setDebugMode(false);
  }

  @Before
  public void resizeBrowser() {
    getDriverInstance().manage().window().setSize(new Dimension(600, 800));
  }

  @Test
  public void scenario() throws Exception {

    HelperWelcome.openWelcomePage();

    if (!getDriverInstance().getCurrentUrl().contains(getBaseUrl())) {
      HelperWelcome.openWelcomePage();
    }

    HomePage homePage = PageFactory.getPageObject(HomePage.class);

    SearchPage searchPage = homePage.sqMobileMenu.actionSearch(com.amadeus.selenium.sqmobile.menu.CommonMenuPage.Customer.EGYPTAIR);
    // searchPage.fillSearchPage();
    searchPage.fillSearchPageforAjaxList();
    searchPage.clickSearchButton();

    OutBoundAvailPage outBoundAvailPage = PageFactory.getPageObject(OutBoundAvailPage.class);
    outBoundAvailPage.selectDirectFlight();

    FareReviewPage fareReviewPage = PageFactory.getPageObject(FareReviewPage.class);
    fareReviewPage.validateFareReviewPage();

    EgyptAirPaxInfoPage paxInfoPage = (EgyptAirPaxInfoPage)fareReviewPage
        .actionContinue(com.amadeus.selenium.sqmobile.page.review.FareReviewPage.Customer.EGYPTAIR);
    paxInfoPage.fillPaxInfo();
    paxInfoPage.fillContactInfo("Mobile", "", "4566236589", "email@email.com", "+65", "4563253652");

    EgyptAirPaymentPage paymentPage = (EgyptAirPaymentPage)paxInfoPage.continuePayment("Visa");
    // paymentPage.fillPaymentDetail("AddressLine-1 ", "Address Line 2", "City", "sa", "123454", "Singapore");

    ConfirmationPage confirmationPage = paymentPage.clickContinue();
    confirmationPage.validateConfirmationPage();

    String failedSteps = this.getFailedSteps();
    if (failedSteps != null && failedSteps.length() > 0) {
      reporter.fail("Validation(s) failed");
    }
  }
}