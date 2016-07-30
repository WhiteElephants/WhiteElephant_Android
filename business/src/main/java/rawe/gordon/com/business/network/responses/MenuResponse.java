package rawe.gordon.com.business.network.responses;

import java.util.List;

import rawe.gordon.com.business.network.responses.pojo.MenuModel;

/**
 * Created by gordon on 16/5/5.
 */
public class MenuResponse extends BaseResponse {
    private List<MenuModel> menus;

    public List<MenuModel> getMenus() {
        return menus;
    }

    public void setMenus(List<MenuModel> menus) {
        this.menus = menus;
    }
}
