package com.qa.testBase;


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.qa.utils.PropertiesManager;
 
public class BaseTest {
 
	public static Logger log;
	public static ExtentReports extent;
	public static ExtentTest test;
	public static PropertiesManager properties = PropertiesManager.getInstance();
	static String reportPath = System.getProperty("user.dir") + properties.getConfig("HTMLREPORT_PATH") + properties.getConfig("HTMLREPORT_NAME");

	@BeforeSuite
	public static void beforeSuite() throws Exception {
		try {
			log = Logger.getLogger("Logger");
			PropertyConfigurator.configure(System.getProperty("user.dir") + properties.getConfig("LOG4J_PROPERTIES_FILENAME"));
			extent = createInstance(reportPath);
		}
		catch(Exception e){
			throw e;
		}
	}
    
    @AfterMethod(alwaysRun = true)
	public synchronized void afterMethod(ITestResult result) throws Exception {
		if (result.getStatus() == ITestResult.FAILURE) {
			log.info("Test " + result.getMethod().getMethodName() + " module has been started");
			test.log(Status.FAIL, MarkupHelper
					.createLabel(result.getName() + ": Test Case Failed due to below issues: ", ExtentColor.RED));
			test.fail(result.getThrowable());
			log.info("Test " + result.getMethod().getMethodName() + " module has been failed");
			log.info(result.getThrowable());
		} else if (result.getStatus() == ITestResult.SKIP) {
			log.info("Test " + result.getMethod().getMethodName() + " module has been started");
			test.log(Status.SKIP,
					MarkupHelper.createLabel(result.getName() + ": Test Case Skipped: ", ExtentColor.ORANGE));
			log.info("Test " + result.getMethod().getMethodName() + " module has been skipped");
			log.info(result.getThrowable());
			test.skip(result.getThrowable());
		} else {
			test.log(Status.PASS,
					MarkupHelper.createLabel(result.getName() + ": Test Case Passed", ExtentColor.GREEN));
		}
	}
	
	@AfterSuite(alwaysRun = true)
	public void generateReport() throws Exception {
		extent.flush();		
	}

	public static ExtentReports createInstance(String fileName) {
		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setTheme(Theme.STANDARD);
		htmlReporter.config().setDocumentTitle("SOFTWARE AUTOMATION REPORT");
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName("QA AUTOMATION REPORT");

		extent = new ExtentReports();
		extent.setSystemInfo("OS Name:", System.getProperty("os.name"));
		extent.setSystemInfo("OS Version:", System.getProperty("os.version"));
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.attachReporter(htmlReporter);
		return extent;
	}

	public static void startTest(String testName, String description){
		test = extent.createTest(testName, description);
		log.info("Test " + "[" +testName + "]" + " has been started");
	}

	public static void setTestCategory(String category){
		test.assignCategory(category);	
	}

	public static void logInfo(String message){
		test.info(message);
		log.info(message);
	}

	public static void logError(String message){
		test.error(message);
		log.error(message);
	}

	public static void endTest(String testName, String description){
		test = extent.createTest(testName, description);
		log.info("Test " + "[" +testName + "]" + " has been Completed");
	}
}