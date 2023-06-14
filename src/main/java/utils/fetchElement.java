package utils;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class fetchElement {

    public WebElement getWebElement(String identifierType,String identifierValue)
    {
        switch(identifierType)
        {
            case "XPATH":
                return BaseTest.driver.findElement(By.xpath(identifierValue));
            case "CSS":
                return BaseTest.driver.findElement(By.cssSelector(identifierValue));
            case "ID":
                return BaseTest.driver.findElement(By.id(identifierValue));
            case "NAME":
                return BaseTest.driver.findElement(By.name(identifierValue));
            case "TAGNAME":
                return BaseTest.driver.findElement(By.tagName(identifierValue));
            default:
                return null;
        }
    }

    public List<WebElement> getWebElements(String identifierType, String identifierValue)
    {
        switch(identifierType)
        {
            case "XPATH":
                return BaseTest.driver.findElements(By.xpath(identifierValue));
            case "CSS":
                return BaseTest.driver.findElements(By.cssSelector(identifierValue));
            case "ID":
                return BaseTest.driver.findElements(By.id(identifierValue));
            case "NAME":
                return BaseTest.driver.findElements(By.name(identifierValue));
            case "TAGNAME":
                return BaseTest.driver.findElements(By.tagName(identifierValue));
            default:
                return null;
        }
    }
}
