package rawe.gordon.com.fruitmarketclient.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.iknow.imageselect.R;
import com.iknow.imageselect.fragments.models.ImageMediaEntry;

import java.util.List;

import rawe.gordon.com.business.imageloader.SquareImageView;
import rawe.gordon.com.business.utils.ToastUtil;

/**
 * Created by gordon on 16/8/26.
 */
public class MultiSelectAdapter extends RecyclerView.Adapter<MultiSelectAdapter.ImageSelectHolder> {

    List<ImageMediaEntry> data;
    private LayoutInflater inflater;
    private Context context;
    private ItemClickListener listener;
    public static final int SELECT_MAX_LIMIT = 9;

    public MultiSelectAdapter(Context context, List<ImageMediaEntry> src, ItemClickListener listener) {
        this.data = src;
        this.listener = listener;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MultiSelectAdapter.ImageSelectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageSelectHolder(inflater.inflate(R.layout.layout_select_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final MultiSelectAdapter.ImageSelectHolder holder, int position) {
        final ImageMediaEntry entry = data.get(position);
        Glide.clear(holder.imageView);
        Glide.with(context).load(entry.getProtocolPath()).diskCacheStrategy(DiskCacheStrategy.RESULT).crossFade().into(holder.imageView);
        holder.setChosen(entry.isSelected());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasReachLimit() && !entry.isSelected()) {
                    ToastUtil.say("一次最多只能选" + SELECT_MAX_LIMIT + "张");
                    return;
                }
                entry.setSelected(!entry.isSelected());
                holder.setChosen(entry.isSelected());

            }
        });
        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null) listener.onclicked(holder.imageView, entry.getProtocolPath());
                return true;
            }
        });
        holder.imageView.toggleMask(entry.isSelected());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ImageSelectHolder extends RecyclerView.ViewHolder {

        public SquareImageView imageView;
        private ImageView indicator;

        public ImageSelectHolder(View itemView) {
            super(itemView);
            imageView = (SquareImageView) itemView.findViewById(R.id.img_view);
            indicator = (ImageView) itemView.findViewById(R.id.select_indicator);
        }

        public void setChosen(boolean selected) {
            if (selected) indicator.setImageResource(R.drawable.ic_chosen);
            else indicator.setImageBitmap(null);
            imageView.toggleMask(selected);
        }
    }

    public boolean hasReachLimit() {
        return MultiSelectFragment.filterSelected(data).size() >= SELECT_MAX_LIMIT;
    }

    public interface ItemClickListener {
        void onclicked(ImageView view, String url);
    }
}
