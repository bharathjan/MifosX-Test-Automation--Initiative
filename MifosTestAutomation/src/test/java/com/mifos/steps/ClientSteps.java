package com.mifos.steps;

import java.util.List;

import com.mifos.pages.FrontPage;
import com.mifos.pages.MifosWebPage;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ClientSteps {
	
	
	final public FrontPage varFrontPage = new FrontPage();
	public String ExcelSheetPath = null;
	
	@Given("^I setup the clients \"([^\"]*)\"$")
	public void I_setup_the_clients(String sheetName,
			List<String> excelSheetName) throws Throwable {
		ExcelSheetPath = varFrontPage.getClientExcelSheetPath();
		varFrontPage.clientNavigation(ExcelSheetPath, excelSheetName, sheetName);
	}

	@Then("^I entered the values into client from \"([^\"]*)\" sheet & Verified$")
	public void I_entered_the_values_into_client_from_sheet(String sheetName,
			List<String> excelSheetName) throws Throwable{
		varFrontPage.setupClient(ExcelSheetPath, excelSheetName, sheetName);
	}

	@When("^I set up the new create loan from \"([^\"]*)\" sheet$")
	public void I_set_up_the_new_create_loan_from_sheet(String sheetName,
			List<String> excelSheetName)throws Throwable {
		varFrontPage.createNewLoanExcelSheet(ExcelSheetPath, excelSheetName, sheetName);
	}

	@Then("^I verified the \"([^\"]*)\" details successfully$")
	public void I_verified_the_details_successfully(String sheetName,
			List<String> excelSheetName) throws Throwable {
		varFrontPage.verifyTabsdata(ExcelSheetPath, excelSheetName, sheetName);
	}
	
	@Then("^I Navigate to Accounting$")
	public void I_Navigate_to_Accounting() throws Throwable {
		varFrontPage.navigateToUrl(MifosWebPage.BASE_URL
				+ MifosWebPage.getResource("AccountingSearchJournalEntries"));
	}
	
	@Then("^I search with transaction id & verified the details successfully$")
	public void I_search_with_transaction_id_verified_the_details_successfully(DataTable excel) throws Throwable {
		varFrontPage.searchWithTransactinID(ExcelSheetPath, excel);
	}
	

	@Then("^I went back to the client$")
	public void I_went_back_to_the_client() throws Throwable {
		varFrontPage.clickBackToClient();
		Thread.sleep(4000);
	}

	@When("^I make repayment and verified the following tabs$")
	public void I_make_repayment_and_verified_the_following_tabs( DataTable excel)
			throws Throwable {
			varFrontPage.createMakeRepayment(ExcelSheetPath, excel);
	}

	@Then("^I search with \"([^\"]*)\" on mifos$")
	public void I_search_with_on_mifos(String user) throws Throwable {
		varFrontPage.searchUser(user);		
	}

	@Then("^I undo the disbursal$")
	public void I_undo_the_disbursal() throws Throwable {
		varFrontPage.undoDisbursal();
	}
	@Then("^I make reverse of transaction$")
	public void I_make_reverse_of_transaction() throws Throwable {
		varFrontPage.reverseTransaction();
	}
	
/*	@When("^I set up the new savings$")
	public void I_set_up_the_new_savings(List<String> excelsheet) throws Throwable {
		varFrontPage.createNewLoanExcelSheet(excelsheet);  
	}*/
	
/*	@When("^I make prepayloan and verified the following tabs$")
	public void I_make_prepay_and_verified_the_following_tabs(DataTable excel) throws Throwable {
		varFrontPage.createMakeRepayment(excel);
	}*/
}
