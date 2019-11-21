import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

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

        testUrl = "https://www.epiesa.ro/";

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
        WebElement selectManufacturerElement = driver.findElement(By.name("select_marca"));
        Select selectManufacturer = new Select(selectManufacturerElement);
//        manufacturers = selectManufacturer.getOptions().stream().filter(manufacturer -> !manufacturer.getText().equalsIgnoreCase("Make")).map(WebElement::getText).collect(Collectors.toList());
        manufacturerList = selectManufacturer.getOptions()
                .stream()
                .filter(manufacturer -> !manufacturer.getText().contains("MARCA"))
                .map(manufacturer -> new Manufacturer(manufacturer.getText().trim()))
                .collect(Collectors.toList());

        manufacturerList.forEach(manufacturer -> {
            System.out.println("Select marca: " + manufacturer.getName());
            selectManufacturer.selectByVisibleText(manufacturer.getName());
            WebElement selectModelElement = driver.findElement(By.name("select_model"));
            while (new Select(selectModelElement).getOptions().size() < 2);
            Select selectModel = new Select(selectModelElement);
            manufacturer.setModels(selectModel.getOptions()
                    .stream()
                    .filter(model -> !model.getText().contains("MODEL"))
                    .map(model -> new Model(model.getAttribute("value")))
                    .collect(Collectors.toList()));
            manufacturer.getModels().forEach(model -> {
                System.out.println("Select model: " + model.getName());
                selectModel.selectByValue(model.getName());
                WebElement selectVariantElement = driver.findElement(By.name("select_carburant"));
                while (new Select(selectVariantElement).getOptions().size() < 2);
                Select selectVariant = new Select(selectVariantElement);

                model.setFuelTypes(selectVariant.getOptions()
                .stream()
                .filter(fuelType -> !fuelType.getText().contains("CARBURANT"))
                .map(fuelType -> new FuelType(fuelType.getAttribute("value")))
                .collect(Collectors.toList()));

                model.getFuelTypes().forEach(fuelType -> {
                    System.out.println("Select carburant: " + fuelType.getName());
                    selectVariant.selectByValue(fuelType.getName());
                    WebElement selectEngineElement = driver.findElement(By.name("select_cilindree"));
                    while (new Select(selectEngineElement).getOptions().size() < 2);
                    Select selectEngine = new Select(selectEngineElement);

                    fuelType.setEngineSizes(selectEngine.getOptions()
                            .stream()
                            .filter(engineSize -> !engineSize.getText().contains("CILINDREE"))
                            .map(engineSize -> new EngineSize(engineSize.getAttribute("value")))
                            .collect(Collectors.toList()));

                    fuelType.getEngineSizes().forEach(engineSize -> {
                        System.out.println("Select cilindree: " + engineSize.getName());
                        selectEngine.selectByValue(engineSize.getName());
                        WebElement selectYearElement;
                        try {
                            selectYearElement = driver.findElement(By.name("select_motorizari"));
                            while (!selectYearElement.isEnabled());
                        } catch (StaleElementReferenceException e) {
                            selectYearElement = driver.findElement(By.name("select_motorizari"));
                            while (!selectYearElement.isEnabled());
                        }
                        Select selectYear = new Select(selectYearElement);

                        engineSize.setPowers(selectYear.getOptions()
                                .stream()
                                .filter(year -> !year.getText().contains("PUTERE"))
                                .map(WebElement::getText)
                                .collect(Collectors.toList()));
                    });
                });

            });
            System.out.println(manufacturer);
        });

        //manufacturerList.forEach(Manufacturer::toString);

        System.out.println("End");

    }

    public void teardown() {
        driver.quit();
    }

}
