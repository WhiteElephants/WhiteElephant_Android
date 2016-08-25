package rawe.gordon.com.fruitmarketclient.generals.pops;

/**
 * Created by gordon on 2015/7/26.
 */
public class LocationEntity {
    private String privince;
    private String city;
    private String county;

    public String getProvince() {
        return privince;
    }

    public void setPrivince(String privince) {
        this.privince = privince;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    @Override
    public String toString() {
        return getProvince() + " " + getCity() + " " + getCounty();
    }
}
