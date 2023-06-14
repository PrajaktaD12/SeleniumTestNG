package TestCases;

import base.BaseTest;
import org.testng.annotations.Test;
import pageEvents.OpenNewAccntEvents;
import utils.fetchElement;

public class Create_New_Account extends BaseTest {

    fetchElement ele = new fetchElement();
    OpenNewAccntEvents newAccount = new OpenNewAccntEvents();

    @Test
    public void NewAccount(){

        Verify_Login_Functionality VLF=new Verify_Login_Functionality();
        VLF.VerifyValidLogin();
        newAccount.CreateNewAccount();

    }
}
