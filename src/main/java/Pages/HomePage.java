package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.time.Duration;

public class HomePage extends BasePage{

    public static final String URL= "https://www.decathlon.com/";

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @FindBy(css = "search-button.search-action--hidden-on-drawer button[aria-label='Search']" )
    private WebElement searchButton;

    @FindBy(id = "cmdk-input")
    private WebElement searchInput;

    @FindBy(css = "a.menu-list__link[href='/collections/mens']")
    private WebElement menMenuLink;

    @FindBy(css = "#submenu-2 a[href='/collections/mens-shirts']")
    private WebElement menTShirtSubcategoryLink;

    public HomePage()
    {
        PageFactory.initElements(driver,this);
    }

    public void navigateToHomePage()
    {
        driver.get(URL);
    }

    public boolean isHomePageLoaded()
    {
        return driver.getCurrentUrl().equals(URL);
    }


    public void clickSearchButton()
    {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        click(searchButton);
    }

    public void searchForProduct(String productName)
    {
        wait.until(ExpectedConditions.elementToBeClickable(searchInput));
        set(searchInput,productName);
        searchInput.sendKeys(Keys.ENTER);
    }


    public void hoverOverMenMenu(){
        wait.until(ExpectedConditions.visibilityOf(menMenuLink));
        new Actions(driver).moveToElement(menMenuLink).perform();
    }

    public void clickMenTSHirtSubcategory()
    {
        wait.until(ExpectedConditions.visibilityOf(menTShirtSubcategoryLink));
        click(menTShirtSubcategoryLink);
    }

}
