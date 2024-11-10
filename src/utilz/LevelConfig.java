package utilz;

import java.util.Map;

public class LevelConfig {

    private Map<String, Integer> alienTypes;

    public LevelConfig(Map<String, Integer> alienTypes) {
        this.alienTypes = alienTypes;
    }

    public Map<String, Integer> getAlienTypes() {
        return alienTypes;
    }

    public void setAlienTypes(Map<String, Integer> alienTypes) {
        this.alienTypes = alienTypes;
    }
}
