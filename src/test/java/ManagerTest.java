import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.lang.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.*;


public class ManagerTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        // Set up ChromeDriver using WebDriverManager
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterEach
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testMain() throws InterruptedException {
        driver.get("http://localhost:8080/login");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(300));
        //search keyword
        WebElement searchUsername = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        WebElement searchPassword = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("password")));
        //passing value
        searchUsername.sendKeys("Jenny");
        searchPassword.sendKeys("Wall2003@");
        //confirm
        WebElement roleDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("roleId")));
        Select roleSelect = new Select(roleDropdown);
        roleSelect.selectByVisibleText("MANAGER");

        WebElement loginButton = wait.until(ExpectedConditions.
                elementToBeClickable(By.xpath
                        ("//input[@type='submit' and @value='Đăng Nhập']")));
        loginButton.click();

        wait.until(ExpectedConditions.urlContains("/managerHome"));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        driver.get("http://localhost:8080/manager/mpost");
        String URL2 = driver.getCurrentUrl();
        assertEquals(URL2, "http://localhost:8080/manager/mpost" );
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("delete-user")));
        deleteButton.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WebElement confirmDeleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("confirmDelete")));
        confirmDeleteButton.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        driver.get("http://localhost:8080/manager/managerHome");
        String URL3 = driver.getCurrentUrl();
        assertEquals(URL3, "http://localhost:8080/manager/managerHome" );
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WebElement viewButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("open-modal-btn")));
        viewButton.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        wait.until(ExpectedConditions.urlContains("http://localhost:8080/manager/centerHome?centerIdn=1"));
        String URL4 = driver.getCurrentUrl();
        assertEquals(URL4, "http://localhost:8080/manager/centerHome?centerIdn=1" );
        WebElement getTea = wait.until(ExpectedConditions.elementToBeClickable(By.id("boxInfot")));
        getTea.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        wait.until(ExpectedConditions.urlContains("http://localhost:8080/manager/qlgv?centerId=1"));
        String URL5 = driver.getCurrentUrl();
        assertEquals(URL5, "http://localhost:8080/manager/qlgv?centerId=1" );

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(By.className("add-btn")));
        addBtn.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        wait.until(ExpectedConditions.urlContains("http://localhost:8080/manager/mapTea?centerId=1"));
        assertEquals(URL5, "http://localhost:8080/manager/qlgv?centerId=1" );
        WebElement aprBtn = wait.until(ExpectedConditions.elementToBeClickable(By.className("approve-btn")));
        aprBtn.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WebElement rjtBtn = wait.until(ExpectedConditions.elementToBeClickable(By.className("reject-btn")));
        rjtBtn.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.get("http://localhost:8080/manager/qlgv?centerId=1");
        wait.until(ExpectedConditions.urlContains("http://localhost:8080/manager/qlgv?centerId=1"));
        assertEquals(URL5, "http://localhost:8080/manager/qlgv?centerId=1" );
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
