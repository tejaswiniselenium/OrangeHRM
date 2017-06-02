package test.driver;

import test.resources.generic.FrameworkLibrary;

/* ######################################################################################################
 * Class Name: TestRunner
 * Description: This serves as driver script and drives the complete execution
 * Author: Testing Masters
 * Organization: Testing Master Technologies
 * Date Created: 14-Feb-2016
 * ######################################################################################################
 */
public class TestRunner extends FrameworkLibrary
{
/* ######################################################################################################
 * Class Name: main
 * Description: The Main Method of the Complete Framework
 * Author: Testing Masters
 * Organization: Testing Master Technologies
 * Date Created: 14-Sept-2016
 * ######################################################################################################
 */
	public static void main(String []args) 
	{
		onExecutionStart();
		String SelectedTestCases = getTestCases();
 	    for (String TestCase: SelectedTestCases.split(";;"))
	    {
	    	 if(TestCase.length()==0) continue;
	    	 CurrentTestCase = TestCase;
	    	 getIterations();
	    	 for(int Iteration = StartIteration;Iteration<=EndIteration;Iteration++)
	    	 { 
	    		 if (isIterationDataExist(Iteration)) 
	    		 {
	    			 	onTestCaseStart();
	    			 	execute(ClassPath);
	    			 	onTestCaseFinish();
	    		 }
	    	 }
	    }
 	    
 	   onExecutionFinish();	
 	   
	}
}
