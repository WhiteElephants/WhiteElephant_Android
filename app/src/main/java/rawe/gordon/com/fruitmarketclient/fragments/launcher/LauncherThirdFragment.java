package rawe.gordon.com.fruitmarketclient.fragments.launcher;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gordon.rawe.business.models.Post;

import java.util.ArrayList;
import java.util.List;

import rawe.gordon.com.business.db.DBManager;
import rawe.gordon.com.business.fragments.LauncherBaseFragment;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.fragments.posts.PostComposeFragment;

/**
 * Created by gordon on 16/7/31.
 */
public class LauncherThirdFragment extends LauncherBaseFragment {

    private RecyclerView draftRecyclerView, publishedRecyclerView;
    private View noDraftTip, noDocumentTip;

    public static LauncherThirdFragment newInstance() {
        LauncherThirdFragment thirdLauncher = new LauncherThirdFragment();
        return thirdLauncher;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.layout_launcher_third_fragment;
    }

    @Override
    protected void bindViews(View rootView) {
        draftRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_draft);
        publishedRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_published);
        noDraftTip = rootView.findViewById(R.id.no_draft_tip);
        noDocumentTip = rootView.findViewById(R.id.no_document_tip);
    }

    @Override
    protected void prepareData() {
        draftRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        publishedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void fetchNetWorkData() {
        reloadDraft();
        loadDocuments();
    }

    @Override
    protected String getTitle() {
        return "草稿";
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (menuVisible) {
            reloadDraft();
        }
    }

    private void loadDocuments() {
        final List<Post> posts = new ArrayList<>();
        if (posts.size() > 0) noDocumentTip.setVisibility(View.GONE);
    }

    private void reloadDraft() {
        final List<Post> posts = DBManager.getInstance().getAllPosts();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                draftRecyclerView.setAdapter(new DraftAdapter(getActivity(), posts));
                if (posts.size() > 0) noDraftTip.setVisibility(View.GONE);
            }
        });
    }

    public static class DraftHoler extends RecyclerView.ViewHolder {

        public TextView createTime, title;
        public ImageView thumbView;

        public DraftHoler(View itemView) {
            super(itemView);
            createTime = (TextView) itemView.findViewById(R.id.create_time);
            title = (TextView) itemView.findViewById(R.id.post_name);
            thumbView = (ImageView) itemView.findViewById(R.id.post_thumb);
        }
    }

    public static class DraftAdapter extends RecyclerView.Adapter<DraftHoler> {
        private List<Post> data;
        private Context context;

        public DraftAdapter(Context context, List<Post> data) {
            this.data = data;
            this.context = context;
        }

        @Override
        public DraftHoler onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DraftHoler(LayoutInflater.from(context).inflate(R.layout.layout_draft_item, parent, false));
        }

        @Override
        public void onBindViewHolder(DraftHoler holder, final int position) {
            final Post post = data.get(position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PostComposeFragment.resumeFromDb((Activity) context, post.getData(), post.getUuid());
                }
            });
            Glide.with(context).load(data.get(position).getThumbPath()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.thumbView);
            holder.createTime.setText(post.getCreateTime());
            holder.title.setText(TextUtils.isEmpty(post.getPostName()) ? "未设置标题" : data.get(position).getPostName());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
}
