/**
 *
 */
package com.mifos.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import com.ibm.icu.text.NumberFormat;
import com.mifos.testing.framework.webdriver.LazyWebElement;
import cucumber.api.DataTable;

//import org.jopendocument.dom.spreadsheet.MutableCell;
//import org.jopendocument.dom.spreadsheet.Sheet;
//import org.jopendocument.dom.spreadsheet.SpreadSheet;
/**
 * @author salma
 * 
 */
public class FrontPage extends MifosWebPage {

	Set<String> setTransactionID = new TreeSet<String>();
	String value = "";
	public String rowval;

	// //////// login /////////

	public void loginExcelSheet(String loginExcelSheetPath,
			List<String> excelSheetName, String sheetName) throws Throwable {
		try {

			parseExcelSheet(loginExcelSheetPath, excelSheetName, sheetName);
			clickButton(getResource("frontend.login.signup"), "id");

			Thread.sleep(getResourceKey("extralarge"));
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}

	}

	public void submitLogin(String key, String value) {
		HashMap<String, String> logindetails = new HashMap<String, String>();
		logindetails.put("frontend.login." + key, value);
		submitIDValues(logindetails);

	}

	public String getLoginExcelSheetPath() {
		// TODO Auto-generated method stub
		return getResource("loginfolder");
	}

	public String getProductExcelSheetPath() {
		// TODO Auto-generated method stub
		return getResource("productfolder");
	}

	public String getClientExcelSheetPath() {
		// TODO Auto-generated method stub
		return getResource("clientfolder");
	}

	public String parseDate(XSSFCell cell2) throws ParseException {

		DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
		Date date = cell2.getDateCellValue();
		return dateFormat.format(date);
	}

	public String parseExcelSheet(String excelSheetPath,
			List<String> excelsheet, String sheetname) {
		XSSFSheet sheet = null;

		try {
			for (String excelname : excelsheet) {
				XSSFCell cell1 = null;
				XSSFCell cell2 = null;
				FileInputStream file = new FileInputStream(new File(
						excelSheetPath + "/" + excelname));
				XSSFWorkbook workbook = new XSSFWorkbook(file);
				sheet = workbook.getSheet(sheetname);
				System.out.println("Opened file name :" + excelname
						+ " with sheet " + sheetname);
				Iterator<Row> rowIterator = sheet.iterator();
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();

					Iterator<Cell> cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						cell1 = (XSSFCell) cellIterator.next();
						System.out.println("Cell One ... key="
								+ cell1.getRichStringCellValue());
						String key = cell1.getRichStringCellValue().toString();
						if (!cellIterator.hasNext()) {
							System.out.println("No Such Element");
						} else {
							// cell iterator by calling its next method
							cell2 = (XSSFCell) cellIterator.next();

							switch (key) {
							// Client Creation
							case "NavigateURL":
								System.out.println(cell2);
								navigateToUrl(MifosWebPage.BASE_URL + cell2);
								Thread.sleep(getResourceKey("medium"));
								break;

							case "clickonclient":
								clickButton(
										getResource("frontend.clients.clients."
												+ cell2), "xpath");
								Thread.sleep(getResourceKey("medium"));
								break;

								// Loan Product creation
							case "clickonLoanproduct":
								clickButton(
										getResource("frontend.admin.products."
												+ cell2), "xpath");
								Thread.sleep(getResourceKey("small"));
								break;
							case "createLoanproduct":
								String cell2value = cell2
								.getRichStringCellValue().toString();
								if (cell2value.equals("createloanproduct")) {
									clickButton(
											getResource("frontend.admin.products.loanproducts."
													+ cell2), "xpath");

								} else {
									clickButton(
											getResource("frontend.admin.products.charges."
													+ cell2), "xpath");
								}
								Thread.sleep(getResourceKey("medium"));
								break;
							}

							switch (cell2.getCellType()) {
							case Cell.CELL_TYPE_NUMERIC:
								int i = (int) cell2.getNumericCellValue();
								value = String.valueOf(i);

								if (excelname != null
										&& excelname
										.equalsIgnoreCase("Login.xlsx")) {
									submitLogin(key, value);
								}

								if (excelname != null
										&& excelname
										.equalsIgnoreCase("Createclient.xlsx")) {
									if (HSSFDateUtil.isCellDateFormatted(cell2)) {
										value = parseDate(cell2);
										createClient(key, value);
									} else {
										createClient(key, value);
									}
								}

								if (excelname != null
										&& excelname.contains("Loanproduct")) {
									if (HSSFDateUtil.isCellDateFormatted(cell2)) {
										value = parseDate(cell2);
										createLoanProduct(key, value);
									} else {
										createLoanProduct(key, value);
									}
								}

								if (excelname != null
										&& excelname.contains("Newcreateloan")
										|| excelname.contains("Makerepayment")) {
									if (HSSFDateUtil.isCellDateFormatted(cell2)) {
										value = parseDate(cell2);
										createNewLoan(key, value);
									} else {
										createNewLoan(key, value);
									}

								}
								break;

							case Cell.CELL_TYPE_STRING:
								System.out.println("Cell Two ... value="
										+ cell2.getRichStringCellValue());
								value = cell2.getRichStringCellValue()
										.toString();
								if (excelname != null
										&& excelname
										.equalsIgnoreCase("Login.xlsx")) {
									submitLogin(key, value);
								}
								if (excelname != null
										&& excelname
										.equalsIgnoreCase("Createclient.xlsx")) {
									createClient(key, value);
								}
								if (excelname != null
										&& excelname.contains("Loanproduct")) {
									createLoanProduct(key, value);
								}
								if (excelname != null
										&& excelname.contains("Newcreateloan")
										|| excelname.contains("Makerepayment")) {
									createNewLoan(key, value);
								}
								break;
							}
						}
					}
				}
				// file.close();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	// //////// Loan Product /////////
	public void productNavigation(String productExcelSheetPath,
			List<String> excelsheet, String sheetName) throws Throwable {
		try {
			parseExcelSheet(productExcelSheetPath, excelsheet, sheetName);
			Thread.sleep(getResourceKey("extralarge"));
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}

	}

	public void setupProduct(String productExcelSheetPath,
			List<String> excelsheet, String sheetName) throws Throwable {
		try {
			String verifyResponse = parseExcelSheet(productExcelSheetPath,
					excelsheet, sheetName);
			clickButton(getResource("frontend.admin.createoffice.savebutton"),
					"id");
			((JavascriptExecutor) getWebDriver())
			.executeScript("scroll(500,0);");

			Thread.sleep(getResourceKey("extralarge"));
			verifySuccessMessage(
					"frontend.admin.products.createloan.productname.verified",
					verifyResponse, "css");

		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
	}

	public void createLoanProduct(String key, String value)
			throws InterruptedException {
		HashMap<String, String> loanproduct = new HashMap<String, String>();
		switch (key) {

		case "Cash":
			clickButton(getResource("frontend.admin.products.createloan.cash"),
					"xpath");
			Thread.sleep(getResourceKey("medium"));
			break;
		case "Accrualperiodic":
			clickButton(
					getResource("frontend.admin.products.createloan.periodic"),
					"xpath");
			Thread.sleep(getResourceKey("medium"));
			break;
		case "Accrualupfront":
			clickButton(
					getResource("frontend.admin.products.createloan.upfront"),
					"xpath");
			Thread.sleep(getResourceKey("medium"));
			break;

		case "fundsource":
			/*
			 * clickButton(
			 * getResource("frontend.admin.products.createloan.periodic"),
			 * "xpath");
			 */
			clickButton(getResource("frontend.admin.products.createloan.asset."
					+ key), "xpath");
			HashMap<String, String> fundsource = new HashMap<String, String>();
			fundsource.put("frontend.admin.products.createloan.asset." + key
					+ ".input", value);
			submitXPathValues(fundsource);
			getElement(
					getResource("frontend.admin.products.createloan.asset."
							+ key + ".input"), "xpath").sendKeys(Keys.TAB);
			break;
		case "loanprotfolio":
		case "interestreceivable":
		case "feesreceivable":
		case "penaltiesreceivable":
		case "transferinsuspense":
			clickButton(getResource("frontend.admin.products.createloan.asset."
					+ key), "xpath");
			HashMap<String, String> transferinsuspense = new HashMap<String, String>();
			transferinsuspense.put("frontend.admin.products.createloan.asset."
					+ key + ".input", value);
			submitXPathValues(transferinsuspense);
			getElement(
					getResource("frontend.admin.products.createloan.asset."
							+ key + ".input"), "xpath").sendKeys(Keys.TAB);
			break;
		case "incomefrominterest":
		case "incomefromfees":
		case "incomefrompenalties":
		case "incomefromrecoveryrepayments":
			clickButton(
					getResource("frontend.admin.products.createloan.income."
							+ key), "xpath");
			HashMap<String, String> income = new HashMap<String, String>();
			income.put("frontend.admin.products.createloan.income." + key
					+ ".input", value);
			submitXPathValues(income);
			getElement(
					getResource("frontend.admin.products.createloan.income."
							+ key + ".input"), "xpath").sendKeys(Keys.TAB);
			break;
		case "loseswrittenoff":
			clickButton(
					getResource("frontend.admin.products.createloan.expenses."
							+ key), "xpath");
			HashMap<String, String> loseswrittenoff = new HashMap<String, String>();
			loseswrittenoff.put("frontend.admin.products.createloan.expenses."
					+ key + ".input", value);
			submitXPathValues(loseswrittenoff);
			getElement(
					getResource("frontend.admin.products.createloan.expenses."
							+ key + ".input"), "xpath").sendKeys(Keys.TAB);
			break;
		case "overpaymentliability":
			clickButton(
					getResource("frontend.admin.products.createloan.liabilities."
							+ key), "xpath");
			HashMap<String, String> overpaymentliability = new HashMap<String, String>();
			overpaymentliability.put(
					"frontend.admin.products.createloan.liabilities." + key
					+ ".input", value);
			submitXPathValues(overpaymentliability);
			getElement(
					getResource("frontend.admin.products.createloan.liabilities."
							+ key + ".input"), "xpath").sendKeys(Keys.TAB);
			break;
		case "amortization":
		case "repaideveryfrequency":
		case "nominalinterestratefrequency":
		case "interestmethod":
		case "repaymentstrategy":
		case "interestcalculationperiod":
		case "daysinyear":
		case "daysinmonth":
		case "interestrecalculationcompoundingon":
		case "advancepaymentsadjustmenttype":
		case "preclosureinterestcalculationrule":
		case "frequencyforrecalculateoutstandingprincipal":
		case "currency":
		case "fund":
			selectDropDownvalues("frontend.admin.products.createloan." + key,
					"id", "text", value);
			break;
		case "overduecharges":
		case "charges":
			selectDropDownvalues("frontend.admin.products.createloan." + key,
					"id", "text", value);
			Thread.sleep(getResourceKey("medium"));
			clickButton(getResource("frontend.admin.products.createloan." + key
					+ ".add"), "xpath");
			Thread.sleep(getResourceKey("medium"));
			break;
		case "productname":
		case "shortname":
		case "principaldefault":
		case "principalminimum":
		case "principalmaximum":
		case "numberofrepaymentsminimum":
		case "numberofrepaymentsmaximum":
		case "nominalinterestrateminimum":
		case "nominalinterestratemaximum":
		case "numberofrepaymentsdefault":
		case "nominalinterestratedefault":
		case "decimalplaces":
		case "currencyinmultiplesof":
		case "minimumdaysbetweendisbursalandfirstrepaymentdate":
		case "moratoriumonprincipalpayment":
		case "moratoriumoninterestpayment":
		case "interestfreeperiod":
		case "arrearstolerance":
		case "numberofdaysaloanmaybeoverduebeforemovingintoarrears":
		case "maximumnumberofdaysaloanmaybeoverduebeforebecomingaNPA(nonperformingasset)":
		case "maximumtranchecount":
		case "maximumallowedoutstandingbalance":
		case "mandatoryguarantee":
		case "minimumguaranteefromownfunds":
		case "minimumguaranteefromguarantorfunds":
		case "rapidevery":
		case "frequencyintervalforrecalculation":
		case "startdate":
		case "closedate":
		case "frequencydateforrecalculation":
			loanproduct.put("frontend.admin.products.createloan." + key, value);
			submitIDValues(loanproduct, true);
			break;
		case "description":
			loanproduct.put("frontend.admin.products.createloan." + key, value);
			submitCssValues(loanproduct, true);
			break;
		case "placeguaranteefundson-hold?":
		case "recalculateinterest":
		case "termsvarybasedonloancycle":
		case "allowfixingoftheinstallmentamount":
		case "enablemultipledisbursals":
		case "includeincustomerloancounter":
			boolean checked = value.equals("checked");
			LazyWebElement check = getElement(
					getResource("frontend.admin.products.createloan." + key),
					"xpath");
			if (check.isSelected() != checked) {
				clickButton(getResource("frontend.admin.products.createloan."
						+ key), "xpath");
				Thread.sleep(getResourceKey("medium"));
			}
			break;
			// Charges
		case "chargesappliesto":
		case "chargetimetype":
		case "chargecalculation":
		case "chargepaymentby":
			selectDropDownvalues("frontend.admin.products.createcharge." + key,
					"id", "text", value);
			Thread.sleep(getResourceKey("medium"));
			break;
		case "chargecurrency":
			selectDropDownvalues("frontend.admin.products.createcharge." + key,
					"id", "value", value);
			Thread.sleep(getResourceKey("medium"));
			break;
		case "name":
		case "amount":
			loanproduct.put("frontend.admin.products.createcharge." + key,
					value);
			submitIDValues(loanproduct, true);
			break;
		case "active":
		case "ispenalty":
			boolean Checked = value.equals("checked");
			LazyWebElement ischeck = getElement(
					getResource("frontend.admin.products.createcharge." + key),
					"id");
			if (ischeck.isSelected() != Checked) {
				clickButton(getResource("frontend.admin.products.createcharge."
						+ key), "id");
				Thread.sleep(getResourceKey("medium"));
			}
			break;

		}
	}

	// //////// Client /////////

	public void clientNavigation(String clientExcelSheetPath,
			List<String> excelsheet, String sheetname) throws Throwable {
		try {
			parseExcelSheet(clientExcelSheetPath, excelsheet, sheetname);
			Thread.sleep(getResourceKey("extralarge"));
			// file.close();
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
	}

	public void setupClient(String clientExcelSheetPath,
			List<String> excelsheet, String sheetname) throws Throwable {
		try {
			parseExcelSheet(clientExcelSheetPath, excelsheet, sheetname);

			clickButton(getResource("frontend.admin.createoffice.savebutton"),
					"id");
			((JavascriptExecutor) getWebDriver())
			.executeScript("scroll(500,0);");

			Thread.sleep(getResourceKey("extralarge"));
			verifySuccessMessage("frontend.clientform.name.verified", value,
					"css");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createClient(String key, String value)
			throws InterruptedException {
		switch (key) {
		case "office":
			clickButton(getResource("frontend.clientform." + key), "xpath");
			Thread.sleep(getResourceKey("medium"));
			HashMap<String, String> createClient = new HashMap<String, String>();
			createClient.put("frontend.clientform.searchoffice", value);
			submitCssValues(createClient);
			Thread.sleep(getResourceKey("medium"));
			clickButton(getResource("frontend.clientform.selectfirstoffice"),
					"xpath");
			Thread.sleep(getResourceKey("medium"));
			break;
		case "firstname":
		case "middlename":
		case "lastname":
		case "mobilenumber":
		case "externalid":
			HashMap<String, String> createClient1 = new HashMap<String, String>();
			System.out.println("frontend.clientform." + key);
			createClient1.put("frontend.clientform." + key, value);
			submitIDValues(createClient1, true);
			break;
		case "gender":
		case "clienttype":
		case "clientclassification":
			selectDropDownvalues("frontend.clientform." + key, "id", "text",
					value);
			break;
		case "activationdate":
		case "dateofbirth":
		case "submittedon":
			HashMap<String, String> createClient2 = new HashMap<String, String>();
			createClient2.put("frontend.clientform." + key, value);
			submitIDValues(createClient2, true);
			clickButton(getResource("frontend.clientform.dateofbirthclick"),
					"css");
			Thread.sleep(getResourceKey("small"));
			break;
		case "active":
		case "opensavingsaccount":
			boolean checked = value.equals("checked");
			LazyWebElement check = getElement(
					getResource("frontend.clientform." + key), "id");
			if (check.isSelected() != checked) {
				clickButton(getResource("frontend.clientform." + key), "id");
				Thread.sleep(getResourceKey("medium"));
			}
			break;

		}
	}

	public void createNewLoanExcelSheet(String clientExcelSheetPath,
			List<String> excelsheet, String sheetName)
					throws InterruptedException {

		try {
			parseExcelSheet(clientExcelSheetPath, excelsheet, sheetName);
			Thread.sleep(getResourceKey("extralarge"));
			// file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createNewLoan(String key, String value)
			throws InterruptedException, IOException {
		switch (key) {
		case "Transaction ID":
			HashMap<String, String> Transaction = new HashMap<String, String>();
			System.out.println("frontend.accounting.searchjournal." + key);

			Transaction.put("frontend.accounting.searchjournal." + key, value);
			submitIDValues(Transaction, true);

		case "clickon":
			if (value.equals("approve")) {
				((JavascriptExecutor) getWebDriver())
				.executeScript("scroll(500,0);");
				Thread.sleep(getResourceKey("medium"));
			}
			System.out.println("click" + key);
			clickButton(getResource("frontend.clients.clients." + value),
					"xpath");
			break;
		case "loantrancheclick":
			if (value.contains("plus")) {
				String[] value1 = value.split(" ");
				clickButton(getResource("frontend.clients.clients.plus"),
						"xpath");
				rowval = value1[1];
			}
			break;
		case "principaldefault":
			HashMap<String, String> loanproduct = new HashMap<String, String>();
			loanproduct.put("frontend.admin.products.createloan." + key, value);
			submitIDValues(loanproduct, true);
			break;
		case "principal":
			int rowval1 = Integer.valueOf(rowval) + 1;
			getWebDriver().findElement(
					By.xpath("//*[@id='main']//table[2]/tbody/tr[" + rowval1
							+ "]/td[2]/input")).sendKeys(value);
			break;
		case "expecteddisbursementon":
			getWebDriver().findElement(
					By.xpath("//*[@id='disbursementDetail[" + rowval
							+ "].expectedDisbursementDate']")).sendKeys(value);
			break;
		case "product":
			selectDropDownvalues("frontend.clients.clients.newloan." + key,
					"id", "text", value);
			Thread.sleep(getResourceKey("medium"));
			break;
		case "activatedate":
		case "submittedon":
			HashMap<String, String> submittedon = new HashMap<String, String>();
			submittedon.put("frontend.clients.clients.newloan." + key, value);
			submitIDValues(submittedon, true);
			clickButton(getResource("frontend.clientform.dateofbirthclick"),
					"css");
			break;
		case "submitbutton":
			clickButton(getResource("frontend.admin.createoffice.savebutton"),
					"id");
			Thread.sleep(getResourceKey("large"));
			break;
		case "disbursementon":
			HashMap<String, String> disbursementon = new HashMap<String, String>();
			disbursementon
			.put("frontend.clients.clients.newloan." + key, value);
			submitIDValues(disbursementon, true);
			clickButton(getResource("frontend.clientform.dateofbirthclick"),
					"css");
			break;
		case "installmentamount":
		case "maximumallowedaoutstandingbalance":
			HashMap<String, String> amount = new HashMap<String, String>();
			amount.put("frontend.clients.clients.newloan." + key, value);
			submitIDValues(amount, true);
			break;
		case "approveddate":
			HashMap<String, String> approveddate = new HashMap<String, String>();
			approveddate.put("frontend.clients.clients." + key, value);
			submitIDValues(approveddate, true);
			clickButton(getResource("frontend.clientform.dateofbirthclick"),
					"css");
			Thread.sleep(getResourceKey("medium"));
			clickButton(getResource("frontend.admin.createoffice.savebutton"),
					"id");
			Thread.sleep(6000);
			break;
		case "transactiondate":
			Thread.sleep(getResourceKey("medium"));
			HashMap<String, String> transactiondate = new HashMap<String, String>();
			transactiondate.put(
					"frontend.clients.clients.makerepayment." + key, value);
			submitIDValues(transactiondate, true);
			clickButton(getResource("frontend.clientform.dateofbirthclick"),
					"css");
			Thread.sleep(getResourceKey("medium"));
			break;
		case "actualdisbursedate":
			Thread.sleep(getResourceKey("small"));
			HashMap<String, String> actualdisbursedate = new HashMap<String, String>();
			actualdisbursedate.put("frontend.clients.clients." + key, value);
			submitIDValues(actualdisbursedate, true);
			clickButton(getResource("frontend.clientform.dateofbirthclick"),
					"css");
			Thread.sleep(getResourceKey("medium"));
			clickButton(getResource("frontend.admin.createoffice.savebutton"),
					"id");
			Thread.sleep(getResourceKey("extralarge"));
			break;
		case "transactionamount":
			HashMap<String, String> transactionamount = new HashMap<String, String>();
			transactionamount.put("frontend.clients.clients.makerepayment."
					+ key, value);
			submitIDValues(transactionamount, true);
			break;
		}
	}

	public double parseDecimal(String input) throws ParseException {
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale
				.getDefault());
		ParsePosition parsePosition = new ParsePosition(0);
		Number number = numberFormat.parse(input, parsePosition);

		if (parsePosition.getIndex() != input.length()) {
			throw new ParseException("Invalid input", parsePosition.getIndex());
		}
		return number.doubleValue();
	}

	public void verifyTabsdata(String clientExcelSheetPath,
			List<String> excelsheet, String sheetname)
					throws InterruptedException, IOException, ParseException {

		for (String excelname : excelsheet) {
			String strCellValue = "";
			int sheetIndex = 2;
			int colIndex = 0;
			int rowCount = 0;
			try {
				FileInputStream file = new FileInputStream(new File(
						clientExcelSheetPath + "\\" + excelname));
				XSSFWorkbook workbook = new XSSFWorkbook(file);
				XSSFSheet sheet = workbook.getSheet(sheetname);

				if (sheetname.equals("Disbursement")
						|| sheetname.equals("Repaymentdisbursement")
						|| sheetname.equals("Repayment")) {
					rowCount = 2;
				} else {
					if (sheetname.equals("Summary")) {
						sheetIndex = 2;
					} else if (sheetname.equals("Repayment Schedule")) {
						sheetIndex = 4;
					} else if (sheetname.equals("Transactions")) {
						sheetIndex = 6;
						colIndex = 2;
					}
					getWebDriver().findElement(
							By.xpath("//a[contains(.,'" + sheetname + "')]"))
							.click();
				}

				rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
				System.out.println("row count " + rowCount);
				
				for (int rowCount1 = 1; rowCount1 <= rowCount; rowCount1++) {

					List<WebElement> applicationCol = null;
					if (sheetname.equals("Summary")
							|| sheetname.equals("Repayment Schedule")
							|| sheetname.equals("Transactions")) {

						System.out
						.println(getWebDriver()
								.findElement(
										By.xpath("//*[@id='main']/div[3]/div/div/div/div/div/div[2]/div[3]/div[4]/div/div/div["
												+ sheetIndex
												+ "]/table/tbody/tr["
												+ rowCount1 + "]"))
												.getText());

						setTransactionID
						.add(getWebDriver()
								.findElement(
										By.xpath("//*[@id='main']/div[3]/div/div/div/div/div/div[2]/div[3]/div[4]/div/div/div["
												+ sheetIndex
												+ "]/table/tbody/tr["
												+ rowCount1 + "]/td[1]"))
												.getText());

						System.out.println("Transaction Id = " + setTransactionID);

						applicationCol = getWebDriver()
								.findElements(
										By.xpath("//*[@id='main']/div[3]/div/div/div/div/div/div[2]/div[3]/div[4]/div/div/div["
												+ sheetIndex
												+ "]/table/tbody/tr["
												+ rowCount1 + "]/td"));
						System.out.println("Col count  "
								+ applicationCol.size());

					} else if (sheetname.equals("Disbursement")
							|| sheetname.equals("Repaymentdisbursement")
							|| sheetname.equals("Repayment")) {
						colIndex = 4;
						System.out
						.println(getWebDriver()
								.findElement(
										By.xpath(".//*[@id='main']/div[3]/div/div/div/div/div/div[4]/table/tbody/tr["
												+ rowCount1 + "]"))
												.getText());
						applicationCol = getWebDriver()
								.findElements(
										By.xpath(".//*[@id='main']/div[3]/div/div/div/div/div/div[4]/table/tbody/tr["
												+ rowCount1 + "]/td"));
						System.out.println("Col count  "
								+ applicationCol.size());

					}

					for (; colIndex < applicationCol.size(); colIndex++) {

						double screenVal = 0.0;
						String textVal = applicationCol.get(colIndex).getText();
						DateFormat dateFormat = new SimpleDateFormat(
								"dd MMMM yyyy");
						Date date = null;
						if ((sheet.getRow(rowCount1) == null)
								|| (sheet.getRow(rowCount1).getCell(colIndex) == null)) {
							continue;
						}
						switch (sheet.getRow(rowCount1).getCell(colIndex)
								.getCellType()) {
								case Cell.CELL_TYPE_BLANK:

									break;
								case Cell.CELL_TYPE_NUMERIC:
									if (HSSFDateUtil.isCellDateFormatted(sheet.getRow(
											rowCount1).getCell(colIndex))) {
										date = sheet.getRow(rowCount1)
												.getCell(colIndex).getDateCellValue();
										try {
											Assert.assertEquals(textVal,
													dateFormat.format(date));
										} catch (Throwable e) {
											Assert.fail("Tab Name:" + sheetname
													+ " Row number:" + rowCount1
													+ " Column number:" + colIndex
													+ " Expected result:"
													+ dateFormat.format(date)
													+ " Actual result:" + textVal);
										}
									} else {
										if ((textVal != null)
												&& !(textVal.trim().equals("")))
											screenVal = parseDecimal(applicationCol
													.get(colIndex).getText());
										double value = (double) sheet.getRow(rowCount1)
												.getCell(colIndex)
												.getNumericCellValue();
										strCellValue = String.valueOf(value);
										try {
											Assert.assertEquals(screenVal,
													parseDecimal(strCellValue), 0.0);
										} catch (Throwable e) {
											Assert.fail("Tab Name:" + sheetname
													+ " Row number:" + rowCount1
													+ " Column number:" + colIndex
													+ " Expected result:"
													+ parseDecimal(strCellValue)
													+ " Actual result:" + screenVal);
										}
									}

									break;
								case Cell.CELL_TYPE_STRING:
									strCellValue = sheet.getRow(rowCount1)
									.getCell(colIndex).getStringCellValue();
									try {
										if (textVal.contains("$")
												&& strCellValue.contains("$")) {
											textVal = textVal.substring(
													textVal.indexOf(" ") + 1,
													textVal.length());
											strCellValue = strCellValue.substring(
													strCellValue.indexOf(" ") + 1,
													strCellValue.length());
											Assert.assertEquals(textVal, strCellValue);
										} else {
											Assert.assertEquals(textVal, strCellValue);
										}
									} catch (Throwable e) {
										Assert.fail("Tab Name:" + sheetname
												+ " Row number:" + rowCount1
												+ " Column number:" + colIndex
												+ " Expected result:" + strCellValue
												+ " Actual result:" + textVal);
									}

									break;
						}
					}
				}
				Thread.sleep(getResourceKey("large"));
			} catch (FileNotFoundException fnfe) {
				fnfe.printStackTrace();
			}

			break;
		}

	}

	public void clickBackToClient() {
		clickButton(getResource("frontend.clientform.backtoclientname"),
				"xpath");
	}

	public void createMakeRepayment(String clientExcelSheetPath,
			DataTable payment) throws InterruptedException, IOException,
			ParseException {
		List<List<String>> options = payment.raw();
		for (List<String> option : options) {
			makeRepayment(clientExcelSheetPath, option, option.get(1));
			for (int i = 2; i < option.size(); i++) {
				verifyTabsdata(clientExcelSheetPath, option, option.get(i));
			}
		}
	}

	public void makeRepayment(String clientExcelSheetPath,
			List<String> excelsheet, String sheetName)
					throws InterruptedException {
		try {
			parseExcelSheet(clientExcelSheetPath, excelsheet, sheetName);
			clickButton(getResource("frontend.admin.createoffice.savebutton"),
					"id");
			Thread.sleep(getResourceKey("extralarge"));
			// file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void searchWithTransactinID(String excelSheetPath,
			DataTable transaction) throws InterruptedException, IOException,
			ParseException {
		try {
			List<List<String>> sheetOptions = transaction.raw();
			for (List<String> sheetOption : sheetOptions) {
				
				for (int sheet = 1; sheet < sheetOption.size(); sheet++) {
					int transactionId = sheet - 1;
					getWebDriver()
							.findElement(
									By.xpath("//input[@placeholder='Search by transaction']"))
							.sendKeys(
									Keys.chord(Keys.CONTROL, "a"),
									"L"
											+ setTransactionID.toArray()[transactionId]);
					Thread.sleep(getResourceKey("medium"));
					clickButton(
							getResource("frontend.accounting.searchjournal.transactionid.submit"),
							"xpath");

					if (sheetOption.get(sheet).equals("Disbursement")
							|| sheetOption.get(sheet).equals(
									"Repaymentdisbursement")) {

						verifyTabsdata(excelSheetPath, sheetOption,
								sheetOption.get(sheet));
						clickButton(
								getResource("frontend.accounting.searchjournal.transactionid.Parameters"),
								"xpath");
						Thread.sleep(getResourceKey("medium"));
					} else
						verifyTabsdata(excelSheetPath, sheetOption,
								sheetOption.get(sheet));

				}
			}

			// Iterator<String> IDIterator = transactionID.iterator();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void searchUser(String user) throws InterruptedException {
		getWebDriver().findElement(By.id("search")).sendKeys(user);
		getWebDriver().findElement(By.id("search")).sendKeys(Keys.ENTER);
		Thread.sleep(getResourceKey("extralarge"));
		getWebDriver().findElement(By.xpath(".//div[1]/div/span[2]/a")).click();
		Thread.sleep(getResourceKey("large"));

	}

	public void undoDisbursal() throws InterruptedException {
		clickButton(getResource("frontend.clients.clients.undodisbursal"),
				"xpath");
		clickButton(getResource("frontend.admin.createoffice.savebutton"), "id");
		Thread.sleep(getResourceKey("medium"));
	}

	public void reverseTransaction() throws InterruptedException {
		getWebDriver().findElement(By.xpath("//a[contains(.,'Transactions')]"))
		.click();
		Thread.sleep(getResourceKey("medium"));
		System.out.println(getText(
				"frontend.clients.clients.makerepayment.reversetransaction",
				"Xpath"));
		if (getText(
				"frontend.clients.clients.makerepayment.reversetransaction",
				"Xpath").equals("Repayment")) {
			clickButton(
					getResource("frontend.clients.clients.makerepayment.reversetransaction"),
					"xpath");
			Thread.sleep(getResourceKey("large"));
			clickButton(
					getResource("frontend.clients.clients.transaction.undo"),
					"css");
			Thread.sleep(getResourceKey("large"));
			clickButton(
					getResource("frontend.clients.clients.transaction.secondundo"),
					"xpath");
			Thread.sleep(getResourceKey("large"));
		}
	}

}
