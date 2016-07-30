package rawe.gordon.com.business.network;

import java.util.HashMap;
import java.util.Map;

import rawe.gordon.com.business.network.responses.CategoriesResponse;
import rawe.gordon.com.business.network.responses.CommodityResponse;
import rawe.gordon.com.business.network.responses.OrderResponse;
import rawe.gordon.com.business.network.responses.PopularResponse;
import rawe.gordon.com.business.network.responses.UserResponse;
import rawe.gordon.com.business.network.responses.SlideResponse;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gordon on 16/5/4.
 */
public class RestClient {
//        public static final String BASE_URL = "http://192.168.0.102:1128/";
    public static final String BASE_URL = "http://10.32.64.121:1128/";
    private static RestClient ourInstance = new RestClient();
    private FruitRequestService sharedService;

    public static RestClient getInstance() {
        return ourInstance;
    }

    private RestClient() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        sharedService = retrofit.create(FruitRequestService.class);
    }

    public void getCategories(Callback<CategoriesResponse> callback) {
        sharedService.fetchCategories().enqueue(callback);
    }

    public void getHomeSlides(Callback<SlideResponse> callback) {
        sharedService.fetchSlides().enqueue(callback);
    }

    public void getPopularCommodities(Callback<PopularResponse> callback) {
        sharedService.getPopularCommodities().enqueue(callback);
    }

    public void getCommodityById(String uuid, Callback<CommodityResponse> callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", uuid);
        sharedService.getCommodityById(params).enqueue(callback);
    }

    public void registerUser(String username, String password, String phone, Callback<UserResponse> callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("phone", phone);
        sharedService.registerUser(params).enqueue(callback);
    }

    public void getUserInfo(String userid, Callback<UserResponse> callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userid);
        sharedService.fetchUser(params).enqueue(callback);
    }

    public void authenticate(String username, String password, Callback<UserResponse> callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        sharedService.authentificateUser(params).enqueue(callback);
    }

    public void addCommodityToCart(String user_id, String commodity_id, String color, String size, String thumbnail, int count, float price, String name, Callback<OrderResponse> callback) {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", user_id);
        params.put("commodity_id", commodity_id);
        params.put("color", color);
        params.put("size", size);
        params.put("thumbnail", thumbnail);
        params.put("amount", count);
        params.put("price", price);
        params.put("name", name);
        sharedService.createOrder(params).enqueue(callback);
    }
}
