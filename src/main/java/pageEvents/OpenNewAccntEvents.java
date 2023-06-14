package pageEvents;

import pageObjects.OpenNewAccntElements;
import utils.fetchElement;
import base.BaseTest;

public class OpenNewAccntEvents {

    fetchElement ele=new fetchElement();
    public void CreateNewAccount()
    {
        ele.getWebElement("XPATH", OpenNewAccntElements.OpenNewAccntLnk).click();
    }
}
