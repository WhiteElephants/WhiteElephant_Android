package rawe.gordon.com.business.utils;

import com.gordon.rawe.business.models.CartOrder;
import com.gordon.rawe.business.models.User;

import java.util.List;

import rawe.gordon.com.business.db.DBManager;

/**
 * Created by gordon on 16/5/22.
 */
public class LoginManager {
    private static LoginManager ourInstance = new LoginManager();

    public static LoginManager getInstance() {
        return ourInstance;
    }

    private LoginManager() {
    }

    public boolean isLogin() {
        List<User> users = DBManager.getInstance().getUsers();
        return users != null && users.size() > 0;
    }

    public void logOut() {
        DBManager.getInstance().removeAllUsers();
        DBManager.getInstance().removeAllOrders();
    }

    public User getLogedInUser() {
        List<User> users = DBManager.getInstance().getUsers();
        if (users == null || users.size() == 0) return null;
        else return users.get(0);
    }

    public List<CartOrder> getCartOrders() {
        return DBManager.getInstance().getCartOrders();
    }

    public void addOneCart() {

    }
}
