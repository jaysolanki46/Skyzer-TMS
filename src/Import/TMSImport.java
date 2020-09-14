package Import;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import Init.TMSLogger;

public class TMSImport {

	TMSLogger logger;
	
	public TMSImport(String fileName) throws InterruptedException, SecurityException, IOException {
		
		try {
			logger = new TMSLogger();
			
			logger.info(this.getClass().getName(), "Chrome driver initialization is in process...");
			ChromeOptions options = new ChromeOptions();
			options.setHeadless(true);
			options.addArguments("start-maximized");
			options.setExperimentalOption("useAutomationExtension", false);
			options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
			
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("credentials_enable_service", false);
			prefs.put("password_manager_enabled", false); 
			options.setExperimentalOption("prefs", prefs);
			
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\libs\\chromedriver.exe");
			WebDriver driver = new ChromeDriver(options);
			driver.get("http://10.63.192.11/checklogin.php");
			
			driver.findElement(By.tagName("button")).click();
			driver.findElement(By.name("I_Name")).sendKeys("import");
			driver.findElement(By.name("I_Password")).sendKeys("pass");
			driver.findElement(By.tagName("button")).click();
			Thread.sleep(3000);
			
			driver.findElement(By.xpath("//span[@class='glyphicon glyphicon-cog']")).click();
			Thread.sleep(3000);
			
			driver.findElement(By.xpath("//a[@data-serverpage='import.php']")).click();
			Thread.sleep(3000);
			
			driver.findElement(By.name("I_Upload")).sendKeys("N:\\AAPAYMENTS\\Daily Imports\\New Vision_ VSM\\"+ fileName +".csv");
			Thread.sleep(3000);

			logger.info(this.getClass().getName(), fileName + " file import is in process...");
			driver.findElement(By.xpath("//input[@onclick=\'CheckSubmission( );\']")).click();
			Thread.sleep(60000);
			logger.info(this.getClass().getName(), fileName + " file imported sucessfully ...");
			
			driver.close();
			
		} catch (Exception e) {
			logger.error(this.toString(), e.getMessage());
		}
	}
}
