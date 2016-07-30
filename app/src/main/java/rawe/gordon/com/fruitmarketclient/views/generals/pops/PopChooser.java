package rawe.gordon.com.fruitmarketclient.views.generals.pops;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rawe.gordon.com.fruitmarketclient.R;

/**
 * Created by gordon on 2015/7/25.
 */
public class PopChooser extends Dialog implements View.OnClickListener {
    private Context context;
    private PopMenuLevel level;
    private LocationEntity locationEntity;
    private PopChooserListener listener;
    private Map<String, Map<String, List<String>>> provinces;

    public PopChooser(Context context, PopMenuLevel level, LocationEntity locationEntity, Map<String, Map<String, List<String>>> provinces) {
        super(context);
        this.context = context;
        this.level = level;
        this.locationEntity = locationEntity;
        this.provinces = provinces;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_pop_chooser);
        configureWindowFeature();
    }

    public PopChooser setListener(PopChooserListener listener) {
        this.listener = listener;
        return this;
    }

    private void configureWindowFeature() {
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        getWindow().getAttributes().windowAnimations = R.style.PopChooserAnimation;
    }

    @Override
    protected void onStart() {
        super.onStart();
        switch (level) {
            case LEVEL1:
                level1Job();
                break;
            case LEVEL2:
                level2Job();
                break;
            case LEVEL3:
                level3Job();
                break;
        }
    }

    private void level1Job() {
        ((TextView) findViewById(R.id.tv)).setText("请选择省份");
        final List<String> levelOneItems = new ArrayList<>();
        levelOneItems.addAll(provinces.keySet());
        ((ListView) findViewById(R.id.lv)).setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return levelOneItems.size();
            }

            @Override
            public String getItem(int i) {
                return levelOneItems.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                View root;
                if (view == null)
                    root = LayoutInflater.from(context).inflate(R.layout.layout_pop_item, viewGroup, false);
                else root = view;
                ((TextView) root.findViewById(R.id.center)).setText(getItem(i));
                root.setOnClickListener(PopChooser.this);
                return root;
            }
        });

    }

    private void level2Job() {
        ((TextView) findViewById(R.id.tv)).setText("请选择城市");
        final List<String> levelOneItems = new ArrayList<>();
        levelOneItems.addAll(provinces.get(locationEntity.getProvince()).keySet());
        ((ListView) findViewById(R.id.lv)).setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return levelOneItems.size();
            }

            @Override
            public String getItem(int i) {
                return levelOneItems.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                View root;
                if (view == null)
                    root = LayoutInflater.from(context).inflate(R.layout.layout_pop_item, viewGroup, false);
                else root = view;
                ((TextView) root.findViewById(R.id.center)).setText(getItem(i));
                root.setOnClickListener(PopChooser.this);
                return root;
            }
        });
    }

    private void level3Job() {
        ((TextView) findViewById(R.id.tv)).setText("请选择县份");
        final List<String> levelOneItems = new ArrayList<>();
        levelOneItems.addAll(provinces.get(locationEntity.getProvince()).get(locationEntity.getCity()));
        ((ListView) findViewById(R.id.lv)).setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return levelOneItems.size();
            }

            @Override
            public String getItem(int i) {
                return levelOneItems.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                View root;
                if (view == null)
                    root = LayoutInflater.from(context).inflate(R.layout.layout_pop_item, viewGroup, false);
                else root = view;
                ((TextView) root.findViewById(R.id.center)).setText(getItem(i));
                root.setOnClickListener(PopChooser.this);
                return root;
            }
        });

    }

    @Override
    public void onClick(View view) {
        dismiss();
        if (this.level == PopMenuLevel.LEVEL1) {
            this.locationEntity.setPrivince(((TextView) view.findViewById(R.id.center)).getText().toString());
            new PopChooser(context, PopMenuLevel.LEVEL2, this.locationEntity, this.provinces).setListener(listener).show();
        }
        if (this.level == PopMenuLevel.LEVEL2) {
            this.locationEntity.setCity(((TextView) view.findViewById(R.id.center)).getText().toString());
            new PopChooser(context, PopMenuLevel.LEVEL3, this.locationEntity, this.provinces).setListener(listener).show();
        }
        if (this.level == PopMenuLevel.LEVEL3) {
            this.locationEntity.setCounty(((TextView) view.findViewById(R.id.center)).getText().toString());
            if (listener != null) listener.finish(locationEntity);
        }
    }

    public enum PopMenuLevel {
        LEVEL1,
        LEVEL2,
        LEVEL3
    }

    public interface PopChooserListener {
        void finish(LocationEntity locationEntity);
    }
}
