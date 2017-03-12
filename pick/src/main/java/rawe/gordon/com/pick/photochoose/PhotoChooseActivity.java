package rawe.gordon.com.pick.photochoose;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import rawe.gordon.com.business.bus.RxBus;
import rawe.gordon.com.pick.R;
import rawe.gordon.com.pick.event.PhotoChooseEvent;
import rawe.gordon.com.pick.helper.PreCachingLayoutManager;
import rawe.gordon.com.pick.model.MediaInfo;
import rawe.gordon.com.pick.router.ActivityHelper;
import rawe.gordon.com.pick.transfer.Transfer;
import rawe.gordon.com.pick.utils.DateUtil;
import rawe.gordon.com.pick.utils.DrawableUtil;
import rawe.gordon.com.pick.utils.PhotoFilter;
import rawe.gordon.com.pick.utils.StringUtil;
import rawe.gordon.com.pick.utils.ViewUtil;
import rawe.gordon.com.pick.view.LoadingView;
import rawe.gordon.com.pick.view.SquareImageView;
import rx.Subscription;
import rx.functions.Action1;


/**
 * Created by gordon on 3/4/17.
 */

public class PhotoChooseActivity extends AppCompatActivity implements PhotoChooseScenario.View, View.OnClickListener {

    RecyclerView rvPhotos;
    TextView tvSend;
    CardView sendArea;
    TextView tvTitle;
    View back;
    ImageView backImage;
    TextView photoFilter, photoPreview;
    View filterArea;
    ViewGroup filterList;
    LoadingView loadingView;

    PhotoChooseScenario.Presenter presenter;
    MultiSelectAdapter adapter;

    Subscription subscription;
    PhotoChooseModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null)
            model = new PhotoChooseModel((PhotoChooseScenario.Criterion) Transfer.getInstance().getObjectAndClean(PhotoChooseScenario.SELECT_CRITERION));
        else {
            String sessionId = savedInstanceState.getString(PhotoChooseScenario.ACTIVITY_RESUME);
            model = (PhotoChooseModel) Transfer.getInstance().getObjectAndClean(sessionId);
        }
        new PhotoChoosePresenter(PhotoChooseActivity.this).passParams(model);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_photochoose_act);
        presenter.findViewsAndBinkLinks();
        presenter.refreshFirstTime();
        if (savedInstanceState == null) {
            presenter.start();
        } else {
            presenter.resumeSavedInstance(model);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null) subscription.unsubscribe();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(PhotoChooseScenario.ACTIVITY_RESUME, model.criterion.uuidIdentifier);
        Transfer.getInstance().putObject(model.criterion.uuidIdentifier, model);
    }

    @Override
    public void setPresenter(PhotoChooseScenario.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void displayMedias(final List<MediaInfo> mediaInfos) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                model.passMedias(mediaInfos);
                ((PhotoChoosePresenter) presenter).passParams(model);
                rvPhotos.setAdapter(adapter = new MultiSelectAdapter(PhotoChooseActivity.this, model, new MultiSelectAdapter.ItemClickListener() {
                    @Override
                    public void onClicked(ImageView view, String url, int startIndex) {
                        presenter.gotoChoosePicturesOrVideos(PhotoChooseActivity.this, startIndex);
                    }

                    @Override
                    public void onIndicatorClicked(MediaInfo mediaInfo, int index) {
                        presenter.handleIndicator(mediaInfo);
                        presenter.changeNavText();
                    }
                }));
                presenter.changeNavText();
                presenter.fillFilterList();
                toggleLoading(false);
            }
        });
    }

    @Override
    public Context getContext() {
        return PhotoChooseActivity.this;
    }

    @Override
    public void refreshAdapter() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void finishPage() {
        finish();
    }

    @Override
    public void changeSendBtnStatus(int cur, int total) {
        if (cur == 0) {
            tvSend.setText(tvSend.getResources().getString(R.string.chat_im_send_camel));
            sendArea.setCardBackgroundColor(sendArea.getResources().getColor(R.color.chat_im_photo_choose_send_green_dark));
            return;
        }
        tvSend.setText(tvSend.getResources().getString(R.string.chat_im_send_camel) + "(" + cur + "/" + total + ")");
        sendArea.setCardBackgroundColor(sendArea.getResources().getColor(R.color.chat_im_photo_choose_send_green));
    }

    @Override
    public void browseChoosePicturesOrVideos(Activity from, List<MediaInfo> photos, int startIndex, int maxLimit, List<MediaInfo> otherSelected, String uuidIdentifier) {
        ActivityHelper.browseChoosePicturesOrVideos(PhotoChooseActivity.this, photos, startIndex, maxLimit, otherSelected, uuidIdentifier);
    }

    @Override
    public void refreshFirst(String title) {
        tvTitle.setText(title);
        photoFilter.setText(title);
    }

    @Override
    public void toggleFilter(boolean filterBlock, String filter) {
        if (filterBlock) {
            filterArea.setVisibility(View.VISIBLE);
        } else {
            filterArea.setVisibility(View.GONE);
        }
        photoFilter.setText(StringUtil.firstCamel(filter));
        tvTitle.setText(StringUtil.firstCamel(filter));
    }

    @Override
    public void refreshResources(PhotoChooseModel model) {
        rvPhotos.setAdapter(adapter = new MultiSelectAdapter(PhotoChooseActivity.this, model, new MultiSelectAdapter.ItemClickListener() {
            @Override
            public void onClicked(ImageView view, String url, int startIndex) {
                presenter.gotoChoosePicturesOrVideos(PhotoChooseActivity.this, startIndex);
            }

            @Override
            public void onIndicatorClicked(MediaInfo mediaInfo, int index) {
                presenter.handleIndicator(mediaInfo);
                presenter.changeNavText();
            }
        }));
        presenter.changeNavText();
    }

    @Override
    public void renderFilterList(List<String> filters) {
        LayoutInflater inflater = LayoutInflater.from(PhotoChooseActivity.this);
        for (final String filter : filters) {
            View view;
            filterList.addView(view = inflater.inflate(R.layout.layout_photo_choose_filter_item, filterList, false));
            ((TextView) view.findViewById(R.id.filter_title)).setText(StringUtil.firstCamel(filter));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ViewUtil.isFastDoubleClick()) return;
                    presenter.switchFilter(filter);
                    presenter.hideFilter();
                }
            });
        }
        presenter.hideFilter();
    }

    @Override
    public void toggleLoading(boolean showLoading) {
        if (showLoading) {
            loadingView.show();
        } else {
            loadingView.disMiss();
        }
    }

    @Override
    public void renderSavedInstance(List<MediaInfo> mediaInfos) {
        rvPhotos.setAdapter(adapter = new MultiSelectAdapter(PhotoChooseActivity.this, model, new MultiSelectAdapter.ItemClickListener() {
            @Override
            public void onClicked(ImageView view, String url, int startIndex) {
                presenter.gotoChoosePicturesOrVideos(PhotoChooseActivity.this, startIndex);
            }

            @Override
            public void onIndicatorClicked(MediaInfo mediaInfo, int index) {
                presenter.handleIndicator(mediaInfo);
                presenter.changeNavText();
            }
        }));
        presenter.changeNavText();
        presenter.fillFilterList();
        toggleLoading(false);
    }

    @Override
    public void findViews() {
        rvPhotos = (RecyclerView) findViewById(R.id.rv_photos);
        tvSend = (TextView) findViewById(R.id.send_text);
        tvTitle = (TextView) findViewById(R.id.nav_text);
        back = findViewById(R.id.back_container);
        sendArea = (CardView) findViewById(R.id.send);
        loadingView = (LoadingView) findViewById(R.id.loading_view);
        backImage = (ImageView) findViewById(R.id.back_image);
        filterList = (ViewGroup) findViewById(R.id.filter_list);
        filterArea = findViewById(R.id.filter_area);
        photoFilter = (TextView) findViewById(R.id.photo_filter);
        photoPreview = (TextView) findViewById(R.id.photo_preview);
        backImage.setImageDrawable(DrawableUtil.decodeFromVector(getApplicationContext(), R.drawable.ic_arrow_back));
    }

    @Override
    public void bindViews() {
        PreCachingLayoutManager manager = new PreCachingLayoutManager(this, PhotoChooseScenario.Criterion.COLUMN_COUNT);
        manager.setExtraLayoutSpace(1400);
        back.setOnClickListener(this);
        sendArea.setOnClickListener(this);
        photoPreview.setOnClickListener(this);
        photoFilter.setOnClickListener(this);
        filterArea.setOnClickListener(this);
        rvPhotos.setLayoutManager(manager);
        subscription = RxBus.getDefault().toObserverable(PhotoChooseEvent.class).subscribe(new Action1<PhotoChooseEvent>() {
            @Override
            public void call(PhotoChooseEvent photoChooseEvent) {
                if (photoChooseEvent.whichToWhich != PhotoChooseEvent.TO_LAST_PAGE ||
                        !TextUtils.equals(photoChooseEvent.uuidIdentifier, model.criterion.uuidIdentifier))
                    return;
                switch (photoChooseEvent.intentType) {
                    case PhotoChooseEvent.CANCEL:
                        presenter.refreshWithBrowseResult(photoChooseEvent.mediaInfos);
                        break;
                    case PhotoChooseEvent.DONE:
                        presenter.deliverResultAndFinish(photoChooseEvent.mediaInfos);
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back_container) {
            presenter.handleCancel();
        } else if (view.getId() == R.id.send) {
            presenter.handleDone();
        } else if (view.getId() == R.id.photo_filter) {
            presenter.toggleFilter();
        } else if (view.getId() == R.id.photo_preview) {
            presenter.handlePreview();
        } else if (view.getId() == R.id.filter_area) {
            presenter.hideFilter();
        }
    }

    /**
     * Created by gordon on 16/8/26.
     */
    public static class MultiSelectAdapter extends RecyclerView.Adapter<MultiSelectAdapter.ImageSelectHolder> {

        List<MediaInfo> data;
        private LayoutInflater inflater;
        private Context context;
        private ItemClickListener listener;
        private List<MediaInfo> diff;
        private PhotoChooseScenario.Criterion criterion;

        public MultiSelectAdapter(Context context, PhotoChooseModel model, ItemClickListener listener) {
            this.data = model.getWorkingGroup();
            this.listener = listener;
            this.context = context;
            this.diff = model.getDiff();
            this.criterion = model.criterion;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public ImageSelectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ImageSelectHolder(inflater.inflate(R.layout.layout_select_item, parent, false));
        }

        @Override
        public void onBindViewHolder(final ImageSelectHolder holder, final int position) {
            final MediaInfo entry = data.get(position);
            Glide.clear(holder.imageView);
            Glide.with(context).load(entry.path)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT).crossFade().into(holder.imageView);
            holder.setChosen(entry.isSelected);
            holder.indicator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (hasReachLimit() && !entry.isSelected) {
                        Toast.makeText(context, context.getResources().
                                getString(R.string.chat_im_chat_one_time_max_prefix) + criterion.maxLimit +
                                context.getResources().getString(R.string.chat_im_chat_one_time_max_suffix), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    /**
                     * 将数据传出去，在外面更新
                     * */
                    if (listener != null) listener.onIndicatorClicked(entry, position);
                    holder.setChosen(entry.isSelected);
                }
            });
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                        listener.onClicked(holder.imageView, entry.path, position);
                }
            });
            if (entry.mediaType == MediaInfo.VIDEO) {
                holder.tvDuration.setText(DateUtil.transferLongToDate(DateUtil.VIDEO_DURATION, entry.duration));
                holder.tvDuration.setVisibility(View.VISIBLE);
            } else {
                holder.tvDuration.setVisibility(View.GONE);
            }
            if (PhotoChooseScenario.Criterion.showMask)
                holder.imageView.toggleMask(entry.isSelected);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ImageSelectHolder extends RecyclerView.ViewHolder {

            public SquareImageView imageView;
            private ImageView indicator;
            private TextView tvDuration;

            public ImageSelectHolder(View itemView) {
                super(itemView);
                imageView = (SquareImageView) itemView.findViewById(R.id.img_view);
                indicator = (ImageView) itemView.findViewById(R.id.select_indicator);
                tvDuration = (TextView) itemView.findViewById(R.id.video_duration);
            }

            public void setChosen(boolean selected) {
                if (selected) indicator.setImageResource(R.drawable.ic_chosen);
                else indicator.setImageResource(R.drawable.ic_choosable);
                if (PhotoChooseScenario.Criterion.showMask) imageView.toggleMask(selected);
            }
        }

        public boolean hasReachLimit() {
            return PhotoFilter.filterSelected(data).size() + diff.size() >= criterion.maxLimit;
        }

        public interface ItemClickListener {
            void onClicked(ImageView view, String url, int index);

            void onIndicatorClicked(MediaInfo mediaInfo, int index);
        }
    }
}
