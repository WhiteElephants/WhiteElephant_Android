package com.iknow.imageselect.fragments;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.iknow.imageselect.R;
import com.iknow.imageselect.fragments.adapters.MultiSelectAdapter;
import com.iknow.imageselect.fragments.models.ImageMediaEntry;
import com.iknow.imageselect.fragments.provider.SourceProvider;
import com.iknow.imageselect.widget.SpacesItemDecoration;

import java.util.List;

import rawe.gordon.com.business.fragments.BaseFragment;

/**
 * Created by gordon on 16/8/26.
 */
public class MultiSelectFragments extends BaseFragment {
    private RecyclerView recyclerView;
    private List<ImageMediaEntry> imageMediaEntries;

    @Override
    protected int getContentLayout() {
        return R.layout.layout_multi_select;
    }

    @Override
    protected void bindViews(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
    }

    @Override
    protected void prepareData() {
        imageMediaEntries = SourceProvider.getAllImages();
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        recyclerView.addItemDecoration(new SpacesItemDecoration(5));
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new MultiSelectAdapter(getActivity(), imageMediaEntries));
    }

    @Override
    protected boolean performShutEffect() {
        return true;
    }

    @Override
    protected String getTitle() {
        return "选择图片";
    }
}
