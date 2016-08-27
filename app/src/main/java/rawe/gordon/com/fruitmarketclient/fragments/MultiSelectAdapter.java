package rawe.gordon.com.fruitmarketclient.fragments;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.iknow.imageselect.R;
import com.iknow.imageselect.display.DisplayOptions;
import com.iknow.imageselect.fragments.models.ImageMediaEntry;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import rawe.gordon.com.business.imageloader.SquareImageView;

/**
 * Created by gordon on 16/8/26.
 */
public class MultiSelectAdapter extends RecyclerView.Adapter<MultiSelectAdapter.ImageSelectHolder> {

    List<ImageMediaEntry> data;
    private LayoutInflater inflater;
    private Context context;
    private ItemClickListener listener;

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
        ImageLoader.getInstance().displayImage(entry.getProtocolPath(), holder.imageView, DisplayOptions.getCacheFadeLowerQualityOptions());
        holder.setChosen(entry.isSelected());
        holder.indicatorArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entry.setSelected(!entry.isSelected());
                holder.setChosen(entry.isSelected());
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onclicked(holder.imageView, entry.getProtocolPath());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ImageSelectHolder extends RecyclerView.ViewHolder {

        public SquareImageView imageView;
        private ImageView indicator;
        private View indicatorArea;

        public ImageSelectHolder(View itemView) {
            super(itemView);
            imageView = (SquareImageView) itemView.findViewById(R.id.img_view);
            indicator = (ImageView) itemView.findViewById(R.id.select_indicator);
            indicatorArea = itemView.findViewById(R.id.select_indicator_area);
        }

        public void setChosen(boolean selected) {
            indicator.setImageResource(selected ? R.drawable.ic_chosen : R.drawable.ic_choosable);
        }
    }

    public interface ItemClickListener {
        void onclicked(ImageView view, String url);
    }
}
