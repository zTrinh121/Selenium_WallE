import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
    public void testShowModal() throws InterruptedException{
        adminCenterPage.clickViewModal();
        Assertions.assertTrue(adminCenterPage.isModalDisplay());
        Thread.sleep(3000);
    }

    @Test
    public void testClickApproveBtnToast()  throws InterruptedException{
        adminCenterPage.clickApproveBtn();
        String message = adminCenterPage.toastContent();
        String actualResult = "Duyệt trung tâm thành công";
        Assertions.assertEquals("Duyệt trung tâm thành công", actualResult, "The no result text should match the expected message");
        Thread.sleep(3000);
    }

    @Test
    public void testClickRejectBtnToast() throws InterruptedException{
        adminCenterPage.clickRejectBtn();
        String message = adminCenterPage.toastContent();
        String actualResult = "Từ chối trung tâm thành công";
        Assertions.assertEquals("Từ chối trung tâm thành công", actualResult, "The no result text should match the expected message");
        Thread.sleep(3000);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
