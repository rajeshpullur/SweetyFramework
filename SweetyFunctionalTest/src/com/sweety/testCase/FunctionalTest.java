package com.sweety.testCase;


import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
//import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
//import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.sweety.appFunctions.appFunctions;


public class FunctionalTest {

	
	appFunctions appFunc = new appFunctions();
	
	
	@BeforeClass
	public void login(){
		
		appFunc.login();
		
	}

	@Test(dataProvider="getData")
	public void FuncTest(String lData) throws InterruptedException{
				
		appFunc.newLevelEntry(lData);
		appFunc.report();
		appFunc.verifyReport();
		
	}
	
	@DataProvider
	public Object[][] getData(){
		Object[][] data = new Object[5][1];
		
		data[0][0]="120";
		data[1][0]="200";
		data[2][0]="250";
		data[3][0]="-300";
		data[4][0]="123";
		
		return data;
	}


	@AfterClass
	public void closeApp() {
		appFunc.closeApp();
	}

}
