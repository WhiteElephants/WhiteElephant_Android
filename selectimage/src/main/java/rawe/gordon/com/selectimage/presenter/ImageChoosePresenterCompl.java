package rawe.gordon.com.selectimage.presenter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import rawe.gordon.com.business.application.ContextHolder;
import rawe.gordon.com.selectimage.activities.AbsImageSelectActivity;
import rawe.gordon.com.selectimage.activities.BrowseDetailActivity;
import rawe.gordon.com.selectimage.model.MediaInfo;
import rawe.gordon.com.selectimage.view.IImageChooseView;

/**
 * @Author: J.Chou
 * @Email: who_know_me@163.com
 * @Created: 2016年04月06日 6:38 PM
 * @Description:
 */

public class ImageChoosePresenterCompl implements IImageChoosePresenter {

  Activity context;
  IImageChooseView imageChooseView;
  String mCameraImagePath;

  public ImageChoosePresenterCompl(Activity context, IImageChooseView chooseView) {
    this.context = context;
    this.imageChooseView = chooseView;
  }

  @Override
  public void doCancel() {
    context.finish();
  }

  @Override
  public String takePhotos() {
    try {
      final Intent intent = new Intent();
      final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/";
      final File f = new File(dir);
      if (!f.exists()) {
        f.mkdir();
      }

      mCameraImagePath = dir + DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
      final File f1 = new File(mCameraImagePath);
      final Uri uri = Uri.fromFile(f1);

      intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
      intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
      if (intent.resolveActivity(ContextHolder.getInstance().getContext().getPackageManager()) != null) {
        context.startActivityForResult(intent, AbsImageSelectActivity.PHOTO_REQUEST_CAMERA);
      }
    } catch (Throwable e) {
      e.printStackTrace();
      Toast.makeText(context, "启动相机失败,请重试", Toast.LENGTH_LONG).show();
    }

    return mCameraImagePath;
  }

  @Override public void doSendAction() {

  }

  @Override public void doPreview(ArrayList<MediaInfo> hasCheckedImages) {
    if(hasCheckedImages.isEmpty())
      return;
    BrowseDetailActivity.goToBrowseDetailActivitySelected(context,hasCheckedImages);
  }
}
