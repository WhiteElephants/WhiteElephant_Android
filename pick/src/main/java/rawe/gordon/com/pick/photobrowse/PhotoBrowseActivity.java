package rawe.gordon.com.pick.photobrowse;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import rawe.gordon.com.pick.R;
import rawe.gordon.com.pick.model.MediaInfo;
import rawe.gordon.com.pick.router.ActivityHelper;
import rawe.gordon.com.pick.transfer.Transfer;
import rawe.gordon.com.pick.utils.DrawableUtil;
import rawe.gordon.com.pick.view.LoadingView;

/**
 * Created by gordon on 3/6/17.
 */

public class PhotoBrowseActivity extends AppCompatActivity implements PhotoBrowseScenario.View, View.OnClickListener {

    private ImageView backImage, originalImage, chooseImage;
    private View backArea, originalArea, chooseArea;
    private TextView navigationText;
    private ViewPager viewPager;
    TextView tvSend;
    CardView sendArea;
    LoadingView loadingView;

    private PhotoBrowseScenario.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (!fetchData()) {
            finish();
            return;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_browse_detail_activity);
        presenter.start();
    }

    @Override
    public void setPresenter(PhotoBrowseScenario.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void findViews() {
        navigationText = (TextView) findViewById(R.id.nav_text);
        backArea = findViewById(R.id.back_container);
        backImage = (ImageView) findViewById(R.id.back_image);
        originalImage = (ImageView) findViewById(R.id.radio_original);
        chooseImage = (ImageView) findViewById(R.id.radio_choose);
        originalArea = findViewById(R.id.original_container);
        chooseArea = findViewById(R.id.choose_container);
        sendArea = (CardView) findViewById(R.id.send);
        tvSend = (TextView) findViewById(R.id.send_text);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        loadingView = (LoadingView) findViewById(R.id.loading_view);
    }

    private boolean fetchData() {
        try {
            PhotoBrowseModel params = (PhotoBrowseModel) Transfer.getInstance().getObject(PhotoBrowseScenario.TOKEN_PHOTOS);
            if (params == null) return false;
            new PhotoBrowsePresenter(params, this);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void addIcons() {
        backImage.setImageDrawable(DrawableUtil.decodeFromVector(getApplicationContext(), R.drawable.ic_arrow_back));
        originalImage.setImageDrawable(DrawableUtil.decodeFromVector(getApplicationContext(), R.drawable.ic_radio_button_unchecked));
        chooseImage.setImageDrawable(DrawableUtil.decodeFromVector(getApplicationContext(), R.drawable.ic_choosable));
    }

    @Override
    public void finishPage() {
        finish();
    }

    @Override
    public void renderNavText(String txt) {
        this.navigationText.setText(txt);
    }

    @Override
    public void renderChooseIcon(boolean chosen) {
        chooseImage.setImageDrawable(chosen ? DrawableUtil.decodeFromVector(getApplicationContext(),
                R.drawable.ic_chosen) : DrawableUtil.decodeFromVector(getApplicationContext(), R.drawable.ic_choosable));
    }

    @Override
    public void renderOriginalIcon(boolean isOriginal) {
        originalImage.setImageDrawable(isOriginal ? DrawableUtil.decodeFromVector(getApplicationContext(),
                R.drawable.ic_radio_button_checked) : DrawableUtil.decodeFromVector(getApplicationContext(), R.drawable.ic_radio_button_unchecked));
    }

    @Override
    public void navPage(int index) {
        viewPager.setCurrentItem(index);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void fillPager(List<MediaInfo> infos) {
        viewPager.setAdapter(new CycleBrowseAdapter(this, viewPager, infos));
    }

    @Override
    public void changeSendBtnStatus(int cur, int total) {
        if (cur == 0) {
            tvSend.setText(R.string.chat_im_photo_browse_send);
            sendArea.setCardBackgroundColor(sendArea.getResources().getColor(R.color.chat_im_photo_choose_send_green_dark));
            return;
        }
        tvSend.setText(tvSend.getResources().getString(R.string.chat_im_photo_browse_send) + "(" + cur + "/" + total + ")");
        sendArea.setCardBackgroundColor(sendArea.getResources().getColor(R.color.chat_im_photo_choose_send_green));
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
    public void bindLinks() {
        sendArea.setOnClickListener(this);
        backArea.setOnClickListener(this);
        chooseArea.setOnClickListener(this);
        originalArea.setOnClickListener(this);
        presenter.fillPagerData();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                presenter.setCurrentIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        presenter.refresh();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.choose_container) {
            presenter.toggleCurChosen();
        } else if (id == R.id.original_container) {
            presenter.toggleOriginal();
        } else if (id == R.id.back_container) {
            presenter.handleCancel();
        } else if (id == R.id.send) {
            presenter.handleSure();
        }
    }

    public static class CycleBrowseAdapter extends PagerAdapter {

        private List<View> views;
        List<MediaInfo> mediaInfos;
        ViewPager pager;
        Context context;

        public CycleBrowseAdapter(Context context, ViewPager viewPager, List<MediaInfo> infos) {
            this.views = new ArrayList<>();
            this.pager = viewPager;
            this.mediaInfos = infos;
            this.context = context;
            for (int i = 0; i < mediaInfos.size(); i++) {
                views.add(LayoutInflater.from(context).inflate(R.layout.layout_photo_view_item, pager, false));
            }
        }

        @Override
        public int getCount() {
            return mediaInfos.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView;
            final MediaInfo mediaInfo = mediaInfos.get(position);
            container.addView(itemView = views.get(position));
            ImageView icVideo = (ImageView) itemView.findViewById(R.id.video_icon);
            TextView tvDuration = (TextView) itemView.findViewById(R.id.video_size_tv);
            if (mediaInfo.mediaType == MediaInfo.VIDEO) {
                // TODO: 3/6/17
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ActivityHelper.playVideo((Activity) context, mediaInfo.getProtocaledPath());
                    }
                });
            }
            icVideo.setVisibility(mediaInfo.mediaType == MediaInfo.VIDEO ? View.VISIBLE : View.GONE);
            tvDuration.setVisibility(mediaInfo.mediaType == MediaInfo.VIDEO ? View.VISIBLE : View.GONE);
            Glide.with(context).load(mediaInfo.path)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .crossFade().into((ImageView) itemView.findViewById(R.id.photo_view));
            return itemView;
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Glide.clear(((View) object).findViewById(R.id.photo_view));
            container.removeView((View) object);
        }
    }

    @Override
    public void onBackPressed() {
        presenter.handleCancel();
    }
}
