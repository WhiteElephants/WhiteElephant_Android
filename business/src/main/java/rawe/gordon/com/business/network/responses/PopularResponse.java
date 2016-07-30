package rawe.gordon.com.business.network.responses;

import java.util.List;

import rawe.gordon.com.business.network.responses.pojo.CommodityModel;

/**
 * Created by gordon on 16/5/16.
 */
public class PopularResponse extends BaseResponse {
    List<CommodityModel> commodities;

    public List<CommodityModel> getCommodities() {
        return commodities;
    }

    public void setCommodities(List<CommodityModel> commodities) {
        this.commodities = commodities;
    }
}
