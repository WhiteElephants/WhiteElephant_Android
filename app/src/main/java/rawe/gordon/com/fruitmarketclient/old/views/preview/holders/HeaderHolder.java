package rawe.gordon.com.fruitmarketclient.old.views.preview.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.compose.models.HeaderNode;

/**
 * Created by gordon on 16/8/28.
 */
public class HeaderHolder extends RecyclerView.ViewHolder {
    public TextView titleView;

    public HeaderHolder(View itemView) {
        super(itemView);
        titleView = (TextView) itemView.findViewById(R.id.text_view);
    }

    public void bindValue(HeaderNode model) {
        titleView.setText(model.getContent());
    }
}
