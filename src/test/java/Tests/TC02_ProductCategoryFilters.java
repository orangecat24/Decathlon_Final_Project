package Tests;

import Pages.HomePage;
import Pages.ProductCollectionsPage;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TC02_ProductCategoryFilters extends BaseTest{

    HomePage homePage;

    ProductCollectionsPage tShirtCollection;

    @Test
    public void productCategoryFiltersTest() throws InterruptedException {

        homePage = new HomePage();

        homePage.navigateToHomePage();

        Assert.assertTrue(homePage.isHomePageLoaded());

        homePage.stayOnUsSite();

        homePage.dismissOfferBanner();

        homePage.hoverOverMenMenu();

        homePage.clickMenTSHirtSubcategory();

        tShirtCollection = new ProductCollectionsPage();

        Assert.assertTrue(tShirtCollection.isOnCollectionsPage());

        int initialProductCount = tShirtCollection.getResultsCount();

        Assert.assertEquals(initialProductCount,4);

        tShirtCollection.openColorFilter();

        tShirtCollection.selectFirstColor();

        int filteredProductCount = tShirtCollection.getFilteredResultsCount(initialProductCount);

        System.out.println(filteredProductCount);

        Assert.assertNotEquals(filteredProductCount, initialProductCount);

        tShirtCollection.clickVisibleClearButton();

        int clearedProductCount = tShirtCollection.getFilteredResultsCount(filteredProductCount);

        Assert.assertEquals(clearedProductCount, initialProductCount);

        tShirtCollection.openPriceFilter();

        tShirtCollection.setPriceRange("20","30");

        Thread.sleep(3000);

        Assert.assertTrue(tShirtCollection.isEveryVisiblePriceWithinRange(20,30));


    }

}
