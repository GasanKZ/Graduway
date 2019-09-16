import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import java.lang.Thread;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class Jobs {	
	
private static WebDriver driver;
private int waitTime=10;

public boolean loginProcess(String email ,String password) {
	driver.findElement(By.xpath("//div/div/gw-header/div/header/div[1]/div/span")).click();
	driver.findElement(By.id("mat-input-0")).sendKeys(email);
	driver.findElement(By.id("mat-input-1")).sendKeys(password);
	driver.findElement(By.xpath("//gw-login-social/gw-login-form/form/button")).click();

	return true;
}

	@BeforeClass
	void openBrowser() {
         System.setProperty("webdriver.chrome.driver", "C:\\SeleniumDrivres\\chromedriver.exe");
		 driver = new ChromeDriver();
		 driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS); //need to learn about it
		 driver.get("https://connect.graduway.com");
		 driver.manage().window().maximize();
	}

	@Test
	void jobsPage() {
		openBrowser();
		loginProcess("graduwayautomation@mailinator.com","Eit6Egahgh0a");
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//main/banner/div/div/gw-image-select-and-crop/div/div/div")));
		driver.navigate().to("https://connect.graduway.com/jobs");
		
		List<WebElement> jobs = driver.findElements(By.xpath("//gw-scroll-trigger/div/div/div/h3/a"));
		boolean jobExists = false;  
		
		for (int i = 0 ; i<jobs.size();i++) {
			if (jobs.get(i).getText().equals("Contracts Assistant")) {
				jobExists = true;
				break;
			}
		}
		assertTrue(jobExists);
		System.out.println("Contacs Assistant is in the list");
		driver.quit();
	}
	@Test
	void buttonExists() {
		openBrowser();
		loginProcess("graduwayautomation@mailinator.com","Eit6Egahgh0a");
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//main/banner/div/div/gw-image-select-and-crop/div/div/div")));
		driver.navigate().to("https://connect.graduway.com/jobs");
		assertNotNull(driver.findElement(By.cssSelector("body > app > app-portal > gw-layout > div > div > mat-sidenav-container > mat-sidenav-content > div > main > div > div > app-jobs > gw-two-col-layout > div > div > div.gw-col.gw-col-md-4.gw-col-xs-12.align-self-top > div > div.ng-star-inserted > button")));
		System.out.println("Post a job button exists");
	}
}
