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

class Login {	
	
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
		 driver.get("https://connect.graduway.com/");
		 driver.manage().window().maximize();
	}

	@Test
	void loginWrongDataFails() {
		openBrowser();
		loginProcess("nochance@mailinator.com","notworking");
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//gw-error-dialog/gw-dialog/div/div/div[1]/div")));
		String errormessage = driver.findElement(By.xpath("//gw-error-dialog/gw-dialog/div/div/div[1]/div")).getText();
		assertEquals(errormessage, "Incorrect user name or password.");
		driver.findElement(By.xpath("//gw-error-dialog/gw-dialog/div/div/div[2]/div")).click();
		driver.switchTo().activeElement().findElement(By.xpath("//gw-error-dialog/gw-dialog/div/div/div[2]/div/div/button")).click();
		System.out.println("Login with wrong data fails to login");
		driver.quit();
	}
	
	@Test
	void loginCorrectAndHeaderImagePresent() {
		driver.navigate().refresh();
		loginProcess("graduwayautomation@mailinator.com","Eit6Egahgh0a");
		driver.findElement(By.xpath("//gw-login-social/gw-login-form/form/button")).click();
		System.out.println("Login Passed");
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='image']")));
		WebElement mainBanner = driver.findElement(By.xpath("//div[@id='image']"));
		assertNotNull(mainBanner);
		System.out.println("Login with correct data does login and the feed image exists");
		driver.quit();
		}

	
}
