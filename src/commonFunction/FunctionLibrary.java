package commonFunction;

import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.testng.Reporter;

import connstant.AppUtil;

public class FunctionLibrary extends  AppUtil {
	public static boolean verifyLogin(String username,String password)
	{
		driver.get(config.getProperty("Url"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(By.cssSelector(config.getProperty("ObjUser"))).sendKeys(username);
		driver.findElement(By.cssSelector(config.getProperty("ObjPass"))).sendKeys(password);
		driver.findElement(By.cssSelector(config.getProperty("ObjLoginBtn"))).click();
		String expected = "dashboard";
		String actual = driver.getCurrentUrl();
		if (actual.contains(expected))
		{
			Reporter.log("Login success : " +expected + "   " +actual,true);
			return true;
		}
		else    //capture error message
		{ String errormsg = driver.findElement(By.cssSelector(config.getProperty("ObjErrormessage"))).getText();
			Reporter.log("Login not success : " +expected + "   " +actual,true);
			return false;
		}

	}
}

