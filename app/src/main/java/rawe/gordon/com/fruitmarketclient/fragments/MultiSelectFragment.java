package rawe.gordon.com.fruitmarketclient.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.iknow.imageselect.R;
import com.iknow.imageselect.fragments.models.ImageMediaEntry;
import com.iknow.imageselect.fragments.provider.SourceProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rawe.gordon.com.business.activities.SitoImageViewActivity;
import rawe.gordon.com.business.activities.TransparentBoxActivity;
import rawe.gordon.com.business.fragments.BaseFragment;
import rawe.gordon.com.business.utils.CacheBean;
import rawe.gordon.com.business.utils.ToastUtil;
import rawe.gordon.com.fruitmarketclient.fragments.posts.PostComposeFragment;

/**
 * Created by gordon on 16/8/26.
 */
public class MultiSelectFragment extends BaseFragment {
    public static final int INTENTION_TO_POST = 0;
    public static final int INTENTION_TO_CHOOSE = 1;
    private int intention = INTENTION_TO_POST;
    public static final String KEY_INTENTION_TO_POST = "KEY_INTENTION_TO_POST";
    public static final String KEY_ALLOW_EMPTY = "KEY_ALLOW_EMPTY";

    private RecyclerView recyclerView;
    private View takeCamera;
    private List<ImageMediaEntry> imageMediaEntries;
    private boolean allowEmpty = true;
    MultiSelectAdapter adapter;

    @Override
    protected int getContentLayout() {
        return R.layout.layout_multi_select;
    }

    @Override
    protected void bindViews(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        takeCamera = rootView.findViewById(R.id.take_photo);
    }

    private void handleCacheBean() {
        Object intentionValue = CacheBean.getParam(KEY_INTENTION_TO_POST, KEY_INTENTION_TO_POST);
        Object allowValue = CacheBean.getParam(KEY_ALLOW_EMPTY, KEY_ALLOW_EMPTY);
        Object listenerValue = CacheBean.getParam(PostComposeFragment.KEY_RESULT_LISTENER, PostComposeFragment.KEY_RESULT_LISTENER);
        CacheBean.clean(KEY_INTENTION_TO_POST);
        CacheBean.clean(KEY_ALLOW_EMPTY);
        CacheBean.clean(PostComposeFragment.KEY_RESULT_LISTENER);
        intention = intentionValue == null ? INTENTION_TO_POST : (int) intentionValue;
        allowEmpty = allowValue == null ? false : (boolean) allowValue;
        if (listenerValue != null) listener = (ResultListener) listenerValue;
    }

    @Override
    protected void prepareData() {
        handleCacheBean();
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(manager);
        imageMediaEntries = SourceProvider.getAllImages();
        recyclerView.setAdapter(adapter = new MultiSelectAdapter(getActivity(), imageMediaEntries, new MultiSelectAdapter.ItemClickListener() {
            @Override
            public void onclicked(ImageView view, String url) {
                int[] coord = new int[2];
                view.getLocationOnScreen(coord);
                SitoImageViewActivity.ImageModel model = new SitoImageViewActivity.ImageModel(url, coord[0], coord[1], view.getWidth(), view.getHeight());
                SitoImageViewActivity.goToSitoImageBrowsePage(getActivity(), model);
            }
        }));

        takeCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }

    private void reloadPictures(String protocolUrl) {
        ImageMediaEntry entry = new ImageMediaEntry();
        entry.setStoragePath(protocolUrl.substring(5));
        entry.setSelected(true);
        imageMediaEntries.add(0, entry);
        adapter.notifyItemInserted(0);
    }

    @Override
    protected boolean performShutEffect() {
        return true;
    }

    @Override
    protected String getTitle() {
        return "选择图片";
    }

    @Override
    protected String getRightText() {
        return "确定";
    }

    @Override
    protected void onRightTextClicked() {
        if (!allowEmpty && filterSelected(imageMediaEntries).size() == 0) {
            ToastUtil.say("至少选选一张图片才行");
            return;
        }
        if (intention == INTENTION_TO_POST) {
            PostComposeFragment.startWithContainer(getActivity());
            CacheBean.putParam(KEY_INTENTION_TO_POST, KEY_INTENTION_TO_POST, filterSelected(imageMediaEntries));
        } else {
            if (listener != null) listener.onResult(filterSelected(imageMediaEntries));
        }
        closeWithAnimation(new Callback() {
            @Override
            public void onAnimationFinish() {
                getActivity().finish();
            }
        });

    }

    @Override
    protected int getLeftDrawable() {
        return R.drawable.ic_arrow_back;
    }

    @Override
    protected void onLeftIconClicked() {
        closeWithAnimation(new Callback() {
            @Override
            public void onAnimationFinish() {
                getActivity().finish();
            }
        });
    }

    public List<ImageMediaEntry> filterSelected(List<ImageMediaEntry> src) {
        List<ImageMediaEntry> retValues = new ArrayList<>();
        for (ImageMediaEntry entry : src) {
            if (entry.isSelected()) retValues.add(entry);
        }
        return retValues;
    }

    public static void startWithBoxActivity(Activity from, int intent, boolean allowEmpty) {
        if (from == null || from.isFinishing()) return;
        CacheBean.putParam(MultiSelectFragment.KEY_INTENTION_TO_POST, MultiSelectFragment.KEY_INTENTION_TO_POST, intent);
        CacheBean.putParam(MultiSelectFragment.KEY_ALLOW_EMPTY, MultiSelectFragment.KEY_ALLOW_EMPTY, allowEmpty);
        TransparentBoxActivity.startFragmentInside(from, MultiSelectFragment.class);
    }

    private ResultListener listener;

    public interface ResultListener {
        void onResult(List<ImageMediaEntry> selected);
    }


    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getActivity(), "rawe.gordon.com.fruitmarketclient.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath.substring(5));
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            galleryAddPic();
            reloadPictures(mCurrentPhotoPath);
        }
    }
}
