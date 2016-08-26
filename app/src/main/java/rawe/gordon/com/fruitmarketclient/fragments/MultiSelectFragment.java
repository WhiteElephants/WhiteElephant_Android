package rawe.gordon.com.fruitmarketclient.fragments;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.iknow.imageselect.R;
import com.iknow.imageselect.fragments.adapters.MultiSelectAdapter;
import com.iknow.imageselect.fragments.models.ImageMediaEntry;
import com.iknow.imageselect.fragments.provider.SourceProvider;

import java.util.ArrayList;
import java.util.List;

import rawe.gordon.com.business.activities.BaseActivity;
import rawe.gordon.com.business.fragments.BaseFragment;
import rawe.gordon.com.business.utils.CacheBean;
import rawe.gordon.com.business.utils.ToastUtil;
import rawe.gordon.com.fruitmarketclient.fragments.posts.PostComposeFragment;

/**
 * Created by gordon on 16/8/26.
 */
public class MultiSelectFragment extends BaseFragment {
    public static final int INTENTION_TO_POST = 0;
    public static final int INTENTION_TO_CHOOSE = 1;
    private int intention = INTENTION_TO_POST;
    public static final String KEY_INTENTION_TO_POST = "KEY_INTENTION_TO_POST";

    private RecyclerView recyclerView;
    private List<ImageMediaEntry> imageMediaEntries;
    private boolean allowEmpty = true;

    @Override
    protected int getContentLayout() {
        return R.layout.layout_multi_select;
    }

    @Override
    protected void bindViews(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
    }

    @Override
    protected void prepareData() {
        imageMediaEntries = SourceProvider.getAllImages();
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new MultiSelectAdapter(getActivity(), imageMediaEntries));
    }

    @Override
    protected boolean performShutEffect() {
        return true;
    }

    @Override
    protected String getTitle() {
        return "选择图片";
    }

    @Override
    protected String getRightText() {
        return "确定";
    }

    @Override
    protected void onRightTextClicked() {
        if (!allowEmpty && filterSelected(imageMediaEntries).size() == 0) {
            ToastUtil.say("至少选选一张图片才行");
            return;
        }
        closeWithAnimation(new Callback() {
            @Override
            public void onAnimationFinish() {
                if (intention == INTENTION_TO_POST) {
                    PostComposeFragment.startWithContainer(getActivity(), null);
                    CacheBean.putParam(KEY_INTENTION_TO_POST, KEY_INTENTION_TO_POST, filterSelected(imageMediaEntries));
                } else {
                    if (listener != null) listener.onResult(filterSelected(imageMediaEntries));
                }
                closeWithAnimation(new Callback() {
                    @Override
                    public void onAnimationFinish() {
                        if (getActivity() instanceof BaseActivity)
                            ((BaseActivity) getActivity()).removeFragmentWithoutEffect(MultiSelectFragment.this);
                        else
                            getActivity().getSupportFragmentManager().beginTransaction().remove(MultiSelectFragment.this).commitAllowingStateLoss();
                    }
                });
            }
        });

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
                ((BaseActivity)getActivity()).removeFragmentWithoutEffect(MultiSelectFragment.this);
            }
        });
    }

    public MultiSelectFragment setListener(ResultListener listener) {
        this.listener = listener;
        return this;
    }

    public MultiSelectFragment setIntention(int intention) {
        this.intention = intention;
        return this;
    }

    public MultiSelectFragment setAllowEmpty(boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
        return this;
    }

    private ResultListener listener;

    public interface ResultListener {
        void onResult(List<ImageMediaEntry> selected);
    }

    public List<ImageMediaEntry> filterSelected(List<ImageMediaEntry> src) {
        List<ImageMediaEntry> retValues = new ArrayList<>();
        for (ImageMediaEntry entry : src) {
            if (entry.isSelected()) retValues.add(entry);
        }
        return retValues;
    }
}
