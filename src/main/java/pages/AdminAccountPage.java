package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdminAccountPage {

    private By numberPageTwo = By.cssSelector("body > div:nth-child(1) > div:nth-child(2) > section:nth-child(1) > main:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(3) > span:nth-child(2) > button:nth-child(2)");
    private By numberPageThree = By.cssSelector("button:nth-child(3)");
    private By lockAccountBtn = By.cssSelector(".action-button[data-user-id='1']");
    private By goToPageInput = By.id("goToPageInput");
    private By goToPageButton = By.id("goToPageButton");
    private By pageSizeSelector = By.id("pageSizeSelector");
    private By numberPageFive = By.cssSelector("button:nth-child(4)");
    private By toast = By.cssSelector("#toastContainer");
    private WebDriver driver;
    public AdminAccountPage(WebDriver driver){
        this.driver = driver;
    }

    public void clickPage(){
        driver.findElement(numberPageTwo).click();
    }

    public boolean isPageNumberThreeDisplay(){
        return driver.findElement(numberPageThree).isDisplayed();
    }
    public boolean isPageNumberFiveDisplay(){
        return driver.findElement(numberPageFive).isDisplayed();
    }
    public void clickGoToPageBtn(){
        driver.findElement(goToPageButton).click();
    }

    public WebElement getGoToPageInput(){
        return driver.findElement(goToPageInput);
    }
    public WebElement getPageSelect(){
        return driver.findElement(pageSizeSelector);
    }
    public void clickLockBtn(){
        driver.findElement(lockAccountBtn).click();
    }
    public By getModal(){
        return toast;
    }

    public boolean isToastDisplay(){
        return driver.findElement(toast).isDisplayed();
    }
}
