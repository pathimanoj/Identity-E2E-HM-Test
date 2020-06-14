package homeoffice;
import car.domain.Car;
import car.processor.CarInputProcessor;
import car.processor.CarOutputProcessor;
import org.junit.Before;
import org.junit.After;
import homeoffice.enums.Browsers;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public class VehicleTaxCheckTest {

    TestBase testBase;

    @Before
    public void initializeTest() {
        testBase = new TestBase();
        testBase.selectBrowser(Browsers.CHROME.name());
    }

    @After
    public void endTest() {
        TestBase.driver.quit();
    }

    @Test
    public void validateVehiclesDetails() throws IOException, InterruptedException {

        CarInputProcessor input = new CarInputProcessor(System.getProperty("user.dir")+ "/src/test/java/resources/car_input.txt");
        CarOutputProcessor output = new CarOutputProcessor(System.getProperty("user.dir")+ "/src/test/java/resources/car_output.txt");
        List<String> registrationList = input.getAllRegistrationNumbers();
        boolean isPassed = true;

        // Checks in car tax website for each registration number retrieved from input file
        for (String regNo : registrationList) {
            System.out.println("Running for: "+regNo);
            if(!checkInWebsite(regNo, output))
            {
                isPassed = false;
            }
        }

        Assert.assertTrue(isPassed);

    }

    public boolean checkInWebsite(String regNo, CarOutputProcessor output) throws InterruptedException, MalformedURLException {
        System.out.println("Checking in website for registration number: "+regNo);

        TestBase.driver.get("https://cartaxcheck.co.uk/");
        TestBase.driver.manage().window().maximize();

        //Wait till car tax check homepage loads
        WebDriverWait wait = new WebDriverWait(TestBase.driver, 30);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("vrm-input")));

        // Enter vehicle registration number in search field
        TestBase.driver.findElement(By.id("vrm-input")).sendKeys(regNo);
        TestBase.driver.findElement(By.xpath("//form[@id='vehicle-search']/button")).click();

        Thread.sleep(3000);


            //Retrieve vehicle registration number
            List<WebElement> regList = TestBase.driver.findElements(By.xpath("//div[@id='m']/div/div[3]/div/div/span/div[2]/dl[1]/dd[1]"));
            String vehicleRegNumber = regList.get(0).getText();

            //Retrieve vehicle make
            List<WebElement> makeList = TestBase.driver.findElements(By.xpath("//div[@id='m']/div/div[3]/div/div/span/div[2]/dl[2]/dd[1]"));
            String vehicleMake = makeList.get(0).getText();

            //Retrieve vehicle model
            List<WebElement> modelList = TestBase.driver.findElements(By.xpath("//div[@id='m']/div/div[3]/div/div/span/div[2]/dl[3]/dd[1]"));
            String vehicleModel = modelList.get(0).getText();

            //Retrieve vehicle colour
            List<WebElement> colourList = TestBase.driver.findElements(By.xpath("//div[@id='m']/div/div[3]/div/div/span/div[2]/dl[4]/dd[1]"));
            String vehicleColour = colourList.get(0).getText();

            //Retrieve vehicle registration year
            List<WebElement> yearList = TestBase.driver.findElements(By.xpath("//div[@id='m']/div/div[3]/div/div/span/div[2]/dl[5]/dd[1]"));
            String vehicleYear = yearList.get(0).getText();

            Car webSiteResultCar = new Car();
            webSiteResultCar.setColor(vehicleColour);
            webSiteResultCar.setMake(vehicleMake);
            webSiteResultCar.setModel(vehicleModel);
            webSiteResultCar.setRegistration(vehicleRegNumber);
            webSiteResultCar.setYear(vehicleYear);

            if(!output.compareWithGivenCar(webSiteResultCar)){
                System.out.println("Test case failed for:" +regNo);
                System.out.println("Vehicle details not found for:" +regNo);
                return false;
            }
            else{

                System.out.println("Test case Passed for:" +vehicleRegNumber);
                return true;
            }
        }
    }
