package rawe.gordon.com.fruitmarketclient.generals.dialogs.addcart;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rawe.gordon.com.business.utils.AnimatorUtil;
import rawe.gordon.com.business.utils.ToastUtil;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.fruitmarketclient.generals.dialogs.base.AbstractDialog;

/**
 * Created by gordon on 5/17/16.
 */
public class AddToCartChooseDialog extends AbstractDialog {

    public final static String UNSELECTED = "UNSELECTED";
    GridLayout colorGrid, sizeGrid;
    int COLOR_COUNT = 6;
    int SIZE_COUNT = 6;
    String[] colors = {"#1ABC9C", "#2ECC71", "#3498DB", "#9B59B6", "#34495E", "#F1C40F", "#E67E22", "#E74C3C", "#ECF0F1"};
    String[] sizes = {"XXS", "XS", "S", "M", "L", "XL", "XXL", "3XL"};
    private String selectedSize = UNSELECTED, selectedColor = UNSELECTED;
    private int count = 1;

    private List<View> colorViews, sizeViews;

    public static AddToCartChooseDialog newInstance(StateListener listener) {
        AddToCartChooseDialog frag = new AddToCartChooseDialog();
        frag.setListener(listener);
        return frag;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.layout_add_to_cart_choose;
    }

    @Override
    protected void bindViews(final View rootView) {
        colorGrid = (GridLayout) rootView.findViewById(R.id.color_grids);
        sizeGrid = (GridLayout) rootView.findViewById(R.id.size_grids);
        rootView.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedColor.equals(UNSELECTED) || selectedSize.equals(UNSELECTED)) {
                    ToastUtil.say("required params is not choosed");
                    AnimatorUtil.playLeftRightBlinkAnimation(rootView.findViewById(R.id.title));
                    return;
                }
                ToastUtil.say(getState());
                if (listener != null) listener.onStateChanged(selectedColor, selectedSize, 1);
                dismiss();
            }
        });
    }

    @Override
    protected void prepareData() {
        addColorGrids();
        addSizeGrids();
    }

    @Override
    protected void onContainerClicked() {

    }

    @Override
    protected boolean getTouchOutSideMode() {
        return false;
    }

    private void addColorGrids() {
        colorViews = new ArrayList<>();
        for (final String color : colors) {
            final View view;
            colorGrid.addView(view = LayoutInflater.from(getContext()).inflate(R.layout.layout_color_item, colorGrid, false));
            view.findViewById(R.id.color_rect).setBackgroundColor(Color.parseColor(color));
            final View mask = view.findViewById(R.id.color_mask);
            view.findViewById(R.id.color_rect).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mask.getVisibility() == View.GONE) {
                        mask.setVisibility(View.VISIBLE);
                        addColor(color, mask);
                    } else {
                        mask.setVisibility(View.GONE);
                        removeColor();
                    }
                }
            });
            colorViews.add(view);
        }
        colorGrid.post(new Runnable() {
            @Override
            public void run() {
                for (View view : colorViews) {
                    view.getLayoutParams().width = colorGrid.getWidth() / COLOR_COUNT;
                    view.requestLayout();
                }
            }
        });
    }

    private void addSizeGrids() {
        sizeViews = new ArrayList<>();
        for (final String size : sizes) {
            final View view;
            sizeGrid.addView(view = LayoutInflater.from(getContext()).inflate(R.layout.layout_size_item, sizeGrid, false));
            ((TextView) view.findViewById(R.id.size_text)).setText(size);
            final View mask = view.findViewById(R.id.size_mask);
            view.findViewById(R.id.size_text).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mask.getVisibility() == View.GONE) {
                        mask.setVisibility(View.VISIBLE);
                        addSize(size, mask);
                    } else {
                        mask.setVisibility(View.GONE);
                        removeSize();
                    }
                }
            });
            sizeViews.add(view);
        }
        sizeGrid.post(new Runnable() {
            @Override
            public void run() {
                for (View view : sizeViews) {
                    view.getLayoutParams().width = sizeGrid.getWidth() / SIZE_COUNT;
                    view.requestLayout();
                }
            }
        });
    }

    private void addColor(String color, View view) {
        selectedColor = color;
        for (View v : colorViews) {
            v.findViewById(R.id.color_mask).setVisibility(View.GONE);
        }
        view.findViewById(R.id.color_mask).setVisibility(View.VISIBLE);
    }

    private void addSize(String size, View view) {
        selectedSize = size;
        for (View v : sizeViews) {
            v.findViewById(R.id.size_mask).setVisibility(View.GONE);
        }
        view.findViewById(R.id.size_mask).setVisibility(View.VISIBLE);
    }

    private void removeColor() {
        selectedColor = UNSELECTED;
    }

    private void removeSize() {
        selectedSize = UNSELECTED;
    }

    private String getState() {
        return this.selectedColor + this.selectedSize;
    }

    private StateListener listener;

    public void setListener(StateListener listener) {
        this.listener = listener;
    }

    public interface StateListener {
        void onStateChanged(String color, String size, int count);
    }
}
