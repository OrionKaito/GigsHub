package com.example.myfuckingpc.gigshub1;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.astuetz.PagerSlidingTabStrip;

public class PagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {
    private static int NUM_ITEMS = 3;
    private int tabIcons[] = {R.drawable.ic_home, R.drawable.ic_search, R.drawable.ic_profile2};

    public PagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return HomeFragment.newInstance(0, "Home");
            case 1:
                return SearchFragment.newInstance(1, "Personal");
            case 2:
                return PersonalFragment.newInstance(2, "Search");
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public int getPageIconResId(int position) {
        return tabIcons[position];
    }
}
