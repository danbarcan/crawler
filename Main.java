public class Main {
    public static void main(String[] args) {
        ChromeDriverTest chromeDriverTest = new ChromeDriverTest();

        chromeDriverTest.prepare();
        try {
            chromeDriverTest.test();
        } finally {
            chromeDriverTest.teardown();
        }
    }
}

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChromeDriverTest {

    private String testUrl;
    private WebDriver driver;

    public void prepare() {
        //setup chromedriver
        System.setProperty(
                "webdriver.chrome.driver",
                "webdriver/chromedriver.exe");

        testUrl = "https://www.autopartsuk.com/";

        // Create a new instance of the Chrome driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        driver = new ChromeDriver();

        //maximize window
        driver.manage().window().maximize();

        // And now use this to visit myBlog
        // Alternatively the same thing can be done like this
        // driver.navigate().to(testUrl);
        driver.get(testUrl);
    }

    public void test() {
        List<Manufacturer> manufacturerList = new ArrayList<>();
//        List<String> manufacturers = new ArrayList<>();
        Map<String, List<String>> modelsByManufacturer = new HashMap<>();
        WebElement link = driver.findElement(By.linkText("find your car manually"));
        link.click();
        WebElement selectManufacturerElement = driver.findElement(By.id("car-manufacturer"));
        Select selectManufacturer = new Select(selectManufacturerElement);
//        manufacturers = selectManufacturer.getOptions().stream().filter(manufacturer -> !manufacturer.getText().equalsIgnoreCase("Make")).map(WebElement::getText).collect(Collectors.toList());
        manufacturerList = selectManufacturer.getOptions()
                .stream()
                .filter(manufacturer -> !manufacturer.getText().equalsIgnoreCase("Make"))
                .map(manufacturer -> new Manufacturer(manufacturer.getText()))
                .collect(Collectors.toList());

        manufacturerList.forEach(manufacturer -> {
            selectManufacturer.selectByVisibleText(manufacturer.getName());
            WebElement selectModelElement = driver.findElement(By.id("car-model"));
            while (!selectModelElement.isEnabled());
            Select selectModel = new Select(selectModelElement);
            manufacturer.setModels(selectModel.getOptions()
                    .stream()
                    .filter(model -> !model.getText().equalsIgnoreCase("Model"))
                    .map(model -> new Model(model.getText()))
                    .collect(Collectors.toList()));
            manufacturer.getModels().forEach(model -> {
                selectModel.selectByVisibleText(model.getName());
                WebElement selectVariantElement = driver.findElement(By.id("car-variant"));
                while (!selectVariantElement.isEnabled());
                Select selectVariant = new Select(selectVariantElement);
                selectVariant.selectByVisibleText("All");

                WebElement selectEngineElement = driver.findElement(By.id("car-engine-size"));
                while (!selectEngineElement.isEnabled());
                Select selectEngine = new Select(selectEngineElement);

                model.setEngineSizes(selectEngine.getOptions()
                        .stream()
                        .filter(engineSize -> !engineSize.getText().equalsIgnoreCase("Engine Size"))
                        .map(engineSize -> new EngineSize(engineSize.getText()))
                        .collect(Collectors.toList()));

                model.getEngineSizes().forEach(engineSize -> {
                    selectEngine.selectByVisibleText(engineSize.getName());
                    WebElement selectYearElement;
                    try {
                        selectYearElement = driver.findElement(By.id("car-year-of-manufacture"));
                        while (!selectYearElement.isEnabled());
                    } catch (StaleElementReferenceException e) {
                        selectYearElement = driver.findElement(By.id("car-year-of-manufacture"));
                        while (!selectYearElement.isEnabled());
                    }
                    Select selectYear = new Select(selectYearElement);

                    engineSize.setYears(selectYear.getOptions()
                            .stream()
                            .filter(year -> !year.getText().equalsIgnoreCase("Year Of Manufacture"))
                            .map(WebElement::getText)
                            .collect(Collectors.toList()));
                });
            });
            System.out.println(manufacturer);
        });

        manufacturerList.forEach(Manufacturer::toString);

        System.out.println("End");

    }

    public void teardown() {
        driver.quit();
    }

}
