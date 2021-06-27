package com.qa.utils;

import java.io.*;
import java.util.Properties;


/**
 * EnvironmentPropertiesReader is to set the environment variable declaration
 * mapping for config properties in the UI test
 */
public class PropertiesManager {

	public static PropertiesManager envProperties=null;
	private Properties configProperties;
	private Properties constants;
	private Properties logMessages;

	public PropertiesManager() {
		configProperties = ConfigFile();	
		constants = ConstantsFile();
		logMessages = LogMessagesFile();
	}
	public Properties ConfigFile() {
		File file = new File(System.getProperty("user.dir") + "/Config Files/Config.properties");
		FileInputStream fileInput = null;
		Properties props = new Properties();
		try {
			fileInput = new FileInputStream(file);
			props.load(fileInput);
		} 
		catch (FileNotFoundException e) {			
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();			
		}
		return props;
	}
	
	public Properties ConstantsFile() {
		File file = new File(System.getProperty("user.dir") + "/Config Files/Constants.properties");
		FileInputStream fileInput = null;
		Properties props = new Properties();
		try {
			fileInput = new FileInputStream(file);
			props.load(fileInput);
		} 
		catch (FileNotFoundException e) {			
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();			
		}
		return props;
	}
	
	public Properties LogMessagesFile() {
		File file = new File(System.getProperty("user.dir") + "/Config Files/LogMessages.properties"); 
		FileInputStream fileInput = null;
		Properties props = new Properties();
		try {
			fileInput = new FileInputStream(file);
			props.load(fileInput);
		} 
		catch (FileNotFoundException e) {			
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();			
		}
		return props;
	}
		
	public static PropertiesManager getInstance() {
		if (envProperties == null) {
			envProperties = new PropertiesManager();
		}
		return envProperties;
	}

	public String getConfig(String key) {
		return configProperties.getProperty(key);
	}
	
	public String getConstant(String key) {
		return constants.getProperty(key);
	}
	
	public String getLogMessage(String key) {
		return logMessages.getProperty(key);
	}

}
