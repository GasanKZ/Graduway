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

class Admin {	
	
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

//	@Test
//	void jobsPage() {
//		openBrowser();
//		loginProcess("graduwayautomation@mailinator.com","Eit6Egahgh0a");
//		WebDriverWait wait = new WebDriverWait(driver, waitTime);
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//main/banner/div/div/gw-image-select-and-crop/div/div/div")));
//		driver.navigate().to("https://connect.graduway.com/admin/manage-users/profile-attributes");
//		
//		WebElement defaultUserType = driver.findElement(By.xpath("//gw-select/mat-form-field/div/div[1]"));
//		String defaultValue = defaultUserType.getText();
//		assertEquals(defaultValue, "Alumni");
//		System.out.println("Default User type value is Alumni");
//	}
//	
	@Test
	void validateToggles() {
		openBrowser();
		loginProcess("graduwayautomation@mailinator.com","Eit6Egahgh0a");
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='image']")));
		driver.navigate().to("https://connect.graduway.com/admin/manage-users/profile-attributes");
		
		List<WebElement> options = driver.findElements(By.xpath("//category-fields-editable-list/gw-dnd-editable-list/div/div[1]"));
		int itemIndex=-1;
		for (int i=0; i<options.size(); i++) {
			String item = options.get(i).findElement(By.xpath("div[2]/div[1]/div[1]/div")).getText();
			if (item.equals("Field of study")) {
				itemIndex=i;
				break;
			}
		}
		
		boolean inRegistration=false;
		boolean inProfile=false;
		if (itemIndex!=-1) {
			WebElement fosElement = options.get(itemIndex);
			inRegistration = fosElement.findElement(By.xpath("div[2]/div[2]/div[1]/gw-switch/mat-slide-toggle")).getAttribute("class").equals("mat-slide-toggle mat-accent mat-checked");
			inProfile = fosElement.findElement(By.xpath("div[2]/div[2]/div[1]/gw-switch/mat-slide-toggle")).getAttribute("class").equals("mat-slide-toggle mat-accent mat-checked");
		}
		
		System.out.println("register"+ inRegistration);
		System.out.println("profile"+inProfile);
		
		assertTrue(inRegistration&&inProfile);
		
		driver.quit();
	}
}
