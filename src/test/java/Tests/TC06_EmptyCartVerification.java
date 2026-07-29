package Tests;

import Pages.CartPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC06_EmptyCartVerification extends BaseTest{

    CartPage cartPage;

    @Test
    public void removeItemsFromCartTest() throws InterruptedException {
        cartPage = prepareCartWithThreeProducts();

        int rowsBeforeDelete = cartPage.getCartItemsCount();

        cartPage.removeItemByIndexOfProduct(0);

        cartPage.waitForCartItemsCountToBe(rowsBeforeDelete - 1);

        int rowsAfterDelete = cartPage.getCartItemsCount();

        Assert.assertEquals(rowsAfterDelete, rowsBeforeDelete - 1, "Cart row count did not decrease by 1 after deleting the item");

        while (cartPage.getCartItemsCount() > 0)
        {
            int rowsBefore = cartPage.getCartItemsCount();

            cartPage.removeItemByIndexOfProduct(0);

            cartPage.waitForCartItemsCountToBe(rowsBefore - 1);

            Assert.assertEquals(cartPage.getCartItemsCount(), rowsBefore - 1, "Cart row count did not decrease by 1 after deleting the item");
        }

        Assert.assertTrue(cartPage.isEmptyCartMessageDisplayed(), "Empty cart message was not displayed");
    }
}
