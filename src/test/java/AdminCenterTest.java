import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.AdminAccountPage;
import pages.AdminCenterPage;

public class AdminCenterTest {
    private WebDriver driver;
    private AdminCenterPage adminCenterPage;

    @BeforeEach
    public void setUp() {
        // Set up ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/centerManagement");
        adminCenterPage = new AdminCenterPage(driver);
    }

    @Test
    public void testSearchNoResult(){
        adminCenterPage.getSearchContent().sendKeys("nothing");
        adminCenterPage.clickSearchBtn();
        String resultText = adminCenterPage.getNoResult();
        Assertions.assertEquals("No result", resultText, "The no result text should match the expected message");

    }

    @Test
    public void testSearchHasResult(){
        adminCenterPage.getSearchContent().sendKeys("dư");
        adminCenterPage.clickSearchBtn();
        String resultText = adminCenterPage.getResult();
        Assertions.assertEquals("Đặng Hoàng Dư", resultText, "The no result text should match the expected message");

    }

    @Test
    public void testSearchNoResultEnterKey(){
        adminCenterPage.getSearchContent().sendKeys("nothing");
        adminCenterPage.getSearchContent().sendKeys(Keys.ENTER);
        String resultText = adminCenterPage.getNoResult();
        Assertions.assertEquals("No result", resultText, "The no result text should match the expected message");
    }

    @Test
    public void testShowModal(){
        adminCenterPage.clickViewModal();
        Assertions.assertTrue(adminCenterPage.isModalDisplay());
    }

    @Test
    public void testClickApproveBtnToast(){
        adminCenterPage.clickApproveBtn();
        String message = adminCenterPage.toastContent();
        String actualResult = "Duyệt bài thành công";
        Assertions.assertEquals("Duyệt bài thành công", actualResult, "The no result text should match the expected message");
    }

    @Test
    public void testClickRejectBtnToast(){
        adminCenterPage.clickRejectBtn();
        String message = adminCenterPage.toastContent();
        String actualResult = "Từ chối bài thành công";
        Assertions.assertEquals("Từ chối bài thành công", actualResult, "The no result text should match the expected message");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
