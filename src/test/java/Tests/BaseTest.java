package Tests;

import Pages.BasePage;
import Pages.CartPage;
import Pages.ProductCollectionsPage;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static Pages.BasePage.driver;

public class BaseTest {

    protected WebDriver driver;

    @BeforeClass
    public void setUp()
    {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        BasePage basePage = new BasePage();
        basePage.setDriver(driver);
    }

    public CartPage prepareCartWithThreeProducts()
    {
        ProductCollectionsPage productCollectionsPage = new ProductCollectionsPage();

        productCollectionsPage.navigateToMenDownJacketsPage();
        productCollectionsPage.stayOnUsSite();
        productCollectionsPage.dismissOfferBanner();

        Assert.assertTrue(productCollectionsPage.isOnMenDownJacketsCollectionsPage(), "Did not land on the Men's Down Jackets collection page");

        boolean allThreeAddedCorrectly = productCollectionsPage.addThreeProductintoCart();
        Assert.assertTrue(allThreeAddedCorrectly, "One or more products failed to add to cart correctly");

        productCollectionsPage.openCart();

        CartPage cartPage = new CartPage();
        Assert.assertTrue(cartPage.isCartOpen(), "Cart did not open");

        return cartPage;
    }

    @AfterMethod
    public void captureScreenshotOnFailure(ITestResult testResult) throws IOException
    {
        if (testResult.getStatus() == ITestResult.FAILURE)
        {
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            String screenshotsFolder = "screenshots";
            File folder = new File(screenshotsFolder);
            if (!folder.exists())
            {
                folder.mkdirs();
            }

            String fileName = testResult.getName() + "_" + System.currentTimeMillis() + ".png";
            File destination = new File(folder, fileName);

            Files.copy(screenshotFile.toPath(), destination.toPath());

            System.out.println("Screenshot saved: " + destination.getAbsolutePath());
        }
    }

    @AfterClass
    public void tearDown()
    {
        driver.quit();
    }
}
