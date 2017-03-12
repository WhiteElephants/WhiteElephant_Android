package rawe.gordon.com.fruitmarketclient.old.fragments.edits;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tonicartos.widget.stickygridheaders.StickyGridHeadersBaseAdapter;
import com.tonicartos.widget.stickygridheaders.StickyGridHeadersGridView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.business.fragments.EditBaseFragment;
import rawe.gordon.com.business.utils.CacheBean;

/**
 * Created by gordon on 5/25/16.
 */
public class EditDistrictFragment extends EditBaseFragment implements View.OnClickListener {
    public static final String EDIT_DISTRICT_FRAGMENT = "EDIT_DISTRICT_FRAGMENT";
    public static final int EDIT_DISTRICT_CODE = 0x9988;
    private Toolbar toolbar;
    private String district;
    private StickyGridHeadersGridView gridHeadersGridView;
    private String[] districts;
    private int currentIndex = -1;
    private CardView lastView;

    @Override
    protected int getContentLayout() {
        return R.layout.layout_edit_district_fragment;
    }

    @Override
    protected void bindViews(View rootView) {
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        gridHeadersGridView = (StickyGridHeadersGridView) rootView.findViewById(R.id.grid_view);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == EDIT_DISTRICT_CODE) {
                districts = (String[]) msg.obj;
                for (int i = 0; i < districts.length; i++) {
                    if (districts[i].equals(getDistrict())) {
                        currentIndex = i;
                    }
                }
            }
            gridHeadersGridView.setAdapter(new PinnableAdapter(getContext()));
        }
    };

    @Override
    protected void prepareData() {
        toolbar.setTitle("Edit District");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((BaseActivity) getActivity()).removeFragment(EditDistrictFragment.this);
            }
        });
        setDistrict((String) CacheBean.getParam(EDIT_DISTRICT_FRAGMENT, EDIT_DISTRICT_FRAGMENT));
        CacheBean.clean(EDIT_DISTRICT_FRAGMENT);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.obj = getResources().getStringArray(R.array.countries);
                message.what = EDIT_DISTRICT_CODE;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendMessage(message);
            }
        }).start();
    }

    @Override
    public void emitSignals() {

    }

    @Override
    protected int getOkDrawable() {
        return R.drawable.ic_correct_grey;
    }

    @Override
    protected void onOkClicked() {
        if (listener != null) listener.onSelected(getDistrict());
//        ((BaseActivity) getActivity()).removeFragment(EditDistrictFragment.this);
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
        changeOKIcon(R.drawable.ic_correct_white);
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public CardView getLastView() {
        return lastView;
    }

    public void setLastView(CardView lastView) {
        this.lastView = lastView;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public static EditDistrictFragment newInstance(String district, ResultListener listener) {
        EditDistrictFragment editDistrictFragment = new EditDistrictFragment();
        editDistrictFragment.setListener(listener);
        CacheBean.putParam(EDIT_DISTRICT_FRAGMENT, EDIT_DISTRICT_FRAGMENT, district);
        return editDistrictFragment;
    }

    private ResultListener listener;

    public void setListener(ResultListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {

    }

    public interface ResultListener {
        void onSelected(String district);
    }

    interface StateChangeListener {
        void onChange(int index, String content, CardView cardView);

        void onAsign(int index, CardView cardView);
    }

    private StateChangeListener stateChangeListener = new StateChangeListener() {
        @Override
        public void onChange(int index, String content, CardView cardView) {
            if (getLastView() == null || getLastView()==cardView) {
                cardView.setCardBackgroundColor(getResources().getColor(R.color.okColor));
            } else {
                getLastView().setCardBackgroundColor(getResources().getColor(R.color.iconOrange));
                cardView.setCardBackgroundColor(getResources().getColor(R.color.okColor));
            }
            setDistrict(content);
            setCurrentIndex(index);
            setLastView(cardView);
        }

        @Override
        public void onAsign(int index, CardView cardView) {
            if (index == currentIndex) {
                cardView.setCardBackgroundColor(getResources().getColor(R.color.okColor));
                setLastView(cardView);
            } else {
                cardView.setCardBackgroundColor(getResources().getColor(R.color.iconOrange));
            }
        }
    };

    class PinnableAdapter extends BaseAdapter implements StickyGridHeadersBaseAdapter {
        private LayoutInflater inflater;
        private Map<String, List<String>> categories;
        private List<String> keys = new ArrayList<>();

        public PinnableAdapter(Context context) {
            inflater = LayoutInflater.from(context);
            districts = getResources().getStringArray(R.array.countries);
            categories = new LinkedHashMap<>();
            for (String district : districts) {
                String startLetter = district.substring(0, 1);
                if (categories.get(startLetter) == null) {
                    List<String> group = new ArrayList<>();
                    group.add(district);
                    categories.put(startLetter, group);
                } else {
                    categories.get(startLetter).add(district);
                }
            }
            keys.addAll(categories.keySet());
        }

        @Override
        public int getCountForHeader(int header) {
            return categories.get(keys.get(header)).size();
        }

        @Override
        public int getNumHeaders() {
            return categories.size();
        }

        @Override
        public View getHeaderView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.layout_district_header, parent, false);
            }
            ((TextView) convertView.findViewById(R.id.text1)).setText(keys.get(position));
            return convertView;
        }

        @Override
        public int getCount() {
            return districts.length;
        }

        @Override
        public String getItem(int i) {
            return districts[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = inflater.inflate(R.layout.layout_district_item, viewGroup, false);
            }
            final CardView cardView = (CardView) view.findViewById(R.id.card);
            stateChangeListener.onAsign(i, cardView);
            ((TextView) view.findViewById(R.id.text1)).setText(districts[i]);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    stateChangeListener.onChange(i, districts[i], cardView);
                }
            });
            return view;
        }
    }
}
