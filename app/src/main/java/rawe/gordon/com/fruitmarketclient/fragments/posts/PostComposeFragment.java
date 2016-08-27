package rawe.gordon.com.fruitmarketclient.fragments.posts;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.iknow.imageselect.fragments.models.ImageMediaEntry;

import java.util.Collections;
import java.util.List;

import rawe.gordon.com.business.activities.TransparentBoxActivity;
import rawe.gordon.com.business.fragments.BaseFragment;
import rawe.gordon.com.business.utils.CacheBean;
import rawe.gordon.com.business.utils.ToastUtil;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.fragments.MultiSelectFragment;
import rawe.gordon.com.fruitmarketclient.views.posts.PostAdapter;
import rawe.gordon.com.fruitmarketclient.views.posts.mock.Mock;

/**
 * Created by gordon on 8/25/16.
 */
public class PostComposeFragment extends BaseFragment implements PostAdapter.Operation {

    public static final String KEY_RESULT_LISTENER = "KEY_RESULT_LISTENER";

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ItemTouchHelper itemTouchHelper;
    private PostAdapter adapter;
    private List<ImageMediaEntry> data;

    @Override
    protected int getContentLayout() {
        return R.layout.layout_post_compose;
    }

    @Override
    protected void bindViews(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
    }

    @Override
    protected void prepareData() {
        if (CacheBean.getParam(MultiSelectFragment.KEY_INTENTION_TO_POST, MultiSelectFragment.KEY_INTENTION_TO_POST) != null) {
            try {
                data = (List<ImageMediaEntry>) CacheBean.getParam(MultiSelectFragment.KEY_INTENTION_TO_POST, MultiSelectFragment.KEY_INTENTION_TO_POST);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        recyclerView.setLayoutManager(linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter = new PostAdapter(getActivity(), data == null ? Mock.getInitialData() : Mock.composeData(data), this));
        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (viewHolder.getAdapterPosition() == 0 || viewHolder.getAdapterPosition() == adapter.nodes.size() - 1)
                    return makeMovementFlags(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.ACTION_STATE_IDLE);
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int fromPos = viewHolder.getAdapterPosition(), toPos = target.getAdapterPosition();
                if (toPos == 0 || toPos == adapter.nodes.size() - 1) return false;
                Collections.swap(adapter.nodes, fromPos, toPos);
                adapter.notifyItemMoved(fromPos, toPos);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.START || viewHolder.getAdapterPosition() == 0
                        || viewHolder.getAdapterPosition() == adapter.nodes.size() - 1) return;
                if (adapter.nodes.size() == 3) {
                    ToastUtil.say("至少留点内容吧，都删了还怎么玩");
                }
                adapter.nodes.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
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

    @Override
    protected String getRightText() {
        return "发布";
    }

    @Override
    protected void onRightTextClicked() {

    }

    public static void startWithContainer(Activity from) {
        if (from == null || from.isFinishing()) return;
        TransparentBoxActivity.startFragmentInside(from, PostComposeFragment.class);
    }

    @Override
    protected String getTitle() {
        return "创作";
    }

    @Override
    protected boolean performShutEffect() {
        return true;
    }

    @Override
    public void choosePictures(final int triggerPosition) {
        CacheBean.putParam(KEY_RESULT_LISTENER, KEY_RESULT_LISTENER, new MultiSelectFragment.ResultListener() {
            @Override
            public void onResult(List<ImageMediaEntry> selected) {
                adapter.addMultipleImageNodes(selected, triggerPosition);
            }
        });
        MultiSelectFragment.startWithBoxActivity(getActivity(), MultiSelectFragment.INTENTION_TO_CHOOSE, true);
    }


}
