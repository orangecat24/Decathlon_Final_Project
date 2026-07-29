package stepDefinitions;

import Pages.CartPage;
import Pages.ProductCollectionsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class TC04_AddToCartAndVerifyTotalSteps {

    ProductCollectionsPage productCollectionsPage;
    CartPage cartPage;

    @Given("the user navigates to the Men's Down Jackets collection page")
    public void theUserNavigatesToTheMensDownJacketsCollectionPage()
    {
        productCollectionsPage = new ProductCollectionsPage();
        productCollectionsPage.navigateToMenDownJacketsPage();
        productCollectionsPage.stayOnUsSite();
        productCollectionsPage.dismissOfferBanner();

        Assert.assertTrue(productCollectionsPage.isOnMenDownJacketsCollectionsPage());
    }

    @When("the user adds three products to the cart")
    public void theUserAddsThreeProductsToTheCart()
    {
        boolean allThreeAddedCorrectly = productCollectionsPage.addThreeProductintoCart();
        Assert.assertTrue(allThreeAddedCorrectly, "One or more products failed to add to cart correctly");
    }

    @When("the user opens the cart")
    public void theUserOpensTheCart()
    {
        productCollectionsPage.openCart();
        cartPage = new CartPage();
    }

    @Then("the order total should match the sum of item prices")
    public void theOrderTotalShouldMatchTheSumOfItemPrices()
    {
        Assert.assertTrue(cartPage.isCartOpen(), "Cart did not open");
        Assert.assertTrue(cartPage.isOrderTotalCorrect(), "Sum of item prices did not match the Order Total");
    }
}
