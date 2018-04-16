package com.example.askar.KanatTole;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Kanat on 11.04.2018.
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }



    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new Fragment1();
        } else if (position == 1){
            return new Fragment2();
        }
        else if (position == 2){
            return new Fragment3();
        }
        else
            return null;
    }

    @Override
    public int getCount() {
        return 3;
    }


}
