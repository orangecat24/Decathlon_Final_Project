package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ProductCollectionsPage extends BasePage{

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    private static final String menPantsCollectionUrl = "https://www.decathlon.com/collections/mens-pants";

    private static final String mensDownJacketsCollectionUrl= "https://www.decathlon.com/collections/mens-down-jackets";

    ProductPage productPage = new ProductPage();

    @FindBy(css = "[data-testid='products-count']")
    private WebElement productsCount;

    @FindBy(css = "[data-testid='product-grid'] a.product-card__link")
    private List<WebElement> productsList;

    @FindBy(css = "details#Facet-Details-template--18434715222078__main-filter-v-t-shopify-color-pattern[data-auto-close-details='desktop'] summary")
    private WebElement colorFilterSummary;

    @FindBy(css = "details#Facet-Details-template--18434715222078__main-filter-v-t-shopify-color-pattern[data-auto-close-details='desktop'] label.variant-option__button-label--has-swatch")
    private List<WebElement> colorSwatchLabelsList;

    @FindBy(css = "#facet-clear-component button")
    private WebElement clearFiltersButton;

    @FindBy(css = "details[data-auto-close-details='desktop'] [data-testid='price-filter']")
    private WebElement priceFilterSummary;

    @FindBy(id = "Price-GTE-horizontal")
    private WebElement minPriceInput;

    @FindBy(id = "Price-LTE-horizontal")
    private WebElement maxPriceInput;

    @FindBy(css = "[data-testid='product-grid'] product-card")
    private List<WebElement> productTilesList;

    @FindBy(css = "div.facets sorting-filter-component details#Sorting-template--18434715222078__main summary")
    private WebElement sortToggle;

    @FindBy(css = "div.facets label[for='sort-option-price-ascending-template--18434715222078__main']")
    private WebElement priceLowToHighOption;

    @FindBy(css = "div.facets label[for='sort-option-price-descending-template--18434715222078__main']")
    private WebElement priceHighToLowOption;

    @FindBy(css = "a[aria-label='Cart']")
    public WebElement cartIcon;

    public ProductCollectionsPage()
    {
        PageFactory.initElements(driver,this);
        wait.ignoring(StaleElementReferenceException.class);
    }

    public void navigateToMenPantsPage()
    {
        driver.get(menPantsCollectionUrl);
    }

    public void navigateToMenDownJacketsPage()
    {
        driver.get(mensDownJacketsCollectionUrl);
    }

    public boolean isOnMenPantsCollectionsPage()
    {
        String url = driver.getCurrentUrl();
        if (url == null) return false;
        return url.equals(menPantsCollectionUrl);
    }

    public boolean isOnMenDownJacketsCollectionsPage()
    {
        return driver.getCurrentUrl().equals(mensDownJacketsCollectionUrl);
    }


    public int getResultsCount()
    {
        wait.until(ExpectedConditions.visibilityOf(productsCount));
        String countText = productsCount.getText();
        String digits = countText.replaceAll("[^0-9]","");
        return Integer.parseInt(digits);
    }

    public void clickFirstProductTile()
    {
        WebElement firstProductTile = productsList.get(0);
        wait.until(ExpectedConditions.visibilityOf(firstProductTile));
        click(firstProductTile);
    }

    public boolean isOnCollectionsPage()
    {
        wait.until(ExpectedConditions.urlContains("/collections/"));
        return driver.getCurrentUrl().contains("/collections/");
    }

    public void openColorFilter()
    {
        wait.until(ExpectedConditions.visibilityOf(colorFilterSummary));
        click(colorFilterSummary);
    }

    public void selectFirstColor()
    {
        WebElement firstColorLabel = colorSwatchLabelsList.get(0);
        wait.until(ExpectedConditions.visibilityOf(firstColorLabel));
        click(firstColorLabel);
    }

    public boolean isFirstColorFilterSelected()
    {
        WebElement firstColorCheckbox = colorSwatchLabelsList.get(0);
        wait.until(ExpectedConditions.visibilityOf(firstColorCheckbox));
        return firstColorCheckbox.isSelected();
    }

    public int getFilteredResultsCount(int previousCount)
    {
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(productsCount, String.valueOf(previousCount))));
        return getResultsCount();
    }

    public void clickVisibleClearButton()
    {
        WebElement visibleClearButton = wait.until(driver ->
        {
            List<WebElement> allClearButtons = driver.findElements(By.cssSelector("facet-clear-component button"));
            for (WebElement button : allClearButtons)
            {
                if (button.isDisplayed())
                {
                    return button;
                }
            }
            return null;
        });
        click(visibleClearButton);
    }

    public void openPriceFilter()
    {
        wait.until(ExpectedConditions.visibilityOf(priceFilterSummary));
        click(priceFilterSummary);
    }

    public void setPriceRange(String minPrice, String maxPrice)
    {
        wait.until(ExpectedConditions.visibilityOf(minPriceInput));
        wait.until(ExpectedConditions.visibilityOf(maxPriceInput));
        set(minPriceInput,minPrice);
        set(maxPriceInput,maxPrice);

        new Actions(driver).sendKeys(Keys.TAB).perform();
    }

    public boolean isEveryVisiblePriceWithinRange(double min, double max)
    {
        for(WebElement tile : productTilesList)
        {
            WebElement regularPrice = tile.findElement(By.cssSelector("product-price .price__regular .price"));
            WebElement salePrice = tile.findElement(By.cssSelector("product-price .price-item--sale"));

            String priceText;
            if (regularPrice.isDisplayed())
            {
                priceText=regularPrice.getText();
            }
            else
            {
                priceText=salePrice.getText();
            }

            priceText= priceText.replace("$","");

            double priceParsed = Double.parseDouble(priceText);

            System.out.println(priceParsed);

            if (priceParsed < min || priceParsed > max )
            {
                return false;
            }

        }
        return true;
    }


    public void openSortDropdown()
    {
        wait.until(ExpectedConditions.visibilityOf(sortToggle));
        click(sortToggle);
    }

    public void selectSortPriceLowToHigh()
    {
        WebElement oldTile = productTilesList.get(0);

        wait.until(ExpectedConditions.visibilityOf(priceLowToHighOption));
        click(priceLowToHighOption);

        wait.until(ExpectedConditions.urlContains("sort_by=price-ascending"));
        wait.until(ExpectedConditions.stalenessOf(oldTile));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[data-testid='product-grid'] product-card")));
    }

    public void selectSortPriceHighToLow()
    {
        WebElement oldTile = productTilesList.get(0);

        wait.until(ExpectedConditions.visibilityOf(priceHighToLowOption));
        click(priceHighToLowOption);

        wait.until(ExpectedConditions.urlContains("sort_by=price-descending"));
        wait.until(ExpectedConditions.stalenessOf(oldTile));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("[data-testid='product-grid'] product-card")));
    }

    public List<Double> getProductPrices()
    {
        List<Double> prices= new ArrayList<Double>();
        for (WebElement tile : productTilesList)
        {
            WebElement regularPrice = tile.findElement(By.cssSelector("product-price .price__regular .price"));
            WebElement salePrice = tile.findElement(By.cssSelector("product-price .price-item--sale"));
            String priceText;
            if(regularPrice.isDisplayed()) priceText=regularPrice.getText();
            else priceText=salePrice.getText();

            priceText=priceText.replace("$","");
            Double priceParsed= Double.parseDouble(priceText);
            prices.add(priceParsed);
        }
        return prices;
    }


    public boolean isSortedAscending(List<Double> prices)
    {
        for(int i=0;i<prices.size()-1;i++)
        {
            System.out.println(prices.get(i));

            if(prices.get(i)>prices.get(i+1))
            {
                return false;
            }

        }

        return true;

    }
    public boolean isSortedDescending(List<Double> prices)
    {
        for (int i =0;i<prices.size()-1;i++)
            if(prices.get(i)<prices.get(i+1))
                return false;

        return true;
    }


    CartPage cartPage= new CartPage();
    public void openProductByIndex(int index)
    {
        WebElement productTile = productsList.get(index);
        wait.until(ExpectedConditions.visibilityOf(productTile));
        click(productTile);
    }

    private Stack<String> addedProductTitlesStack = new Stack<String>();
    private Stack<String> addedProductVariantsStack = new Stack<String>();


    public boolean addThreeProductintoCart()
    {
        String collectionUrl = driver.getCurrentUrl();

        for (int i = 0; i < 3; i++)
        {
            openProductByIndex(i);

            productPage.selectFirstAvailableSize();

            boolean addedSuccessfully = productPage.clickAddToCart();

            System.out.println("Product index: " + i + " added successfully: " + addedSuccessfully);

            if (!addedSuccessfully)
            {
                return false;
            }
            driver.get(collectionUrl);
        }

        return true;
    }

    public boolean isEveryStackedProductInCart()
    {
        for (int i = 0; i < addedProductTitlesStack.size(); i++)
        {
            String title = addedProductTitlesStack.get(i);
            String variant = addedProductVariantsStack.get(i);

            boolean isInCart = cartPage.isProductInCart(title, variant);

            if (!isInCart)
            {
                System.out.println("Stack mismatch: '" + title + "' (variant: " + variant + ") was not found in the cart");
                return false;
            }
        }

        return true;
    }

    public void openCart()
    {
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon));
        cartIcon.click();
    }

}




