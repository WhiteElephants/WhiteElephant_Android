package rawe.gordon.com.fruitmarketclient.old.fragments.posts;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import rawe.gordon.com.business.activities.TransparentBoxActivity;
import rawe.gordon.com.business.db.DBManager;
import rawe.gordon.com.business.fragments.BaseFragment;
import rawe.gordon.com.business.utils.CacheBean;
import rawe.gordon.com.business.utils.DateUtil;
import rawe.gordon.com.business.utils.ToastUtil;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.old.fragments.MultiSelectFragment;
import rawe.gordon.com.fruitmarketclient.old.fragments.preview.PostPreviewFragment;
import rawe.gordon.com.fruitmarketclient.old.generals.dialogs.warning.DialogHelper;
import rawe.gordon.com.fruitmarketclient.compose.adapter.GroupImageAdapter;
import rawe.gordon.com.fruitmarketclient.compose.adapter.PostAdapter;
import rawe.gordon.com.fruitmarketclient.compose.mock.Mock;
import rawe.gordon.com.fruitmarketclient.compose.models.GroupNode;
import rawe.gordon.com.fruitmarketclient.compose.models.HeaderNode;
import rawe.gordon.com.fruitmarketclient.compose.models.ImageNode;
import rawe.gordon.com.fruitmarketclient.compose.models.MergedNode;
import rawe.gordon.com.fruitmarketclient.compose.models.Node;
import rawe.gordon.com.fruitmarketclient.compose.models.NodeType;
import rawe.gordon.com.fruitmarketclient.compose.models.TextNode;
import rawe.gordon.com.pick.model.MediaInfo;

/**
 * Created by gordon on 8/25/16.
 */
public class PostComposeFragment extends BaseFragment implements PostAdapter.Operation {

    public static final String KEY_RESULT_LISTENER = "KEY_RESULT_LISTENER";
    public static final String KEY_POST_MODEL = "KEY_POST_MODEL";
    public static final String KEY_POST_MODEL_RESUME = "KEY_POST_MODEL_RESUME";

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ItemTouchHelper itemTouchHelper;
    private PostAdapter adapter;
    private List<MediaInfo> data;
    String postUuid = UUID.randomUUID().toString().replace("-", "");
    private String initialStringData = "";

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
        recyclerView.setLayoutManager(linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        if (CacheBean.getParam(MultiSelectFragment.KEY_INTENTION_TO_POST, MultiSelectFragment.KEY_INTENTION_TO_POST) != null) {
            try {
                data = (List<MediaInfo>) CacheBean.getParam(MultiSelectFragment.KEY_INTENTION_TO_POST, MultiSelectFragment.KEY_INTENTION_TO_POST);
                CacheBean.clean(MultiSelectFragment.KEY_INTENTION_TO_POST);
                recyclerView.setAdapter(adapter = new PostAdapter(getActivity(), Mock.composeData(data), this));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (CacheBean.getParam(KEY_POST_MODEL_RESUME, KEY_POST_MODEL_RESUME) != null) {
            ResumeModel resumedNodes = (ResumeModel) CacheBean.getParam(KEY_POST_MODEL_RESUME, KEY_POST_MODEL_RESUME);
            CacheBean.clean(KEY_POST_MODEL_RESUME);
            postUuid = resumedNodes.uuid;
            initialStringData = JSON.toJSONString(resumedNodes.nodes);
            recyclerView.setAdapter(adapter = new PostAdapter(getActivity(), resumedNodes.nodes, this));
        }
        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
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
    protected int getLeftDrawable() {
        return R.drawable.ic_arrow_back;
    }

    @Override
    protected void onLeftIconClicked() {
        handleBackAction();
    }

    @Override
    protected int getRightDrawable2() {
        return R.drawable.ic_save_white;
    }

    @Override
    protected void onRightIcon2Clicked() {
        CacheBean.putParam(KEY_POST_MODEL, KEY_POST_MODEL, adapter.nodes);
        PostPreviewFragment.startWithContainer(getActivity());
    }

    @Override
    protected int getRightDrawable() {
        return R.drawable.ic_publish_white;
    }

    @Override
    protected void onRightIconClicked() {
        super.onRightIconClicked();
    }

    public static void startWithContainer(Activity from, List<MediaInfo> images) {
        if (from == null || from.isFinishing()) return;
        CacheBean.putParam(MultiSelectFragment.KEY_INTENTION_TO_POST, MultiSelectFragment.KEY_INTENTION_TO_POST, images);
        TransparentBoxActivity.startFragmentInside(from, PostComposeFragment.class);
    }

    public static void resumeFromDb(Activity from, String postData, String uuid) {
        if (from == null || from.isFinishing()) return;
        List<MergedNode> nodes = JSON.parseArray(postData, MergedNode.class);
        ResumeModel resumeModel = new ResumeModel(uuid, MergedNode.toNodes(nodes));
        CacheBean.putParam(KEY_POST_MODEL_RESUME, KEY_POST_MODEL_RESUME, resumeModel);
        TransparentBoxActivity.startFragmentInside(from, PostComposeFragment.class);
    }

    @Override
    public void closeWithAnimation(Callback callback) {
        GroupImageAdapter.resetColumn();
        super.closeWithAnimation(callback);
    }

    @Override
    protected String getTitle() {
        return "创作";
    }

    @Override
    protected void onTitleClicked() {
        GroupImageAdapter.recalc();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void handleBackPress(Callback callback) {
        handleBackAction();
    }

    private void handleBackAction() {
        if (initialStringData.equals(JSON.toJSONString(adapter.nodes))) {
            closeWithAnimation(new Callback() {
                @Override
                public void onAnimationFinish() {
                    getActivity().finish();
                }
            });
            return;
        }
        String title = ((HeaderNode) adapter.nodes.get(0)).getContent();
        if (TextUtils.isEmpty(title)) {
            DialogHelper.createTwoChoiceDialog(getActivity(), "提示", "保存草稿至少需要标题?", "继续编辑", "直接退出", new DialogHelper.TwoChoiceListener() {
                @Override
                public void onYes() {

                }

                @Override
                public void onNo() {
                    closeWithAnimation(new Callback() {
                        @Override
                        public void onAnimationFinish() {
                            getActivity().finish();
                        }
                    });
                }
            }).show();
        } else {
            DialogHelper.createTwoChoiceDialog(getActivity(), "提示", "是否要保存草稿?", "保存", "不保存", new DialogHelper.TwoChoiceListener() {
                @Override
                public void onYes() {
                    try {
                        saveDraft();
                        closeWithAnimation(new Callback() {
                            @Override
                            public void onAnimationFinish() {
                                getActivity().finish();
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                        ToastUtil.say("保存失败");
                    }
                }

                @Override
                public void onNo() {
                    closeWithAnimation(new Callback() {
                        @Override
                        public void onAnimationFinish() {
                            getActivity().finish();
                        }
                    });
                }
            }).show();
        }
    }

    @Override
    protected boolean performShutEffect() {
        return true;
    }

    @Override
    public void choosePictures(final int triggerPosition) {

    }

    public void saveDraft() throws IOException {
        DBManager.getInstance().savePost(postUuid, ((HeaderNode) adapter.nodes.get(0)).getContent(), JSON.toJSONString(MergedNode.toMergedNodes(adapter.nodes)), DateUtil.currentTime(), getThumbPath());
    }

    private String getThumbPath() {
        for (Node node : adapter.nodes) {
            if (node.getType() == NodeType.IMAGE) return ((ImageNode) node).getStoragePath();
            if (node.getType() == NodeType.GROUP)
                return ((GroupNode) node).getImageNodes().get(0).getStoragePath();
        }
        return "";
    }


    /**
     * text code
     * <p/>
     * *public static void main(String[] args) {
     * List<ImageNode> nodes = new ArrayList<>();
     * for (int i = 0; i < 10; i++) {
     * nodes.add(new ImageNode("shit" + i));
     * }
     * try {
     * ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("data.txt"));
     * outputStream.writeObject(nodes);
     * outputStream.close();
     * } catch (IOException e) {
     * e.printStackTrace();
     * }
     * try {
     * ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("data.txt"));
     * List<ImageNode> retrive = (List<ImageNode>) inputStream.readObject();
     * for (ImageNode node : retrive) {
     * System.out.println(node.getStoragePath());
     * }
     * } catch (IOException e) {
     * e.printStackTrace();
     * } catch (ClassNotFoundException e) {
     * e.printStackTrace();
     * }
     * }
     */

    public static class ResumeModel {
        public String uuid;
        public List<Node> nodes;

        public ResumeModel(String uuid, List<Node> nodes) {
            this.uuid = uuid;
            this.nodes = nodes;
        }
    }
}
