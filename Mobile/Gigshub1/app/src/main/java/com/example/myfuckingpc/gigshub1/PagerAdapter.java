package com.example.myfuckingpc.gigshub1;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.astuetz.PagerSlidingTabStrip;

public class PagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {
    private static int NUM_ITEMS = 4;
    private int tabIcons[] = {R.drawable.ic_home, R.drawable.ic_search, R.drawable.ic_add_event, R.drawable.ic_personal};

    public PagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return HomeFragment.newInstance(0, "Home");
            case 1:
                return SearchFragment.newInstance(1, "Search");
            case 2:
                return CreateFragment.newInstance(2, "Create");
            case 3:
                return PersonalFragment.newInstance(3, "Personal");
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
