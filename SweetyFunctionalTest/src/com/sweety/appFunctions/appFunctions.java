package com.sweety.appFunctions;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.sweety.utility.UtilityLib;

public class appFunctions {

	public WebDriver driver;
	public WebDriverWait wait;
	UtilityLib utilFuncs = new UtilityLib();
	Properties readConfig = utilFuncs
			.loadProperties("C:\\config.properties");
	Properties readRepo = utilFuncs
			.readRepository("C\\repository.properties");

	public void login() {

		driver = new FirefoxDriver();
		driver.get(readConfig.getProperty("appUrl"));
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, 20);
		wait.until(
				ExpectedConditions.presenceOfElementLocated(By.id(readRepo
						.getProperty("userid")))).sendKeys(
				readConfig.getProperty("userName"));
		driver.findElement(By.id(readRepo.getProperty("password"))).sendKeys(
				readConfig.getProperty("password"));
		driver.findElement(By.xpath(readRepo.getProperty("loginButton")))
				.click();
		String loginMessage = driver.findElement(
				By.xpath(readRepo.getProperty("loginMessage"))).getText();
		loginMessage.equalsIgnoreCase(readConfig.getProperty("loginMessage"));
	}

	public void newLevelEntry() {

		try {
			driver.findElement(By.xpath(readRepo.getProperty("level"))).click();
			wait.until(
					ExpectedConditions.presenceOfElementLocated(By
							.xpath(readRepo.getProperty("addNew")))).click();
			wait.until(
					ExpectedConditions.presenceOfElementLocated(By.id(readRepo
							.getProperty("entryLevel")))).sendKeys(
					readConfig.getProperty("levelEntry"));
			driver.findElement(By.xpath(readRepo.getProperty("submit")))
					.click();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			String succMessage = driver.findElement(
					By.xpath(readRepo.getProperty("submitMessage"))).getText();
			System.out.println("Success Message is : " + succMessage);
			boolean message = succMessage.equalsIgnoreCase(readConfig
					.getProperty("successMessage"));
			System.out.println("Success Message matched with Expected : "
					+ message);
		} catch (Exception e) {
			String errorMsg = driver.findElement(
					By.xpath(readRepo.getProperty("errorMessage"))).getText();
			boolean errMsg = errorMsg.equalsIgnoreCase(readConfig
					.getProperty("errorMessage"));
			System.out.println("Error Message is : " + errorMsg);
			System.out.println("Error Message matched with expected message : "
					+ errMsg);

		}
	}

	public void report() throws InterruptedException {

		driver.findElement(By.xpath(readRepo.getProperty("report"))).click();
		wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath(readRepo
						.getProperty("monthlyReport")))).click();
		Thread.sleep(5000);
		String reportHeading = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath(readRepo
						.getProperty("reportName")))).getText();
		reportHeading.equalsIgnoreCase(readConfig.getProperty("reportName"));
		String reportDate = driver.findElement(
				By.xpath(readRepo.getProperty("reportDate"))).getText();
		reportDate.equalsIgnoreCase(readConfig.getProperty("reportDate"));

		System.out.println("Report Name = " + reportHeading);
		System.out.println("Report Date = " + reportDate);

	}

	public void closeApp() {
		driver.quit();
	}

}
