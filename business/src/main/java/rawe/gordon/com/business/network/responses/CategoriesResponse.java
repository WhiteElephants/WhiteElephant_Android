package rawe.gordon.com.business.network.responses;

import java.util.List;

import rawe.gordon.com.business.network.responses.pojo.CategoryModel;

/**
 * Created by gordon on 5/16/16.
 */
public class CategoriesResponse extends BaseResponse {
    private List<CategoryModel> categories;

    public CategoriesResponse() {
    }

    public List<CategoryModel> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryModel> categories) {
        this.categories = categories;
    }
}
