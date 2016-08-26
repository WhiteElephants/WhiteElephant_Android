package rawe.gordon.com.fruitmarketclient.fragments.posts;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.iknow.imageselect.fragments.models.ImageMediaEntry;

import java.util.Collections;
import java.util.List;

import rawe.gordon.com.business.activities.BaseActivity;
import rawe.gordon.com.business.activities.ContainerActivity;
import rawe.gordon.com.business.fragments.BaseFragment;
import rawe.gordon.com.business.utils.ToastUtil;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.fragments.MultiSelectFragments;
import rawe.gordon.com.fruitmarketclient.views.posts.PostAdapter;
import rawe.gordon.com.fruitmarketclient.views.posts.mock.Mock;
import rawe.gordon.com.fruitmarketclient.views.posts.models.Node;

/**
 * Created by gordon on 8/25/16.
 */
public class PostComposeFragment extends BaseFragment implements PostAdapter.Operation {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ItemTouchHelper itemTouchHelper;
    private PostAdapter adapter;

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
        choosePictures(0);
        recyclerView.setLayoutManager(linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter = new PostAdapter(getActivity(), data == null?Mock.getInitialData(): (List<Node>) data, this));
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

            }
        });
    }

    @Override
    protected String getRightText() {
        return "确定";
    }

    @Override
    protected void onRightTextClicked() {

    }

    public static void startWithContainer(Activity from, Bundle bundle) {
        if (from == null || from.isFinishing()) return;
        ContainerActivity.startFragmentInside(from, PostComposeFragment.class, bundle);
    }

    @Override
    protected String getTitle() {
        return "写文章";
    }

    @Override
    protected boolean performShutEffect() {
        return true;
    }

    @Override
    public void choosePictures(final int triggerPosition) {
        ((BaseActivity) getActivity()).addFragmentWithoutAnimation(new MultiSelectFragments().setListener(new MultiSelectFragments.ResultListener() {
            @Override
            public void onResult(final List<ImageMediaEntry> selected) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.addMultipleImageNodes(selected, triggerPosition);
                    }
                }, 500);
            }
        }));
    }


}
