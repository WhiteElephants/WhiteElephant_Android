package rawe.gordon.com.fruitmarketclient.old.views.preview.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.old.views.preview.widgets.AspectImageView;

/**
 * Created by gordon on 16/8/28.
 */
public class ImageHolder extends RecyclerView.ViewHolder {

    public AspectImageView aspectImageView;

    public ImageHolder(View itemView) {
        super(itemView);
        aspectImageView = (AspectImageView) itemView.findViewById(R.id.aspect_image_view);
    }
}
