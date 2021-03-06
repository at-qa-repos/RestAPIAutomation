package com.qa.usersApi.test;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.requestManager.GetRequestManager;
import com.qa.testBase.BaseTest;

import io.restassured.response.Response;

public class GetRequestTest extends BaseTest{

	private static String BASE_URL = properties.getConfig("BASE_URL");

	@Test
	public void verifyUserBasedOnUserId() throws Exception{
		String userId = "1";
		int responseCode=0;
		try {
			startTest(String.format(properties.getLogMessage("VerifyUserTest"), userId), String.format(properties.getLogMessage("VerifyUserTestDescription"), userId));
			setTestCategory(properties.getLogMessage("VerifyUserTestCategory"));	
			logInfo(String.format(properties.getLogMessage("VerifyUserTestStart"), userId));
			Response response  = given().when().get(BASE_URL+"users/"+userId);
			responseCode=response.getStatusCode();
			Assert.assertEquals(200, responseCode);
			logInfo(String.format(properties.getLogMessage("VerifyGetUserDetailsTest"), userId));
		} catch (Exception e) {
			logError(properties.getLogMessage("ErrorOccurred"));
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void verifyUserDetailsFieldFormats() throws Exception{
		String userId = "1";
		int responseCode=0;
		try {
			startTest(String.format(properties.getLogMessage("VerifyUserDetailsFormatTest"), userId), String.format(properties.getLogMessage("VerifyUserDetailsFormatTestDescription"), userId));
			setTestCategory(properties.getLogMessage("VerifyUserDetailsFormatTestCategory"));	
			logInfo(String.format(properties.getLogMessage("VerifyUserDetailsFormatTestStart"), userId));
			Response response  = given().when().get(BASE_URL+"users/"+userId);
			responseCode=response.getStatusCode();
			Assert.assertEquals(200, responseCode);
			logInfo(String.format(properties.getLogMessage("VerifyGetUserDetailsFormatTest"), userId));
			GetRequestManager requestManager = new GetRequestManager();
			Assert.assertTrue(requestManager.verifyFieldFormats(response));
		} catch (Exception e) {
			logError(properties.getLogMessage("ErrorOccurred"));
			e.printStackTrace();
			throw e;
		}
	}
}
