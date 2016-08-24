package rawe.gordon.com.fruitmarketclient.views.posts.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.views.posts.models.TextNode;
import rawe.gordon.com.fruitmarketclient.views.posts.watch.EditTextWatcher;

/**
 * Created by gordon on 16/8/23.
 */
public class TextViewHolder extends RecyclerView.ViewHolder {

    private EditText editText;
    private TextNode model;
    public EditTextWatcher watcher;

    public TextViewHolder(View itemView,EditTextWatcher watcher) {
        super(itemView);
        this.watcher = watcher;
        editText = (EditText) itemView.findViewById(R.id.input);
        editText.addTextChangedListener(this.watcher);
    }

    private void setText(String text) {
        editText.setText(text);
    }

    public void bindValue(TextNode model) {
        this.model = model;
        setText(this.model.getContent());
    }
}
