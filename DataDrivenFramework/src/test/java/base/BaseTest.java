package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import utilities.DbManager;
import utilities.ExcelReader;
import utilities.MonitoringMail;

public class BaseTest {

	/*
	 * WebDriver - done 
	 * TestNg - done
	 *  Database - done
	 *   Mail - done
	 *    Logs - done 
	 *    Properties -
	 * done 
	 * Excel - done 
	 * Keywords - done
	 * Screenshots 
	 * Reports 
	 * Listeners
	 * 
	 */

	public static WebDriver driver;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\testdata.xlsx");
	public static Properties OR = new Properties();
	public static Properties Config = new Properties();
	public static FileInputStream fis;
	public static MonitoringMail mail = new MonitoringMail();
	public static WebDriverWait wait;

	@BeforeSuite
	public void setUp() {

		if (driver == null) {

			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				Config.load(fis);
				log.debug("Config properties file loaded");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				log.debug("OR properties file loaded");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (Config.getProperty("browser").equals("firefox")) {

				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\geckodriver.exe");
				driver = new FirefoxDriver();
				log.debug("Firefox Launched !!!");

			} else if (Config.getProperty("browser").equals("chrome")) {

				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\chromedriver.exe");
				driver = new ChromeDriver();
				log.debug("Chrome Launched !!!");

			} else if (Config.getProperty("browser").equals("ie")) {

				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				log.debug("IE Launched !!!");

			}

			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(Config.getProperty("implicit.wait")),
					TimeUnit.SECONDS);
			driver.get(Config.getProperty("testsiteurl"));
			log.debug("Navigated to : " + Config.getProperty("testsiteurl"));
			try {
				DbManager.setMysqlDbConnection();
				log.debug("DB Connection established !!!");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			wait = new WebDriverWait(driver, Integer.parseInt(Config.getProperty("explicit.wait")));

		}

	}

	public static boolean isElementPresent(String key) {

		try {
			if (key.endsWith("_XPATH")) {

				driver.findElement(By.xpath(OR.getProperty(key)));

			} else if (key.endsWith("_CSS")) {
				driver.findElement(By.cssSelector(OR.getProperty(key)));
			} else if (key.endsWith("_ID")) {
				driver.findElement(By.id(OR.getProperty(key)));
			}
			log.debug("Finding an Element : " + key);
			return true;
		} catch (Throwable t) {

			log.error("Error while finding an Element : " + key);
			log.debug(t.getMessage());
			return false;
			// break the test - Assertions.fail("")
		}

	}

	public void click(String key) {

		try {
			if (key.endsWith("_XPATH")) {

				driver.findElement(By.xpath(OR.getProperty(key))).click();

			} else if (key.endsWith("_CSS")) {
				driver.findElement(By.cssSelector(OR.getProperty(key))).click();
			} else if (key.endsWith("_ID")) {
				driver.findElement(By.id(OR.getProperty(key))).click();
			}
		} catch (Throwable t) {

			log.error("Error while clicking on an Element : " + key);
			log.debug(t.getMessage());
			// break the test - Assertions.fail("")
		}

		log.debug("Clicking on an Element : " + key);
	}

	public void type(String key, String value) {

		try {
			if (key.endsWith("_XPATH")) {

				driver.findElement(By.xpath(OR.getProperty(key))).sendKeys(value);

			} else if (key.endsWith("_CSS")) {
				driver.findElement(By.cssSelector(OR.getProperty(key))).sendKeys(value);
			} else if (key.endsWith("_ID")) {
				driver.findElement(By.id(OR.getProperty(key))).sendKeys(value);
			}
		} catch (Throwable t) {

			log.debug("Error while typing in an Element : " + key);
			log.debug(t.getMessage());

		}

		log.debug("Typing in an Element : " + key + "  entered the value as : " + value);
	}

	@AfterSuite
	public void tearDown() {

		driver.quit();
		log.debug("Test execution completed !!!");
	}

}
