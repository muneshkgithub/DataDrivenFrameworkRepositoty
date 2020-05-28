package testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import base.BaseTest;

public class OpenAccountTest extends BaseTest {
	
	@Test
	public void openAccount()
	{
		click("button.btn:nth-child(2)");
		WebElement weCustomer = driver.findElement(By.cssSelector(OR.getProperty("customer_CSS")));
		Select selCustomer = new Select(weCustomer);
		selCustomer.selectByValue("Harry Potter");
		
		WebElement weCurrency = driver.findElement(By.cssSelector(OR.getProperty("currency_CSS")));
		Select selCurrency = new Select(weCustomer);
		selCurrency.selectByValue("doller");
		click("btnProcess_CSS");
		Alert altAccount = driver.switchTo().alert();
		altAccount.accept();
		System.out.println("Account created successfully");
		
	}

}
