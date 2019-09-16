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

class FeedHeadings {	
	
private static WebDriver driver;
private int waitTime=10;

public boolean loginProcess(String email ,String password) {
	driver.findElement(By.xpath("//gw-header//header//div[1]/div[1]")).click();
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
		 driver.get("https://connect.graduway.com/");
		 driver.manage().window().maximize();
	}

	@Test
	void feedList() {
		openBrowser();
		loginProcess("graduwayautomation@mailinator.com","Eit6Egahgh0a");
		List<WebElement> allFeedList = driver.findElements(By.xpath("//mat-sidenav-container/mat-sidenav-content/div/main/div/aside/div/gw-navbar/ul/gw-parent-menu"));
		List<String> categories = new ArrayList<String>();
		
		List<String> expectedCetegories = new ArrayList<String>( 
				 List.of("Feed","Directory","Mentoring","Jobs","Groups","Events"));	
			
		for (int i = 0; i < allFeedList.size(); i++) {
			String[] temp = allFeedList.get(i).getText().split(" ");
		    categories.add(temp[temp.length - 1]);
		}
		
		boolean validateHeadings=true;
		for (int i=0; i<expectedCetegories.size(); i++) {
			System.out.println("my array :" + expectedCetegories.get(i));
			System.out.println("values array :" + categories.get(i));

			if (!expectedCetegories.get(i).equals(categories.get(i))) {
				validateHeadings=false;
				break;
			}
			
		}
		assertTrue(validateHeadings);
		System.out.println("The headings are correct");
		driver.quit();
	}
	

	
}
