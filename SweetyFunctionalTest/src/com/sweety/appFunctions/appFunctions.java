package com.sweety.appFunctions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.google.common.collect.MultimapBuilder.ListMultimapBuilder;
import com.sweety.utility.UtilityLib;

public class appFunctions {

	public WebDriver driver;
	public WebDriverWait wait;
	UtilityLib utilFuncs = new UtilityLib();

	Properties readLoginData = utilFuncs
			.loadProperties("C:\\SweetyProject\\TestData\\loginData.properties");
	Properties readLevelData = utilFuncs
			.loadProperties("C:\\SweetyProject\\TestData\\levelEntryData.properties");
	Properties readReportData = utilFuncs
			.loadProperties("C:\\SweetyProject\\TestData\\ReportData.properties");

	Properties readobjLogin = utilFuncs
			.readRepository("C:\\SweetyProject\\Repository\\login.properties");

	Properties readobjEntry = utilFuncs
			.readRepository("C:\\SweetyProject\\Repository\\levelEntry.properties");

	Properties readobjReport = utilFuncs
			.readRepository("C:\\SweetyProject\\Repository\\Report.properties");

	public void login() {

		try {
			driver = new FirefoxDriver();
			driver.get(readLoginData.getProperty("appUrl"));
			driver.manage().window().maximize();
			wait = new WebDriverWait(driver, 20);
			wait.until(
					ExpectedConditions.presenceOfElementLocated(By
							.id(readobjLogin.getProperty("userid")))).sendKeys(
					readLoginData.getProperty("userName"));
			driver.findElement(By.id(readobjLogin.getProperty("password")))
					.sendKeys(readLoginData.getProperty("password"));
			driver.findElement(
					By.xpath(readobjLogin.getProperty("loginButton"))).click();
			String loginMessage = driver.findElement(
					By.xpath(readobjLogin.getProperty("loginMessage")))
					.getText();
			Assert.assertEquals(loginMessage,
					readLoginData.getProperty("loginMessage"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public void newLevelEntry(String data) {

		try {
			driver.findElement(By.xpath(readobjEntry.getProperty("level")))
					.click();
			wait.until(
					ExpectedConditions.presenceOfElementLocated(By
							.xpath(readobjEntry.getProperty("addNew"))))
					.click();
			wait.until(
					ExpectedConditions.presenceOfElementLocated(By
							.id(readobjEntry.getProperty("entryLevel"))))
					.sendKeys(data);

			driver.findElement(By.xpath(readobjEntry.getProperty("submit")))
					.click();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			String succMessage = driver.findElement(
					By.xpath(readobjEntry.getProperty("submitMessage")))
					.getText();
			System.out.println("Success Message is : " + succMessage);
			Assert.assertEquals(succMessage,
					readLevelData.getProperty("successMessage"));
			
			String entryValue = driver.findElement(By.xpath(readobjEntry.getProperty("entryValue"))).getText();
			Assert.assertEquals(entryValue, data+" mg/dl");

		} catch (Exception e) {
			String errorMsg = driver.findElement(
					By.xpath(readobjEntry.getProperty("errorMessage")))
					.getText();
			Assert.assertEquals(errorMsg,
					readLevelData.getProperty("errorMessage"));

			System.out.println("Error Message is : " + errorMsg);

		}
	}

	public void report() throws InterruptedException {

		driver.findElement(By.xpath(readobjReport.getProperty("report")))
				.click();
		wait.until(
				ExpectedConditions.presenceOfElementLocated(By
						.xpath(readobjReport.getProperty("monthlyReport"))))
				.click();
		Thread.sleep(5000);
		String reportHeading = wait.until(
				ExpectedConditions.presenceOfElementLocated(By
						.xpath(readobjReport.getProperty("reportName"))))
				.getText();
		Assert.assertEquals(reportHeading,
				readReportData.getProperty("reportName"));

		String reportDate = driver.findElement(
				By.xpath(readobjReport.getProperty("reportDate"))).getText();
		Assert.assertEquals(reportDate,
				readReportData.getProperty("reportDate"));

		System.out.println("Report Name = " + reportHeading);
		System.out.println("Report Date = " + reportDate);

	}

	public void verifyReport() {
		List<Integer> allValues = new ArrayList<Integer>();
		List<WebElement> allEntries = driver.findElements(By
				.xpath(readobjReport.getProperty("allEntries")));

		try {
			for (int a = 0; a < allEntries.size(); a++) {
				allValues.add(Integer.parseInt(allEntries.get(a).getText()));
				System.out.println("verifyReport() : " + allValues.get(a));
			}

			Integer maxVal = Collections.max(allValues);
			Integer minVal = Collections.min(allValues);

			System.out.println("Max value is : " + maxVal);
			System.out.println("Min Value is : " + minVal);

			int sum = 0;
			Iterator<Integer> iterator = allValues.iterator();
			while (iterator.hasNext()) {
				int val = iterator.next();
				sum = sum + val;
			}

			// calculate average value
			Integer average = sum / allValues.size();
			
			System.out
					.println("verifyReport() : [ Rounded ] Average value of array elements is : "
							+ Math.round(average));

			Integer minValue = Integer.parseInt(driver.findElement(
					By.xpath(readobjReport.getProperty("minValue"))).getText());
			Integer maxValue = Integer.parseInt(driver.findElement(
					By.xpath(readobjReport.getProperty("maxValue"))).getText());
			Integer avgValue = Integer.parseInt(driver.findElement(
					By.xpath(readobjReport.getProperty("avgValue"))).getText());
			
			Assert.assertEquals(minValue, minVal);
			Assert.assertEquals(maxValue, maxVal);
			Assert.assertEquals(avgValue, average);

			System.out.println("Minimum value : " + minValue);
			System.out.println("Maximum value : " + maxValue);
			System.out.println("Average value : " + avgValue);
		} catch (Exception e) {
			System.out.println("verifyReport() : Exception Occured"
					+ e.getMessage());
			e.printStackTrace();
		}

	}

	public void closeApp() {
		driver.quit();
	}

}
