package com.iknow.imageselect.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iknow.imageselect.R;
import com.iknow.imageselect.model.MediaInfo;
import com.iknow.imageselect.widget.PicItemCheckedView;
import com.iknow.imageselect.widget.TitleView;

import java.util.ArrayList;

/**
 * Author: Jason.Chou
 * Email: who_know_me@163.com
 * Created: 2016年04月12日 5:03 PM
 * Description:
 */
public class SingleSelectImageActivity extends AbsImageSelectActivity {

    private ArrayList<PicItemCheckedView> hasCheckedView = new ArrayList<>();
    private TextView mPreviewBtn;

    public static void startActivityForResult(Activity context) {
        context.startActivityForResult(new Intent(context, SingleSelectImageActivity.class),
                SINGLE_PIC_SELECT_REQUEST);
    }

    @Override
    protected void initTitleView(TitleView titleView) {
        titleView.setOnRightBtnClickListener(new TitleView.OnRightBtnClickListener() {
            @Override
            public void onRBtnClick(View v) {
                mTakeCameraImagePath = imageChoosePresenter.takePhotos();
            }
        });
        titleView.setOnLeftBtnClickListener(new TitleView.OnLeftBtnClickListener() {
            @Override
            public void onRBtnClick(View v) {
                imageChoosePresenter.doCancel();
            }
        });
    }

    @Override
    protected void initBottomView(View bottomView) {
        mPreviewBtn = (TextView) bottomView.findViewById(R.id.preview_btn);
        mPreviewBtn.setTextAppearance(mContext, R.style.gray_text_18_style);
        mPreviewBtn.setEnabled(false);
        mPreviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                Bundle bd = new Bundle();
                imageChoosePresenter.doPreview(hasCheckedImages);
            }
        });
    }

    @Override
    protected void onBindViewHolderToChild(MediaInfo model,
                                           ImageSelectViewHolder holder, int position) {

    }

    @Override
    protected View getRecyclerItemView(ViewGroup parentView, int position) {
        if (parentView == null) {
            parentView = new PicItemCheckedView(this, true);
        }

        try {
            PicItemCheckedView view = ((PicItemCheckedView) parentView);
            long picId = allMedias.get(position).fileId;
            if (picId < 0) {
                throw new RuntimeException("the pic id is not num");
            }

            final ImageView iv = view.getImageView();
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parentView;
    }

    @Override
    protected void onImageSelectItemClick(View view, int position) {
        if (view instanceof PicItemCheckedView) {

            PicItemCheckedView item = (PicItemCheckedView) view;
            boolean isChecked = item.isChecked();

            if (isChecked) {
                hasCheckedImages.remove(allMedias.get(position));
                item.setChecked(false);
            } else {
                resetImagesChecked();
                hasCheckedView.add(item);
                hasCheckedImages.add(allMedias.get(position));
                item.setChecked(true);
            }

            if (hasCheckedImages.size() > 0) {
                mPreviewBtn.setTextAppearance(mContext, R.style.text_18_ffffff);
                mPreviewBtn.setEnabled(true);
            } else {
                mPreviewBtn.setTextAppearance(mContext, R.style.gray_text_18_style);
                mPreviewBtn.setEnabled(false);
            }
        }
    }

    @Override
    protected void onImageItemClick(View view, int position) {

    }

    @Override
    protected void onCameraActivityResult(String path) {
        Bundle bd = new Bundle();
        hasCheckedImages.clear();
        hasCheckedImages.add(MediaInfo.buildOneImage(path));
        bd.putSerializable("ImageData", hasCheckedImages);
        Intent intent = new Intent();
        intent.putExtras(bd);
        SingleSelectImageActivity.this.setResult(RESULT_OK, intent);
        SingleSelectImageActivity.this.finish();
    }

    private void resetImagesChecked() {
        if (hasCheckedView.size() > 0) {
            hasCheckedView.get(0).setChecked(false);
            hasCheckedView.clear();
            hasCheckedImages.clear();
        }
    }
}
