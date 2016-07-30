package rawe.gordon.com.business.network.responses.pojo;

import java.util.List;

/**
 * Created by gordon on 16/5/5.
 */
public class MenuModel {
    private String type;
    private List<String> types;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }
}
