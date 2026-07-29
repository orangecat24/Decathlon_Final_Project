package Tests;

import Pages.HomePage;
import Pages.ProductPage;
import Pages.SearchResultsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC01_SearchAndOpenProduct extends BaseTest{

    @Test
    public void navigateToHomePage() throws InterruptedException {
        HomePage homePage = new HomePage();

        homePage.navigateToHomePage();

        Assert.assertTrue(homePage.isHomePageLoaded());

        homePage.stayOnUsSite();

        homePage.dismissOfferBanner();      

        homePage.clickSearchButton();

        homePage.searchForProduct("backpack");

        SearchResultsPage searchResultsPage = new SearchResultsPage();

        Assert.assertTrue(searchResultsPage.isPageTitleReflectingSearchTerm("backpack"));

        Assert.assertTrue(searchResultsPage.getResultsCount()>0);

        searchResultsPage.clickFirstProductTile();

        Assert.assertTrue(searchResultsPage.isOnProductPage());

        ProductPage productPage= new ProductPage();

        Assert.assertTrue(productPage.isProductTItleNonEmpty());

        Assert.assertTrue(productPage.isPriceCurrencyPatternCorrect());

        Assert.assertTrue(productPage.isAddToCartButtonDIsplayed());

        productPage.goBack();

        searchResultsPage.clickArpenazBackpackTile();

        Assert.assertTrue(searchResultsPage.isOnProductPage());

        productPage.selectUnavailableOption();

        Assert.assertTrue(productPage.isNotifyMeButtonDisplayed());

        Assert.assertTrue(productPage.isSoldOutButtonDisplayed());

        Assert.assertFalse(productPage.isAddToCartEnabled());

    }
}
