package rawe.gordon.com.fruitmarketclient.old.views.preview.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.compose.models.TextNode;

/**
 * Created by gordon on 16/8/28.
 */
public class TextHolder extends RecyclerView.ViewHolder {

    public TextView textView;

    public TextHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.text_view);
    }

    public void bindValue(TextNode model) {
        textView.setText(model.getContent());
    }
}
