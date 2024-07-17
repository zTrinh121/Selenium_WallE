package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private WebDriver driver;
    private By usernameLocator = By.id("username");
    private By passwordLocator = By.id("password");
    private By loginButtonLocator = By.xpath("//input[@type='submit']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterUsername(String username) {
        driver.findElement(usernameLocator).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordLocator).sendKeys(password);
    }

    public void clickLoginButton() {
        driver.findElement(loginButtonLocator).click();
    }
}
