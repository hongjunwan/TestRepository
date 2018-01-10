package com.example.student.a20180108;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by student on 2018-01-08.
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter{
    private int tabCount;

    public TabPagerAdapter(FragmentManager fm, int tabCount){
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position){
        switch (position){
            case 0:
                BookFragment bookFragment = new BookFragment();
                return bookFragment;
            case 1:
                NewsFragment newsFragment = new NewsFragment();
                return newsFragment;
            case 2:
                BlogFragment blogFragment = new BlogFragment();
                return blogFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
