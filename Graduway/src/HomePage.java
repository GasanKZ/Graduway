import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.By.ByTagName;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class HomePage {	
	
private static WebDriver driver;
private WebElement logo;
private int winWidth, winHeight;
private String logo_xpath="//gw-logo//a//img";

	@BeforeClass
	void openBrowser() {
         System.setProperty("webdriver.chrome.driver", "C:\\SeleniumDrivres\\chromedriver.exe");
		 driver = new ChromeDriver();
		 driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS); //need to learn about it
		 driver.get("https://connect.graduway.com/");
		 driver.manage().window().maximize();
	}
	
	@Test
	void logoExists() {
		openBrowser();
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(logo_xpath)));
		logo = driver.findElements(By.xpath(logo_xpath)).get(0);
		assertNotNull(logo);
		System.out.println("Logo exists on the page");
	}
	
	@Test
	void logoTopLocation() {
		logo = driver.findElements(By.xpath(logo_xpath)).get(0);
		winWidth = driver.manage().window().getSize().getWidth();
		winHeight = driver.manage().window().getSize().getHeight();
		int xPos = logo.getLocation().getX();
		int yPos = logo.getLocation().getY();
	    String logoSRC = logo.getAttribute("src");
	    URL imageURL = null;
	    BufferedImage saveImage = null;
	    
		try {
			imageURL = new URL(logoSRC);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     try {
		   saveImage = ImageIO.read(imageURL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     
	   int eleHeight =  saveImage.getHeight();
	   int eleWidth = saveImage.getWidth();	

	assertTrue("Logo is NOT in the upper left quadrant", ((xPos + eleWidth) <= winWidth/2) && (yPos + eleHeight) <= winHeight/2);
	System.out.println("The logo is in the top corner");
	}
	
	@Test
	void swipeImagesNumber() {
		List<WebElement> imageSwiper = driver.findElements(By.xpath("//div[@class='swiper-wrapper']//*[contains(@class,'swiper-slide ng')]"));
		int numberOfImages = imageSwiper.size();
		System.out.println("Number of images = " + numberOfImages);
		assertEquals(numberOfImages,3);
	}
	
	@Test
	void footerAddress() {		
		WebElement emailFooter = driver.findElement(By.xpath("//div[@class='footer-item']//p//a[1]"));
		String emailText = emailFooter.getText();
		assertEquals(emailText, "alumni@graduway.com");
		System.out.println("Contact email address is \"alumni@graduway.com\"");
		
	driver.quit();
	}
	
}
