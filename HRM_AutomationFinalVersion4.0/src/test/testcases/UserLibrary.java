package test.testcases;

import test.resources.generic.WebLibrary;

/* ######################################################################################################
 * Class Name: UserLib
 * Description: Contains the methods which are used for the common functionalities of the application
 * Author: Testing Masters
 * Date Created: 14-Feb-2016
 * ######################################################################################################
 */
public class UserLibrary extends WebLibrary 
{
	/*
	 * ######################################################################################################
	 *  Method Name: Login
	 *  Description: To Login to a HRM Application,Based on UID,Password
	 *  Input Parameters:URL,UID,Password
	 *  Output: True/False 
	 *  Author: Veerendra 
	 *  Date Created:21-Aug-2016
	 * ######################################################################################################
	 */
	public static boolean Login(String URL, String UID, String Password) 
	{
		boolean loginstatus = true;
		boolean status;

		status = OpenUrl(URL);
		if (status)
			LogEventWithScreeshot("info", "Application is Up and Running");
		else
			LogEventWithScreeshot("fail", "Unable to Launch HRM Application");

		status = SetText("//input[@id='txtUsername']", UID);
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

		wait(2);
		status = Exist("//a[contains(text(),'Welcome')]");
		if (status)
			LogEventWithScreeshot("pass", "Login is sucessful");
		else {
			LogEventWithScreeshot("fail", "Login is not sucessful");
			loginstatus = false;
		}

		return loginstatus;
	}

	public static boolean Logout() 
	{
		Boolean status;
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
		return status;
	}
}
