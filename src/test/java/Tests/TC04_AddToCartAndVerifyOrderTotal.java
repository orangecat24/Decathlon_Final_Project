package Tests;

import Pages.CartPage;
import Pages.ProductCollectionsPage;
import Pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC04_AddToCartAndVerifyOrderTotal extends BaseTest{

    ProductCollectionsPage productCollectionsPage;

    ProductPage productPage ;

    CartPage cartPage;

    @Test
    public void addToCartAndVerifyOrderTotalTest() throws InterruptedException {

        cartPage = prepareCartWithThreeProducts();
        
        Assert.assertTrue(cartPage.isOrderTotalCorrect(), "Sum of item prices did not match the Order Total");

    }
}
