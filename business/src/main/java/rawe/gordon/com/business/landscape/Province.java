package rawe.gordon.com.business.landscape;

import java.util.Map;

/**
 * Created by gordon on 16/5/26.
 */
public class Province {
    private Map<String,City> cities;

    public Province() {
    }

    public Map<String, City> getCities() {
        return cities;
    }

    public void setCities(Map<String, City> cities) {
        this.cities = cities;
    }
}
