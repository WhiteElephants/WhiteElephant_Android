package rawe.gordon.com.fruitmarketclient.views.generals.dialogs.filter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tonicartos.widget.stickygridheaders.StickyGridHeadersBaseAdapter;
import com.tonicartos.widget.stickygridheaders.StickyGridHeadersGridView;

import java.util.ArrayList;
import java.util.List;

import rawe.gordon.com.business.utils.CacheBean;
import rawe.gordon.com.business.utils.ToastUtil;
import rawe.gordon.com.fruitmarketclient.R;
import rawe.gordon.com.business.network.RestClient;
import rawe.gordon.com.business.network.responses.CategoriesResponse;
import rawe.gordon.com.business.network.responses.pojo.CategoryModel;
import rawe.gordon.com.business.network.responses.pojo.CategorySubModel;
import rawe.gordon.com.fruitmarketclient.views.generals.dialogs.base.AbstractDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gordon on 16/5/15.
 */
public class CategorySelectDialog extends AbstractDialog {

    private View ok;
    private StickyGridHeadersGridView gridHeadersGridView;
    private List<CategoryModel> categories;
    private List<String> selectedCategories = new ArrayList<>();
    private SelectResultListener listener;

    public static CategorySelectDialog newInstance(SelectResultListener listener, List<String> selectedCategories) {
        CategorySelectDialog frag = new CategorySelectDialog();
        frag.setListener(listener);
        frag.setSelectedCategories(selectedCategories);
        return frag;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.layout_category_select;
    }

    @Override
    protected void bindViews(View rootView) {
        ok = rootView.findViewById(R.id.ok);
        gridHeadersGridView = (StickyGridHeadersGridView) rootView.findViewById(R.id.grid_view);
    }

    @Override
    protected void prepareData() {
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSelected(selectedCategories);
                dismiss();
            }
        });
        fetchCategories();
    }

    private void fetchCategories() {
        RestClient.getInstance().getCategories(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                categories = response.body().getCategories();
                addGrids();
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable t) {
                ToastUtil.say("fetch categories failed...");
            }
        });
    }

    private void addGrids() {
        gridHeadersGridView.setAdapter(new PinnableAdapter(getContext(), categories));
    }

    @Override
    protected void onContainerClicked() {

    }

    @Override
    protected boolean getTouchOutSideMode() {
        return false;
    }


    class PinnableAdapter extends BaseAdapter implements StickyGridHeadersBaseAdapter {
        private LayoutInflater inflater;
        private List<CategoryModel> models;
        private List<CategorySubModel> subModels = new ArrayList<>();

        public PinnableAdapter(Context context, List<CategoryModel> models) {
            inflater = LayoutInflater.from(context);
            this.models = models;
            for (CategoryModel model : models) {
                for (CategorySubModel subModel : model.getSubTypes()) {
                    subModels.add(subModel);
                }
            }
        }

        @Override
        public int getCountForHeader(int header) {
            return models.get(header).getSubTypes().size();
        }

        @Override
        public int getNumHeaders() {
            return models.size();
        }

        @Override
        public View getHeaderView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.layout_category_header, parent, false);
            }
            ((TextView) convertView.findViewById(R.id.text1)).setText(models.get(position).getCategory());
            return convertView;
        }

        @Override
        public int getCount() {
            return subModels.size();
        }

        @Override
        public CategorySubModel getItem(int i) {
            return subModels.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = inflater.inflate(R.layout.layout_category_item, viewGroup, false);
            }
            ((TextView) view.findViewById(R.id.text1)).setText(getItem(i).getTile());
            ((SimpleDraweeView) view.findViewById(R.id.drawee)).setImageURI(Uri.parse(getItem(i).getLogo()));
            final View mask = view.findViewById(R.id.select_mask);
            mask.setVisibility(selectedCategories.contains(getItem(i).getTile()) ? View.VISIBLE : View.GONE);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (mask.getVisibility() == View.VISIBLE) {
                        mask.setVisibility(View.GONE);
                        removeCategory(getItem(i).getTile());
                    } else {
                        addCategory(getItem(i).getTile());
                        mask.setVisibility(View.VISIBLE);
                    }
                }
            });

            return view;
        }
    }

    private void addCategory(String category) {
        if (selectedCategories.contains(category)) return;
        selectedCategories.add(category);
    }

    private void removeCategory(String category) {
        selectedCategories.remove(category);
    }

    private void setListener(SelectResultListener listener) {
        this.listener = listener;
    }

    private void setSelectedCategories(List<String> selectedCategories) {
        this.selectedCategories = selectedCategories;
    }

    public interface SelectResultListener {
        void onSelected(List<String> selects);
    }
}
