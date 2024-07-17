package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProfileUpdateTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        // Thiết lập ChromeDriver
        System.setProperty("webdriver.chrome.driver", "path_to_chromedriver");
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/login");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Đăng nhập vào ứng dụng
        WebElement usernameField = driver.findElement(By.name("username"));
        usernameField.sendKeys("your_username");
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("your_password");
        WebElement loginButton = driver.findElement(By.cssSelector("input[type='submit']"));
        loginButton.click();

        // Điều hướng đến trang profile
        driver.get("http://localhost:8080/profile");
    }

    @Test
    public void testUpdateProfile() {
        // Điền thông tin mới vào form
        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("name")));
        nameField.clear();
        nameField.sendKeys("New Name");

        WebElement addressField = driver.findElement(By.name("address"));
        addressField.clear();
        addressField.sendKeys("New Address");

        WebElement phoneField = driver.findElement(By.name("phone"));
        phoneField.clear();
        phoneField.sendKeys("1234567890");

        WebElement dobField = driver.findElement(By.name("dob"));
        dobField.clear();
        dobField.sendKeys("2001-01-01");

        // Gửi form
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        // Kiểm tra xem thông tin đã được cập nhật thành công
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("successMessageId"))); // Giả sử có một phần tử hiển thị thông báo thành công
        assertTrue(successMessage.isDisplayed());
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

