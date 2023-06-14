package pageEvents;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pageObjects.HomePageElements;
import utils.fetchElement;
import base.BaseTest;

import java.util.Properties;

public class HomePageEvents {

    fetchElement ele=new fetchElement();
    public void ValidLogin()
    {
        ele.getWebElement("CSS", HomePageElements.usernameTxt).sendKeys(BaseTest.props.getProperty("username"));
        ele.getWebElement("XPATH", HomePageElements.passwordTxt).sendKeys(BaseTest.props.getProperty("password"));
        ele.getWebElement("XPATH", HomePageElements.logInBtn).click();
        Assert.assertEquals(true, ele.getWebElement("XPATH",HomePageElements.logOutBtn).isDisplayed());
    }
}
