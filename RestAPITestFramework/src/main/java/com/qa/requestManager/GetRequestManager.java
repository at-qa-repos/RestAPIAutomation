package com.qa.requestManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import com.qa.utils.PropertiesManager;

import io.restassured.response.Response;

public class GetRequestManager {

	HashMap<String, String> testData;

	public static PropertiesManager properties = PropertiesManager.getInstance();
	String firstNameResponse = "";
	String lastNameResponse = "";
	String emailResponse = "";
	String dateOfBirthResponse = "";

	public boolean verifyUserDetails(Response response, String firstName, String lastName, String email, String dateOfBirth){
		firstNameResponse = response.jsonPath().getString("firstName");
		lastNameResponse = response.jsonPath().getString("lastName");
		emailResponse = response.jsonPath().getString("email");
		dateOfBirthResponse = response.jsonPath().getString("dayOfBirth");
		String expectedString = firstName + "||" + lastName + "||" + email + "||" + dateOfBirth;
		String responseString = firstNameResponse + "||" + lastNameResponse + "||" + emailResponse + "||" + dateOfBirthResponse;
		if(expectedString.equals(responseString)){
			return true;
		}
		else{
			return false;
		}
	}	

	public boolean verifyFieldFormats(Response response) throws Exception{		
		try {
			firstNameResponse = response.jsonPath().getString("firstName");
			lastNameResponse = response.jsonPath().getString("lastName");
			dateOfBirthResponse = response.jsonPath().getString("dayOfBirth");
			String DATE_FORMAT = "yyyy-MM-dd";
			DateFormat df = new SimpleDateFormat(DATE_FORMAT);            			
			if(firstNameResponse.length()>=2 && firstNameResponse.length()<=30 && lastNameResponse.length()>=2 && lastNameResponse.length()<=15){			
				df.setLenient(false);
	            df.parse(dateOfBirthResponse);
				return true;
			}
			return false;	
		}
		catch (Exception e) {
			throw e;
		}
	}
}
