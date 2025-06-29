package testcases;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import routes.Routes;
import utils.ConfigReader;

public class BaseClass {
	
	ConfigReader configReader;
	RequestLoggingFilter requestLoggingFilter; 
	ResponseLoggingFilter responseLoggingFilter;
	
	@BeforeClass
	public void setup() throws FileNotFoundException {
		RestAssured.baseURI = Routes.BASE_URL;
		
		configReader = new ConfigReader();
		
		// Setup filters for logging 
		FileOutputStream fos = new FileOutputStream(".\\logs\\test_logging.txt"); 
		PrintStream log = new PrintStream(fos, true); 
		requestLoggingFilter = new RequestLoggingFilter(log); 
		responseLoggingFilter = new ResponseLoggingFilter(log); 
		RestAssured.filters(requestLoggingFilter, responseLoggingFilter); 
	}
	
	// Helper method to check if a list is sorted in Descending order
		public boolean isSortedDescending(List<Integer> list) {
			for(int i=0;i<list.size()-1;i++) {
				if(list.get(i)<list.get(i+1)) {
					return false;
				}
			}
			return true;
		}
	
	// Helper method to check if a list is sorted in Ascending order
			public boolean isSortedAscending(List<Integer> list) {
				for(int i=0;i<list.size()-1;i++) {
					if(list.get(i)>list.get(i+1)) {
						return false;
					}
				}
				return true;
			}
			
	

}
