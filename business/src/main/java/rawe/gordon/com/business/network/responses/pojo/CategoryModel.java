package rawe.gordon.com.business.network.responses.pojo;

import java.util.List;

/**
 * Created by gordon on 5/16/16.
 */
public class CategoryModel {
    private String category;

    private List<CategorySubModel> subTypes;

    public CategoryModel() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<CategorySubModel> getSubTypes() {
        return subTypes;
    }

    public void setSubTypes(List<CategorySubModel> subTypes) {
        this.subTypes = subTypes;
    }

    @Override
    public String toString() {
        return "ClassPojo [category = " + category + ", subTypes = " + subTypes + "]";
    }
}
