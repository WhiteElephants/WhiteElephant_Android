package rawe.gordon.com.business.network.responses;

import rawe.gordon.com.business.network.responses.pojo.CommodityModel;

/**
 * Created by gordon on 5/17/16.
 */
public class CommodityResponse extends BaseResponse {
    private CommodityModel commodity;

    public CommodityModel getCommodity() {
        return commodity;
    }

    public void setCommodity(CommodityModel commodity) {
        this.commodity = commodity;
    }
}
