package com.takhir.rssreader.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.takhir.rssreader.CustomFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = "ViewPagerAdapter";

    private List<Fragment> childFragments;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        childFragments = new ArrayList<>();
        childFragments.add(new CustomFragment());
    }

    public void addFragment(CustomFragment fragment) {
        //childFragments.add(fragment);
        //notifyDataSetChanged();
        //Log.d(TAG, "Added");
        //Log.d(TAG, "Size: " + childFragments.size());
    }

    @Override
    public Fragment getItem(int position) {
        return childFragments.get(position);
    }

    @Override
    public int getCount() {
        return childFragments.size(); //3 items
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = getItem(position).getClass().getName();
        return title.subSequence(title.lastIndexOf(".") + 1, title.length());
    }

}
