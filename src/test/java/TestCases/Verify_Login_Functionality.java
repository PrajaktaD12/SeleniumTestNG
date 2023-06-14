package TestCases;

import base.BaseTest;
import org.testng.annotations.Test;
import pageEvents.HomePageEvents;
import utils.fetchElement;

public class Verify_Login_Functionality extends BaseTest {

    fetchElement ele = new fetchElement();
    HomePageEvents homepage = new HomePageEvents();

    @Test
    public void VerifyValidLogin(){

        homepage.ValidLogin();
    }
}
