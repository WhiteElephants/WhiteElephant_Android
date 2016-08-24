package rawe.gordon.com.fruitmarketclient.views.posts.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import rawe.gordon.com.fruitmarketclient.views.posts.watch.EditTextWatcher;

/**
 * Created by gordon on 16/8/23.
 */
public class VideoViewHolder extends RecyclerView.ViewHolder {

    private EditTextWatcher watcher;

    public VideoViewHolder(View itemView, EditTextWatcher watcher) {
        super(itemView);
        this.watcher = watcher;
    }
}
