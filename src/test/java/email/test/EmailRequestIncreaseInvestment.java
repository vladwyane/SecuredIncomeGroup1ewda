package email.test;

import admin.pages.AdminActivateFunds;
import admin.pages.AdminDashboard;
import admin.pages.AdminFundsPending;
import admin.pages.FancyBox;
import org.testng.annotations.Test;
import test.base.TestBase;
import test.base.data.HelperMethods;
import test.base.data.Users;
import user.pages.IndividualInvestments;
import user.pages.UserAccounts;

public class EmailRequestIncreaseInvestment extends TestBase{

    @Test(groups = "EmailRequestIncreaseInvestment", dependsOnGroups = "EmailActivateInvestment", alwaysRun = true, priority = 32)
    public void testRequestIncreaseInvestment() {
        app.goTo("http://securedincomegroup.stgng.co");
        HelperMethods helperMethods = new HelperMethods();
        helperMethods.signIn(Users.CHESALOV);
        UserAccounts userAccounts = new UserAccounts(app.getDriver());
        userAccounts.clickAccountNameIndividual();
        IndividualInvestments individualInvestments = new IndividualInvestments(app.getDriver());
        individualInvestments.clickLinkAddToInvestment();
        individualInvestments.enterAddInvestment("100");
        individualInvestments.clickSubmitFundingButton();
        app.sAssert().assertEquals(userAccounts.getAlertFinishFunding(), "FINISH FUNDING: $100");
        app.sAssert().assertEquals(individualInvestments.getDateFunded(), "03/07/2018");
        app.sAssert().assertEquals(individualInvestments.getMaturityDate(), "06/07/2018");
        app.sAssert().assertAll();

    }

    @Test(groups = "EmailRequestIncreaseInvestment", dependsOnGroups = "EmailActivateInvestment", alwaysRun = true, priority = 32)
    public void testEmailRequestIncreaseInvestmentForUser() throws InterruptedException {
        app.sAssert().assertEquals(app.checkGoogleMail(Users.VLADWYANE), "Request to Increase Your Investment");
        app.sAssert().assertAll();
    }

    @Test(groups = "EmailRequestIncreaseInvestment", dependsOnGroups = "EmailActivateInvestment", alwaysRun = true, priority = 34)
    public void testEmailRequestIncreaseInvestmentForAdmin() throws InterruptedException {
        app.sAssert().assertEquals(app.checkGoogleMail(Users.VLAD), "ADD TO INVESTMENT");
        app.sAssert().assertAll();
    }
}