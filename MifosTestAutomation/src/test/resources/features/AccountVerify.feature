Feature:LoanRBI
Background:
	Given I navigate to mifos
	And I use login folder 
	And I login into mifos site using "Login" excel sheet
		| Login.xlsx  |
	Then I should see logged in successfully

@AccountVerify		
Scenario: As User creates the loan, disburse, make repayment and verifies  

    Given I setup the clients "Setup"
		| Clientnavigation.xlsx |
	Then I entered the values into client from "Input" sheet & Verified 
		 |Createclient.xlsx|
	
	  When I set up the new create loan from "NewLoanInput" sheet
	  			| Newcreateloan.xlsx|
	   And I verified the "Transactions" details successfully 
	  			| Newcreateloan.xlsx|
	   When I make repayment and verified the following tabs
       			|Makerepayment1.xlsx|Input|Transactions|		
       		#	|Makerepayment2.xlsx|Input|Summary|Repayment Schedule|Transactions|	
       		#	|Makerepayment3.xlsx|Input|Summary|Repayment Schedule|Transactions|
       Then I Navigate to Accounting
	   And I search with transaction id & verified the details successfully
	   			| Newcreateloan.xlsx|Disbursement|Repaymentdisbursement|Repayment|		
       			