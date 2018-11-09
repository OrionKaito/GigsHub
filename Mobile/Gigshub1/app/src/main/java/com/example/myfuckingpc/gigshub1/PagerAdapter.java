package com.example.myfuckingpc.gigshub1;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.astuetz.PagerSlidingTabStrip;

public class PagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {
    private static int NUM_ITEMS = 4;
    private int tabIcons[] = {R.drawable.ic_home, R.drawable.ic_search, R.drawable.ic_add_event, R.drawable.ic_personal};
    private static int NUM_ITEMS_NOT = 3;
    private int tabIconsNot[] = {R.drawable.ic_home, R.drawable.ic_search, R.drawable.ic_personal};
    private int type;

    public PagerAdapter(FragmentManager fm, int type) {
        super(fm);
        this.type = type;
    }

    @Override
    public Fragment getItem(int i) {
        if (type == 2) {
            switch (i) {
                case 0:
                    return HomeFragment.newInstance(0, "Home");
                case 1:
                    return SearchFragment.newInstance(1, "Search");
                case 2:
                    return CreateFragment.newInstance(2, "Create");
                case 3:
                    return PersonalFragment.newInstance(3, "Personal", type);
                default:
                    return null;
            }
        } else {
            switch (i) {
                case 0:
                    return HomeFragment.newInstance(0, "Home");
                case 1:
                    return SearchFragment.newInstance(1, "Search");
                case 2:
                    return PersonalFragment.newInstance(2, "Personal", type);
                default:
                    return null;
            }
        }
    }

    @Override
    public int getCount() {
        if (type == 2) {
            return NUM_ITEMS;
        } else {
            return NUM_ITEMS_NOT;
        }
    }

    @Override
    public int getPageIconResId(int position) {
        if (type == 2) {
            return tabIcons[position];
        } else {
            return tabIconsNot[position];
        }

    }
}
