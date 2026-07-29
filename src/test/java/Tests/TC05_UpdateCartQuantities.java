package Tests;

import Pages.BasePage;
import Pages.CartPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC05_UpdateCartQuantities extends BaseTest {

    CartPage cartPage;

    @Test
    public void updateCartQuantitiesTest() throws InterruptedException {

        cartPage = prepareCartWithThreeProducts();

        int quantityBefore = cartPage.getItemQuantityByIndexOfProduct(0);

        double firstItemUnitPrice = cartPage.getItemPriceByIndexOfProduct(0);

        double originalOrderTotal = cartPage.getOrderTotal();

        cartPage.increaseItemQuantityByIndexOfProduct(0);

        cartPage.waitForOrderTotalToChange(originalOrderTotal);

        int quantityAfter = cartPage.getItemQuantityByIndexOfProduct(0);

        Assert.assertEquals(quantityAfter, quantityBefore + 1, "Quantity did not increase by 1");

        double orderTotalAfterIncrease = Math.round(cartPage.getOrderTotal() * 100.0) / 100.0;

        double expectedOrderTotal = Math.round((originalOrderTotal + firstItemUnitPrice) * 100.0) / 100.0;

        Assert.assertEquals(orderTotalAfterIncrease, expectedOrderTotal, "Order Total did not increase by exactly one unit price");

        cartPage.decreaseItemQuantityByIndexOfProduct(0);

        cartPage.waitForOrderTotalToChange(orderTotalAfterIncrease);

        int quantityAfterDecrease = cartPage.getItemQuantityByIndexOfProduct(0);

        Assert.assertEquals(quantityAfterDecrease, quantityBefore, "Quantity did not return to its original value");

        double orderTotalAfterDecrease = Math.round(cartPage.getOrderTotal() * 100.0) / 100.0;

        double roundedOriginalTotal = Math.round(originalOrderTotal * 100.0) / 100.0;

        Assert.assertEquals(orderTotalAfterDecrease, roundedOriginalTotal, "Order Total did not return to its original value");
    }
}
