package test.resources.generic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import test.testcases.ScriptVariables;

public class FrameworkLibrary extends Dlib
{
	
}
/* ######################################################################################################
 * Class Name: TLogger
 * Description: Class to generate report and add log events to the extent report
 * Author: Testing Masters
 * Organization: Testing Master Technologies
 * Date Created: 10-Feb-2016
 * ######################################################################################################
 */
class TLogger extends TestData
{
/* ######################################################################################################
 * Method Name: LogEvent
 * Description: Method to Log Events at run time to the log file
 * Author: Testing Masters
 * Organization: Testing Master Technologies
 * Date Created: 10-Aug-2016
 * ######################################################################################################
 */
	public static void LogEvent(String Status,String Description)
	{
		WebLibrary.wait(0.5);
		if(Status.equalsIgnoreCase("pass"))
		{
			logger.log(LogStatus.PASS,  Description);
			System.out.println("\t " +"StepStatus:" + Status + "; StepDesciption:" + Description);
		}
		else if(Status.equalsIgnoreCase("fail"))
		{
			logger.log(LogStatus.FAIL, Description);
			System.out.println("\t " +"StepStatus:" + Status + "; StepDesciption:" + Description);
		}
		else if (Status.equalsIgnoreCase("warning"))
		{
			logger.log(LogStatus.WARNING, Description);
			System.out.println("\t " +"StepStatus:" + Status + "; StepDesciption:" + Description);
		}
		else if(Status.equalsIgnoreCase("info"))
		{
			logger.log(LogStatus.INFO, Description);
			System.out.println("\t " +"StepStatus:" + Status + "; StepDesciption:" + Description);
		}
	}
/* ######################################################################################################
 * Method Name: LogEvent
 * Description: Method to Log Events at run time along with the screenshot to the log file
 * Author: Testing Masters
 * Organization: Testing Master Technologies
 * Date Created: 10-Aug-2016
 * ######################################################################################################
 */
	public static void LogEventWithScreeshot(String Status,String Description)
	{
		WebLibrary.wait(0.5);
		String ScreenShotPath = getscreenshot(driver);
		String scn = logger.addScreenCapture(ScreenShotPath);
		if(Status.equalsIgnoreCase("pass"))
		{
			logger.log(LogStatus.PASS, Description + scn);
			System.out.println("\t " +"StepStatus:" + Status + "; StepDesciption:" + Description);
		}
		else if(Status.equalsIgnoreCase("FAIL"))
		{
			logger.log(LogStatus.FAIL, Description + scn);
			System.out.println("\t " +"StepStatus:" + Status + "; StepDesciption:" + Description);
		}
		else if (Status.equalsIgnoreCase("warning"))
		{
			logger.log(LogStatus.WARNING, Description + scn);
			System.out.println("\t " +"StepStatus:" + Status + "; StepDesciption:" + Description);
		}
		else if(Status.equalsIgnoreCase("info"))
		{
			logger.log(LogStatus.INFO, Description + scn);
			System.out.println("\t " +"StepStatus:" + Status + "; StepDesciption:" + Description);
		}
	}
/* ######################################################################################################
 * Method Name: getscreenshot
 * Description: Method to Capture Screenshot of the current driver instance
 * Author: Testing Masters
 * Organization: Testing Master Technologies
 * Date Created: 10-Aug-2016
 * ######################################################################################################
 */
	public static String getscreenshot(WebDriver driver)
	  {
		  try
		  {
			String ScreenshotName;
			DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
			String DateTimeStamp = dateTimeInstance.format(Calendar.getInstance().getTime());
			DateTimeStamp = DateTimeStamp.replace(",", "");
			DateTimeStamp = DateTimeStamp.replace(" ", "_");
			DateTimeStamp = DateTimeStamp.replace(":", "_");
			ScreenshotName =  CurrentTestCase + "_"+ DateTimeStamp;
			TakesScreenshot ts =(TakesScreenshot) driver;
			File source = ts.getScreenshotAs(OutputType.FILE);
			String Dest = ScreenshotsFolderPath + ScreenshotName + ".png";
			File destination = new File(Dest);
			FileUtils.copyFile(source, destination);
			return Dest;
		  }
		  catch(Exception e)
		  {
			  return e.getMessage();
		  }
	  }
}
/* ######################################################################################################
 * Class Name: TestData
 * Description: Class to retreive and store test data into excel files 
 * Author: Testing Masters
 * Organization: Testing Master Technologies
 * Date Created: 10-Feb-2016
 * ######################################################################################################
 */
class TestData extends GlobalVariables
{
/* ######################################################################################################
 * Method Name: loadData
 * Description: Method to load testdata from excel and assign it to script variables
 * Author: Testing Masters
 * Organization: Testing Master Technologies
 * Date Created: 10-Aug-2016
 * ######################################################################################################
 */
	public static void loadData()
	{
		try
		{
			clearFields();
			ScriptVariables s1 = new ScriptVariables();
			//access to the testdata file
			File f1 = new File(CurrentDirectory + "\\" + testdatapath);
			//create file input stream object
			FileInputStream fis = new FileInputStream(f1);
			//create work book object
			XSSFWorkbook wb1 = new XSSFWorkbook(fis);
			//create work sheet object
			XSSFSheet ws1 = wb1.getSheet(TestDataSheetName);
			//get total row count.
			int rowcount = ws1.getLastRowNum();
			//use 'foundrow' to verify if the specified testcase exists
			String ColumnValue1;
			int foundrow = 0;
			for(int j = 0;j<=rowcount;j++)
			{
				Cell cell = ws1.getRow(j).getCell(0);
				String ExcelTestCaseName =cell.getStringCellValue();
				if(ExcelTestCaseName.toLowerCase().trim().equalsIgnoreCase(CurrentTestCase.toLowerCase().trim()))
				{
					cell = ws1.getRow(j).getCell(1);
					int ExcelIteration =Dlib.getCellValueAsInt(cell);
					if (ExcelIteration == CurrentIteration)
					{
						int colcount = ws1.getRow(j).getLastCellNum();
						for(int i = 2;i<colcount;i++)
						{
							cell =ws1.getRow(j).getCell(i);
							ColumnValue1 = Dlib.getCellValueAsString(cell);
							if (cell!=null && ColumnValue1.contains(":="))
							{
								String [] arrvalues = ColumnValue1.split(":=");
								setField(arrvalues[0].trim(), s1, arrvalues[1].trim());
							}
						}
						foundrow = 1;
						break;
					}
				}
			}
			//if the testcase row is not found
			if(foundrow==0)
			{
				System.out.println("TestCase" + CurrentTestCase + " row not found in datatable");
			}
			
			//close work book
			wb1.close();
			//close input stream
			fis.close();
		}
		catch(Exception e)
		{
			System.out.println("Error in assigning the values to the script variables");
		}
	}
/* ######################################################################################################
 * Method Name: exportData
 * Description: Method to assign values of script variables to excel Sheet Testdata
 * Author: Testing Masters
 * Organization: Testing Master Technologies
 * Date Created: 10-Aug-2016
 * ######################################################################################################
 */
	public static void exportData()
	{
		try
		{
			//Access TestData File
			File f1 = new File(CurrentDirectory + "\\" + testdatapath);
			//Create FileInput Stream object
			FileInputStream fis = new FileInputStream(f1);
			//create work book object
			XSSFWorkbook wb1 = new XSSFWorkbook(fis);
			//create work sheet object
			XSSFSheet ws1 = wb1.getSheet(TestDataSheetName);
			//get row count
			int rowcount = ws1.getLastRowNum();
			//using 'foundrow' variable to verify if the testcase row exists
			int foundrow = 0;
			for(int j = 0;j<=rowcount;j++)
			{
				Cell cell = ws1.getRow(j).getCell(0);
				String ExcelTestCaseName = cell.getStringCellValue();
				if(ExcelTestCaseName.toLowerCase().trim().equalsIgnoreCase(CurrentTestCase.toLowerCase().trim()))
				{		
					cell = ws1.getRow(j).getCell(1);
					int ExcelIteration =Dlib.getCellValueAsInt(cell);
					if (ExcelIteration == CurrentIteration)
					{
						int colcount = ws1.getRow(j).getLastCellNum();
						for(int i = 2;i<colcount;i++)
						{
							String ColumnName1 = ws1.getRow(j).getCell(i).getStringCellValue();
							String [] ColumnValues = ColumnName1.split(":=");
							String CurrentValue =getField(ColumnValues[0]);
							if (ColumnValues[0].length()>1)
							ws1.getRow(j).getCell(i).setCellValue(ColumnValues[0] + ":=" + CurrentValue);
						}
						foundrow = 1;
						break; 
					}
				}
			}
			//if testcase row not exist
			if(foundrow==0)
			{
				System.out.println("TestCase" + CurrentTestCase + " row not found in datatable");
			}
				
			//close input stream
			fis.close();
			//open output stream
		    FileOutputStream outFile =new FileOutputStream(f1);
		    //write contents to excel
		    wb1.write(outFile);
		    //close output stream
		    outFile.close();
		    //close work book
		    wb1.close();
		}
		catch(Exception e)
		{
			
		}
	}
/* ######################################################################################################
 * Method Name: setField
 * Description: Method to assign values of script variables at run time
 * Author: Testing Masters
 * Organization: Testing Master Technologies
 * Date Created: 10-Aug-2016
 * ######################################################################################################
 */
	public static void setField(String VairableName, Object someObject,String Value)
    {
    	Class<?> c = someObject.getClass();

    	Field[] fields = c.getFields();

	    for(Field field: fields)
	    {
	    	String Fieldname = field.getName();
		    if (Fieldname.equalsIgnoreCase(VairableName))
		    {
		    	try
			    {
			    	field.set(someObject, Value);
			    	break;
			    } catch (IllegalAccessException ignore)
			    {
			    	System.out.println(ignore);
			    }	
		    }
	    }
	    	
    }
/* ######################################################################################################
 * Method Name: getField
 * Description: Method to get value of script variable at run time
 * Author: Testing Masters
 * Organization: Testing Master Technologies
 * Date Created: 10-Aug-2016
 * ######################################################################################################
 */
	public static String getField(String VairableName)
    {
		ScriptVariables someObject = new ScriptVariables();
		String Value = "";
    	Class<?> c = someObject.getClass();

    	Field[] fields = c.getFields();

	    for(Field field: fields)
	    {
	    	String Fieldname = field.getName();
		    if (Fieldname.equalsIgnoreCase(VairableName))
		    {
		    	try
			    {
		    		Value = (String) field.get(someObject);
			    	break;
			    } catch (IllegalAccessException ignore)
			    {
			    	System.out.println(ignore);
			    }	
		    }
	    }
	    return Value;	
    }
/* ######################################################################################################
 * Method Name: clearFields
 * Description: Method to clear all field values of script variable at run time
 * Author: Testing Masters
 * Organization: Testing Master Technologies
 * Date Created: 10-Aug-2016
 * ######################################################################################################
 */
	public static void clearFields()
    {
		ScriptVariables someObject = new ScriptVariables();
    	Class<?> c = someObject.getClass();

    	Field[] fields = c.getFields();

	    for(Field field: fields)
	    {
		    try
			 {
			    field.set(someObject, "");
			 } 
		    catch (IllegalAccessException ignore)
			 {
		    	
			 }	
		  }
	 }
}

class GlobalVariables extends ScriptVariables
{
	public static String RunManagerName = "RunManager.xlsx";
	public static String MainMethodName  = "testdefinition";
	public static String testdatapath = "TestData\\TestData.xlsx";
	public static String TestDataSheetName = "TestData";
	public static String CurrentTestCase = "";
	public static String ScreenshotsFolderPath = "";
	public static String ResultsFolderPath;
	public static String CurrentResultsFolderPath;
	public static String ResultFilePath;
	public static String CurrentDirectory;
	public static String TestDataPath;
	public static ExtentReports report;
	public static ExtentTest logger;
	public static WebDriver driver;
	public static String ClassPath;
	public static int StartIteration;
	public static int EndIteration;
	public static int CurrentIteration;
}
class Dlib extends TLogger
{
/* ######################################################################################################
 * Method Name: onTestCaseStart
 * Description: Does the setup such as Loading Data,Launching WebDriver instance at the begining of every testcase
 * Author: Testing Masters
 * Organization: Testing Master Technologies
 * Date Created: 14-Feb-2016
 * ######################################################################################################
 */
	public static void onTestCaseStart()
	{
		String TestCaseDescription = getTestCaseDescription(CurrentTestCase);
		String CurrentBrowser = getCurrentBrowser(CurrentTestCase);
   	    logger = report.startTest(CurrentTestCase + "_" + CurrentIteration, TestCaseDescription);
   	    ClassPath = "test.testcases" + "." + CurrentTestCase;
        TestData.loadData();
        System.out.println("Start:" + CurrentTestCase + ", Iteration:" + CurrentIteration);
        driver = WebLibrary.launchBrowser(CurrentBrowser);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
/* ######################################################################################################
 * Method Name: getCurrentBrowser
 * Description: gets the current browser specified in the runmanager sheet
 * Author: Testing Masters
 * Organization: Testing Master Technologies
 * Date Created: 14-Feb-2016
 * ######################################################################################################
 */
	public static String getCurrentBrowser(String currentTestCase) 
	{
		String CurrentBrowser = "";
		try
		{
			//Access to RunManager.xls file
			File f1 = new File(CurrentDirectory + "\\" +RunManagerName);
			//Create File Input Steam Object
			FileInputStream fis = new FileInputStream(f1);
			//Create Workbook Object
			XSSFWorkbook wb1 = new XSSFWorkbook(fis);
			//Create Worksheet Object
			XSSFSheet ws1 = wb1.getSheet("Main");
			//get Row count
			int rowcount = ws1.getLastRowNum();
			//Iterate Through all rows and get the testcases which are set to true.
			for(int j = 1;j<=rowcount;j++)
			{
				Cell cell = ws1.getRow(j).getCell(0);
				if(cell.getStringCellValue().toLowerCase().trim().equalsIgnoreCase(currentTestCase.toLowerCase().trim()))
				{
					Cell cell1 = ws1.getRow(j).getCell(2);
					if (cell1!=null)
					{
						CurrentBrowser = cell1.getStringCellValue();
						break;
					}
					else
					{
						break;
					}
				}
			}
			//close workbook
			wb1.close();
			//close input stream
			fis.close();
		}
		catch(Exception e)
		{
			CurrentBrowser = "";
		}
		return CurrentBrowser;
	}
/* ######################################################################################################
 * Method Name: onTestCaseFinish
 * Description: closes driver instance and ends report of logger
 * Author: Testing Masters
 * Organization: Testing Master Technologies
 * Date Created: 14-Feb-2016
 * ######################################################################################################
 */
	public static void onTestCaseFinish()
	{
		TestData.exportData();
        driver.quit();
        System.out.println("End:" + CurrentTestCase + "\n");
        report.endTest(logger);
	}
/* ######################################################################################################
 * Method Name: onExecutionStart
 * Description: Set up the environment before the execution of the current run
 * Author: Testing Masters
 * Organization: Testing Master Technologies
 * Date Created: 14-Feb-2016
 * ######################################################################################################
 */
	public static void onExecutionStart() 
	{
		  	String CurrentFolderPath = System.getProperty("user.dir");
		  	CurrentFolderPath = CurrentFolderPath.replace("\\src","");
			CurrentDirectory = CurrentFolderPath;
			TestDataPath = CurrentFolderPath + "\\TestData\\";
			ResultsFolderPath = CurrentFolderPath + "\\Results\\";
			String CurrentResultsPath = createresultsfolder();
			CurrentResultsFolderPath = CurrentResultsPath;
			ScreenshotsFolderPath = CurrentResultsFolderPath + "\\ScreenShots\\";
			ResultFilePath = CurrentResultsFolderPath + "\\Summary.html";
			report = new ExtentReports(ResultFilePath);
			CurrentIteration = 0;
			StartIteration = 0;
			EndIteration = 0;
	 }
/* ######################################################################################################
 * Method Name: getTestCases
 * Description: get the selected TestCases List from the RunManager Sheet
 * Author: Testing Masters
 * Organization: Testing Master Technologies
 * Date Created: 14-Feb-2016
 * ######################################################################################################
 */
	public static String getTestCases()
	{
		String ExcelTestCasesName = "";
		try
		{
			//Access to RunManager.xls file
			File f1 = new File(CurrentDirectory + "\\" + RunManagerName);
			//Create File Input Steam Object
			FileInputStream fis = new FileInputStream(f1);
			//Create Workbook Object
			XSSFWorkbook wb1 = new XSSFWorkbook(fis);
			//Create Worksheet Object
			XSSFSheet ws1 = wb1.getSheet("Main");
			//get Row count
			int rowcount = ws1.getLastRowNum();
			//Iterate Through all rows and get the testcases which are set to true.
			for(int j = 1;j<=rowcount;j++)
			{
				Cell cell = ws1.getRow(j).getCell(3);
				if(cell.getStringCellValue().toLowerCase().trim().equalsIgnoreCase("true".toLowerCase().trim()))
				{
					Cell cell1 = ws1.getRow(j).getCell(0);
					ExcelTestCasesName = ExcelTestCasesName + ";;" + cell1.getStringCellValue();
				}
			}
			//close workbook
			wb1.close();
			//close input stream
			fis.close();
		}
		catch(Exception e)
		{
			
		}
		return ExcelTestCasesName;
	}
/* ######################################################################################################
 * Method Name: getTestCaseDescription
 * Description: get the description of the current testcase from the RunManager Sheet
 * Author: Testing Masters
 * Organization: Testing Master Technologies
 * Date Created: 14-Feb-2016
 * ######################################################################################################
 */
	public static String getTestCaseDescription(String TestCaseName)
	{
		String TestCaseDescription = "";
		try
		{
			//Access to RunManager.xls file
			File f1 = new File(CurrentDirectory + "\\" +RunManagerName);
			//Create File Input Steam Object
			FileInputStream fis = new FileInputStream(f1);
			//Create Workbook Object
			XSSFWorkbook wb1 = new XSSFWorkbook(fis);
			//Create Worksheet Object
			XSSFSheet ws1 = wb1.getSheet("Main");
			//get Row count
			int rowcount = ws1.getLastRowNum();
			//Iterate Through all rows and get the testcases which are set to true.
			for(int j = 1;j<=rowcount;j++)
			{
				Cell cell = ws1.getRow(j).getCell(0);
				if(cell.getStringCellValue().toLowerCase().trim().equalsIgnoreCase(TestCaseName.toLowerCase().trim()))
				{
					try
					{
						Cell cell1 = ws1.getRow(j).getCell(1);
						TestCaseDescription = cell1.getStringCellValue();
					}
					catch(Exception e)
					{
						TestCaseDescription  = "";
					}
				}
			}
			//close workbook
			wb1.close();
			//close input stream
			fis.close();
		}
		catch(Exception e)
		{
			TestCaseDescription = "";
		}
		return TestCaseDescription;
	}
/* ######################################################################################################
 * Method Name: execute
 * Description: Execute the selected TestCases Dynamically using refelection API's
 * Author: Testing Masters
 * Organization: Testing Master Technologies
 * Date Created: 14-Feb-2016
 * ######################################################################################################
 */
	@SuppressWarnings("rawtypes")
	public static boolean execute(String TestCaseName)
	{
		boolean status = true;
		
		TestCaseName = TestCaseName.replace("test.testcases.", "");
		Class[] classes = getAllClasses("test.testcases");
        for(Class c:classes)
        {
            Method[] m = c.getDeclaredMethods();
            for (int i = 0; i < m.length; i++)
            {
            	String MethodName = m[i].toString();
            	String []arr1 = MethodName.split("\\.");
            	MethodName = arr1[arr1.length-1];
            	MethodName = MethodName.replace("()", "");
            	if (MethodName.equalsIgnoreCase(TestCaseName))
            	{
            		 try 
            		 {
						m[i].invoke((Object[]) null, (Object[]) null);
					 } 
            		 catch (Exception e) 
            		 {
						System.out.println("Unable to Execute TestCase:" + MethodName);
						status = false;
					 }
            	}
            }
        }
		return status;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Class[] getAllClasses(String pckgname) 
	{
		try
		{
		    ArrayList classes=new ArrayList(); 
		    // Get a File object for the package 
		    File directory=null; 
		    try 
		    { 
		      directory=new File(Thread.currentThread().getContextClassLoader().getResource(pckgname.replace('.', '/')).getFile()); 
		    } 
		    catch(NullPointerException x) 
		    { 
		      System.out.println("Nullpointer");
		      throw new ClassNotFoundException(pckgname+" does not appear to be a valid package"); 
		    } 
		    if(directory.exists()) 
		    { 
		      String[] files=directory.list(); 
		      for(int i=0; i<files.length; i++) 
		      { 
		    	  if(files[i].endsWith(".class")) 
		    	  { 
		    		  classes.add(Class.forName(pckgname+'.'+files[i].substring(0, files[i].length()-6))); 
		    	  } 
		      } 
		    } 
		    else 
		    { 
		    	System.out.println("Directory does not exist");
		        throw new ClassNotFoundException(pckgname+" does not appear to be a valid package"); 
		    } 
		    Class[] classesA=new Class[classes.size()]; 
		    classes.toArray(classesA); 
		    return classesA;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
/* ######################################################################################################
 * Method Name: createresultsfolder
 * Description: creates results folder based on date and time stamp
 * Author: Testing Masters
 * Organization: Testing Master Technologies
 * Date Created: 14-Feb-2016
 * ######################################################################################################
 */
	private static String createresultsfolder() 
	{
		DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
	    String DateTimeStamp = dateTimeInstance.format(Calendar.getInstance().getTime());
	    DateTimeStamp = DateTimeStamp.replace(",", "");
	    DateTimeStamp = DateTimeStamp.replace(" ", "_");
	    DateTimeStamp = DateTimeStamp.replace(":", "-");
		File dir = new File(ResultsFolderPath + DateTimeStamp);
		dir.mkdir();
		return ResultsFolderPath + DateTimeStamp;
	}
/* ######################################################################################################
 * Method Name: onExecutionFinish
 * Description: Closes Report and Invoke the result summary.html 
 * Author: Testing Masters
 * Organization: Testing Master Technologies
 * Date Created: 14-Feb-2016
 * ######################################################################################################
 */
	public static void onExecutionFinish() 
	{
		report.close();
		WebDriver driver = new InternetExplorerDriver();
		driver.get(ResultFilePath);
		driver.manage().window().maximize();
	 }
	
	public static void getIterations()
	{
		String IterationType = "";
		try
		{
			//Access to RunManager.xls file
			File f1 = new File(CurrentDirectory + "\\" +RunManagerName);
			//Create File Input Steam Object
			FileInputStream fis = new FileInputStream(f1);
			//Create Workbook Object
			XSSFWorkbook wb1 = new XSSFWorkbook(fis);
			//Create Worksheet Object
			XSSFSheet ws1 = wb1.getSheet("Main");
			//get Row count
			int rowcount = ws1.getLastRowNum();
			//Iterate Through all rows and get the testcases which are set to true.
			for(int j = 1;j<=rowcount;j++)
			{
				Cell cell = ws1.getRow(j).getCell(0);
				if(cell.getStringCellValue().toLowerCase().trim().equalsIgnoreCase(CurrentTestCase.toLowerCase().trim()))
				{
					Cell cell1 = ws1.getRow(j).getCell(4);
					if (cell1!=null)
					{
						IterationType = getCellValueAsString(cell1);
						if (IterationType.equalsIgnoreCase("All Iterations"))
						{
							StartIteration = 1;
							EndIteration = 100;
						}
						else if(IterationType.contains("-"))
						{
							String [] ArrIterationType = IterationType.split("-");
							StartIteration = Integer.parseInt(ArrIterationType[0]);
							EndIteration = Integer.parseInt(ArrIterationType[1]);
						}
						else 
						{
							StartIteration = Integer.parseInt(IterationType);
							EndIteration = Integer.parseInt(IterationType);
						}
						break;
					}
					else
					{
						System.out.println("Please Specify Iteration Type");
					}
				}
			}
			//close workbook
			wb1.close();
			//close input stream
			fis.close();
		}
		catch(Exception e)
		{
		}
	}
	public static boolean isIterationDataExist(int Iteration)
	{
		boolean status = false;
		try
		{
			//access to the testdata file
			File f1 = new File(CurrentDirectory + "\\" + testdatapath);
			//create file input stream object
			FileInputStream fis = new FileInputStream(f1);
			//create work book object
			XSSFWorkbook wb1 = new XSSFWorkbook(fis);
			//create work sheet object
			XSSFSheet ws1 = wb1.getSheet(TestDataSheetName);
			//get total row count.
			int rowcount = ws1.getLastRowNum();
			//use 'foundrow' to verify if the specified testcase exists
			for(int j = 0;j<=rowcount;j++)
			{
				Cell cell = ws1.getRow(j).getCell(0);
				if (cell!=null)
				{
					String ExcelTestCaseName =cell.getStringCellValue();
					if(ExcelTestCaseName.toLowerCase().trim().equalsIgnoreCase(CurrentTestCase.toLowerCase().trim()))
					{
						cell = ws1.getRow(j).getCell(1);
						if (cell!=null)
						{
							String ExcelIteration = getCellValueAsString(cell);
							if (Integer.parseInt(ExcelIteration) == Iteration)
							{
								CurrentIteration = Iteration;
								status = true;
								break;
							}
						}
					}
				}
			}
			//close work book
			wb1.close();
			//close input stream
			fis.close();
		}
		catch(Exception e)
		{
			System.out.println("Error in getting the iteration from the Test Data");
		}
		return status;
	}
	public static int getCellValueAsInt(Cell c1)
	{
		int output = 0;
		switch (c1.getCellType()) 
        {
            case Cell.CELL_TYPE_STRING:
            	output = Integer.parseInt(c1.getStringCellValue());
                break;
            case Cell.CELL_TYPE_NUMERIC:
            	output = (int) c1.getNumericCellValue();
                break;
        }
		return output;
	}
	public static String getCellValueAsString(Cell c1)
	{
		String output = "";
		switch (c1.getCellType()) 
        {
            case Cell.CELL_TYPE_STRING:
            	output = c1.getStringCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC:
            	output = String.valueOf(c1.getNumericCellValue());
                break;
        }
		return output;
	}
}