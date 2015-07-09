Feature:Client
Background:
	Given I navigate to mifos
	And I use login folder 
	And I login into mifos site using "Login" excel sheet
		| Login.xlsx  |
	Then I should see logged in successfully

@clientcreations		
Scenario: As User creates the loan, disburse and verifies the tabs and again undo disburse 

    Given I setup the clients "Setup"
		| Clientnavigation.xlsx |
	Then I entered the values into client from "Input" sheet & Verified 
		 |Createclient.xlsx|
	
#	  When I set up the new create loan from "NewLoanInput" sheet
#	  			| Newcreateloan.xlsx|
#	  Then I verified the "Summary" details successfully 
#	  			| Newcreateloan.xlsx|
#	  And I verified the "Repayment Schedule" details successfully 
#	  			| Newcreateloan.xlsx|
#	  When I make repayment and verified the following tabs
#      			|Makerepayment1.xlsx|Input|Summary|Repayment Schedule|Transactions|