package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;


public class BaseTest {

    public static WebDriver driver;
    public static ExtentSparkReporter sparkReporter;
    public static ExtentReports extent;
    public ExtentTest logger;
    public static Properties props=new Properties();


    @BeforeSuite
    public static void setUp(){
        //configuration file setup
        FileReader reader= null;
        try {
            reader = new FileReader(System.getProperty("user.dir")+"//config//configuration.properties");
            props.load(reader);
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Extent report setup
        sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")+ File.separator+"reports"+File.separator+"ExtentReports.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        sparkReporter.config().setTheme(Theme.DARK);
        extent.setSystemInfo("Username",props.getProperty("username"));
        extent.setSystemInfo("Environment",props.getProperty("env"));
        sparkReporter.config().setDocumentTitle("Automation Report");
        sparkReporter.config().setReportName("Automation Test results");


    }

    @BeforeMethod
    public void setDrivers(Method testMethod){
        //setup browser driver
        System.out.println("Setup System property for chrome");
        logger = extent.createTest(testMethod.getName());
        //System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//driver//chromedriver_mac64 (1)//chromedriver");
        WebDriverManager.chromedriver().setup();
        driver=new ChromeDriver();
        driver.get(props.getProperty("url")); //fetching url from configuration file
        driver.manage().window().maximize();
    }

    public static ExtentReports getInstance() { //if extent is null then it will fetch the extent instance
        if(extent == null) {
            setUp();
        }
        return extent;
    }


    @AfterMethod
    public void generateReport(ITestResult result){
        //Generate report after execution
        if(result.getStatus() == ITestResult.FAILURE) {
            logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test case Failed", ExtentColor.RED));
            String screenshotPath = null;
            try {
                screenshotPath = BaseTest.getScreenshot(driver, result.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            logger.log(Status.FAIL,result.getThrowable());
            logger.fail(MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotPath).build());

        }else{ if(result.getStatus() == ITestResult.SUCCESS)
            logger.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " - Test case Passed", ExtentColor.GREEN));
            String screenshotPath = null;
            try {
                screenshotPath = BaseTest.getScreenshot(driver, result.getName());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            logger.log(Status.PASS,result.getThrowable());
            logger.pass(MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotPath).build());
        }
        driver.quit();
    }

    @AfterSuite
    public void clearReport(){
        extent.flush();
    }

    public static String getScreenshot(WebDriver driver, String screenshotName) throws Exception {
        //below line is just to append the date format with the screenshot name to avoid duplicate names
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File screeImage = ts.getScreenshotAs(OutputType.FILE);
        String source = ts.getScreenshotAs(OutputType.BASE64); //It will add screenshot in extent reports.
        //after execution, you could see a folder "screenshots" under src folder
        String destination = ".//screenshots/"+screenshotName+dateName+".jpg";
        File finalDestination = new File(destination);
        FileUtils.copyFile(screeImage, finalDestination);
        return source;
    }

}
