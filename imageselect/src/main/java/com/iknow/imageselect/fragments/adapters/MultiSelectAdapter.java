package com.iknow.imageselect.fragments.adapters;

import android.content.Context;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iknow.imageselect.R;
import com.iknow.imageselect.display.DisplayOptions;
import com.iknow.imageselect.fragments.models.ImageMediaEntry;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import rawe.gordon.com.business.imageloader.SquareImageView;

/**
 * Created by gordon on 16/8/26.
 */
public class MultiSelectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<ImageMediaEntry> data;
    private LayoutInflater inflater;

    public MultiSelectAdapter(Context context, List<ImageMediaEntry> src) {
        this.data = src;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageSelectHolder(inflater.inflate(R.layout.image_select_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageLoader.getInstance().displayImage(data.get(position).getProtocaledPath(), ((ImageSelectHolder) holder).imageView, DisplayOptions.getCacheFadeOptions());
//        ((ImageSelectHolder) holder).imageView.setImageBitmap(ThumbnailUtils.createVideoThumbnail(data.get(position).getStoragePath(), MediaStore.Images.Thumbnails.MICRO_KIND));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ImageSelectHolder extends RecyclerView.ViewHolder {

        public SquareImageView imageView;

        public ImageSelectHolder(View itemView) {
            super(itemView);
            imageView = (SquareImageView) itemView.findViewById(R.id.img_view);
        }
    }
}
