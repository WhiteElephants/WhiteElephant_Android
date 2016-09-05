package rawe.gordon.com.fruitmarketclient.fragments.launcher;

import android.view.View;

import okhttp3.ResponseBody;
import rawe.gordon.com.business.fragments.LauncherBaseFragment;
import rawe.gordon.com.business.network.RestClient;
import rawe.gordon.com.business.network.requests.AddPostRequest;
import rawe.gordon.com.business.utils.ToastUtil;
import rawe.gordon.com.fruitmarketclient.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gordon on 16/7/31.
 */
public class LauncherFirstFragment extends LauncherBaseFragment {

    private View request;

    public static LauncherFirstFragment newInstance() {
        LauncherFirstFragment firstLauncher = new LauncherFirstFragment();
        return firstLauncher;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.layout_launcher_first_fragment;
    }

    @Override
    protected void bindViews(View rootView) {
        request = rootView.findViewById(R.id.request);
    }

    @Override
    protected void prepareData() {

    }

    @Override
    public void fetchNetWorkData() {
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestClient.getInstance().createPost(AddPostRequest.createSamplePost(), new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ToastUtil.say(response.toString());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        ToastUtil.say("fail");
                    }
                });
            }
        });
    }

    @Override
    protected String getTitle() {
        return "推荐";
    }
}
