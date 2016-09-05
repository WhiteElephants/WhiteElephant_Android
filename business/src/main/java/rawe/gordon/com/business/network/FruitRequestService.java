package rawe.gordon.com.business.network;

import java.util.Map;

import okhttp3.ResponseBody;
import rawe.gordon.com.business.network.requests.AddPostRequest;
import rawe.gordon.com.business.network.responses.CategoriesResponse;
import rawe.gordon.com.business.network.responses.CommodityResponse;
import rawe.gordon.com.business.network.responses.OrderResponse;
import rawe.gordon.com.business.network.responses.PopularResponse;
import rawe.gordon.com.business.network.responses.SlideResponse;
import rawe.gordon.com.business.network.responses.UserResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by gordon on 16/5/5.
 */
public interface FruitRequestService {
    /**
     * do not place / before path
     */

    @GET("categories")
    Call<CategoriesResponse> fetchCategories();

    @GET("slides")
    Call<SlideResponse> fetchSlides();

    @GET("api/commodity/sample")
    Call<PopularResponse> getPopularCommodities();

    @POST("api/commodity/fetch")
    Call<CommodityResponse> getCommodityById(@Body Map<String, Object> params);

    @POST("api/user/register")
    Call<UserResponse> registerUser(@Body Map<String, Object> params);

    @POST("api/user/fetch")
    Call<UserResponse> fetchUser(@Body Map<String, Object> params);

    @POST("api/user/auth")
    Call<UserResponse> authentificateUser(@Body Map<String, Object> params);

    @POST("api/order/create")
    Call<OrderResponse> createOrder(@Body Map<String, Object> params);


    /**
     * 以上的工程不相关的请求，从别的app拷贝过来的，直接忽视，不过有些可以用的可以拷贝下来。
     */

    @POST("create")
    Call<ResponseBody> createPost(@Body AddPostRequest postRequest);
}
