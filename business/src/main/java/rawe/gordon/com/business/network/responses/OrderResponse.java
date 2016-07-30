package rawe.gordon.com.business.network.responses;

import rawe.gordon.com.business.network.responses.pojo.OrderModel;

/**
 * Created by gordon on 16/5/5.
 */
public class OrderResponse extends BaseResponse {
    private OrderModel order;

    public OrderModel getOrder() {
        return order;
    }

    public void setOrder(OrderModel order) {
        this.order = order;
    }
}
