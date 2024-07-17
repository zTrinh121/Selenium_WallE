package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdminCenterPage {
    private By noResult = By.id("no-result");
    private By searchInput = By.id("searchInput");
    private By searchFormBtn = By.cssSelector("#searchForm button");
    private By modal = By.cssSelector("#viewPostModal .modal-content");
    private By viewModalBtn = By.cssSelector("tr[id='1'] a[class='view-details']");
    private By searchResult = By.cssSelector("td[data-title='Đặng Hoàng Dư']");
    private By toast = By.id("toastContainer");
    private By approveBtn = By.cssSelector("tr[id='2'] button[class='approve']");
    private By rejectBtn = By.cssSelector("tr[id='3'] button[class='reject']");
    private WebDriver driver;
    public AdminCenterPage(WebDriver driver){
        this.driver = driver;
    }

    public WebElement getSearchContent(){
        return driver.findElement(searchInput);
    }
    public void clickSearchBtn(){
        driver.findElement(searchFormBtn).click();
    }

    public String getNoResult(){
        return driver.findElement(noResult).getText();
    }
    public String getResult(){
        return driver.findElement(searchResult).getText();
    }

    public void clickViewModal(){
        driver.findElement(viewModalBtn).click();
    }

    public boolean isModalDisplay(){
        return driver.findElement(modal).isDisplayed();
    }

    public String toastContent(){
        return driver.findElement(toast).getText();
    }

    public void clickApproveBtn(){
        driver.findElement(approveBtn).click();
    }
    public void clickRejectBtn(){
        driver.findElement(rejectBtn).click();
    }

}
