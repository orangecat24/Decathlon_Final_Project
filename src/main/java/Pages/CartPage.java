package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.devtools.v136.page.Page;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage extends BasePage{

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    @FindBy(id = "rebuy-cart-close")
    private WebElement closeCartButton;

    @FindBy(css = ".rebuy-cart__flyout")
    private WebElement cartFlyout;

    @FindBy(css = "ul[data-smartcart-items] li.rebuy-cart__flyout-item")
    private List<WebElement> cartItemsList;

    @FindBy(css = "ul[data-smartcart-items] li.rebuy-cart__flyout-item .rebuy-cart__flyout-item-price .rebuy-money:not(.compare-at) span:last-of-type")
    private List<WebElement> itemPriceElements;

    @FindBy(css = ".rebuy-cart__flyout-subtotal-final-amount span:last-of-type")
    private WebElement orderTotalElement;

    @FindBy(css = ".rebuy-cart__flyout-subtotal-amount")
    private WebElement orderTotalContainer;

    @FindBy(css = "a[aria-label='Cart']")
    public WebElement cartIcon;

    @FindBy(css = "ul[data-smartcart-items] li.rebuy-cart__flyout-item button[aria-label*='Increase']")
    private List<WebElement> increaseQuantityButtonsList;

    @FindBy(css = "ul[data-smartcart-items] li.rebuy-cart__flyout-item button[aria-label*='Decrease']")
    private List<WebElement> decreaseQuantityButtonsList;

    @FindBy(css = "ul[data-smartcart-items] li.rebuy-cart__flyout-item .rebuy-cart__flyout-item-quantity-widget-label")
    private List<WebElement> quantityLabelsList;

    @FindBy(css = "ul[data-smartcart-items] li.rebuy-cart__flyout-item button.rebuy-cart__flyout-item-remove")
    private List<WebElement> removeButtonsList;


    public CartPage()
    {
        PageFactory.initElements(driver,this);
        wait.ignoring(StaleElementReferenceException.class);
    }


    public void closeCartDrawer(){
        wait.until(ExpectedConditions.elementToBeClickable(closeCartButton));
        click(closeCartButton);
    }

    public int getProductQuantityInCart(String itemTitle,String  itemVariant)
    {
        for (WebElement item : cartItemsList)
        {
            String currentTitle = item.findElement(By.cssSelector(".rebuy-cart__flyout-item-product-title")).getText().trim();
            String currentVariant = item.findElement(By.cssSelector(".rebuy-cart__flyout-item-variant-title")).getText().trim();

            if (currentTitle.equals(itemTitle) && currentVariant.equals(itemVariant))
            {
                WebElement quantityLabel = item.findElement(By.cssSelector(".rebuy-cart__flyout-item-quantity-widget-label"));

                String quantityText = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].lastChild.textContent;", quantityLabel);

                return Integer.parseInt(quantityText.trim());
            }
        }

        return 0;
    }

    public int waitForQuantityToChange(String productTitle,String expectedVariant, int previousQuantity)
    {
        wait.until(new ExpectedCondition<Boolean>()
        {
            public Boolean apply(WebDriver driver)
            {
                return getProductQuantityInCart(productTitle, expectedVariant) != previousQuantity;
            }
        });

        return getProductQuantityInCart(productTitle, expectedVariant);
    }

    public boolean isCartOpen()
    {
        wait.until(ExpectedConditions.visibilityOf(cartFlyout));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul[data-smartcart-items] li.rebuy-cart__flyout-item")));
        return cartFlyout.isDisplayed();
    }

    public double getSumOfItemPrices()
    {
        wait.until(ExpectedConditions.visibilityOfAllElements(cartItemsList));
        double sum = 0;

        for (WebElement item : cartItemsList)
        {
            List<WebElement> priceElements = item.findElements(By.cssSelector(".rebuy-money"));

            String priceText = null;

            for (WebElement priceElement : priceElements)
            {
                String cssClass = priceElement.getAttribute("class");

                if (!cssClass.contains("compare-at"))
                {
                    priceText = priceElement.findElement(By.cssSelector("span:last-of-type")).getText().trim();
                    break;
                }
            }

            priceText = priceText.replace("$", "");
            sum += Double.parseDouble(priceText);
        }

        return sum;
    }


    public double getOrderTotal()
    {
        wait.until(ExpectedConditions.visibilityOf(orderTotalContainer));
        wait.until(ExpectedConditions.textToBePresentInElement(orderTotalContainer, "$"));   // <-- add

        List<WebElement> allDollarSpans = orderTotalContainer.findElements(By.xpath(".//span[contains(text(),'$')]"));
        String totalText = null;

        for (WebElement span : allDollarSpans)
        {
            WebElement parent = span.findElement(By.xpath(".."));
            String parentClass = parent.getAttribute("class");

            if (parentClass.contains("compare-amount"))
            {
                break;
            }
            else
            {
                totalText = span.getText().trim();
            }

        }

        totalText = totalText.replace("$", "");
        return Double.parseDouble(totalText);
    }

    public boolean isOrderTotalCorrect()
    {
        double sumOfItems = getSumOfItemPrices();
        double orderTotal = getOrderTotal();
        return (sumOfItems - orderTotal) < 0.001;
    }

    public void increaseItemQuantityByIndexOfProduct(int index)
    {
        int quantityBefore = getItemQuantityByIndexOfProduct(index);

        waitAndClickButtonAtIndex(increaseQuantityButtonsList, index);

        waitForItemQuantityToBe(index, quantityBefore + 1);
    }

    private void waitAndClickButtonAtIndex(List<WebElement> buttonsList, int index)
    {
        wait.until(new ExpectedCondition<Boolean>()
        {
            public Boolean apply(WebDriver driver)
            {
                try
                {
                    WebElement button = buttonsList.get(index);
                    if (button.isDisplayed() && button.isEnabled())
                    {
                        button.click();
                        return true;
                    }
                    return false;
                }
                catch (StaleElementReferenceException e)
                {
                    return false;
                }
            }
        });
    }

    public int getItemQuantityByIndexOfProduct(int index)
    {
        wait.until(ExpectedConditions.visibilityOfAllElements(cartItemsList));
        wait.until(ExpectedConditions.visibilityOfAllElements(quantityLabelsList));

        WebElement item = cartItemsList.get(index);

        WebElement quantityElement = quantityLabelsList.get(index);

        String quantityText = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].lastChild.textContent;", quantityElement);

        quantityText= quantityText.trim();

        return Integer.parseInt(quantityText);
    }

    public void waitForItemQuantityToBe(int index, int expectedQuantity)
    {
        wait.until(new ExpectedCondition<Boolean>()
        {
            public Boolean apply(WebDriver driver)
            {
                int currentQuantity = getItemQuantityByIndexOfProduct(index);
                System.out.println("Polling index " + index + ": reads " + currentQuantity + ", waiting for " + expectedQuantity);
                return currentQuantity == expectedQuantity;
            }
        });
    }

    public double getItemPriceByIndexOfProduct(int index)
    {
        wait.until(ExpectedConditions.visibilityOfAllElements(cartItemsList));
        WebElement item = cartItemsList.get(index);

        List<WebElement> priceElements = item.findElements(By.cssSelector(".rebuy-money"));

        String priceText = null;

        for (WebElement priceElement : priceElements)
        {
            String cssClass = priceElement.getAttribute("class");

            if (!cssClass.contains("compare-at"))
            {
                priceText = priceElement.findElement(By.cssSelector("span:last-of-type")).getText().trim();
                break;
            }
        }

        priceText = priceText.replace("$", "");
        return Double.parseDouble(priceText);
    }

    public void decreaseItemQuantityByIndexOfProduct(int index)
    {
        int quantityBefore = getItemQuantityByIndexOfProduct(index);

        waitAndClickButtonAtIndex(decreaseQuantityButtonsList, index);

        waitForItemQuantityToBe(index, quantityBefore - 1);
    }

    public void removeItemByIndexOfProduct(int index)
    {
        waitAndClickButtonAtIndex(removeButtonsList, index);
    }


    public int getCartItemsCount()
    {
        return cartItemsList.size();
    }

    public void waitForCartItemsCountToBe (int expectedQuantity)
    {
        wait.until(new ExpectedCondition<Boolean>() {

            public Boolean apply(WebDriver driver) {
                return getCartItemsCount() == expectedQuantity;

            }
        });
    }

    public void waitForOrderTotalToChange(double previousTotal)
    {
        wait.until(new ExpectedCondition<Boolean>()
        {
            public Boolean apply(WebDriver driver)
            {
                return getOrderTotal() != previousTotal;
            }
        });
    }

    @FindBy(css = ".rebuy-cart__flyout-empty-cart h4")
    private WebElement emptyCartMessage;

    public boolean isEmptyCartMessageDisplayed()
    {
        wait.until(ExpectedConditions.visibilityOf(emptyCartMessage));
        return emptyCartMessage.getText().trim().equals("Your cart is empty!");
    }

    public boolean isProductInCart(String itemTitle, String itemVariant)
    {
        for (WebElement item : cartItemsList)
        {
            String currentTitle = item.findElement(By.cssSelector(".rebuy-cart__flyout-item-product-title")).getText().trim();
            String currentVariant = item.findElement(By.cssSelector(".rebuy-cart__flyout-item-variant-title")).getText().trim();

            if (currentTitle.equals(itemTitle) && currentVariant.equals(itemVariant))
            {
                return true;
            }
        }

        return false;
    }

}