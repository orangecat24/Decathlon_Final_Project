package stepDefinitions;

import Pages.HomePage;
import Pages.ProductPage;
import Pages.SearchResultsPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class TC01_SearchAndOpenProductSteps {
    HomePage homePage;
    SearchResultsPage searchResultsPage;
    ProductPage productPage;

    @Given("the user is on the home page ready to search")
    public void theUserIsOnTheHomePageReadyToSearch()
    {
        homePage = new HomePage();
        homePage.navigateToHomePage();
        Assert.assertTrue(homePage.isHomePageLoaded());

        homePage.stayOnUsSite();
        homePage.dismissOfferBanner();
    }

    @When("the user searches for {string}")
    public void theUserSearchesFor(String searchTerm)
    {
        homePage.clickSearchButton();
        homePage.searchForProduct(searchTerm);
    }

    @Then("the search results should be displayed")
    public void theSearchResultsShouldBeDisplayed()
    {
        searchResultsPage = new SearchResultsPage();
        Assert.assertTrue(searchResultsPage.getResultsCount() > 0);
    }

    @When("the user opens the first product tile")
    public void theUserOpensTheFirstProductTile()
    {
        searchResultsPage.clickFirstProductTile();
    }

    @Then("the product page should load correctly")
    public void theProductPageShouldLoadCorrectly()
    {
        Assert.assertTrue(searchResultsPage.isOnProductPage());

        productPage = new ProductPage();
        Assert.assertTrue(productPage.isProductTItleNonEmpty());
        Assert.assertTrue(productPage.isPriceCurrencyPatternCorrect());
        Assert.assertTrue(productPage.isAddToCartButtonDIsplayed());
    }

    @When("the user goes back and opens the Arpenaz backpack")
    public void theUserGoesBackAndOpensTheArpenazBackpack()
    {
        productPage.goBack();
        searchResultsPage.clickArpenazBackpackTile();

        Assert.assertTrue(searchResultsPage.isOnProductPage());
    }

    @And("selects an unavailable option")
    public void selectsAnUnavailableOption()
    {
        productPage.selectUnavailableOption();
    }

    @Then("the notify me button should be displayed")
    public void theNotifyMeButtonShouldBeDisplayed() throws InterruptedException {
        Assert.assertTrue(productPage.isNotifyMeButtonDisplayed());
    }

    @And("the sold out button should be displayed")
    public void theSoldOutButtonShouldBeDisplayed()
    {
        Assert.assertTrue(productPage.isSoldOutButtonDisplayed());
    }

    @And("the add to cart button should be disabled")
    public void theAddToCartButtonShouldBeDisabled()
    {
        Assert.assertFalse(productPage.isAddToCartEnabled());
    }
}
