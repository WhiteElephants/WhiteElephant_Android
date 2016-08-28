package rawe.gordon.com.fruitmarketclient.fragments.preview;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import rawe.gordon.com.business.activities.TransparentBoxActivity;
import rawe.gordon.com.business.fragments.BaseFragment;
import rawe.gordon.com.business.utils.CacheBean;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.fragments.posts.PostComposeFragment;
import rawe.gordon.com.fruitmarketclient.views.posts.models.Node;
import rawe.gordon.com.fruitmarketclient.views.preview.holders.PreviewAdapter;

/**
 * Created by gordon on 16/8/28.
 */
public class PostPreviewFragment extends BaseFragment {
    private List<Node> nodes;
    private RecyclerView recyclerView;

    @Override
    protected int getContentLayout() {
        return R.layout.layout_post_preview_fragment;
    }

    @Override
    protected void bindViews(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
    }

    @Override
    protected void prepareData() {
        nodes = (List<Node>) CacheBean.getParam(PostComposeFragment.KEY_POST_MODEL, PostComposeFragment.KEY_POST_MODEL);
        CacheBean.clean(PostComposeFragment.KEY_POST_MODEL);
        workFlow();
    }

    private void workFlow() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new PreviewAdapter(nodes,getContext()));
    }

    @Override
    protected boolean performShutEffect() {
        return true;
    }

    public static void startWithContainer(Activity from) {
        if (from == null || from.isFinishing()) return;
        TransparentBoxActivity.startFragmentInside(from, PostPreviewFragment.class);
    }

    @Override
    protected String getTitle() {
        return "预览";
    }

    @Override
    protected int getLeftDrawable() {
        return R.drawable.ic_arrow_back;
    }

    @Override
    protected void onLeftIconClicked() {
        closeWithAnimation(new Callback() {
            @Override
            public void onAnimationFinish() {
                getActivity().finish();
            }
        });
    }
}
