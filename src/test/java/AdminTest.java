import dev.failsafe.internal.util.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.AdminPage;

import java.time.Duration;

public class AdminTest {

    private WebDriver driver;
    private AdminPage adminPage;

    @BeforeEach
    public void setUp() {
        // Set up ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/admin");
        adminPage = new AdminPage(driver);
    }


    @Test
        public void testSystemNotificationBtn(){

            adminPage.clickSystemNotificationBtn();

            // Wait for the page to load
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(adminPage.getNotificationTable()));

        Assertions.assertTrue(adminPage.isNotificationTableTitleDisplayed(), "The system notification table title should be displayed");

    }

    @Test
    public void testPrivateNotificationBtn(){

        adminPage.clickPrivateNotificationBtn();

        // Wait for the page to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(adminPage.getNotificationTable()));

        Assertions.assertTrue(adminPage.isNotificationTableTitleDisplayed(), "The private notification table title should be displayed");

    }

    @Test
    public void testViewNotificationModalDisplay(){
        adminPage.clickViewNotification();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(adminPage.getViewNotificationModal()));

        Assertions.assertTrue(adminPage.isViewNotificationModalDisplayed(), "The view notification mdoal should be displayed");
    }

    @Test
    public void testViewNotificationModalClose(){
        adminPage.clickCloseViewNotificationModal();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(adminPage.getViewNotificationModal()));
        Assertions.assertFalse(adminPage.isViewNotificationModalDisplayed(), "The view notification mdoal should be disappeared");
    }

    @Test
    public void testViewNotificationModalCloseWindow(){
        adminPage.clickViewNotification();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions action = new Actions(driver);
        action.moveByOffset(0, 0).click().build().perform();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(adminPage.getViewNotificationModal()));

        Assertions.assertFalse(adminPage.isViewNotificationModalDisplayed(), "The view notification mdoal should be disappeared");
    }

    @Test
    public void testEditModalDisplay(){
        adminPage.clickEditNotificationModal();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(adminPage.getUpdateModal()));

        Assertions.assertTrue(adminPage.isEditModalDisplay(), "The edit modal is displayed");
    }

    @Test
    public void testToastSuccessDisplay(){
        adminPage.clickConfirmNotificationModal();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(adminPage.getToastInfoUpdate()));
        Assertions.assertTrue(adminPage.isToastUpdateSuccess(), "The edit toast is displayed");
    }

    @Test
    public void testCreateNotificationModalDisplay(){
        adminPage.clickCreateNotificationModal();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(adminPage.getCreateNotificationModal()));

        Assertions.assertTrue(adminPage.isCreateNotificationModal(), "The create notification modal is displayed");
    }
    @Test
    public void testSubmitCreateNewNotificationWithEmptyTitle(){
        adminPage.clickCreateNotificationModal();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(adminPage.getCreateNotificationModal()));
        adminPage.clickSubmitNewNotification();
        String validationMessage = adminPage.getMessageValidationTitle();

        Assertions.assertEquals(validationMessage, "Please fill out this field.", "The validation message of title field not match.");
    }

    @Test
    public void testSubmitCreateNewNotificationWithEmptyContent(){
        adminPage.clickCreateNotificationModal();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(adminPage.getCreateNotificationModal()));
        adminPage.clickSubmitNewNotification();
        String validationMessage = adminPage.getMessageValidationContent();

        Assertions.assertEquals(validationMessage, "Please fill out this field.", "The validation message of title field not match.");
    }

    @Test
    public void testSubmitCreateNewSystemNotificationSuccess(){
        adminPage.clickCreateNotificationModal();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(adminPage.getCreateNotificationModal()));
        WebElement notificationTitle = adminPage.getNotificationTitleWebElement();
        notificationTitle.sendKeys("Thông báo thứ 1");
        WebElement notificationContent = adminPage.getNotificationContentWebElement();
        notificationContent.sendKeys("Nội dung chi tiết về thông báo thứ 1");
        adminPage.clickSubmitNewNotification();
        wait.until(ExpectedConditions.visibilityOfElementLocated(adminPage.getToastInfoUpdate()));
        Assertions.assertTrue(adminPage.isToastUpdateSuccess(), "The edit toast is displayed");
    }
//    @Test
//    public void testSubmitCreateNewPrivateNotificationSuccess(){
//        adminPage.clickCreateNotificationModal();
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(adminPage.getCreateNotificationModal()));
//        WebElement notificationTitle = adminPage.getNotificationTitleWebElement();
//        notificationTitle.sendKeys("Thông báo thứ 1");
//        WebElement notificationContent = adminPage.getNotificationContentWebElement();
//        notificationContent.sendKeys("Nội dung chi tiết về thông báo thứ 1");
//        Select selectType = new Select(adminPage.getNotificationTypeWebElement());
//        selectType.selectByVisibleText("Riêng tư");
//        adminPage.getNotificationSendToWebElement().sendKeys("AnDL");
//        adminPage.clickSubmitNewNotification();
//        wait.until(ExpectedConditions.visibilityOfElementLocated(adminPage.getToastInfoUpdate()));
//        Assertions.assertTrue(adminPage.isToastUpdateSuccess(), "The edit toast is displayed");
//    }

    @Test
    public void testNavigateAccountManagement(){
        adminPage.getAccountManagement().click();
        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = "http://localhost:8080/accountManagement";
        Assertions.assertEquals(expectedUrl, currentUrl, "The page should redirect to the expected URL");
    }

    @Test
    public void testNavigateCenterManagement(){
        adminPage.getCenterManagement().click();
        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = "http://localhost:8080/centerManagement";
        Assertions.assertEquals(expectedUrl, currentUrl, "The page should redirect to the expected URL");
    }

    @Test
    public void testNavigatePostManagement(){
        adminPage.getPostManagement().click();
        String currentUrl = driver.getCurrentUrl();
        String expectedUrl = "http://localhost:8080/approveManagement";
        Assertions.assertEquals(expectedUrl, currentUrl, "The page should redirect to the expected URL");
    }

    @AfterEach
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
}
