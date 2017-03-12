package rawe.gordon.com.fruitmarketclient.compose.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import rawe.gordon.com.fruitmarketclient.R;

/**
 * Created by gordon on 16/8/23.
 */
public class GroupImageItemHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public View imageContainer;

    public GroupImageItemHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.image_view);
        imageContainer = itemView.findViewById(R.id.image_container);
    }
}
