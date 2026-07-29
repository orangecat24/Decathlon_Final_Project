package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    public static WebDriver driver;

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @FindBy(id = "spicegems_cr_btn_no" )
    public WebElement stayOnUsSiteButton;

    @FindBy(css = "button.klaviyo-close-form[aria-label='Close dialog']")
    public WebElement closeOfferBannerButton;

    public void stayOnUsSite()
    {
        wait.until(ExpectedConditions.elementToBeClickable(stayOnUsSiteButton));
        click(stayOnUsSiteButton);
    }

    public void dismissOfferBanner()
    {
        wait.until(ExpectedConditions.elementToBeClickable(closeOfferBannerButton));
        click(closeOfferBannerButton);
    }


    public void setDriver(WebDriver driver)
    {
        BasePage.driver = driver;
    }


    protected void set(WebElement element, String text)
    {
        element.clear();
        element.sendKeys(text);
    }

    protected void click(WebElement element)
    {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    

    public void goBack()
    {
        driver.navigate().back();
    }

}
