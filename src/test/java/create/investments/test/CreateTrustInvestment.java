package create.investments.test;

import admin.pages.AdminActivateFunds;
import admin.pages.AdminDashboard;
import admin.pages.AdminFundsPending;
import admin.pages.FancyBox;
import common.elements.Header;
import create.account.RemoveAccount;
import create.investment.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import test.base.TestBase;
import test.base.data.HelperMethods;
import test.base.data.Users;
import user.pages.IndividualInvestments;
import user.pages.JointInvestments;
import user.pages.TrustInvestments;
import user.pages.UserAccounts;

public class CreateTrustInvestment extends TestBase{

    @Test(groups = "CreateTrustInvestment", dependsOnGroups = "EmailRetirementInvestment", alwaysRun = true, priority = 58)
    public void testCreateTrustInvest1Year() {
        app.goTo("http://securedincomegroup.stgng.co/");
        HelperMethods helperMethods = new HelperMethods();
        helperMethods.signIn(Users.CHESALOV);
        UserAccounts userAccounts = new UserAccounts(app.getDriver());
        userAccounts.clickAccountNameTrust();
        TrustInvestments trustInvestments = new TrustInvestments(app.getDriver());
        trustInvestments.clickLinkCreateInvestment();
        Step1Invest step1Invest = new Step1Invest(app.getDriver());
        step1Invest.chooseInvestment1Year();
        step1Invest.enterInvestmentAmount("10,111.99");
        step1Invest.clickSaveButton();
        Step2Invest step2Invest = new Step2Invest(app.getDriver());
        step2Invest.checkedPayoutDividends();
        step2Invest.checkedDivCheckboxSameAddress();
        step2Invest.clickSaveButton();
        Step3Invest step3Invest = new Step3Invest(app.getDriver());
        step3Invest.clickConfirmButton();
        Step4Invest step4Invest = new Step4Invest(app.getDriver());
        step4Invest.agreeAll("si");
        step4Invest.clickFinishButton();
        Step5Invest step5Invest = new Step5Invest(app.getDriver());
        step5Invest.clickFundByCheckButton();
        step5Invest.clickFinishButton();
        app.sAssert().assertEquals(userAccounts.getAlertFinishFunding(), "FINISH FUNDING: $10,111.99");
        app.sAssert().assertAll();
    }

    @Test(groups = "CreateTrustInvestment", dependsOnGroups = "EmailRetirementInvestment", alwaysRun = true, priority = 60)
    public void testActivateTrustInvestment() throws InterruptedException {
        app.goTo("http://securedincomegroup.stgng.co");
        HelperMethods helperMethods = new HelperMethods();
        helperMethods.signIn(Users.ADMIN);
        RemoveAccount actionsWithWPAdmin = new RemoveAccount(app.getDriver());
        actionsWithWPAdmin.changeYearPublish("04","2017");
        app.goTo("http://securedincomegroup.stgng.co/admin-dashboard/");
        AdminDashboard adminDashboard = new AdminDashboard(app.getDriver());
        adminDashboard.clickLinkFundsPending();
        AdminFundsPending adminFundsPending = new AdminFundsPending(app.getDriver());
        String investNum = adminFundsPending.getFirstInvNum();
        app.goTo("http://securedincomegroup.stgng.co/admin-dashboard/");
        adminDashboard.clickLinkFundsActivate();
        AdminActivateFunds adminActivateFunds = new AdminActivateFunds(app.getDriver());
        adminActivateFunds.enterAccountNumber(investNum);
        adminActivateFunds.enterFundAmount("10111.99");
        adminActivateFunds.enterFundDate("04/03/2017");
        adminActivateFunds.clickSubmitButton();
        Thread.sleep(2000);
        FancyBox fancyBox = new FancyBox(app.getDriver());
        fancyBox.clickSubmitButton();
        Header header = new Header(app.getDriver());
        header.clickLinkSign();
        helperMethods.signIn(Users.CHESALOV);
        UserAccounts userAccounts = new UserAccounts(app.getDriver());
        userAccounts.clickAccountNameTrust();
        app.sAssert().assertEquals(userAccounts.getAlertRenewPending(), "RENEWAL PENDING : DUE 04/03/2018");
        app.sAssert().assertAll();

    }
}