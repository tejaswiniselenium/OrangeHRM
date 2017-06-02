package test.resources.generic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/* ######################################################################################################
 * Class Name: WebLib
 * Description: Contains the methods which are generic to all web page applications
 * Author: Testing Master
 * Organization: Testing Master Technologies
 * Date Created: 14-Feb-2016
 * ######################################################################################################
 */
public class WebLibrary extends FrameworkLibrary
{
/* ######################################################################################################
 * Method Name: SetText
 * Description: To clear contents and enter text in a Web Edit box
 * Input Parameters: Element Xpath , Data Value
 * Output: True/False
 * Author: Testing Master
 * Organization: Testing Master Technologies
 * Date Created: 14-Mar-2016
 * ######################################################################################################
 */
	public static Boolean SetText(String ObjectXpath,String Value)
    {
        Boolean stepStatus = true;
        try
        {
        	Highlight(ObjectXpath);
        	driver.findElement(By.xpath(ObjectXpath)).click();
        	driver.findElement(By.xpath(ObjectXpath)).clear();
            driver.findElement(By.xpath(ObjectXpath)).sendKeys(Value);
            String Enteredvalue="";
            Enteredvalue=driver.findElement(By.xpath(ObjectXpath)).getAttribute("value");
            if (!Enteredvalue.equalsIgnoreCase(Value))
            {
            	stepStatus = false;
            }
        }
        catch (Exception e)
        {
            stepStatus = false;
        }
        return stepStatus;
    }
	public static Boolean Mani(String ObjectId,String Value)
    {
        Boolean stepStatus = true;
        try
        {
        	Highlight(ObjectId);
        	driver.findElement(By.xpath(ObjectId)).click();
        	driver.findElement(By.xpath(ObjectId)).clear();
            driver.findElement(By.xpath(ObjectId)).sendKeys(Value);
            String Enteredvalue="";
            Enteredvalue=driver.findElement(By.xpath(ObjectId)).getAttribute("value");
            if (!Enteredvalue.equalsIgnoreCase(Value))
            {
            	stepStatus = false;
            }
        }
        catch (Exception e)
        {
            stepStatus = false;
        }
        return stepStatus;
    }
/* ######################################################################################################
 * Method Name: ClickElement
 * Description: To perform Click operation on a WebElement
 * Input Parameters: Element Xpath
 * Output: True/False
 * Author: Testing Master
 * Organization: Testing Master Technologies
 * Date Created: 14-Mar-2016
 * ######################################################################################################
 */
    public static Boolean ClickElement(String ObjectXpath)
    {
        Boolean stepStatus = true;
        try
        {
        	Highlight(ObjectXpath);
        	driver.findElement(By.xpath(ObjectXpath)).click();
        }
        catch (Exception e)
        {
            stepStatus = false;
        }
        return stepStatus;
    }
/* ######################################################################################################
 * Method Name: SetTextAndEscape
 * Description: To clear contents , enter text in WebElement and then click on Escape
 * Input Parameters: Element Xpath
 * Output: True/False
 * Author: Testing Master
 * Organization: Testing Master Technologies
 * Date Created: 14-Mar-2016
 * ######################################################################################################
 */
    public static Boolean SetTextAndEscape(String ObjectXpath,String Value)
    {
        Boolean stepStatus = true;
        try
        {
        	Highlight(ObjectXpath);
        	driver.findElement(By.xpath(ObjectXpath)).click();
        	driver.findElement(By.xpath(ObjectXpath)).clear();
            driver.findElement(By.xpath(ObjectXpath)).sendKeys(Value);
            wait(0.5);
            driver.findElement(By.xpath(ObjectXpath)).sendKeys(Keys.ESCAPE);
            String Enteredvalue="";
            Enteredvalue=driver.findElement(By.xpath(ObjectXpath)).getAttribute("value");
            if (!Enteredvalue.equalsIgnoreCase(Value))
            {
            	stepStatus = false;
            }
        }
        catch (Exception e)
        {
            stepStatus = false;
        }
        return stepStatus;
    }
/* ######################################################################################################
 * Method Name: Exist
 * Description: To verify the existence of WebElement
 * Input Parameters: Element Xpath
 * Output: True/False
 * Author: Testing Master
 * Organization: Testing Master Technologies
 * Date Created: 14-Mar-2016
 * ######################################################################################################
 */
	public static Boolean Exist(String ObjectXpath)
    {
        Boolean stepStatus = true;
        try
        {
        	driver.findElement(By.xpath(ObjectXpath));
        }
        catch (Exception e)
        {
            stepStatus = false;
        }
        return stepStatus;
    }
/* ######################################################################################################
 * Method Name: SelectOPtionByText
 * Description: To select option from dropdown based on visible text
 * Input Parameters: Element Xpath , Text of an option
 * Output: True/False
 * Author: Testing Master
 * Organization: Testing Master Technologies
 * Date Created: 14-Mar-2016
 * ######################################################################################################
 */
	public static Boolean SelectOPtionByText(String ObjectXpath,String Option)
	{
		Boolean stepStatus = true;
        try
        {
        	Highlight(ObjectXpath);
        	ObjectXpath = ObjectXpath + "/option[text()='" + Option + "']";
        	driver.findElement(By.xpath(ObjectXpath)).click();
        	String selectedvalue = driver.findElement(By.xpath(ObjectXpath)).getAttribute("text");
        	if(!selectedvalue.trim().equalsIgnoreCase(Option.trim()))
        	{
        		stepStatus = false;
        	}
        }
        catch (Exception e)
        {
            stepStatus = false;
        }
        return stepStatus;
	}
/* ######################################################################################################
 * Method Name: SelectOPtionByValue
 * Description: To select option from dropdown based on value
 * Input Parameters: Element Xpath , value of an option
 * Output: True/False
 * Author: Testing Master
 * Organization: Testing Master Technologies
 * Date Created: 14-Mar-2016
 * ######################################################################################################
 */
	public static Boolean SelectOPtionByValue(String ObjectXpath,String Option)
	{
		Boolean stepStatus = true;
        try
        {
    		Highlight(ObjectXpath);
    		ObjectXpath = ObjectXpath + "/option[@value='" + Option + "']";
        	driver.findElement(By.xpath(ObjectXpath)).click();
        	String selectedvalue = driver.findElement(By.xpath(ObjectXpath)).getAttribute("text");
        	if(!selectedvalue.trim().equalsIgnoreCase(Option.trim()))
        	{
        		stepStatus = false;
        	}
        }
        catch (Exception e)
        {
            stepStatus = false;
        }
        return stepStatus;
	}
/* ######################################################################################################
 * Method Name: launchBrowser
 * Description: To Launch a selected browser
 * Input Parameters: Browser Name
 * Output: WebDriver Instance
 * Author: Testing Master
 * Organization: Testing Master Technologies
 * Date Created: 14-Mar-2016
 * ######################################################################################################
 */
    public static WebDriver launchBrowser(String BrowserName)
	{
		WebDriver Tempdriver = null;
		switch(BrowserName.toLowerCase())
		{
			case "firefox":
			{
				Tempdriver = new FirefoxDriver();
				break;
			}
			case "internetexplorer":
			{
				DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
				System.setProperty("webdriver.ie.driver", GlobalVariables.CurrentDirectory + "\\" +"JarFiles\\BrowserServers\\IEDriverServer.exe");
				Tempdriver = new InternetExplorerDriver(capabilities);
				break;
			}
			case "chrome":
			{
				System.setProperty("webdriver.chrome.driver",GlobalVariables.CurrentDirectory + "\\" +"JarFiles\\BrowserServers\\chromedriver.exe");
				Tempdriver = new ChromeDriver();
				break;
			}
			default:
			{
				System.out.println("please Select the correct browser");
			}
		}
		return Tempdriver;
	}
/* ######################################################################################################
 * Method Name: OpenUrl
 * Description: To Open a specified URL
 * Input Parameters: URL
 * Output: True/False
 * Author: Testing Master
 * Organization: Testing Master Technologies
 * Date Created: 14-Mar-2016
 * ######################################################################################################
 */
    public static Boolean OpenUrl(String URL)
    {
    	Boolean stepStatus = true;
    	try
    	{
    		driver.get(URL);
    		driver.manage().window().maximize();
    	}
    	catch(Exception e)
    	{
    		stepStatus = false;
    	}
    	String CurrentUrl = driver.getCurrentUrl();
    	if (!CurrentUrl.contains(URL))
    	{
    		stepStatus = false;
    	}
    	return stepStatus;
    }
/* ######################################################################################################
 * Method Name: SetImplicitWait
 * Description: To set implicit wait for a driver instance
 * Input Parameters: TimeInSec
 * Output: True/False
 * Author: Testing Master
 * Organization: Testing Master Technologies
 * Date Created: 14-Mar-2016
 * ######################################################################################################
 */
	public static Boolean SetImplicitWait(int TimeInSec)
    {
        Boolean stepStatus = true;
        try
        {
        	driver.manage().timeouts().implicitlyWait(TimeInSec, TimeUnit.SECONDS);
        }
        catch (Exception e)
        {
            stepStatus = false;
        }
        return stepStatus;
    }
/* ######################################################################################################
 * Method Name: SwitchToWindowByTitle
 * Description: To switch a driver instance of a driver to a new window based on the title
 * Input Parameters: Partial Title value
 * Output: True/False
 * Author: Testing Master
 * Organization: Testing Master Technologies
 * Date Created: 14-Mar-2016
 * ######################################################################################################
 */
	public static boolean SwitchToWindowByTitle(String Text)
	{
		Boolean stepStatus = false;
		try
        {
			Set<String> allhandles = driver.getWindowHandles();
			for(String handle1: allhandles)
			{
				driver.switchTo().window(handle1);
				String pageURL = driver.getTitle();
				if (pageURL.contains(Text))
				{
					stepStatus = true;
					break;
				}
			}
        }
        catch (Exception e)
        {
            stepStatus = false;
        }
		return stepStatus;
	}
/* ######################################################################################################
 * Method Name: SwitchToWindowByURL
 * Description: To switch a driver instance of a driver to a new window based on the URL
 * Input Parameters: Partial URL value
 * Output: True/False
 * Author: Testing Master
 * Organization: Testing Master Technologies
 * Date Created: 14-Mar-2016
 * ######################################################################################################
 */
	public static boolean SwitchToWindowByURL(String Text)
	{
		Boolean stepStatus = false;
		try
        {
			Set<String> allhandles = driver.getWindowHandles();
			for(String handle1: allhandles)
			{
				driver.switchTo().window(handle1);
				String pageURL = driver.getCurrentUrl();
				if (pageURL.contains(Text))
				{
					stepStatus = true;
					break;
				}
			}
        }
        catch (Exception e)
        {
            stepStatus = false;
        }
		return stepStatus;
	}
/* ######################################################################################################
 * Method Name: SwitchToWindowByHandle
 * Description: To switch a driver instance of a driver to a new window based on the Handle
 * Input Parameters: Window Handle
 * Output: True/False
 * Author: Testing Master
 * Organization: Testing Master Technologies
 * Date Created: 14-Mar-2016
 * ######################################################################################################
 */
	public static boolean SwitchToWindowByHandle(String HandleText)
	{
		Boolean stepStatus = true;
		try
        {
			driver.switchTo().window(HandleText);
        }
        catch (Exception e)
        {
            stepStatus = false;
        }
		return stepStatus;
	}
	
/* ######################################################################################################
 * Method Name: wait
 * Description: To Make the execution halt for the specified time
 * Input Parameters: TimeInSeconds
 * Output: Void
 * Author: Testing Master
 * Organization: Testing Master Technologies
 * Date Created: 14-Mar-2016
 * ######################################################################################################
 */
    public static void wait(double d)
    {
    	try
    	{
    		Thread.sleep((long) (d*1000));
    	}
    	catch(Exception e)
    	{
    		
    	}
    }
/* ######################################################################################################
 * Method Name: wait
 * Description: To Make the execution halt for the specified time
 * Input Parameters: TimeInSeconds
 * Output: Void
 * Author: Testing Master
 * Organization: Testing Master Technologies
 * Date Created: 14-Mar-2016
 * ######################################################################################################
 */
        public static void wait(int d)
        {
        	try
        	{
        		Thread.sleep((long) (d*1000));
        	}
        	catch(Exception e)
        	{
        		
        	}
        }
/* ######################################################################################################
 * Method Name: Highlight
 * Description: To Highlight a WebElement Based on the Java Script Executor
 * Input Parameters: Xpath of a WebElement
 * Output: Void
 * Author: Testing Master
 * Organization: Testing Master Technologies
 * Date Created: 14-Mar-2016
 * ######################################################################################################
 */
    public static void Highlight(String ObjectXpath)
	{
		try
		{
		   WebElement element = driver.findElement(By.xpath(ObjectXpath));
		   JavascriptExecutor js = (JavascriptExecutor)driver;
		   for (int iCnt = 0; iCnt < 2; iCnt++) 
		   {
		         js.executeScript("arguments[0].style.border='solid 4px black'", element);
		         Thread.sleep(200);
		         js.executeScript("arguments[0].style.border=''", element);
		         Thread.sleep(200);
		   }
		}
		catch(Exception e)
		{
			
		}
	}
/* ######################################################################################################
 * Method Name: JsSetText
 * Description: To Enter Text in a WebElement Based on the Java Script Executor
 * Input Parameters: Xpath of a WebElement, Value
 * Output: True/False
 * Author: Testing Master
 * Organization: Testing Master Technologies
 * Date Created: 14-Mar-2016
 * ######################################################################################################
 */
	public static Boolean JsSetText(String ObjectXpath,String Value)
	{
		boolean stepstatus = true;
		try
		{
			Highlight(ObjectXpath);
			WebElement element = driver.findElement(By.xpath(ObjectXpath));
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].value='" + Value + "'", element);
            String Enteredvalue="";
            Enteredvalue=driver.findElement(By.xpath(ObjectXpath)).getAttribute("value");
            if (!Enteredvalue.equalsIgnoreCase(Value))
            {
            	stepstatus = false;
            }
		}
		catch(Exception e)
		{
			stepstatus = false;
		}
		return stepstatus;
	}
/* ######################################################################################################
 * Method Name: getTimeStamp
 * Description: To generate a string based on date and time stamp
 * Input Parameters: None
 * Output: Current Date and Time Stamp as String
 * Author: Testing Master
 * Organization: Testing Master Technologies
 * Date Created: 14-Mar-2016
 * ######################################################################################################
 */
	public static String getTimeStamp() 
	{
		DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
	    String DateTimeStamp = dateTimeInstance.format(Calendar.getInstance().getTime());
	    DateTimeStamp = DateTimeStamp.replace(",", "");
	    DateTimeStamp = DateTimeStamp.replace(" ", "_");
	    DateTimeStamp = DateTimeStamp.replace(":", "-");
		return  DateTimeStamp;
	}
/* ######################################################################################################
 * Method Name: JsClickElement
 * Description: To Click on a WebElement Based on the Java Script Executor
 * Input Parameters: Xpath of WebElement
 * Output: True/False
 * Author: Testing Master
 * Organization: Testing Master Technologies
 * Date Created: 14-Mar-2016
 * ######################################################################################################
 */
	public static Boolean JsClickElement(String ObjectXpath)
	{
		boolean stepstatus = true;
		try
		{
			Highlight(ObjectXpath);
			WebElement element = driver.findElement(By.xpath(ObjectXpath));
		  //Creating JavaScriptExecuter Interface
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].click();", element);
		}
		catch(Exception e)
		{
			stepstatus = false;
		}
		return stepstatus;
	}
}
