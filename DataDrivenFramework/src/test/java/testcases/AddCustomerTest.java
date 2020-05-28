package testcases;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import utilities.ExcelReader;
import utilities.TestUtil;

public class AddCustomerTest extends BaseTest {

	@Test(dataProvider = "getData")
	public void addACustomer(String firstName, String lastName, String postCode) {

		click("addCustomerBtn_CSS");
		type("firstName_CSS", firstName);
		type("lastName_CSS", lastName);
		type("postCode_CSS", postCode);
		click("addCust_CSS");

	}

	@DataProvider
	public Object[][] getData() {

		return TestUtil.getData("AddCustomerTest");
	}

}
