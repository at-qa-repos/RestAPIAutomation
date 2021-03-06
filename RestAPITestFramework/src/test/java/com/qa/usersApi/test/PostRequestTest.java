package com.qa.usersApi.test;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.qa.requestManager.GetRequestManager;
import com.qa.testBase.BaseTest;
import com.qa.utils.ExcelUtils;
import com.qa.utils.GenericUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class PostRequestTest extends BaseTest{

	private static String BASE_URL = properties.getConfig("BASE_URL");
	HashMap<String, String> testData;
	
	@Test
	public void verifyCreateUserTest() throws Exception{
		testData = ExcelUtils.getTestCaseData(System.getProperty("user.dir") + properties.getConfig("TESTDATA_EXCELFILE_PATH"), properties.getConfig("EXCEL_SHEETNAME"), "TC_01");
		String random= GenericUtils.RandomString(5);
		String firstName = testData.get("FIRSTNAME");
		String lastName = testData.get("LASTNAME");
		String email = "workingemail" + random + "@gmail.com";
		String dateOfBirth = testData.get("DATEOFBIRTH");
		String userId = "";
		String requestBody = "{\n" +
	            "  \"firstName\": \""+ firstName +"\",\n" +
	            "  \"lastName\": \""+ lastName +"\",\n" +
	            "  \"email\": \""+ email + "\",\n" +
	            "  \"dayOfBirth\": \""+ dateOfBirth +"\" \n}";
		GetRequestManager requestManager;
		int responseCode=0;
		try {
			startTest(String.format(properties.getLogMessage("VerifyCreateUserTest")), String.format(properties.getLogMessage("VerifyCreateUserTestDescription")));
			setTestCategory(properties.getLogMessage("VerifyCreateUserTestCategory"));	
			logInfo(String.format(properties.getLogMessage("VerifyCreateUserTestStart")));
			RestAssured.baseURI = BASE_URL;
			Response response = given()
	                .header("Content-type", "application/json")
	                .and()
	                .body(requestBody)
	                .when()
	                .post("users")
	                .then()
	                .extract().response();
			responseCode=response.getStatusCode();
			if(responseCode==201){				
				requestManager = new GetRequestManager();
				Assert.assertTrue(requestManager.verifyUserDetails(response, firstName, lastName, email, dateOfBirth));
				userId = response.jsonPath().getString("id");
				logInfo(String.format(properties.getLogMessage("CreateUserSuccess"), userId));
			}
			else{
				logError(String.format(properties.getLogMessage("InvalidResponse"), responseCode));
				Assert.assertTrue(responseCode==201);
			}
		} catch (Exception e) {
			logError(properties.getLogMessage("ErrorOccurred"));
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void verifyCreateUserWithDuplicateEmailIdTest() throws Exception{
		testData = ExcelUtils.getTestCaseData(System.getProperty("user.dir") + properties.getConfig("TESTDATA_EXCELFILE_PATH"), properties.getConfig("EXCEL_SHEETNAME"), "TC_01");
		String firstName = testData.get("FIRSTNAME");
		String lastName = testData.get("LASTNAME");
		String email = "workingemail-1@gmail.com";
		String dateOfBirth = testData.get("DATEOFBIRTH");
		String requestBody = "{\n" +
	            "  \"firstName\": \""+ firstName +"\",\n" +
	            "  \"lastName\": \""+ lastName +"\",\n" +
	            "  \"email\": \""+ email + "\",\n" +
	            "  \"dayOfBirth\": \""+ dateOfBirth +"\" \n}";
		int responseCode=0;
		try {
			startTest(String.format(properties.getLogMessage("VerifyCreateUserWithDuplicateEmailTest")), String.format(properties.getLogMessage("VerifyCreateUserWithDuplicateEmailTestDescription")));
			setTestCategory(properties.getLogMessage("VerifyCreateUserWithDuplicateEmailTestCategory"));	
			logInfo(String.format(properties.getLogMessage("VerifyCreateUserWithDuplicateEmailTestStart")));
			RestAssured.baseURI = BASE_URL;
			Response response = given()
	                .header("Content-type", "application/json")
	                .and()
	                .body(requestBody)
	                .when()
	                .post("users")
	                .then()
	                .extract().response();
			responseCode=response.getStatusCode();
			Assert.assertEquals(409, responseCode);
			logInfo(String.format(properties.getLogMessage("CreateUserWithDuplicateEmail"), firstName, lastName, dateOfBirth));
		} catch (Exception e) {
			logError(properties.getLogMessage("ErrorOccurred"));
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void verifyCreateUserWithDuplicateFN_LN_DOBTest() throws Exception{
		testData = ExcelUtils.getTestCaseData(System.getProperty("user.dir") + properties.getConfig("TESTDATA_EXCELFILE_PATH"), properties.getConfig("EXCEL_SHEETNAME"), "TC_01");
		String random= GenericUtils.RandomString(5);
		String firstName = testData.get("FIRSTNAME");
		String lastName = testData.get("LASTNAME");
		String email = "workingemail" + random + "@gmail.com";
		String dateOfBirth = testData.get("DATEOFBIRTH");
		String userId = "";
		String requestBody = "{\n" +
	            "  \"firstName\": \""+ firstName +"\",\n" +
	            "  \"lastName\": \""+ lastName +"\",\n" +
	            "  \"email\": \""+ email + "\",\n" +
	            "  \"dayOfBirth\": \""+ dateOfBirth +"\" \n}";
		GetRequestManager requestManager;
		int responseCode=0;
		try {
			startTest(String.format(properties.getLogMessage("VerifyCreateUserWithDuplicateFN_LN_DOBTest")), String.format(properties.getLogMessage("VerifyCreateUserWithDuplicateFN_LN_DOBTestDescription")));
			setTestCategory(properties.getLogMessage("VerifyCreateUserWithDuplicateFN_LN_DOBTestCategory"));	
			logInfo(String.format(properties.getLogMessage("VerifyCreateUserWithDuplicateFN_LN_DOBTestStart")));
			RestAssured.baseURI = BASE_URL;
			Response response = given()
	                .header("Content-type", "application/json")
	                .and()
	                .body(requestBody)
	                .when()
	                .post("users")
	                .then()
	                .extract().response();
			responseCode=response.getStatusCode();
			if(responseCode==201){				
				requestManager = new GetRequestManager();
				Assert.assertTrue(requestManager.verifyUserDetails(response, firstName, lastName, email, dateOfBirth));
				userId = response.jsonPath().getString("id");
				logInfo(String.format(properties.getLogMessage("CreateUserWithDuplicateFN_LN_DOBSuccess"), firstName, lastName, dateOfBirth));
			}
			else{
				logError(String.format(properties.getLogMessage("InvalidResponse"), responseCode));
				Assert.assertTrue(responseCode==201);
			}
		} catch (Exception e) {
			logError(properties.getLogMessage("ErrorOccurred"));
			e.printStackTrace();
			throw e;
		}
	}

}
