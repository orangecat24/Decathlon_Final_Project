package Tests;

import Pages.ProductCollectionsPage;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class TC03_VerifyDescendingSort extends BaseTest{

    ProductCollectionsPage productCollectionsPage;

    @Test
    public void verifySortingResultsTest() throws InterruptedException {
        productCollectionsPage = new ProductCollectionsPage();

        productCollectionsPage.navigateToMenPantsPage();

        productCollectionsPage.stayOnUsSite();

        productCollectionsPage.dismissOfferBanner();

        Assert.assertTrue(productCollectionsPage.isOnMenPantsCollectionsPage());

        productCollectionsPage.openSortDropdown();

        productCollectionsPage.selectSortPriceHighToLow();

        List<Double> HighToLowPrices = productCollectionsPage.getProductPrices();

        Assert.assertTrue(productCollectionsPage.isSortedDescending(HighToLowPrices));

        productCollectionsPage.openSortDropdown();

        productCollectionsPage.selectSortPriceLowToHigh();

        List<Double> LowToHighPrices = productCollectionsPage.getProductPrices();

        Assert.assertTrue(productCollectionsPage.isSortedAscending(LowToHighPrices));

    }


}
