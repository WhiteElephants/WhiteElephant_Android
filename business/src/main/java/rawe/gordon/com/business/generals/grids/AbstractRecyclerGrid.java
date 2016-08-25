package rawe.gordon.com.business.generals.grids;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.List;

import rawe.gordon.com.business.R;

/**
 * Created by gordon on 5/6/16.
 */
public abstract class AbstractRecyclerGrid<T> extends RelativeLayout {

    private RecyclerView recyclerView;
    private View rootView;
    private RecyclerGridAdapter adapter;
    private Activity activity;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    public AbstractRecyclerGrid(Context context, AttributeSet attrs) {
        super(context, attrs);
        rootView = LayoutInflater.from(context).inflate(R.layout.layout_recycler_grid, this);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setNestedScrollingEnabled(false);
    }

    public void addBatch(List<T> src) {
        adapter.addBatch(src);
    }

    public void initialize(List<T> source, final Activity activity) {
        recyclerView.setLayoutManager(staggeredGridLayoutManager = new StaggeredGridLayoutManager(getColumnCount(), StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter = new RecyclerGridAdapter(source));
        this.activity = activity;
    }

    protected void setColumnCount(int columnCount) {
        staggeredGridLayoutManager.setSpanCount(columnCount);
    }

    protected void setDirection(int orientation) {
        staggeredGridLayoutManager.setOrientation(orientation);
    }

    protected abstract int getItemLayout();

    protected abstract int getColumnCount();

    protected abstract void bindViews(View view, T model, Activity activity);

    public class RecyclerGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<T> source;

        public RecyclerGridAdapter(List<T> source) {
            this.source = source;
        }

        public void addBatch(List<T> src) {
            int from = source.size();
            source.addAll(src);
            notifyItemRangeInserted(from, src.size());
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RecyclerGridViewHolder(LayoutInflater.from(parent.getContext()).inflate(getItemLayout(), parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            RecyclerGridViewHolder recyclerGridViewHolder = (RecyclerGridViewHolder) holder;
            recyclerGridViewHolder.setModel(source.get(position));
            bindViews(recyclerGridViewHolder.view, source.get(position), activity);
        }

        @Override
        public int getItemCount() {
            return source.size();
        }

        private class RecyclerGridViewHolder extends RecyclerView.ViewHolder {
            T model;
            View view;

            public void setModel(T model) {
                this.model = model;
            }

            public RecyclerGridViewHolder(View itemView) {
                super(itemView);
                view = itemView;
            }
        }
    }
}
