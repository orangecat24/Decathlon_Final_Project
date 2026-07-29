package stepDefinitions;

import Pages.CartPage;
import Pages.ProductCollectionsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class TC06_EmptyCartVerificationSteps {

    ProductCollectionsPage productCollectionsPage;
    CartPage cartPage;

    int rowsBeforeDelete;

    @Given("the cart contains three products for removal")
    public void theCartContainsThreeProductsForRemoval()
    {
        productCollectionsPage = new ProductCollectionsPage();
        productCollectionsPage.navigateToMenDownJacketsPage();
        productCollectionsPage.stayOnUsSite();
        productCollectionsPage.dismissOfferBanner();

        boolean allThreeAddedCorrectly = productCollectionsPage.addThreeProductintoCart();
        Assert.assertTrue(allThreeAddedCorrectly, "One or more products failed to add to cart correctly");

        productCollectionsPage.openCart();
        cartPage = new CartPage();
        Assert.assertTrue(cartPage.isCartOpen(), "Cart did not open");
    }

    @When("the user removes the first item from the cart")
    public void theUserRemovesTheFirstItemFromTheCart()
    {
        rowsBeforeDelete = cartPage.getCartItemsCount();

        cartPage.removeItemByIndexOfProduct(0);
        cartPage.waitForCartItemsCountToBe(rowsBeforeDelete - 1);
    }

    @Then("the cart row count should decrease by 1")
    public void theCartRowCountShouldDecreaseBy1()
    {
        int rowsAfterDelete = cartPage.getCartItemsCount();
        Assert.assertEquals(rowsAfterDelete, rowsBeforeDelete - 1, "Cart row count did not decrease by 1 after deleting the item");
    }

    @When("the user removes all remaining items from the cart")
    public void theUserRemovesAllRemainingItemsFromTheCart()
    {
        while (cartPage.getCartItemsCount() > 0)
        {
            int rowsBefore = cartPage.getCartItemsCount();

            cartPage.removeItemByIndexOfProduct(0);
            cartPage.waitForCartItemsCountToBe(rowsBefore - 1);

            Assert.assertEquals(cartPage.getCartItemsCount(), rowsBefore - 1, "Cart row count did not decrease by 1 after deleting the item");
        }
    }

    @Then("the empty cart message should be displayed")
    public void theEmptyCartMessageShouldBeDisplayed()
    {
        Assert.assertTrue(cartPage.isEmptyCartMessageDisplayed(), "Empty cart message was not displayed");
    }
}
