package rawe.gordon.com.business.utils;

import java.util.ArrayList;
import java.util.List;

import rawe.gordon.com.business.network.responses.pojo.CommodityModel;

/**
 * Created by gordon on 16/5/4.
 */
public class FakeDataGenerator {

    public static List<CommodityModel> getMerchandises() {
        List<CommodityModel> fake = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            fake.add(new CommodityModel());
        }
        return fake;
    }
}
