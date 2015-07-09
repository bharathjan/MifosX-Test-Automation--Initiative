Feature:LoanRBI
Background:
	Given I navigate to mifos
	And I use login folder 
	And I login into mifos site using "Login" excel sheet
		| Login.xlsx  |
	Then I should see logged in successfully
	
@LoanProduct
Scenario Outline: As User creates the product loan using excelsheet

	Given I setup the product loan "Setup"
		| Productloannavigation.xlsx |
	Then I entered the values into product loan from "ProductLoanInput" Sheet & Verified
		| <excelsheet> |
#	Then I should see product loan created successfully	from "ProductLoanOutput" Sheet
#		| <excelsheet> |
 Examples:
		|excelsheet| 
	    #|Loanproduct.xlsx|
	    |1063-MS-EPP-DB-DL-REC-NON-RNI-CTRFD-SAR-MD-TR-1-LateRepayment-Loanproduct.xlsx|
		|1064-MS-EPP-DB-DL-REC-NON-RNI-CTRFD-DL-MD-TR-1-LateRepayment-Loanproduct.xlsx|	
		
@clientcreations		
Scenario: As User creates the loan, disburse, make repayment and verifies  

    Given I setup the clients "Setup"
		| Clientnavigation.xlsx |
	Then I entered the values into client from "Input" sheet & Verified 
		 |Createclient.xlsx|
	
	  When I set up the new create loan from "NewLoanInput" sheet
	  			| Newcreateloan.xlsx|
	  Then I verified the "Summary" details successfully 
	  			| Newcreateloan.xlsx|
	  And I verified the "Repayment Schedule" details successfully 
	  			| Newcreateloan.xlsx|
	  When I make repayment and verified the following tabs
       			|Makerepayment1.xlsx|Input|Summary|Repayment Schedule|Transactions|		
       			|Makerepayment2.xlsx|Input|Summary|Repayment Schedule|Transactions|	
       			|Makerepayment3.xlsx|Input|Summary|Repayment Schedule|Transactions|