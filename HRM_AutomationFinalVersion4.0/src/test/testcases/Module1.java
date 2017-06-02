package test.testcases;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Module1 extends UserLibrary
{
	
	//TC: this TC is to Verify Apply option in Leave Menu
	public static void TC05_VerifyApplyOptionInLeave()
	{
		
		Boolean status;
		
		//Step 1 and Step 2 of the TC, login to application using valid userName & password
		Login(URL, UserName , Password);
		
		status = ClickElement("//b[text()='Leave']");
		
		if (status)
		{
			LogEventWithScreeshot("info", "Clicked on Leave Menu");
			//LogEventWithScreeshot("INFO", "Clicked on Leave Menu");
			
			//Step 3 of the TC, verify the elements presence on page
			if(Exist("//a[text()='Apply']") && Exist("//a[text()='My Leave']") && Exist("//a[text()='Entitlements']") && Exist("//a[text()='Reports']"))
			{
				
				LogEventWithScreeshot("INFO", "Leave options displayed in Menu");
				
			}
			else
			{
				LogEventWithScreeshot("fail", "Some Leave options missing in Leave Menu");
			}
			
			/*
			 By default My Leave page is loaded when we click on leave menu.
			 We need to click on Apply link in order to validate remaining steps of TC
			*/
			
			status = ClickElement("//a[text()='Apply']");
			if(status)
			{
				//Step 4 of the TC, verify the elements presence on page
				if(Exist("//select[@id='applyleave_txtLeaveType']") && Exist("//div[@id='applyleave_leaveBalance']") && Exist("//input[@id='applyleave_txtFromDate']") && Exist("//input[@id='applyleave_txtToDate']") && Exist("//textarea[@id='applyleave_txtComment']"))
				{
					LogEventWithScreeshot("info", "Page options displayed on Apply Leave Page");
				}
				else
				{
					LogEventWithScreeshot("fail", "Some Page options missing Apply Leave on Page");
				}
			
				//Step 5 of the TC, verify the size of the text "Apply Leave" heading	
				String expcted_Font_Size="24px";
				String actual_Font_Size;
			
				actual_Font_Size=driver.findElement(By.xpath("//h1[text()='Apply Leave']")).getCssValue("font-size");
			
				if(expcted_Font_Size.equals(actual_Font_Size))
				{
					LogEventWithScreeshot("info", "Valid font size displayed on page; Font Size= "+actual_Font_Size);
				}
				else
				{
					LogEventWithScreeshot("fail", "Invalid font size displayed on page; Actual Font Size= "+actual_Font_Size+" Expected font size= "+expcted_Font_Size);
				}
			
			}
			
			else
			{
				LogEventWithScreeshot("fail", "Unable to Click on Apply Link");
			}
			
		}
		
		else
		{
			LogEventWithScreeshot("fail", "Unable to Click on Leave Link");
		}
		
		//Logout from application once TC is completed
		Logout();
		
 }
	//TC: this TC is to Verify Leave Type option in Apply Leave page
	public static void  TC06_VerifyLeaveTypeOptionInApplyLeavepage()
	{
		Boolean status;
		
		//Step 1 and Step 2 of the TC, login to application using valid userName & password
		Login(URL, UserName , Password);
		
		status = ClickElement("//b[text()='Leave']");
		
		if(status)
		{
			status = ClickElement("//a[text()='Apply']");
			
			if(status)
			{
				//Step 3 of TC, Verifying Leave Type has Dropdown box with options: Annual Leave & Personal Leave
				if(Exist("//select[@id='applyleave_txtLeaveType']"))
				{ 
					Select drpdwn_LeaveType=new Select(driver.findElement(By.xpath("//select[@id='applyleave_txtLeaveType']")));
					List<WebElement> drpdwn_values=drpdwn_LeaveType.getOptions();
					
					//Get the 2nd drop down value, 3rd drop down value and compare with Expected values
					if(drpdwn_values.get(1).getText().equals("Annual Leave")&& drpdwn_values.get(2).getText().equals("Personal Leave"))
					{
						LogEventWithScreeshot("pass", "Leave types drop down has options in it");
					}
					
					else
					{
						LogEventWithScreeshot("fail", "Leave types drop down does not have options in it");
					}
					
				}
				
				else
				{
					LogEventWithScreeshot("fail", "Leave type drop down not displayed on Page");
				}
			}
			
			else
			{
				LogEventWithScreeshot("fail", "Unable to Click on Apply Link");
			}
			
		}
		else
		{
			LogEventWithScreeshot("fail", "Unable to Click on Leave button");
		}
		
		//Logout from application once TC is completed
		Logout();
	}
	
	public static void TC07_VerifyLeaveBalanceOptionInApplyLeavepage()
	{
		Boolean status;
		
		//Step 1 and Step 2 of the TC, login to application using valid userName & password
		Login(URL, UserName , Password);
		
		status = ClickElement("//b[text()='Leave']");
		
		if(status)
		{
			status = ClickElement("//a[text()='Apply']");
			
			if(status)
			{
				if(Exist("//select[@id='applyleave_txtLeaveType']"))
				{ 
					//we need to test for 2 times. 1 by selecting annual leave type & 2nd by selecting personal leave
					//We can also use Iteration from excel test data by passing different leave types
					
					for(int i=1;i<=2;i++)
				{
					wait(2);
					Select drpdwn_LeaveType=new Select(driver.findElement(By.xpath("//select[@id='applyleave_txtLeaveType']")));
					drpdwn_LeaveType.selectByIndex(i);
					wait(2);
					
					//Step 3 of TC: Verify Leave Balance is Displaying after selecting the Leave type.
					String leaveBalMsg=driver.findElement(By.xpath("//div[@id='applyleave_leaveBalance']")).getText();

					//leave balance is retrieved like "10.00view details" by using getText() method
					//We need to split it to get the leave balance 10.00
					String balMsg[]=leaveBalMsg.split("v");
					float leaveBalance;
					leaveBalance=Float.parseFloat((balMsg[0]));		
					
					if(leaveBalance>=0)
					LogEventWithScreeshot("pass", "Leave Balance is displayed and the Leaves that are remained = "+leaveBalance);
					else
					LogEventWithScreeshot("fail", "Leave Balance is displayed with incorrect value = "+leaveBalance);
					
					//Step 4 of TC: View Details options in Leave balance page should be displayed with different Options
					if(Exist("//a[text()='view details']"))
					{
						driver.findElement(By.xpath("//a[text()='view details']")).click();
						wait(2);
						
						if(Exist("//span[@id='balance_as_of']") && Exist("//td[@id='balance_entitled']") && Exist("//td[@id='balance_taken']") &&  Exist("//td[@id='balance_scheduled']") && Exist("//td[@id='balance_pending']") && Exist("//td[@id='balance_total']"))
						{
							
							LogEventWithScreeshot("pass", "Leave balance page displayed all the Options");
							driver.findElement(By.id("closeButton")).click();
							wait(1);
							
						}
						else
						{
							LogEventWithScreeshot("fail", "Leave balance page not displayed all the Options");
						}
						
					}
					else
					{
						LogEventWithScreeshot("fail", "View details link not displayed on Page");
					}
				  }
				}
				
				else
				{
					LogEventWithScreeshot("fail", "Leave type drop down not displayed on Page");
				}
			}
			
			else
			{
				LogEventWithScreeshot("fail", "Unable to Click on Apply Link");
			}
			
		}
		
		else
		{
			LogEventWithScreeshot("fail", "Unable to Click on Leave button");
		}
		
		//Logout from application once TC is completed
		Logout();
	}
	
	public static void TC08_VerifyCommentOption()
	{
		Boolean status;
		
		//Step 1 and Step 2 of the TC, login to application using valid userName & password
		Login(URL, UserName , Password);
		
		status = ClickElement("//b[text()='Leave']");
		
		if(status)
		{
			status = ClickElement("//a[text()='Apply']");
			if(status)
			{
				//Step 3 of TC: Comment option should be displayed with text box.
				String tagType=driver.findElement(By.id("applyleave_txtComment")).getTagName();
				
				if(tagType.equals("textarea"))
				{
					String comment="This is test comment";
					driver.findElement(By.id("applyleave_txtComment")).sendKeys(comment);
					String textboxcomment=driver.findElement(By.id("applyleave_txtComment")).getAttribute("value");
					
					if(comment.equals(textboxcomment))
					{	
						LogEventWithScreeshot("pass", "Text box displayed, Comment entered and comment displayed are matching");
					}
					else
					{
						LogEventWithScreeshot("fail", "Comment entered and comment displayed are mismatching");	
					}
				}
				else
				{
					LogEventWithScreeshot("fail", "Text box not displayed for comment section");	
				}
			}
			
			else
			{
				LogEventWithScreeshot("fail", "Unable to Click on Apply Link");
			}
		}
		
		else
		{
			LogEventWithScreeshot("fail", "Unable to Click on Leave button");
		}
		
		//Logout from application once TC is completed
		Logout();
	}
	
	public static void TC01_VerifyLogin()
	{
		Boolean status;
		status = OpenUrl(URL);
		if (status)
			LogEventWithScreeshot("info", "Application is Up and Running");
		else
			LogEventWithScreeshot("fail", "Unable to Launch HRM Application");
		
		status = SetText("//input[@id='txtUsername']", UserName);
		if (status)
			LogEventWithScreeshot("info", "Entered UserName");
		else
			LogEventWithScreeshot("fail", "Unable to Enter UserName");
		
		status = SetText("//input[@id='txtPassword']", Password);
		if (status)
			LogEventWithScreeshot("info", "Entered Password");
		else
			LogEventWithScreeshot("fail", "Unable to Enter Password");

		status = ClickElement("//input[@name='Submit']");
		if (status)
			LogEventWithScreeshot("info", "Clicked on Login");
		else
			LogEvent("fail", "Unable to Click on login");
		
		status = Exist("//a[contains(text(),'Welcome')]");
		if (status)
			LogEventWithScreeshot("pass", "Login is sucessful");
		else
			LogEventWithScreeshot("pass", "Login is not sucessful");
		
		ClickElement("//a[contains(text(),'Welcome')]");
		status = ClickElement("//a[text()='Logout']");
		if (status)
			LogEventWithScreeshot("info", "Clicked on Logout");
		else
			LogEventWithScreeshot("fail", "Unable to clickon Clicked on Logout");
		
		status = Exist("//input[@id='txtUsername']");
		if (status)
			LogEventWithScreeshot("pass", "Logout is successful");
		else
			LogEventWithScreeshot("fail", "Logout is Not successful");
	}
	public static void TC02_AddEmergencyContactsAndVerify()
	{
		Boolean status;
		Login(URL, UserName , Password);
		status = ClickElement("//b[text()='My Info']");
		if (status)
			LogEventWithScreeshot("info", "Clicked on My Info button");
		else
			LogEventWithScreeshot("fail", "Unable to Click on My Info button");

		status = ClickElement("//a[text()='Emergency Contacts']");
		if (status)
			LogEventWithScreeshot("info", "Clicked on Emergenecy Contact Details");
		else
			LogEventWithScreeshot("fail", "Unable to Click on Emergenecy Contact Details");

		status = Exist("//input[@id='btnAddContact']");
		if (status)
			LogEventWithScreeshot("pass", "Navigated to Emergenecy contact details page");
		else
			LogEventWithScreeshot("fail", "Unable to Navigate to Emergenecy contact details page");
		
		status = ClickElement("//input[@id='btnAddContact']");
		if (status)
			LogEventWithScreeshot("pass", "Able to click on 'AddContact' button");
		else
			LogEventWithScreeshot("fail", "UnAble to click on 'AddContact' button");
		
		status = SetText("//input[@name='emgcontacts[name]']", Name);
		if (status)
			LogEventWithScreeshot("pass", "Able to Enter Name");
		else
			LogEventWithScreeshot("fail", "UnAble to Enter Name");
		status = SetText("//input[@name='emgcontacts[relationship]']", RelationShip);
		if (status)
			LogEventWithScreeshot("pass", "Able to Enter RelationShip");
		else
			LogEventWithScreeshot("fail", "UnAble to Enter RelationShip");
		status = SetText("//input[@name='emgcontacts[mobilePhone]']", HomePhone);
		if (status)
			LogEventWithScreeshot("pass", "Able to Enter HomePhone");
		else
			LogEventWithScreeshot("fail", "UnAble to Enter HomePhone");
		status = SetText("//input[@name='emgcontacts[homePhone]']", MobilePhone);
		if (status)
			LogEventWithScreeshot("pass", "Able to Enter MobilePhone");
		else
			LogEventWithScreeshot("fail", "UnAble to Enter MobilePhone");
		status = SetText("//input[@name='emgcontacts[workPhone]']", WorkPhone);
		if (status)
			LogEventWithScreeshot("pass", "Able to Enter WorkPhone");
		else
			LogEventWithScreeshot("fail", "UnAble to Enter WorkPhone");
		status = ClickElement("//input[@name='btnSaveEContact']");
		if (status)
			LogEventWithScreeshot("pass", "Able to click on SaveEContact");
		else
			LogEventWithScreeshot("fail", "UnAble to click on SaveEContact");
		
		ClickElement("//a[contains(text(),'Welcome')]");
		status = ClickElement("//a[text()='Logout']");
		if (status)
			LogEventWithScreeshot("info", "Clicked on Logout");
		else
			LogEventWithScreeshot("fail", "Unable to clickon Clicked on Logout");
		
		status = Exist("//input[@id='txtUsername']");
		if (status)
			LogEventWithScreeshot("pass", "Logout is successful");
		else
			LogEventWithScreeshot("fail", "Logout is Not successful");
	}
	public static void TC03_AddDependenciesAndVerify()
	{
		boolean status;
		Login(URL, UserName , Password);
		
		status = ClickElement("//b[text()='My Info']");
		if (status)
			LogEventWithScreeshot("pass", "Able to click on My Info");
		else
			LogEventWithScreeshot("fail", "UnAble to click on My Info");
		status = ClickElement("//a[text()='Dependents']");
		if (status)
			LogEventWithScreeshot("pass", "Able to click on Dependents");
		else
			LogEventWithScreeshot("fail", "UnAble to click on Dependents");
		status = ClickElement("//input[@id='btnAddDependent']");
		if (status)
			LogEventWithScreeshot("pass", "Able to click on AddDependent");
		else
			LogEventWithScreeshot("fail", "UnAble to click on AddDependent");
		
		status = SetText("//input[@id='dependent_name']", "Arjun");
		if (status)
			LogEventWithScreeshot("pass", "Able to Enter on DependentName");
		else
			LogEventWithScreeshot("fail", "UnAble to Enter on DependentName");
		
		status = SelectOPtionByText("//select[@id='dependent_relationshipType']", "Child");
		if (status)
			LogEventWithScreeshot("pass", "Able to Select on RelationshipType");
		else
			LogEventWithScreeshot("fail", "UnAble to Select on RelationshipType");
		status = SetTextAndEscape("//input[@id='dependent_dateOfBirth']", "1995-07-21");
		if (status)
			LogEventWithScreeshot("pass", "Able to Enter on DateOfBirth");
		else
			LogEventWithScreeshot("fail", "UnAble to Enter on DateOfBirth");
		status = ClickElement("//input[@id='btnSaveDependent']");
		if (status)
			LogEventWithScreeshot("pass", "Able to Click on SaveDependent");
		else
			LogEventWithScreeshot("fail", "UnAble to Click on SaveDependent");
		
		Logout();
	}
	public static void TC04_ApplyLeave()
	{
		boolean status;
		Login(URL, UserName , Password);
		
		status = ClickElement("//b[text()='Leave']");
		if (status)
			LogEventWithScreeshot("pass", "Able to click on Leave");
		else
			LogEventWithScreeshot("fail", "UnAble to click on Leave");
		status = ClickElement("//a[text()='Apply']");
		if (status)
			LogEventWithScreeshot("pass", "Able to click on Apply");
		else
			LogEventWithScreeshot("fail", "UnAble to click on Apply");
		status = SelectOPtionByText("//select[@id='applyleave_txtLeaveType']", LeaveType);
		if (status)
			LogEventWithScreeshot("pass", "Able to Select LeaveType");
		else
			LogEventWithScreeshot("fail", "UnAble to Select LeaveType");
		status = SetTextAndEscape("//input[@id='applyleave_txtFromDate']", FromDate);
		if (status)
			LogEventWithScreeshot("pass", "Able to Select FromDate");
		else
			LogEventWithScreeshot("fail", "UnAble to Select FromDate");
		wait(3);
		status = SetTextAndEscape("//input[@id='applyleave_txtToDate']", ToDate);
		if (status)
			LogEventWithScreeshot("pass", "Able to Select ToDate");
		else
			LogEventWithScreeshot("fail", "UnAble to Select ToDate");
		status = SetText("//textarea[@id='applyleave_txtComment']",Comment);
		if (status)
			LogEventWithScreeshot("pass", "Able to Enter Comment");
		else
			LogEventWithScreeshot("fail", "UnAble to Enter Comment");
		status = ClickElement("//input[@id='applyBtn']");
		if (status)
			LogEventWithScreeshot("pass", "Able to Click on Apply Button");
		else
			LogEventWithScreeshot("fail", "UnAble to Click on Apply Button");
		
		if (Exist("//div[@class='message success fadable']"))
			LogEventWithScreeshot("pass", "Leave is applied sucessfully");
		else
			LogEventWithScreeshot("fail", "Unable to apply leave");
		
		Logout();
	}
}
