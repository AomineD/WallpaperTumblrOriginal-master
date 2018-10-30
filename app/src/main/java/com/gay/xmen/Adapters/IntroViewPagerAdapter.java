package com.gay.xmen.Adapters;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.gay.xmen.intro.IntroFragment;

public class IntroViewPagerAdapter extends FragmentStatePagerAdapter {

private int count;

    public IntroViewPagerAdapter(FragmentManager fm, int counts){
        super(fm);

this.count = counts;
    }

    @Override
    public Fragment getItem(int position) {

        IntroFragment introFragment = new IntroFragment();

        Bundle bondol = new Bundle();

        bondol.putInt(IntroFragment.key_id, position);

        introFragment.setArguments(bondol);


        return introFragment;
    }

    @Override
    public int getCount() {
        return count;
    }
}
