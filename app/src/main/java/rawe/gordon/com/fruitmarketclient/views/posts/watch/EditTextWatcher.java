package rawe.gordon.com.fruitmarketclient.views.posts.watch;

import android.text.Editable;
import android.text.TextWatcher;

import rawe.gordon.com.fruitmarketclient.views.posts.models.Node;

/**
 * Created by gordon on 8/24/16.
 */
public class EditTextWatcher implements TextWatcher {
    Node model;

    public void setModel(Node model) {
        this.model = model;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String current = charSequence.toString();
        model.setContent(current);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
