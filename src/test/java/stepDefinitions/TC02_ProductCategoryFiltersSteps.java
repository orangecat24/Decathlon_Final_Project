package stepDefinitions;

import Pages.HomePage;
import Pages.ProductCollectionsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class TC02_ProductCategoryFiltersSteps {
    HomePage homePage;
    ProductCollectionsPage tShirtCollection;

    int initialProductCount;
    int filteredProductCount;
    int clearedProductCount;

    @Given("the user is on the home page ready to browse categories")
    public void theUserIsOnTheHomePageReadyToBrowseCategories()
    {
        homePage = new HomePage();
        homePage.navigateToHomePage();
        Assert.assertTrue(homePage.isHomePageLoaded());

        homePage.stayOnUsSite();
        homePage.dismissOfferBanner();
    }

    @When("the user navigates to the Men's T-Shirts category")
    public void theUserNavigatesToTheMensTShirtsCategory()
    {
        homePage.hoverOverMenMenu();
        homePage.clickMenTSHirtSubcategory();

        tShirtCollection = new ProductCollectionsPage();
        Assert.assertTrue(tShirtCollection.isOnCollectionsPage());
    }

    @Then("the collections page should show the initial product count")
    public void theCollectionsPageShouldShowTheInitialProductCount()
    {
        initialProductCount = tShirtCollection.getResultsCount();
        Assert.assertEquals(initialProductCount, 4);
    }

    @When("the user applies the first color filter")
    public void theUserAppliesTheFirstColorFilter()
    {
        tShirtCollection.openColorFilter();
        tShirtCollection.selectFirstColor();
    }

    @Then("the filtered product count should differ from the initial count")
    public void theFilteredProductCountShouldDifferFromTheInitialCount()
    {
        filteredProductCount = tShirtCollection.getFilteredResultsCount(initialProductCount);
        System.out.println(filteredProductCount);

        Assert.assertNotEquals(filteredProductCount, initialProductCount);
    }

    @When("the user clears all filters")
    public void theUserClearsAllFilters()
    {
        tShirtCollection.clickVisibleClearButton();
    }

    @Then("the product count should return to the initial count")
    public void theProductCountShouldReturnToTheInitialCount()
    {
        clearedProductCount = tShirtCollection.getFilteredResultsCount(filteredProductCount);
        Assert.assertEquals(clearedProductCount, initialProductCount);
    }

    @When("the user sets the price range to {string} and {string}")
    public void theUserSetsThePriceRangeTo(String minPrice, String maxPrice)
    {
        tShirtCollection.openPriceFilter();
        tShirtCollection.setPriceRange(minPrice, maxPrice);
    }

    @Then("every visible price should be within that range")
    public void everyVisiblePriceShouldBeWithinThatRange()
    {
        Assert.assertTrue(tShirtCollection.isEveryVisiblePriceWithinRange(20, 30));
    }
}
