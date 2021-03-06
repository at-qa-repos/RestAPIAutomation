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

public class DeleteRequestTest extends BaseTest{

	private static String BASE_URL = properties.getConfig("BASE_URL");
	HashMap<String, String> testData;

	@Test
	public void verifyDeleteUserTest() throws Exception{
		testData = ExcelUtils.getTestCaseData(System.getProperty("user.dir") + properties.getConfig("TESTDATA_EXCELFILE_PATH"), properties.getConfig("EXCEL_SHEETNAME"), "TC_01");
		String random= GenericUtils.RandomString(5);
		String firstName = testData.get("FIRSTNAME");
		String lastName = testData.get("LASTNAME");
		String email = "workingemail" + random + "@gmail.com";
		String dateOfBirth = testData.get("DATEOFBIRTH");
		String userId = "";
		GetRequestManager requestManager;
		int responseCode=0;
		String requestBody = "{\n" +
				"  \"firstName\": \""+ firstName +"\",\n" +
				"  \"lastName\": \""+ lastName +"\",\n" +
				"  \"email\": \""+ email + "\",\n" +
				"  \"dayOfBirth\": \""+ dateOfBirth +"\" \n}";
		try {
			startTest(String.format(properties.getLogMessage("VerifyDeleteUserTest")), String.format(properties.getLogMessage("VerifyDeleteUserTestDescription")));
			setTestCategory(properties.getLogMessage("VerifyDeleteUserTestCategory"));	
			logInfo(String.format(properties.getLogMessage("VerifyDeleteUserTestStart")));
			RestAssured.baseURI = BASE_URL;
			Response createUserResponse = given()
					.header("Content-type", "application/json")
					.and()
					.body(requestBody)
					.when()
					.post("users")
					.then()
					.extract().response();
			responseCode=createUserResponse.getStatusCode();
			if(responseCode==201){				
				requestManager = new GetRequestManager();
				Assert.assertTrue(requestManager.verifyUserDetails(createUserResponse, firstName, lastName, email, dateOfBirth));
				userId = createUserResponse.jsonPath().getString("id");
				logInfo(String.format(properties.getLogMessage("CreateUserSuccess"), userId));
				Response deleteUserResponse = given()
						.header("Content-type", "application/json")
						.when()
						.delete("users/"+userId)
						.then()
						.extract().response();
				Assert.assertEquals(204, deleteUserResponse.getStatusCode());
				logInfo(String.format(properties.getLogMessage("DeleteUserSuccess"), userId));
			}
		} catch (Exception e) {
			logError(properties.getLogMessage("ErrorOccurred"));
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void verifyDeleteAnAlreadyDeletedUserTest() throws Exception{
		testData = ExcelUtils.getTestCaseData(System.getProperty("user.dir") + properties.getConfig("TESTDATA_EXCELFILE_PATH"), properties.getConfig("EXCEL_SHEETNAME"), "TC_01");
		String random= GenericUtils.RandomString(5);
		String firstName = testData.get("FIRSTNAME");
		String lastName = testData.get("LASTNAME");
		String email = "workingemail" + random + "@gmail.com";
		String dateOfBirth = testData.get("DATEOFBIRTH");
		String userId = "";
		GetRequestManager requestManager;
		int responseCode=0;
		String requestBody = "{\n" +
				"  \"firstName\": \""+ firstName +"\",\n" +
				"  \"lastName\": \""+ lastName +"\",\n" +
				"  \"email\": \""+ email + "\",\n" +
				"  \"dayOfBirth\": \""+ dateOfBirth +"\" \n}";
		try {
			startTest(String.format(properties.getLogMessage("VerifyDeleteNonExistingUserTest")), String.format(properties.getLogMessage("VerifyDeleteNonExistingUserTestDescription")));
			setTestCategory(properties.getLogMessage("VerifyDeleteNonExistingUserTestCategory"));	
			logInfo(String.format(properties.getLogMessage("VerifyDeleteNonExistingUserTestStart")));
			RestAssured.baseURI = BASE_URL;
			Response createUserResponse = given()
					.header("Content-type", "application/json")
					.and()
					.body(requestBody)
					.when()
					.post("users")
					.then()
					.extract().response();
			responseCode=createUserResponse.getStatusCode();
			if(responseCode==201){				
				requestManager = new GetRequestManager();
				Assert.assertTrue(requestManager.verifyUserDetails(createUserResponse, firstName, lastName, email, dateOfBirth));
				userId = createUserResponse.jsonPath().getString("id");
				logInfo(String.format(properties.getLogMessage("CreateUserSuccess"), userId));
				Response deleteUserResponse = given()
						.header("Content-type", "application/json")
						.when()
						.delete("users/"+userId)
						.then()
						.extract().response();
				Assert.assertEquals(204, deleteUserResponse.getStatusCode());
				logInfo(String.format(properties.getLogMessage("DeleteUserSuccess"), userId));
				deleteUserResponse = given()
						.header("Content-type", "application/json")
						.when()
						.delete("users/"+userId)
						.then()
						.extract().response();
				Assert.assertEquals(404, deleteUserResponse.getStatusCode());
				logInfo(String.format(properties.getLogMessage("DeleteNonExistingUserSuccess"), userId));
			}
		} catch (Exception e) {
			logError(properties.getLogMessage("ErrorOccurred"));
			e.printStackTrace();
			throw e;
		}
	}
}
