package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.joda.time.Seconds;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

public class greenKartStepDef {

    public WebDriver driver;
    public WebDriverWait wait;
    public String landingPageProductName;
    public String offerPageProductName;

    @Given("User is on GreenCart Landing Page")
    public void user_is_on_green_cart_landing_page() {

        System.setProperty("webdriver.chrome.driver", "/Users/mukul/Documents/driver");
        driver = new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/seleniumPractise/#/");


    }
    @When("User search with shortname {string} and extract actual name of the product")
    public void user_search_with_shortname_and_extract_actual_name_of_the_product(String shortName) {

        driver.findElement(By.xpath("//input[@type = 'search']")).sendKeys(shortName);
        landingPageProductName = driver.findElement(By.xpath("//div/h4[@class = 'product-name'][contains(.,'Tom')]")).getText().split("-")[0].trim();
        System.out.println(landingPageProductName + " is extracted from Home Page");



    }
    @Then("User search for {string} shortname in Offers Page")
    public void user_search_for_shortname_in_offers_page(String shortName) {

        //Now after clicking on Top Deals it shifts to the new Page i.e. Offers Page
        driver.findElement(By.linkText("Top Deals")).click();

        //This will give me "Total windows opened with automation: Windows id get stored in s1 collection"
        Set<String> s1 = driver.getWindowHandles();
        //Now to iterate through above collection
        Iterator<String> i1 = s1.iterator();
        //It will get to 0th index of set collection: default i1 is null!
        String parentWindow = i1.next();
        String childWindow = i1.next();

        //parent to child
        driver.switchTo().window(childWindow);

        driver.findElement(By.xpath("//input[@type = 'search']")).sendKeys(shortName);

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement textField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr/td[1]")));
        offerPageProductName = textField.getText();

    }

    @Then("Validate HomePage and OfferPage product name is same")
    public void validate_home_page_and_offer_page_product_name_is_same(){

        Assert.assertEquals(offerPageProductName, landingPageProductName);

    }
}
