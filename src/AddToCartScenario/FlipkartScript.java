package AddToCartScenario;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import junit.framework.Assert;

public class FlipkartScript {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		String productName = "winter heater";
		String pincode = "110063";
		String addToCart_xpath = "//button[normalize-space()='ADD TO CART']";
		//Pre Req- Change chromdriver.exe file location acc to your local specified location
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Vanshul Suneja\\Chrome\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.flipkart.com");
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		String landingPage_Title = driver.getTitle();

		Assert.assertEquals(
				"Online Shopping Site for Mobiles, Electronics, Furniture, Grocery, Lifestyle, Books & More. Best Offers!",
				landingPage_Title);
		driver.findElement(By.xpath("//button[contains(text(),'✕')]")).click();
		Thread.sleep(2000);
		// 1. Searching the product
		driver.findElement(By.xpath("//input[@type='text']")).sendKeys(productName);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		String productNameOnSearch = driver.findElement(By.xpath(
				"//span[contains(text(),'" + productName + "')]//following::a[1]//descendant::div//following::a[4]"))
				.getAttribute("title");
		String productNamePage_Title = driver.getTitle();
		Assert.assertEquals("Winter Heater- Buy Products Online at Best Price in India - All Categories | Flipkart.com",
				productNamePage_Title);
		// 2. Always select the first product on search
		driver.findElement(By.xpath(
				"//span[contains(text(),'" + productName + "')]//following::a[1]//descendant::div//following::a[4]"))
				.click();
		// Switching between different tabs
		ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs2.get(1));
		// Adding pincode as by default all products were out of stock
		driver.findElement(By.xpath("//input[@placeholder=\"Enter Delivery Pincode\"]")).sendKeys(pincode);
		driver.findElement(By.xpath("//span[contains(text(),'Check')]")).click();
		Thread.sleep(5000);
		// 4.Adding product to Cart
		if (driver.findElement(By.xpath(addToCart_xpath)).isEnabled()) {
			driver.findElement(By.xpath(addToCart_xpath)).click();
			// 5.Navigating to add To Cart Page
			driver.findElement(By.xpath("//span[contains(text(),'Cart')]")).click();
			Thread.sleep(2000);
		} else {
			System.out.println("Product out of Stock/Can't be added to Cart");
		}
		String productNameInCart = driver.findElement(By.xpath("//div[contains(text(),'My Cart')]//following::a[2]"))
				.getText();
		// 6.Verify productName in cart is same as productName on Search
		Assert.assertEquals(productNameOnSearch, productNameInCart);
		driver.close();
		driver.switchTo().window(tabs2.get(0));
		driver.quit();
	
	}

}
