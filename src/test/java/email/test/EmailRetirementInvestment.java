package email.test;

import admin.pages.AdminActivateFunds;
import admin.pages.AdminDashboard;
import admin.pages.AdminFundsPending;
import admin.pages.FancyBox;
import common.elements.Header;
import create.account.RemoveAccount;
import org.testng.annotations.Test;
import test.base.TestBase;
import test.base.data.HelperMethods;
import test.base.data.Users;
import user.pages.RetirementInvestments;
import user.pages.UserAccounts;

public class EmailRetirementInvestment extends TestBase{

    @Test(groups = "EmailRetirementInvestment", dependsOnGroups = "CreateRetirementInvestment", alwaysRun = true, priority = 55)
    public void testEmailRetirementInvForUser() throws InterruptedException {
        app.sAssert().assertEquals(app.checkGoogleMail(Users.VLADWYANE), "Your Retirement Investment Request Has Been Received");
        app.sAssert().assertAll();
    }

    @Test(groups = "EmailRetirementInvestment", dependsOnGroups = "CreateRetirementInvestment", alwaysRun = true, priority = 55)
    public void testEmailRetirementInvForAdmin() throws InterruptedException {
        app.sAssert().assertEquals(app.checkGoogleMail(Users.VLAD), "SIG Website: PENDING RETIREMENT SETUP");
        app.sAssert().assertAll();
    }

    @Test (groups = "EmailRetirementInvestment", dependsOnGroups = "CreateRetirementInvestment", alwaysRun = true, priority = 56)
    public void testActivateRetireInvestment() throws InterruptedException {
        app.goTo("http://securedincomegroup.stgng.co");
        HelperMethods helperMethods = new HelperMethods();
        helperMethods.signIn(Users.ADMIN);
        AdminDashboard adminDashboard = new AdminDashboard(app.getDriver());
        adminDashboard.clickLinkFundsPending();
        AdminFundsPending adminFundsPending = new AdminFundsPending(app.getDriver());
        String investNum = adminFundsPending.getFirstInvNum();
        RemoveAccount actionsWithWPAdmin = new RemoveAccount(app.getDriver());
        actionsWithWPAdmin.changeYearPublish("09","2017");
        app.goTo("http://securedincomegroup.stgng.co/admin-dashboard/");
        adminDashboard.clickLinkFundsActivate();
        AdminActivateFunds adminActivateFunds = new AdminActivateFunds(app.getDriver());
        adminActivateFunds.enterAccountNumber(investNum);
        adminActivateFunds.enterFundAmount("5,000");
        adminActivateFunds.enterFundDate("10/30/2017");
        adminActivateFunds.clickSubmitButton();
        Thread.sleep(2000);
        FancyBox fancyBox = new FancyBox(app.getDriver());
        fancyBox.clickSubmitButton();
        Header header = new Header(app.getDriver());
        header.clickLinkSign();
        helperMethods.signIn(Users.CHESALOV);
        UserAccounts userAccounts = new UserAccounts(app.getDriver());
        userAccounts.clickAccountNameRetirement();
        RetirementInvestments retirementInvestments = new RetirementInvestments(app.getDriver());
        app.sAssert().assertEquals(retirementInvestments.getDateFunded(), "10/30/2017");
        app.sAssert().assertEquals(retirementInvestments.getMaturityDate(), "04/30/2018");
        app.sAssert().assertEquals(retirementInvestments.getInvestmentRate(), "6.5%");
        app.sAssert().assertEquals(retirementInvestments.getInvestmentTerm(), "6 MONTH(S)");
        app.sAssert().assertEquals(retirementInvestments.getInvestmentStatus(), "ACTIVE");
        app.sAssert().assertEquals(userAccounts.getAlertRenewPending(), "RENEWAL PENDING : DUE 04/30/2018");
        app.sAssert().assertAll();

    }
}
