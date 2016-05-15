package com.sweety.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class UtilityLib {

	public Properties loadProperties(String filePath) {

		Properties loadConfig = new Properties();
		try {
			loadConfig.load(new FileInputStream(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return loadConfig;

	}
	
	public Properties readRepository(String filePath){
		Properties loadConfig = new Properties();
		try {
			loadConfig.load(new FileInputStream(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return loadConfig;
	}
}
