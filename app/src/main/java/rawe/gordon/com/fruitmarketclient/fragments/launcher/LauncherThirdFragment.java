package rawe.gordon.com.fruitmarketclient.fragments.launcher;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gordon.rawe.business.models.Post;

import java.util.List;

import rawe.gordon.com.business.db.DBManager;
import rawe.gordon.com.business.fragments.LauncherBaseFragment;
import rawe.gordon.com.business.utils.ToastUtil;
import rawe.gordon.com.fruitmarketclient.R;

/**
 * Created by gordon on 16/7/31.
 */
public class LauncherThirdFragment extends LauncherBaseFragment {

    private RecyclerView draftRecyclerView, publishedRecyclerView;

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
    }

    @Override
    protected void prepareData() {
        draftRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        publishedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void fetchNetWorkData() {
        reloadDraft();
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

    private void reloadDraft() {
        if (draftRecyclerView == null) return;
        List<Post> posts = DBManager.getInstance().getAllPosts();
        draftRecyclerView.setAdapter(new DraftAdapter(getContext(), posts));
    }

    public static class DraftHoler extends RecyclerView.ViewHolder {

        public DraftHoler(View itemView) {
            super(itemView);
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
            ((TextView) holder.itemView.findViewById(R.id.post_name)).setText(TextUtils.isEmpty(data.get(position).getPostName())?"未设置标题":data.get(position).getPostName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtil.say(data.get(position).getUuid());
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
}
