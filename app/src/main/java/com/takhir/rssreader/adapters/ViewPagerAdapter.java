package com.takhir.rssreader.adapters;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.takhir.rssreader.CustomFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = "ViewPagerAdapter";

    private List<Fragment> childFragments;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        childFragments = new ArrayList<>();
    }

    public void addFragment(CustomFragment fragment) {
        childFragments.add(fragment);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        if (childFragments.size() == 0) {
            return null;
        }
        return childFragments.get(position);
    }

    @Override
    public int getCount() {
        return childFragments.size();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    public void remove(int position){
        if (childFragments.size() != 0 && position < childFragments.size()) {
            childFragments.remove(position);
            notifyDataSetChanged();
        }

    }
}
