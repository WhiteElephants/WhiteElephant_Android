package rawe.gordon.com.fruitmarketclient.generals.dialogs.base;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import rawe.gordon.com.business.R;


/**
 * Created by gordon on 16/5/13.
 */
public abstract class AbstractDialog extends DialogFragment implements View.OnClickListener {
    protected View world;
    private RelativeLayout rootView;

    public AbstractDialog() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        world = inflater.inflate(R.layout.layout_abs_dialog, container, false);
        rootView = (RelativeLayout) world.findViewById(R.id.container);
        rootView.setOnClickListener(this);
        rootView.addView(inflater.inflate(getContentLayout(), rootView, false));
        getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        bindViews(rootView);
        prepareData();
        setCancelable(getTouchOutSideMode());
        return world;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.container)
            onContainerClicked();
    }

    protected abstract int getContentLayout();

    protected abstract void bindViews(View rootView);

    protected abstract void prepareData();

    protected abstract void onContainerClicked();

    protected abstract boolean getTouchOutSideMode();
}