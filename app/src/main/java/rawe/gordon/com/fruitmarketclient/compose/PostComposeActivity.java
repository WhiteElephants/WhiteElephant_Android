package rawe.gordon.com.fruitmarketclient.compose;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.Collections;
import java.util.List;

import rawe.gordon.com.business.utils.ToastUtil;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.compose.adapter.PostAdapter;
import rawe.gordon.com.fruitmarketclient.compose.mock.Mock;
import rawe.gordon.com.fruitmarketclient.compose.models.Node;
import rawe.gordon.com.fruitmarketclient.compose.models.NodeType;
import rawe.gordon.com.fruitmarketclient.compose.models.TextNode;
import rawe.gordon.com.pick.model.MediaInfo;
import rawe.gordon.com.pick.transfer.Transfer;

/**
 * Created by gordon on 3/12/17.
 */

public class PostComposeActivity extends AppCompatActivity implements PostComposeScenario.View, PostAdapter.Operation {

    private RecyclerView recyclerView;
    private PostAdapter adapter;

    PostComposeScenario.Presenter presenter;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        List<MediaInfo> data = (List<MediaInfo>) Transfer.getInstance().getObjectAndClean(PostComposeScenario.POST_COMPOSE_DATA);
        new PostComposePresenter(this, new PostCompostModel(data));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_post_compose);
        presenter.start();
    }

    @Override
    public void setPresenter(PostComposeScenario.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void findViews() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    }

    @Override
    public void bindActions() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void displayData(List<MediaInfo> mediaInfos) {
        recyclerView.setAdapter(adapter = new PostAdapter(this, Mock.composeData(mediaInfos), this));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (viewHolder.getAdapterPosition() == 0 || viewHolder.getAdapterPosition() == adapter.nodes.size() - 1)
                    return makeMovementFlags(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.ACTION_STATE_IDLE);
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = adapter.nodes.get(viewHolder.getAdapterPosition()).getType() == NodeType.TEXT
                        ? ItemTouchHelper.END | ItemTouchHelper.START : ItemTouchHelper.END;
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
                if (viewHolder.getAdapterPosition() == 0
                        || viewHolder.getAdapterPosition() == adapter.nodes.size() - 1) return;
                if (direction == ItemTouchHelper.END) {
                    if (adapter.nodes.size() == 3) {
                        ToastUtil.say("至少留点内容吧，都删了还怎么玩");
                        return;
                    }
                    adapter.nodes.remove(viewHolder.getAdapterPosition());
                    adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                } else if (direction == ItemTouchHelper.START) {
                    //check type first
                    Node node = adapter.nodes.get(viewHolder.getAdapterPosition());
                    if (node.getType() != NodeType.TEXT)
                        return;
                    TextNode textNode = (TextNode) node;
                    textNode.setSubTitle(!textNode.isSubTitle());
                    adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                }
            }

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void choosePictures(int position) {

    }
}
