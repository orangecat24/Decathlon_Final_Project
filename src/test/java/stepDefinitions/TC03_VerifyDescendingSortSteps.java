package stepDefinitions;

import Pages.ProductCollectionsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

import java.util.List;

public class TC03_VerifyDescendingSortSteps {

    ProductCollectionsPage productCollectionsPage;

    @Given("the user is on the Men's Pants collection page")
    public void theUserIsOnTheMensPantsCollectionPage()
    {
        productCollectionsPage = new ProductCollectionsPage();

        productCollectionsPage.navigateToMenPantsPage();

        productCollectionsPage.stayOnUsSite();
        productCollectionsPage.dismissOfferBanner();

        Assert.assertTrue(productCollectionsPage.isOnMenPantsCollectionsPage());
    }

    @When("the user sorts by price high to low")
    public void theUserSortsByPriceHighToLow()
    {
        productCollectionsPage.openSortDropdown();
        productCollectionsPage.selectSortPriceHighToLow();
    }

    @Then("the products should be sorted in descending order")
    public void theProductsShouldBeSortedInDescendingOrder()
    {
        List<Double> highToLowPrices = productCollectionsPage.getProductPrices();
        Assert.assertTrue(productCollectionsPage.isSortedDescending(highToLowPrices));
    }

    @When("the user sorts by price low to high")
    public void theUserSortsByPriceLowToHigh()
    {
        productCollectionsPage.openSortDropdown();
        productCollectionsPage.selectSortPriceLowToHigh();
    }

    @Then("the products should be sorted in ascending order")
    public void theProductsShouldBeSortedInAscendingOrder()
    {
        List<Double> lowToHighPrices = productCollectionsPage.getProductPrices();
        Assert.assertTrue(productCollectionsPage.isSortedAscending(lowToHighPrices));
    }
}
