package chai;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class amazon {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		WebDriver driver=new FirefoxDriver();
		driver.get("http://www.amazon.com");
		driver.findElement(By.xpath("html/body/div[6]/div[3]/a[2]/span[2]")).click();
		driver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
		//driver.switchTo().frame("")
	driver.findElement(By.xpath("//[@id='nav-xshop']/a[2]")).click();
		//driver.findElement(By.tagName("Today's Deals")).click();
		
		
		
	}

}
