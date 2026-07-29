package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v136.page.Page;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SearchResultsPage extends BasePage{

    WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(10));

    @FindBy(css = "[data-testid='products-count']")
    private WebElement productsCount;

    @FindBy(css = "[data-testid='product-grid'] a.product-card__link")
    private List<WebElement> productsList;

    @FindBy(css = "li#template--18434715254846__main-7189867888702 a")
    private WebElement arpenazBackpackTile;


    public SearchResultsPage()
    {
        PageFactory.initElements(driver,this);
    }

    public String getPageTitle()
    {
        return driver.getTitle();
    }

    public int getResultsCount()
    {
        wait.until(ExpectedConditions.visibilityOf(productsCount));
        String countText= productsCount.getText();
        String digits= countText.replaceAll("[^0-9]","");
        return Integer.parseInt(digits);
    }

    public void clickFirstProductTile()
    {
        WebElement firstProductTile= productsList.get(0);
        wait.until(ExpectedConditions.visibilityOf(firstProductTile));
        click(firstProductTile);
    }

    public boolean isOnProductPage()
    {
        wait.until(ExpectedConditions.urlContains("/products/"));
        return driver.getCurrentUrl().contains("/products/");
    }

    public void clickArpenazBackpackTile()
    {
        wait.until(ExpectedConditions.visibilityOf(arpenazBackpackTile));
        click(arpenazBackpackTile);
    }

    public boolean isPageTitleReflectingSearchTerm(String searchTerm)
    {
        return getPageTitle().toLowerCase().contains(searchTerm.toLowerCase());
    }


}
