import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.AdminAccountPage;
import pages.AdminPage;

import java.time.Duration;

public class AdminAccountTest {
    private WebDriver driver;
    private AdminAccountPage adminAccountPagePage;

    @BeforeEach
    public void setUp() {
        // Set up ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/accountManagement");
        adminAccountPagePage = new AdminAccountPage(driver);
    }
    @Test
    public void testClickPageNumberTwo(){
        adminAccountPagePage.clickPage();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Assertions.assertTrue(adminAccountPagePage.isPageNumberThreeDisplay(), "The page number three should displayed");
    }
    @Test
    public void testSubmitGoToPageSuccess(){

        adminAccountPagePage.getGoToPageInput().sendKeys("4");
        adminAccountPagePage.clickGoToPageBtn();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Assertions.assertTrue(adminAccountPagePage.isPageNumberFiveDisplay(), "The page number three should displayed");
    }

    @Test
    public void testSubmitGoToPageFail(){

        adminAccountPagePage.getGoToPageInput().sendKeys("12");
        adminAccountPagePage.clickGoToPageBtn();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        Assertions.assertEquals("Your page choosen is exceed", alertText, "Alert message should indicate an invalid page number");
    }
    @Test
    public void testPageSelectTenPage(){
        Select selectType = new Select(adminAccountPagePage.getPageSelect());
        selectType.selectByVisibleText("10/page");
        Assertions.assertTrue(adminAccountPagePage.isPageNumberFiveDisplay(), "The page number three should displayed");
    }
    @Test
    public void testClickChangeStatusAccount(){
        adminAccountPagePage.clickLockBtn();
        Assertions.assertTrue(adminAccountPagePage.isToastDisplay(), "The page number three should displayed");
    }


    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
