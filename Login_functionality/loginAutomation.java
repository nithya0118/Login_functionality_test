package Project.Project_01;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class loginAutomation {
	
	
	String [][] data = null;
	WebDriver driver;
			
	@DataProvider(name="loginData")
	public String[][] loginDataProvider() throws BiffException, IOException  { 
		
		data = readDatafromExcel();
		return data; 
	}
	
	public String[][] readDatafromExcel() throws BiffException, IOException {
		FileInputStream excel = new FileInputStream("C:\\Users\\lap\\OneDrive\\Desktop\\Testdata01.xls");
		Workbook workbook = Workbook.getWorkbook(excel);
		
		Sheet sheet = workbook.getSheet(0);
		int rowsCount = sheet.getRows();
		int columnCount = sheet.getColumns();
		
		String testdata[][] = new String[rowsCount-1][columnCount];
		
		for (int i=1 ; i<rowsCount ; i++) {
			for (int j=0 ; j<columnCount ; j++) {
				
				testdata[i-1][j]= sheet.getCell(j, i).getContents();
					
			}
		}
		return testdata;
		
	}
	
	@BeforeTest
	public void beforeTest() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}
	
	
	@Test(dataProvider = "loginData")
	
	public void testCaseAutomation(String username, String password) throws Exception {
		
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, 50);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username"))).sendKeys(username);
		Thread.sleep(2000);
		WebElement psword = driver.findElement(By.name("password"));
		psword.sendKeys(password);
		WebElement login = driver.findElement(By.xpath("//button[@type='submit']"));
		login.click();
		Thread.sleep(5000);
	}
	
	@AfterTest
	public void afterTest() {
		driver.quit();	
		
	}

}


