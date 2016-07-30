package rawe.gordon.com.business.network.responses;

import java.util.List;

import rawe.gordon.com.business.network.responses.pojo.OrderModel;
import rawe.gordon.com.business.network.responses.pojo.UserModel;

/**
 * Created by gordon on 16/5/22.
 */
public class UserResponse extends BaseResponse {
    private UserModel user;

    private List<OrderModel> orders;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public List<OrderModel> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderModel> orders) {
        this.orders = orders;
    }
}
