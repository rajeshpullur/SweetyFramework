package com.sweety.testCase;


import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.sweety.appFunctions.appFunctions;


public class FunctionalTest {

	
	appFunctions appFunc = new appFunctions();
	
	@BeforeMethod
	public void login(){
		
		appFunc.login();
		
	}

	@Test
	public void FuncTest() throws InterruptedException{
				
		appFunc.newLevelEntry();
		appFunc.report();
	}

	@AfterMethod
	public void closeApp() {
		
		appFunc.closeApp();
	}

}
