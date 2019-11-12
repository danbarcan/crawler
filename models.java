import java.util.ArrayList;
import java.util.List;

public class Manufacturer {
    private String name;
    private List<Model> models;

    public Manufacturer() {
    }

    public Manufacturer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }

    public void addModel(Model model){
        if (this.models == null) {
            this.models = new ArrayList<>();
        }
        this.models.add(model);
    }

    @Override
    public String toString() {
        return "Manufacturer{" +
                "name='" + name + '\'' +
                ", models=" + models +
                '}';
    }
}


import java.util.ArrayList;
import java.util.List;

public class Model {
    private String name;
    private List<EngineSize> engineSizes;

    public Model() {
    }

    public Model(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EngineSize> getEngineSizes() {
        return engineSizes;
    }

    public void setEngineSizes(List<EngineSize> engineSizes) {
        this.engineSizes = engineSizes;
    }

    public void addEngineSize(EngineSize engineSize) {
        if (this.engineSizes == null) {
            this.engineSizes = new ArrayList<>();
        }
        this.engineSizes.add(engineSize);
    }

    @Override
    public String toString() {
        return "\n\tModel{" +
                "name='" + name + '\'' +
                ", engineSizes=" + engineSizes +
                '}';
    }
}


import java.util.ArrayList;
import java.util.List;

public class EngineSize {
    private String name;
    private List<String> years;

    public EngineSize() {
    }

    public EngineSize(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getYears() {
        return years;
    }

    public void setYears(List<String> years) {
        this.years = years;
    }

    public void addYear(String year) {
        if (this.years == null) {
            this.years = new ArrayList<>();
        }
        this.years.add(year);
    }

    @Override
    public String toString() {
        return "\n\t\tEngineSize{" +
                "name='" + name + '\'' +
                ", years=" + years +
                '}';
    }
}
