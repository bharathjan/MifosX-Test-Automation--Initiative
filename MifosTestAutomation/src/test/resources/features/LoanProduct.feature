Feature:LoanProduct
Background:
	Given I navigate to mifos
	And I use login folder 
	And I login into mifos site using "Login" excel sheet
		| Login.xlsx  |
	Then I should see logged in successfully

@loanproductcreation	
Scenario Outline: As User creates the product loan using excelsheet

	Given I setup the product loan "Setup"
		| Productloannavigation.xlsx |
	Then I entered the values into product loan from "ProductLoanInput" Sheet & Verified
		| <excelsheet> |
#	Then I should see product loan created successfully	from "ProductLoanOutput" Sheet
#		| <excelsheet> |
 Examples:
		|excelsheet| 
	 #   |Loanproduct.xlsx|
	 #   |1063-MS-EPP-DB-DL-REC-NON-RNI-CTRFD-SAR-MD-TR-1-LateRepayment-Loanproduct.xlsx|
		|1112-MS-EPP-DB-SAR-REC-NON-RNI-CTRFD-DL-MD-TR-1-LateRepayment-LoanproductCopy.xlsx|
	  	
			

		
