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
import com.amadeus.selenium.sqmobile.page.paxinfo.PaxInfoPage.Customer;
import com.amadeus.selenium.sqmobile.page.paxinfo.customers.TAMPaxInfoPage;
import com.amadeus.selenium.sqmobile.page.payment.customers.TAMPaymentPage;
import com.amadeus.selenium.sqmobile.page.review.FareReviewPage;
import com.amadeus.selenium.sqmobile.page.search.customers.TAMSearchPage;
import com.amadeus.selenium.sqmobile.page.welcome.WelcomePage;

/**
 * Test case to validate presence of cybersource for TAM on PURC Page
 * @author rshivaswamy
 *
 */ 
public class IATest_TAM_CyberSource extends SeleniumSQTest{

	  //Local Setup -- Required only during maintenance
	  @Override
	  public void localSetUp() throws Exception {
	    //comment this once coding is done
	    setDriverClass(FirefoxDriver.class);
	    setBaseUrl("http://nceetvqanlb23.dev.amadeus.net/");
	    setDebugMode(false);
	  }

	  @Before
	  public void resizeBrowser(){
	    getDriverInstance().manage().window().setSize(new Dimension(600,800));
	  }


	  @Test
	  public void scenario() throws Exception {

	    WelcomePage welcomepage = HelperWelcome.openWelcomePage();
	    //HomePage homePage =PageFactory.getPageObject(HomePage.class);
	    HomePage homePage = welcomepage.changeLocalPreferences("Iceland", "GB" , false);

	    TAMSearchPage searchPage= (TAMSearchPage)homePage.sqMobileMenu.actionSearch(com.amadeus.selenium.sqmobile.menu.CommonMenuPage.Customer.TAM);

	    searchPage.fillSearchPage();
	    searchPage.clickSearchButton();

	    OutBoundAvailPage outBoundAvailPage = PageFactory.getPageObject(OutBoundAvailPage.class);
	    outBoundAvailPage.selectDirectFlight();

	    FareReviewPage fareReviewPage = PageFactory.getPageObject(FareReviewPage.class);
	    fareReviewPage.validateFareReviewPage();

	    TAMPaxInfoPage paxInfoPage = (TAMPaxInfoPage)fareReviewPage.actionContinue(com.amadeus.selenium.sqmobile.page.review.FareReviewPage.Customer.TAM);
	    paxInfoPage.fillPaxInfo();
	    paxInfoPage.fillContactInfo("Mobile", "", "55644122454", "emaIil@email.com", "+65", "5245413345");


	    TAMPaymentPage paymentPage =  (TAMPaymentPage)paxInfoPage.continuePayment(Customer.TAM);
	    paymentPage.fillPaymentDetail("AddressLine-1 ", "Address Line 2", "City", "sa", "123454", "Singapore");
	    paymentPage.fillSocialSecurityCardInfo();
	    paymentPage.cyberSourceValidation();
	    paymentPage.clickContinue();

	    ConfirmationPage confirmationPage = PageFactory.getPageObject(ConfirmationPage.class);
	    confirmationPage.validateConfirmationPage();

	  }
}
