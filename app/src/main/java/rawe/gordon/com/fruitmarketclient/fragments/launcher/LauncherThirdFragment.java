package rawe.gordon.com.fruitmarketclient.fragments.launcher;

import android.content.Context;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import rawe.gordon.com.business.application.SharedKeys;
import rawe.gordon.com.business.utils.PreferencesHelper;
import rawe.gordon.com.business.utils.ToastUtil;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.business.fragments.LauncherBaseFragment;

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
        String draftRecords = PreferencesHelper.getInstance().getString(SharedKeys.KEY_DRAFT);
        List<String> drafts = TextUtils.isEmpty(draftRecords) ? new ArrayList<String>() : JSON.parseArray(draftRecords, String.class);
        draftRecyclerView.setAdapter(new DraftAdapter(getContext(), drafts));
    }

    public static class DraftHoler extends RecyclerView.ViewHolder {

        public DraftHoler(View itemView) {
            super(itemView);
        }
    }

    public static class DraftAdapter extends RecyclerView.Adapter<DraftHoler> {
        private List<String> data;
        private Context context;

        public DraftAdapter(Context context, List<String> data) {
            this.data = data;
            this.context = context;
        }

        @Override
        public DraftHoler onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DraftHoler(LayoutInflater.from(context).inflate(R.layout.layout_draft_item, parent, false));
        }

        @Override
        public void onBindViewHolder(DraftHoler holder, int position) {
            ((TextView)holder.itemView.findViewById(R.id.post_name)).setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
}
