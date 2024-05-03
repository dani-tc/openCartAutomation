package reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestResult;

public class ReportMethods {
    ExtentReports extent;
    ExtentTest test;
    long startTime;
    long endTime;
    double timeTakenSeconds;

    public ExtentTest getTest() {
        return test;
    }

    public void setupReport(String browserName, String reportPath, String testName, String testDescription){
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter ("src/reports/"+browserName+"/"+reportPath);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        test = extent.createTest(testName, testDescription);
        test.log(Status.INFO, "Browser used: " + browserName);
        startTime = System.currentTimeMillis();
        test.log(Status.INFO, "Start Time: " + startTime);
    }

    public void afterMethodReport(ITestResult result){
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "Test Case Failed is " + result.getName());
            test.log(Status.FAIL, "Test Case Failed is " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "Test Case Skipped is " + result.getName());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Test Case Passed is " + result.getName());
            endTime = System.currentTimeMillis();
            test.log(Status.INFO, "End Time: " + endTime);
            timeTakenSeconds = (endTime - startTime) / 1000.0;
            test.log(Status.INFO, "Time taken: " + String.format("%.2f", timeTakenSeconds) + " seconds");
        }
    }

    public void writeReport(){
        extent.flush();
    }


}
