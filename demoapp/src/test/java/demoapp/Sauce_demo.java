package demoapp;

import static org.testng.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class Sauce_demo {
	WebDriver driver;
	String credentials="F:\\Vijay\\Saucedemo\\Saucedemo credentials.xlsx";
	
	@BeforeClass
	void beforeclass() {
		System.setProperty("webdriver.chrome.driver","F:\\Vijay\\chrome\\chromedriver.exe");
	driver = new ChromeDriver();
	driver.get("https://www.saucedemo.com/");
	//driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test(alwaysRun = true)
	void login() throws InterruptedException, IOException{
		
	/*System.setProperty("webdriver.chrome.driver","F:\\Vijay\\chrome\\chromedriver.exe");
	driver = new ChromeDriver();
	driver.get("https://www.saucedemo.com/");
	//driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); */
			
		FileInputStream F = new FileInputStream(credentials);
		XSSFWorkbook WB = new XSSFWorkbook(F);
		XSSFSheet S = WB.getSheetAt(0);
		Cell cell;
		for (int i = 1; i < 4; i++) {
			cell = S.getRow(i).getCell(0);
			String Username = cell.getStringCellValue();
			cell = S.getRow(i).getCell(1);
			String Password = cell.getStringCellValue();
		
		//sending input
		WebElement User = driver.findElement(By.xpath("//input[@type=\"text\"]"));
		User.sendKeys(Username);
		WebElement Pass = driver.findElement(By.xpath("//input[@type=\"password\"]"));
		Pass.sendKeys(Password);
		WebElement Login = driver.findElement(By.xpath("//input[@type=\"submit\"]"));
		Login.click();
		
		String list_of_product = driver.findElement(By.xpath("//span[@class='title']")).getText();

		if(list_of_product.equalsIgnoreCase("Products")) {
			assertTrue(true);
			System.out.println("user name: "+Username+" login sucessfully");
			}
			else{
				assertTrue(false);	
				System.out.println("user name: "+Username+" wrong user");
			}
			
		
	/*	String CurrentUrl = driver.getCurrentUrl();
		System.out.println(CurrentUrl);
		// assertion
		String ExpectedUrl = "https://www.saucedemo.com/inventory.html";
	Assert.assertEquals(ExpectedUrl, CurrentUrl);
*/
		
		//filter
		WebElement Filter = driver.findElement(By.xpath("//select[@data-test=\"product_sort_container\"]"));
		Filter.click();
		WebElement LtoH = driver.findElement(By.xpath("//option[@value=\"lohi\"]"));
		LtoH.click(); 
		
		Thread.sleep(5000);
		
		List<WebElement> items = driver.findElements(By.xpath("//button[contains(text(),'Add to cart')]"));
		for (WebElement item : items) {
            item.click();
            }
		
	
		
		WebElement  itemsincart = driver.findElement(By.cssSelector(".shopping_cart_badge"));
		String No_of_items = itemsincart.getText();
		if(No_of_items.equalsIgnoreCase("6")) {
			assertTrue(true);
			System.out.println("NO of items : "+No_of_items+" item add to cart sucessfully");
			}
			else{
				assertTrue(false);	
				System.out.println("add to cart item assertion fail");
			}
			
		
		
		//view cart
		WebElement cart = driver.findElement(By.xpath("//a[@class='shopping_cart_link']"));
		cart.click(); 
				
		
	/*	 List<WebElement> pricelist = driver.findElements(By.xpath("//div[@class='inventory_item_price']"));
		for (WebElement Lprice : pricelist) {
			String pricetext = Lprice.getText().replace("$", "");
			//System.out.println(pricetext);
	*/	
		//converting
		//	double f1 = Double.parseDouble(pricetext);
		//	System.out.println(f1);
		//	for (int j = 1; j < 6; j++) {
			List<WebElement> items_remove = driver.findElements(By.xpath("//div[@class='cart_item']//button[contains(text(),'Remove')]"));
				for(WebElement remove : items_remove) {
					WebElement price = remove.findElement(By.xpath("//div[@class='inventory_item_price']"));
					String pricetext = price.getText();
					double f1 = Double.parseDouble(pricetext.replace("$",""));
					if(f1 < 15) {
					remove.click();
					}
					
				}
				
				WebElement  itemsincart_after_remove = driver.findElement(By.cssSelector(".shopping_cart_badge"));
				String No_of_items_after_remove = itemsincart_after_remove.getText();
				
				if(No_of_items_after_remove.equalsIgnoreCase("4")) {
					assertTrue(true);
					System.out.println("less than $15  remover from cart sucessfully");
					}
					
				
				
				WebElement cartcontainer = driver.findElement(By.xpath("//div[@id=\"shopping_cart_container\"]"));
				cartcontainer.click();
				
				WebElement checkout = driver.findElement(By.xpath("//button[@name=\"checkout\"]"));
			checkout.click();
						//driver.findElement(By.xpath("//div[@class='cart_item'][j]//button[contains(text(),'Remove')]")).click();
					
				//}
			//	driver.findElement(By.xpath("//div[@id=\"shopping_cart_container\"]")).click();
				//driver.findElement(By.xpath("//button[@name=\"checkout\"]")).click();
				
	
				
				
				
	/*		WebElement  itemsincart = driver.findElement(By.cssSelector(".shopping_cart_badge"));
			String No_of_items = itemsincart.getText();
				System.out.println("item removed");
				System.out.println(No_of_items);
			*/
			
		/*	
			// assertion
			String Expectedquantity = "4";
			Assert.assertEquals(Expectedquantity, No_of_items);
			
			*/
		//driver.findElement(By.xpath("//button[@name=\"checkout\"]")).click();		
		driver.findElement(By.xpath("//input[@id=\"first-name\"]")).sendKeys("Vijay");
		driver.findElement(By.xpath("//input[@id=\"last-name\"]")).sendKeys("Kumar");
		driver.findElement(By.xpath("//input[@id=\"postal-code\"]")).sendKeys("641010");
		driver.findElement(By.xpath("//input[@id=\"last-name\"]")).click();
		
		driver.findElement(By.xpath("//button[@id=\"react-burger-menu-btn\"]")).click();
		
		Thread.sleep(5000);
		
		driver.findElement(By.xpath("//a[@id=\"logout_sidebar_link\"]")).click();
		
		System.out.println("logout sucessfully");
		
		}
		
}
	@AfterClass
	void teardown() {
		driver.quit();
	}
	}
		

