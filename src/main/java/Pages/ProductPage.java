package Pages;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class ProductPage extends BasePage{

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    @FindBy(css = "div[data-testid='group-block'] h3")
    private WebElement productTitle;

    @FindBy(css = "product-price .price__regular .price")
    private WebElement regularPriceText;

    @FindBy(css = "product-price .price-item--sale")
    private WebElement salePriceText;


    @FindBy(css = "[data-testid='standalone-add-to-cart']")
    private WebElement addToCartButton;

    // butoni per te shtuar ne cart eshte i njejti dhe kur sk stok

    @FindBy(css = "input[data-option-available='false']")
    private WebElement unavailableOption;

    @FindBy(id = "BIS_trigger")
    private WebElement notifyMeButton;

    @FindBy(css = "variant-picker")
    private WebElement sizeVariantPicker;

    @FindBy(css = "fieldset.variant-option--has-size-guide  input[data-option-available='true']")
    private List<WebElement> availableSizeInputs;

    @FindBy(css = "[data-testid='cart-bubble']")
    private WebElement cartBubbleCount;

    @FindBy(css = ".variant-option__swatch-value")
    private WebElement selectedColorText;


    public String getSelectedColor()
    {
        wait.until(ExpectedConditions.visibilityOf(selectedColorText));
        return selectedColorText.getText().trim();
    }

    public ProductPage()
    {
        PageFactory.initElements(driver,this);
        wait.ignoring(StaleElementReferenceException.class);
    }

    public boolean isProductTItleNonEmpty()
    {
        wait.until(ExpectedConditions.visibilityOf(productTitle));
        return !productTitle.getText().trim().isEmpty();
    }

    public boolean isPriceCurrencyPatternCorrect()
    {
        wait.until(ExpectedConditions.or(ExpectedConditions.visibilityOf(regularPriceText),ExpectedConditions.visibilityOf(salePriceText)));
        String priceText;
        if (regularPriceText.isDisplayed())
        {
            priceText=regularPriceText.getText();
        }
        else
        {
            priceText=salePriceText.getText();
        }

        return priceText.matches("\\$\\d+\\.\\d{2}");
    }

    public boolean isAddToCartButtonDIsplayed()
    {
        wait.until(ExpectedConditions.visibilityOf(addToCartButton));
        return addToCartButton.isDisplayed();
    }

    public void selectUnavailableOption()
    {
        wait.until(ExpectedConditions.visibilityOf(sizeVariantPicker));
        click(unavailableOption);
    }
    public boolean isNotifyMeButtonDisplayed() throws InterruptedException {
        Thread.sleep(2000);
        wait.until(ExpectedConditions.attributeToBe(addToCartButton, "disabled", "true"));
        wait.until(ExpectedConditions.visibilityOf(notifyMeButton));
        return notifyMeButton.isDisplayed();
    }

    public boolean isSoldOutButtonDisplayed()
    {
        wait.until(new ExpectedCondition<Boolean>()
        {
            public Boolean apply(WebDriver driver)
            {
                return addToCartButton.getText().toLowerCase().contains("sold out");
            }
        });
        return addToCartButton.getText().toLowerCase().contains("sold out");
    }

    public boolean isAddToCartEnabled()
    {
        wait.until(ExpectedConditions.visibilityOf(addToCartButton));
        return addToCartButton.isEnabled();
    }

    public String selectFirstAvailableSize()
    {
        wait.until(ExpectedConditions.visibilityOf(sizeVariantPicker));
        WebElement firstAvailableSize= availableSizeInputs.get(0);
        String sizeValue = firstAvailableSize.getAttribute("value");
        click(firstAvailableSize);

        return sizeValue;
    }

    public boolean clickAddToCart()
    {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));

        int countBeforeAddingToCart = Integer.parseInt(cartBubbleCount.getAttribute("textContent").trim());
        int expectedCount = countBeforeAddingToCart + 1;


        click(addToCartButton);

        wait.until(new ExpectedCondition<Boolean>()
        {
            public Boolean apply(WebDriver driver)
            {
                String rawText = cartBubbleCount.getAttribute("textContent");
                return Integer.parseInt(rawText.trim()) == expectedCount;
            }
        });

        int countAfterAddingToCart = Integer.parseInt(cartBubbleCount.getAttribute("textContent").trim());

        return countAfterAddingToCart == expectedCount;
    }

    public String getProductTitle()
    {
        wait.until(ExpectedConditions.visibilityOf(productTitle));
        return productTitle.getText().trim();
    }

    public boolean isOnCartPage()
    {
        wait.until(ExpectedConditions.urlContains("/cart"));
        return driver.getCurrentUrl().contains("/cart");
    }




}
