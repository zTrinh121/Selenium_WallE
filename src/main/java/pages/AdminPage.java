package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AdminPage {
    //Dashboard Locators
    private By accountManagement = By.cssSelector("body > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > ul:nth-child(1) > li:nth-child(2) > a:nth-child(1) > span:nth-child(2)");
    private By postManagement = By.cssSelector("body > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > ul:nth-child(1) > li:nth-child(3) > a:nth-child(1) > span:nth-child(2)");
    private By centerManagement = By.cssSelector("body > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > ul:nth-child(1) > li:nth-child(4) > a:nth-child(1) > span:nth-child(2)");

    private By systemNotificationBtn = By.id("publicNotificationBtn");
    private By privateNotificationBtn = By.id("privateNotificationBtn");
    private By notificationTable = By.id("notificationTable");
    private By viewNotificationBtn = By.cssSelector(".view-details[data-title='Thông báo về khóa học mới về Lập trình Python']");
    private By viewNotificationModal = By.id("viewNotificationModal");
    private By closeViewNotificationModal = By.id("closeViewNotification");
    private By editNotificationBtn = By.cssSelector(".fas.fa-edit[data-id='10']");
    private By editNotificationModal = By.id("editModal");
    private By confirmEditNotificationModal = By.id("confirmEdit");
    private By deleteNotificationBtn = By.cssSelector(".fas.fa-edit[data-id='12']");
    private By toast = By.id("toast");
    private By createNotificationBtn = By.id("createNotificationBtn");
    private By createNotificationModal = By.id("createNotificationModal");
    private By notificationTitle = By.id("notificationTitle");
    private By notificationType = By.id("notificationType");
    private By notificationContent = By.id("notificationContent");
    private By notificationSendTo = By.id("notificationSendTo");
    private By createNewNotificationBtn = By.id("createNewNotification");


    // Constructor
    private WebDriver driver;
    public AdminPage(WebDriver driver){
        this.driver = driver;
    }

    //PageActions
    public void clickSystemNotificationBtn(){
        driver.findElement(systemNotificationBtn).click();
    }
    public void clickPrivateNotificationBtn(){
        driver.findElement(privateNotificationBtn).click();
    }
    public By getNotificationTable() {
        return notificationTable;
    }
    public boolean isNotificationTableTitleDisplayed() {
        return driver.findElement(notificationTable).isDisplayed();
    }
    public void clickViewNotification(){
        clickSystemNotificationBtn();
        driver.findElement(viewNotificationBtn).click();
    }
    public By getViewNotificationModal(){
        return viewNotificationModal;
    }
    public boolean isViewNotificationModalDisplayed() {
        return driver.findElement(viewNotificationModal).isDisplayed();
    }
    public void clickCloseViewNotificationModal(){
        clickViewNotification();
        driver.findElement(closeViewNotificationModal).click();
    }

    public void clickEditNotificationModal(){
        clickSystemNotificationBtn();
        driver.findElement(editNotificationBtn).click();
    }
    public void clickConfirmNotificationModal(){
        clickEditNotificationModal();
        driver.findElement(confirmEditNotificationModal).click();
    }
    public By getUpdateModal(){
        return editNotificationModal;
    }
    public boolean isEditModalDisplay(){
        return driver.findElement(editNotificationModal).isDisplayed();
    }
    public By getToastInfoUpdate() {
        return toast;
    }
    public boolean isToastUpdateSuccess(){
        return driver.findElement(toast).isDisplayed();
    }
    public void clickCreateNotificationModal(){
        driver.findElement(createNotificationBtn).click();
    }
    public By getCreateNotificationModal() {
        return createNotificationModal;
    }
    public boolean isCreateNotificationModal(){
        return driver.findElement(createNotificationModal).isDisplayed();
    }

    public void clickSubmitNewNotification(){
        driver.findElement(createNewNotificationBtn).click();
    }

    public WebElement getNotificationTitleWebElement(){
        return driver.findElement(notificationTitle);
    }
    public WebElement getNotificationContentWebElement(){
        return driver.findElement(notificationContent);
    }
    public WebElement getNotificationTypeWebElement(){
        return driver.findElement(notificationType);
    }

    public WebElement getNotificationSendToWebElement(){
        return driver.findElement(notificationSendTo);
    }

    public String getMessageValidationTitle(){
        return driver.findElement(notificationTitle).getAttribute("validationMessage");
    }

    public String getMessageValidationContent(){
        return driver.findElement(notificationContent).getAttribute("validationMessage");
    }

    public WebElement getAccountManagement(){
        return driver.findElement(accountManagement);
    }

    public WebElement getCenterManagement(){
        return driver.findElement(centerManagement);
    }

    public WebElement getPostManagement(){
        return driver.findElement(postManagement);
    }

}

