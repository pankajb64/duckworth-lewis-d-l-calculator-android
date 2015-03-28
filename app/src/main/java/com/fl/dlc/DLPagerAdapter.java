package com.fl.dlc;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class DLPagerAdapter extends FragmentPagerAdapter {

    public static final int TYPE_AND_FORMAT_FRAGMENT = 0;
    public static final int TEAM1_DETAILS_FRAGMENT = 1;
    public static final int TEAM2_DETAILS_FRAGMENT = 2;
    public static final int FINAL_RESULT_FRAGMENT = 3;
    public static final int ABOUT_US_FRAGMENT = 4;

    public static final int NUM_ITEMS = 4;

    public DLPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case TYPE_AND_FORMAT_FRAGMENT:
                return TypeAndFormatFragment.newInstance();
            case TEAM1_DETAILS_FRAGMENT:
                return Team1DetailsFragment.newInstance();
            case TEAM2_DETAILS_FRAGMENT:
                return Team2DetailsFragment.newInstance();
            case FINAL_RESULT_FRAGMENT:
                return FinalResultFragment.newInstance();
            //case ABOUT_US_FRAGMENT: return AboutUsFragment.newInstance("","");
            default:
                return TypeAndFormatFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case TYPE_AND_FORMAT_FRAGMENT:
                return "Type and Format";
            case TEAM1_DETAILS_FRAGMENT:
                return "Team 1 Details";
            case TEAM2_DETAILS_FRAGMENT:
                return "Team 2 Details";
            case FINAL_RESULT_FRAGMENT:
                return "Final Result";
            //case ABOUT_US_FRAGMENT: return AboutUsFragment.newInstance("","");
            default:
                return "Type and Format";
        }
    }

}
