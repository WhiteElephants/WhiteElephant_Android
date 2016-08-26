package com.iknow.imageselect.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.iknow.imageselect.R;

import rawe.gordon.com.business.fragments.BaseFragment;

/**
 * Created by gordon on 16/8/26.
 */
public class MultiSelectFragments extends BaseFragment {
    private RecyclerView recyclerView;

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

    }
}
