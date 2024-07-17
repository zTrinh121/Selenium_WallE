package tests;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import pages.LoginPage;
import utils.WebDriverSetup;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {
    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeEach
    public void setUp() {
        driver = new WebDriverSetup().createDriver();
        driver.get("http://localhost:8080/login");
        loginPage = new LoginPage(driver);
      //  login("ThanhDH", "Thanh20032003@", "STUDENT"); // Giả sử đây là thông tin đăng nhập hợp lệ
    }

    private void login(String username, String password, String role) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement usernameElement = wait.until(ExpectedConditions.elementToBeClickable(By.name("username")));
        usernameElement.sendKeys(username);

        WebElement passwordElement = wait.until(ExpectedConditions.elementToBeClickable(By.name("password")));
        passwordElement.sendKeys(password);

        WebElement roleDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.name("roleId")));
        new Select(roleDropdown).selectByVisibleText(role);

        WebElement loginButton = driver.findElement(By.cssSelector("input[type='submit']"));
        loginButton.click();
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/login.csv")
    public void testLogin(String username, String password, String role) {
        driver.get("http://localhost:8080/login");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement usernameElement = wait.until(ExpectedConditions.elementToBeClickable(By.name("username")));
        usernameElement.sendKeys(username);

        WebElement passwordElement = wait.until(ExpectedConditions.elementToBeClickable(By.name("password")));
        passwordElement.sendKeys(password);

        WebElement roleDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.name("roleId")));
        Select roleSelect = new Select(roleDropdown);
        roleSelect.selectByVisibleText(role);

        WebElement loginButton = driver.findElement(By.cssSelector("input[type='submit']"));
        loginButton.click();

        WebDriverWait waitTitle = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Kiểm tra xem có xuất hiện thông báo lỗi không
        List<WebElement> errorMessages = driver.findElements(By.cssSelector(".error-message"));
        if (!errorMessages.isEmpty()) {
            String errorMessage = errorMessages.get(0).getText();
            System.out.println("Login failed: " + errorMessage);
            assertFalse(errorMessage.isEmpty(), "There should be an error message.");
        } else {
            boolean isTitleCorrect = waitTitle.until(ExpectedConditions.titleContains("Dashboard"));
            assertTrue(isTitleCorrect, "Login should be successful and title should contain 'Dashboard'");
            System.out.println("Login successful.");
        }
    }

    @Test
    public void testAccessPersonalInformation() {
        // Khởi tạo WebDriver
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Tăng thời gian chờ

        try {
            // Đăng nhập vào ứng dụng
            driver.get("http://localhost:8080/login");
            WebElement usernameField = driver.findElement(By.name("username"));
            usernameField.sendKeys("ThanhDH"); // Thay thế bằng tên người dùng thực tế
            WebElement passwordField = driver.findElement(By.name("password"));
            passwordField.sendKeys("Thanh20032003@"); // Thay thế bằng mật khẩu thực tế
            WebElement loginButton = driver.findElement(By.cssSelector("input[type='submit']"));
            loginButton.click();

            // Đợi cho đến khi trang dashboard tải xong
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sidebar")));

            // Tìm liên kết đến trang thông tin cá nhân bằng XPath và click vào nó
            WebElement profileLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='sidebar']//a[@href='/profile']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", profileLink);

            // Đợi cho đến khi trang thông tin cá nhân hiển thị
            WebElement profileForm = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".container.light-style")));

            // Thay đổi thông tin cá nhân
            WebElement nameInput = driver.findElement(By.name("name"));
            WebElement addressInput = driver.findElement(By.name("address"));
            WebElement phoneInput = driver.findElement(By.name("phone"));
            WebElement emailInput = driver.findElement(By.name("email"));
            WebElement dobInput = driver.findElement(By.name("dob"));
            WebElement saveButton = driver.findElement(By.xpath("//button[@type='submit' and text()='Lưu thay đổi']"));

            // Xóa và nhập dữ liệu mới
            nameInput.clear();
            nameInput.sendKeys("New");
            addressInput.clear();
            addressInput.sendKeys("123");
            phoneInput.clear();
            phoneInput.sendKeys("0923426789");
            emailInput.clear();
            emailInput.sendKeys("new.email@example.com");
            dobInput.clear();
            dobInput.sendKeys("2000-01-01");

            // Nhấn vào nút Lưu thay đổi
            saveButton.click();

            // Debug: In ra log trước khi tìm kiếm thông báo thành công
            System.out.println("Form submitted, waiting for success message...");

            // Xác nhận cập nhật thành công, đảm bảo ID này phải tồn tại trong HTML sau khi form được gửi
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert.alert-success")));
            assertTrue(successMessage.isDisplayed(), "Update should be successful.");

        } catch (Exception e) {
            // Debug: In ra log nếu có lỗi
            e.printStackTrace();
        } finally {
            // Đóng trình duyệt
            driver.quit();
        }
    }








    @Test
    public void testTimetableAccessPermissions() {
        // Khởi tạo WebDriver
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Đăng nhập vào ứng dụng
            driver.get("http://localhost:8080/login");
            WebElement usernameField = driver.findElement(By.name("username"));
            usernameField.sendKeys("ThanhDH"); // Thay thế bằng tên người dùng thực tế
            WebElement passwordField = driver.findElement(By.name("password"));
            passwordField.sendKeys("Thanh20032003@"); // Thay thế bằng mật khẩu thực tế
            WebElement loginButton = driver.findElement(By.cssSelector("input[type='submit']"));
            loginButton.click();

            // Đợi cho đến khi trang dashboard tải xong
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sidebar")));

            // Tìm liên kết đến trang thời khóa biểu bằng XPath và click vào nó
            WebElement timetableLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='sidebar']//a[@href='/timetable']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", timetableLink);

            // Đợi cho đến khi trang thời khóa biểu hiển thị
            WebElement timetablePage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("timetablePage")));

            // Kiểm tra xem trang thời khóa biểu đã được hiển thị chưa
            assertTrue(timetablePage.isDisplayed(), "Timetable page should be displayed after clicking on timetable link");

        } catch (Exception e) {
            // In ra log nếu có lỗi
            e.printStackTrace();
        } finally {
            // Đóng trình duyệt
            driver.quit();
        }
    }


    @Test
    public void testMaterialAccessPermissions1() {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Đăng nhập vào ứng dụng
            driver.get("http://localhost:8080/login");
            WebElement usernameField = driver.findElement(By.name("username"));
            usernameField.sendKeys("ThanhDH"); // Thay thế bằng tên người dùng thực tế
            WebElement passwordField = driver.findElement(By.name("password"));
            passwordField.sendKeys("Thanh20032003@"); // Thay thế bằng mật khẩu thực tế
            WebElement loginButton = driver.findElement(By.cssSelector("input[type='submit']"));
            loginButton.click();

            // Đợi cho đến khi trang dashboard tải xong
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sidebar")));

            // Tìm liên kết đến trang tài liệu và click vào nó
            WebElement materialLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='sidebar']//a[@href='/material']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", materialLink);

            // Đợi cho đến khi trang tài liệu hiển thị
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("materials-container")));

            // Click vào "Chọn khối"
            WebElement gradeSelect = wait.until(ExpectedConditions.elementToBeClickable(By.id("grade-select")));
            gradeSelect.click();

            // Chọn một khối bất kỳ từ danh sách
            List<WebElement> gradeOptions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#grade-select option")));
            if (!gradeOptions.isEmpty()) {
                // Chọn một tùy chọn bất kỳ, ví dụ tùy chọn thứ 2 (vì tùy chọn đầu tiên thường là "Chọn khối")
                gradeOptions.get(1).click();
            }

            // Thêm xác nhận nếu cần thiết, ví dụ kiểm tra xem khối đã được chọn chưa
            WebElement selectedGrade = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#grade-select option[selected='selected']")));
            assertNotNull(selectedGrade, "Grade should be selected.");

        } catch (Exception e) {
            // In ra log nếu có lỗi
            e.printStackTrace();
        } finally {
            // Đóng trình duyệt
            driver.quit();
        }
    }


    @Test
    public void testMaterialAccessPermissions2() {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Đăng nhập vào ứng dụng
            driver.get("http://localhost:8080/login");
            WebElement usernameField = driver.findElement(By.name("username"));
            usernameField.sendKeys("ThanhDH"); // Thay thế bằng tên người dùng thực tế
            WebElement passwordField = driver.findElement(By.name("password"));
            passwordField.sendKeys("Thanh20032003@"); // Thay thế bằng mật khẩu thực tế
            WebElement loginButton = driver.findElement(By.cssSelector("input[type='submit']"));
            loginButton.click();

            // Đợi cho đến khi trang dashboard tải xong
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sidebar")));

            // Tìm liên kết đến trang tài liệu và click vào nó
            WebElement materialLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='sidebar']//a[@href='/material']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", materialLink);

            // Đợi cho đến khi trang tài liệu hiển thị
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("materials-container")));

            // Click vào "Chọn khối"
            WebElement gradeSelect = wait.until(ExpectedConditions.elementToBeClickable(By.id("grade-select")));
            gradeSelect.click();

            // Chọn một khối bất kỳ từ danh sách
            List<WebElement> gradeOptions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#grade-select option")));
            if (!gradeOptions.isEmpty()) {
                // Chọn một tùy chọn bất kỳ, ví dụ tùy chọn thứ 2 (vì tùy chọn đầu tiên thường là "Chọn khối")
                gradeOptions.get(8).click();
            }

            // Đợi cho đến khi danh sách môn học xuất hiện
            WebElement subjectSelect = wait.until(ExpectedConditions.elementToBeClickable(By.id("subject-select")));
            subjectSelect.click();

            // Chọn một môn học bất kỳ từ danh sách
            List<WebElement> subjectOptions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#subject-select option")));
            if (!subjectOptions.isEmpty()) {
                // Chọn một tùy chọn bất kỳ, ví dụ tùy chọn thứ 2 (vì tùy chọn đầu tiên thường là "Chọn môn học")
                subjectOptions.get(1).click();
            }

            // Thêm xác nhận nếu cần thiết, ví dụ kiểm tra xem môn học đã được chọn chưa
            WebElement selectedSubject = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#subject-select option[selected='selected']")));
            assertNotNull(selectedSubject, "Subject should be selected.");

        } catch (Exception e) {
            // In ra log nếu có lỗi
            e.printStackTrace();
        } finally {
            // Đóng trình duyệt
            driver.quit();
        }
    }

    @Test
    public void testMaterialAccessPermissions() throws IOException, InterruptedException {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Đăng nhập vào ứng dụng
            driver.get("http://localhost:8080/login");
            WebElement usernameField = driver.findElement(By.name("username"));
            usernameField.sendKeys("ThanhDH"); // Thay thế bằng tên người dùng thực tế
            WebElement passwordField = driver.findElement(By.name("password"));
            passwordField.sendKeys("Thanh20032003@"); // Thay thế bằng mật khẩu thực tế
            WebElement loginButton = driver.findElement(By.cssSelector("input[type='submit']"));
            loginButton.click();

            // Đợi cho đến khi trang dashboard tải xong
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".sidebar")));

            // Tìm liên kết đến trang tài liệu và click vào nó
            WebElement materialLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='sidebar']//a[@href='/material']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", materialLink);

            // Đợi cho đến khi trang tài liệu hiển thị
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("materials-container")));

            // Đọc dữ liệu từ file CSV
            try (FileReader reader = new FileReader("path/to/class_subjects.csv");
                 CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {

                for (CSVRecord csvRecord : csvParser) {
                    String grade = csvRecord.get("class");
                    String subject = csvRecord.get("subject");

                    // Chọn khối
                    WebElement gradeSelect = wait.until(ExpectedConditions.elementToBeClickable(By.id("grade-select")));
                    gradeSelect.click();
                    List<WebElement> gradeOptions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#grade-select option")));
                    for (WebElement option : gradeOptions) {
                        if (option.getText().equals(grade)) {
                            option.click();
                            break;
                        }
                    }

                    // Đợi cho đến khi danh sách môn học xuất hiện
                    WebElement subjectSelect = wait.until(ExpectedConditions.elementToBeClickable(By.id("subject-select")));
                    subjectSelect.click();
                    List<WebElement> subjectOptions = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#subject-select option")));
                    for (WebElement option : subjectOptions) {
                        if (option.getText().equals(subject)) {
                            option.click();
                            break;
                        }
                    }

                    // Thêm xác nhận nếu cần thiết, ví dụ kiểm tra xem môn học đã được chọn chưa
                    WebElement selectedSubject = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#subject-select option[selected='selected']")));
                    assertTrue(selectedSubject.isDisplayed(), "Subject should be selected.");

                    // Nghỉ 3 giây
                    Thread.sleep(3000);
                }
            }
        } catch (Exception e) {
            // In ra log nếu có lỗi
            e.printStackTrace();
        } finally {
            // Đóng trình duyệt
            driver.quit();
        }
    }


    @Test
    public void testAccessCourseDetails() {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Tăng thời gian chờ đợi

        try {
            // Đăng nhập vào ứng dụng
            driver.get("http://localhost:8080/login");
            WebElement usernameField = driver.findElement(By.name("username"));
            usernameField.sendKeys("VinhVQ"); // Thay thế bằng tên người dùng thực tế
            WebElement passwordField = driver.findElement(By.name("password"));
            passwordField.sendKeys("Wall2003@"); // Thay thế bằng mật khẩu thực tế
            WebElement loginButton = driver.findElement(By.cssSelector("input[type='submit']"));
            loginButton.click();

            // Đợi cho đến khi trang "Khóa học của tôi" tải xong
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".box-container")));

            // Tìm các khóa học và chọn một khóa học bất kỳ
            List<WebElement> courses = driver.findElements(By.xpath("//div[@class='box-container']//a[contains(text(), 'Xem chi tiết')]"));
            assertTrue(courses.size() > 0, "Should have at least one course.");

            // Click vào nút "Xem chi tiết" của khóa học đầu tiên
            WebElement viewDetailsButton = courses.get(0);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewDetailsButton);

            // Đợi cho đến khi trang chi tiết khóa học hiển thị
            WebElement courseDetailHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".detail-header h3")));
            assertTrue(courseDetailHeader.isDisplayed(), "Course detail page should be displayed after clicking on a course.");

            // Đợi 3 giây để xem chi tiết khóa học
            Thread.sleep(3000);

            // Bấm vào nút "Xem chi tiết" của phần "Kết quả/đánh giá"
            WebElement evaluationDetailsButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-evaluation-details")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", evaluationDetailsButton);

            // Đợi cho đến khi modal "Chi tiết đánh giá" hiển thị
            WebElement evaluationModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("evaluationModal")));
            assertTrue(evaluationModal.isDisplayed(), "Evaluation details modal should be displayed after clicking on 'Xem chi tiết'.");

            // Đợi 3 giây để xem chi tiết đánh giá
            Thread.sleep(3000);

        } catch (TimeoutException | InterruptedException e) {
            System.out.println("Current page HTML:");
            System.out.println(driver.getPageSource());
            throw new RuntimeException(e); // Ném lại ngoại lệ sau khi in ra mã HTML hiện tại của trang
        } finally {
            // Đóng trình duyệt
            driver.quit();
        }
    }

    @Test
    public void testAccessCourseDetails1() {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Tăng thời gian chờ đợi

        try {
            // Đăng nhập vào ứng dụng
            driver.get("http://localhost:8080/login");
            WebElement usernameField = driver.findElement(By.name("username"));
            usernameField.sendKeys("VinhVQ"); // Thay thế bằng tên người dùng thực tế
            WebElement passwordField = driver.findElement(By.name("password"));
            passwordField.sendKeys("Wall2003@"); // Thay thế bằng mật khẩu thực tế
            WebElement loginButton = driver.findElement(By.cssSelector("input[type='submit']"));
            loginButton.click();

            // Đợi cho đến khi trang "Khóa học của tôi" tải xong
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".box-container")));

            // Tìm các khóa học và chọn một khóa học bất kỳ
            List<WebElement> courses = driver.findElements(By.xpath("//div[@class='box-container']//a[contains(text(), 'Xem chi tiết')]"));
            assertTrue(courses.size() > 0, "Should have at least one course.");

            // Click vào nút "Xem chi tiết" của khóa học đầu tiên
            WebElement viewDetailsButton = courses.get(0);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewDetailsButton);

            // Đợi cho đến khi trang chi tiết khóa học hiển thị
            WebElement courseDetailHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".detail-header h3")));
            assertTrue(courseDetailHeader.isDisplayed(), "Course detail page should be displayed after clicking on a course.");

            // Đợi 3 giây để xem chi tiết khóa học
            Thread.sleep(3000);

            // Bấm vào nút "Xem chi tiết" của phần "Tình trạng điểm danh"
            WebElement attendanceDetailsButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-attendance-details")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", attendanceDetailsButton);

            // Đợi cho modal "Chi tiết điểm danh" hiển thị
            WebElement attendanceModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("attendanceModal")));
            assertTrue(attendanceModal.isDisplayed(), "Attendance details modal should be displayed after clicking on 'Xem chi tiết'.");

            // Đợi 3 giây để xem chi tiết điểm danh
            Thread.sleep(3000);

        } catch (TimeoutException | InterruptedException e) {
            System.out.println("Current page HTML:");
            System.out.println(driver.getPageSource());
            throw new RuntimeException(e); // Ném lại ngoại lệ sau khi in ra mã HTML hiện tại của trang
        } finally {
            // Đóng trình duyệt
            driver.quit();
        }
    }





    @Test
    public void testSearchFunctionality1() {
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Đăng nhập vào ứng dụng
            driver.get("http://localhost:8080/login");
            WebElement usernameField = driver.findElement(By.name("username"));
            usernameField.sendKeys("VinhVQ"); // Thay thế bằng tên người dùng thực tế
            WebElement passwordField = driver.findElement(By.name("password"));
            passwordField.sendKeys("Wall2003@"); // Thay thế bằng mật khẩu thực tế
            WebElement loginButton = driver.findElement(By.cssSelector("input[type='submit']"));
            loginButton.click();

            // Đợi cho đến khi trang "Khóa học của tôi" tải xong
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".box-container")));
            // Thêm thời gian chờ 3 giây trước khi thực hiện tìm kiếm
            Thread.sleep(3000);

            // Thực hiện tìm kiếm
            WebElement searchTypeDropdown = driver.findElement(By.id("searchType"));
            searchTypeDropdown.click();
            WebElement allOption = driver.findElement(By.xpath("//option[@value='all']"));
            allOption.click();

            WebElement searchInput = driver.findElement(By.id("search-input"));
            searchInput.sendKeys("Ngữ văn lớp 11"); // Từ khóa tìm kiếm, có thể thay đổi tùy theo yêu cầu

            WebElement searchButton = driver.findElement(By.cssSelector(".search-btn"));
            searchButton.click();

            // Đợi cho đến khi kết quả tìm kiếm hiển thị
            WebElement searchResults = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search-results")));

            // Kiểm tra xem có kết quả tìm kiếm hay không
            assertTrue(searchResults.isDisplayed(), "Search results should be displayed.");
            assertTrue(searchResults.findElements(By.cssSelector(".search-result-item")).size() > 0, "There should be at least one search result.");

        } catch (NoSuchWindowException e) {
            System.out.println("The browser window was closed before the test could complete.");
        } catch (TimeoutException e) {
            System.out.println("Current page HTML:");
            System.out.println(driver.getPageSource());
            throw new RuntimeException(e); // Ném lại ngoại lệ sau khi in ra mã HTML hiện tại của trang
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Đóng trình duyệt
            driver.quit();
        }
    }

    @Test
    public void testSearchFunctionality() {
        System.setProperty("webdriver.chrome.driver", "C:\\path\\to\\chromedriver.exe"); // Đảm bảo đường dẫn đúng
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Tăng thời gian chờ đợi

        try {
            // Đăng nhập vào ứng dụng
            driver.get("http://localhost:8080/login");
            WebElement usernameField = driver.findElement(By.name("username"));
            usernameField.sendKeys("ThanhDH"); // Thay thế bằng tên người dùng thực tế
            WebElement passwordField = driver.findElement(By.name("password"));
            passwordField.sendKeys("Thanh20032003@"); // Thay thế bằng mật khẩu thực tế
            WebElement loginButton = driver.findElement(By.cssSelector("input[type='submit']"));
            loginButton.click();

            // Đợi cho đến khi trang dashboard tải xong
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".box-container")));

            // Thêm thời gian chờ 3 giây trước khi thực hiện tìm kiếm
            Thread.sleep(3000);

            // Thực hiện tìm kiếm
            WebElement searchTypeDropdown = driver.findElement(By.id("searchType"));
            Select searchTypeSelect = new Select(searchTypeDropdown);
            searchTypeSelect.selectByVisibleText("Khóa học"); // Chọn "Khóa học"

            WebElement searchInput = driver.findElement(By.id("search-input"));
            searchInput.sendKeys("Ngữ văn lớp 11"); // Từ khóa tìm kiếm, có thể thay đổi tùy theo yêu cầu

            // Thêm thời gian chờ 3 giây trước khi click vào kết quả đầu tiên
            Thread.sleep(3000);

            // Chọn kết quả tìm kiếm đầu tiên
            WebElement firstResultButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='search-results']/div[1]")));

            // Thử các phương pháp click khác nhau

            // Phương pháp 1: Click trực tiếp
            try {
                firstResultButton.click();
                System.out.println("Clicked using direct click.");
            } catch (Exception e) {
                System.out.println("Direct click failed: " + e.getMessage());
            }

            // Phương pháp 2: Sử dụng Actions để click
            try {
                Actions actions = new Actions(driver);
                actions.moveToElement(firstResultButton).click().perform();
                System.out.println("Clicked using Actions.");
            } catch (Exception e) {
                System.out.println("Click using Actions failed: " + e.getMessage());
            }

            // Phương pháp 3: Sử dụng JavaScript để click
            try {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", firstResultButton);
                System.out.println("Clicked using JavaScript.");
            } catch (Exception e) {
                System.out.println("Click using JavaScript failed: " + e.getMessage());
            }

            // Đợi cho đến khi trang chi tiết khóa học hiển thị
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(), 'Chi tiết khóa học')]")));

            // Thêm thời gian chờ 3 giây trước khi đóng trình duyệt
            Thread.sleep(3000);

        } catch (NoSuchWindowException e) {
            System.out.println("The browser window was closed before the test could complete.");
        } catch (TimeoutException e) {
            System.out.println("Current page HTML:");
            System.out.println(driver.getPageSource());
            throw new RuntimeException(e); // Ném lại ngoại lệ sau khi in ra mã HTML hiện tại của trang
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Đóng trình duyệt
            driver.quit();
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
