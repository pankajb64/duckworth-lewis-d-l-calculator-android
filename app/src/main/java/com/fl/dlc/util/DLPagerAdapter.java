package com.fl.dlc.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fl.dlc.fragment.FinalResultFragment;
import com.fl.dlc.fragment.Team1DetailsFragment;
import com.fl.dlc.fragment.Team2DetailsFragment;
import com.fl.dlc.fragment.TypeAndFormatFragment;


public class DLPagerAdapter extends FragmentPagerAdapter {

    public DLPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case DLConstants.TYPE_AND_FORMAT_FRAGMENT:
                return TypeAndFormatFragment.newInstance();
            case DLConstants.TEAM1_DETAILS_FRAGMENT:
                return Team1DetailsFragment.newInstance();
            case DLConstants.TEAM2_DETAILS_FRAGMENT:
                return Team2DetailsFragment.newInstance();
            case DLConstants.FINAL_RESULT_FRAGMENT:
                return FinalResultFragment.newInstance();
            //case ABOUT_US_FRAGMENT: return AboutUsFragment.newInstance("","");
            default:
                return TypeAndFormatFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return DLConstants.NUM_ITEMS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case DLConstants.TYPE_AND_FORMAT_FRAGMENT:
                return "Type and Format";
            case DLConstants.TEAM1_DETAILS_FRAGMENT:
                return "Team 1 Details";
            case DLConstants.TEAM2_DETAILS_FRAGMENT:
                return "Team 2 Details";
            case DLConstants.FINAL_RESULT_FRAGMENT:
                return "Final Result";
            //case ABOUT_US_FRAGMENT: return AboutUsFragment.newInstance("","");
            default:
                return "Type and Format";
        }
    }

}
