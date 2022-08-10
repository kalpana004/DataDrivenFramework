package driverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import commonFunction.FunctionLibrary;
import connstant.AppUtil;
import utilities.ExcelFileUtil;

public class DriverScript extends AppUtil {
	String inputpath ="C:\\Selenium_9.30 am batch\\DDT_Framework\\TestInput\\LoginData.xlsx";
	String outputpath ="C:\\Selenium_9.30 am batch\\DDT_Framework\\TestOutput\\DataDrivenResults.xlsx";
	String sheetname = "Login";
	ExtentReports report;
	ExtentTest test;
@Test
public void startTest()throws Throwable
{
	//define path for html
	report= new ExtentReports("./Reports/DataDriven.html");
	//create object for excel file util class
	ExcelFileUtil  xl = new ExcelFileUtil (inputpath);
	//count no of rows in a sheet
	int rc =xl.rowCount(sheetname);
	//count no of cells in row
	int cc =xl.cellCount(sheetname);
	Reporter.log(rc+"       "+cc,true);
	for(int i=1;i<=rc;i++)
	{
		test=report.startTest("Validate Login");
		String user =xl.getCellData(sheetname, i, 0);
		String pass = xl.getCellData(sheetname, i, 1);
		//call login method from function library
		boolean res =FunctionLibrary.verifyLogin(user, pass);
		if(res)
		{
			//write as login success into results cell
			xl.setCellData(sheetname, i, 2, "Login Success", outputpath);
			//write as pass into status cell
			xl.setCellData(sheetname, i, 3, "Pass", outputpath);
			test.log(LogStatus.PASS, "Login success");
		}
		else
		{
			//take screen shot
			File screen =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(screen, new File("./Screens/Iteration/"+"  "+i+"Loginpage.png"));
			//write as login success into results cell
			xl.setCellData(sheetname, i, 2, "Login Fail", outputpath);
			//write as pass into status cell
			xl.setCellData(sheetname, i, 3, "Fail", outputpath);
			test.log(LogStatus.FAIL, "Login success");
		}
		report.endTest(test);
		report.flush();
	}
}

}








