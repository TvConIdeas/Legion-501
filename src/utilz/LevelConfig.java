package utilz;

import java.util.Map;

public class LevelConfig {

    // ====================> ATRIBUTOS <====================
    private Map<String, Integer> alienTypes;

    // ====================> CONSTRUCTOR <====================
    public LevelConfig(Map<String, Integer> alienTypes) {
        this.alienTypes = alienTypes;
    }

    // ====================> GET | SET <====================
    public Map<String, Integer> getAlienTypes() {
        return alienTypes;
    }

    public void setAlienTypes(Map<String, Integer> alienTypes) {
        this.alienTypes = alienTypes;
    }
}
