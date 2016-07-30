package rawe.gordon.com.business.application;

import android.content.Context;

/**
 * Created by gordon on 5/6/16.
 */
public class ContextHolder {
    private static ContextHolder ourInstance = new ContextHolder();

    public static ContextHolder getInstance() {
        return ourInstance;
    }

    private ContextHolder() {
    }

    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
