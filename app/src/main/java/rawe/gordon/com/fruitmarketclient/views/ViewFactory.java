package rawe.gordon.com.fruitmarketclient.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gordon on 5/4/16.
 */
public class ViewFactory {
    public static View createView(LayoutInflater inflater, int resId, ViewGroup parent, boolean attachToParent) {
        return inflater.inflate(resId, parent, attachToParent);
    }
}
