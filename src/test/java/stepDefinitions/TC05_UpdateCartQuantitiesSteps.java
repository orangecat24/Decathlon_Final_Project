package stepDefinitions;

import Pages.CartPage;
import Pages.ProductCollectionsPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class TC05_UpdateCartQuantitiesSteps {

    ProductCollectionsPage productCollectionsPage;
    CartPage cartPage;

    int quantityBefore;
    double firstItemUnitPrice;
    double originalOrderTotal;
    double orderTotalAfterIncrease;
    @Given("the cart contains three products for quantity updates")
    public void theCartContainsThreeProductsForQuantityUpdates()
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

        quantityBefore = cartPage.getItemQuantityByIndexOfProduct(0);
        firstItemUnitPrice = cartPage.getItemPriceByIndexOfProduct(0);
        originalOrderTotal = cartPage.getOrderTotal();
    }

    @When("the user increases the quantity of the first item")
    public void theUserIncreasesTheQuantityOfTheFirstItem()
    {
        cartPage.increaseItemQuantityByIndexOfProduct(0);
        cartPage.waitForOrderTotalToChange(originalOrderTotal);
    }

    @Then("the quantity should increase by 1")
    public void theQuantityShouldIncreaseBy1()
    {
        int quantityAfter = cartPage.getItemQuantityByIndexOfProduct(0);
        Assert.assertEquals(quantityAfter, quantityBefore + 1, "Quantity did not increase by 1");
    }

    @And("the order total should increase by exactly one unit price")
    public void theOrderTotalShouldIncreaseByExactlyOneUnitPrice()
    {
        orderTotalAfterIncrease = Math.round(cartPage.getOrderTotal() * 100.0) / 100.0;
        double expectedOrderTotal = Math.round((originalOrderTotal + firstItemUnitPrice) * 100.0) / 100.0;

        Assert.assertEquals(orderTotalAfterIncrease, expectedOrderTotal, "Order Total did not increase by exactly one unit price");
    }

    @When("the user decreases the quantity of the first item")
    public void theUserDecreasesTheQuantityOfTheFirstItem()
    {
        cartPage.decreaseItemQuantityByIndexOfProduct(0);
        cartPage.waitForOrderTotalToChange(orderTotalAfterIncrease);
    }

    @Then("the quantity should return to its original value")
    public void theQuantityShouldReturnToItsOriginalValue()
    {
        int quantityAfterDecrease = cartPage.getItemQuantityByIndexOfProduct(0);
        Assert.assertEquals(quantityAfterDecrease, quantityBefore, "Quantity did not return to its original value");
    }

    @And("the order total should return to its original value")
    public void theOrderTotalShouldReturnToItsOriginalValue()
    {
        double orderTotalAfterDecrease = Math.round(cartPage.getOrderTotal() * 100.0) / 100.0;
        double roundedOriginalTotal = Math.round(originalOrderTotal * 100.0) / 100.0;

        Assert.assertEquals(orderTotalAfterDecrease, roundedOriginalTotal, "Order Total did not return to its original value");
    }
}
