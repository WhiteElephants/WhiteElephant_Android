package rawe.gordon.com.fruitmarketclient.compose;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.alibaba.fastjson.JSON;

import java.util.Collections;
import java.util.List;

import rawe.gordon.com.business.utils.ToastUtil;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.compose.adapter.PostAdapter;
import rawe.gordon.com.fruitmarketclient.compose.models.Node;
import rawe.gordon.com.fruitmarketclient.compose.models.NodeType;
import rawe.gordon.com.fruitmarketclient.compose.models.TextNode;
import rawe.gordon.com.fruitmarketclient.old.fragments.posts.PostComposeFragment;
import rawe.gordon.com.fruitmarketclient.old.generals.dialogs.warning.DialogHelper;
import rawe.gordon.com.pick.manage.PhotoChooseListener;
import rawe.gordon.com.pick.manage.PhotoPictureManager;
import rawe.gordon.com.pick.model.MediaInfo;
import rawe.gordon.com.pick.transfer.Transfer;

/**
 * Created by gordon on 3/12/17.
 */

public class PostComposeActivity extends AppCompatActivity implements PostComposeScenario.View, PostAdapter.Operation, View.OnClickListener {


    @SuppressWarnings("unchecked")
    private void fetchData() {
        Object passedData = Transfer.getInstance().getObjectAndClean(PostComposeScenario.POST_COMPOSE_DATA);
        if (passedData == null) {
            finishPage();
            return;
        }
        if (passedData instanceof List) {
            List<MediaInfo> data = (List<MediaInfo>) passedData;
            new PostComposePresenter(this, model = new PostCompostModel(data));
        } else if (passedData instanceof PostComposeFragment.ResumeModel) {
            PostComposeFragment.ResumeModel resumeModel = (PostComposeFragment.ResumeModel) passedData;
            new PostComposePresenter(this, model = new PostCompostModel().passParams(resumeModel.nodes));
            model.postUuid = resumeModel.uuid;
            model.initialStringData = JSON.toJSONString(resumeModel.nodes);
        } else {
            finishPage();
        }
    }

    private View rightArea, backArea;

    private RecyclerView recyclerView;
    private PostAdapter adapter;

    PostComposeScenario.Presenter presenter;
    PostCompostModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        fetchData();
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
        backArea = findViewById(R.id.left_area);
        rightArea = findViewById(R.id.right_text_area);
    }

    @Override
    public void bindActions() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rightArea.setOnClickListener(this);
        backArea.setOnClickListener(this);
    }

    @Override
    public void displayData(List<MediaInfo> mediaInfos) {
        recyclerView.setAdapter(adapter = new PostAdapter(this, model, this));
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
    public void finishPage() {
        finish();
    }

    @Override
    public void renderHintEmpty() {
        DialogHelper.createTwoChoiceDialog(this, "提示", "保存草稿至少需要标题?", "继续编辑", "直接退出", new DialogHelper.TwoChoiceListener() {
            @Override
            public void onYes() {

            }

            @Override
            public void onNo() {
                finishPage();
            }
        }).show();
    }

    @Override
    public void renderHintSave() {
        DialogHelper.createTwoChoiceDialog(this, "提示", "是否要保存草稿?", "保存", "不保存", new DialogHelper.TwoChoiceListener() {
            @Override
            public void onYes() {
                presenter.saveDraft();
                finishPage();
            }

            @Override
            public void onNo() {
                finishPage();
            }
        }).show();
    }

    @Override
    public void choosePictures(final int position) {
        PhotoPictureManager.getInstance().startToChooseMedias(this, new PhotoChooseListener() {
            @Override
            public void getResult(List<MediaInfo> result) {
                super.getResult(result);
                adapter.addMultipleImageNodes(result, position);
            }
        }, 9);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.left_area) {
            presenter.handleCancel();
        } else if (v.getId() == R.id.right_text_area) {
            presenter.handleSure();
        }
    }

    @Override
    public void onBackPressed() {
        presenter.handleCancel();
    }
}
