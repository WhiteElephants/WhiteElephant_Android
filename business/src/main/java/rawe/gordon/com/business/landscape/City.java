package rawe.gordon.com.business.landscape;

import java.util.List;

/**
 * Created by gordon on 16/5/26.
 */
public class City {
    private List<String> counties;

    public City() {
    }

    public List<String> getCounties() {
        return counties;
    }

    public void setCounties(List<String> counties) {
        this.counties = counties;
    }
}
