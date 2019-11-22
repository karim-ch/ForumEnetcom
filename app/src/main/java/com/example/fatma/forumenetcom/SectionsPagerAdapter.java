package com.example.fatma.forumenetcom;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class SectionsPagerAdapter extends FragmentPagerAdapter {
    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                ProgramFragment programFragment = new ProgramFragment();
                return programFragment;
            case 1:
                WorkshopsFragment workshopsFragment = new WorkshopsFragment();
                return workshopsFragment;
            case 2:
                SponsorsFragment sponsorsFragment = new SponsorsFragment();
                return sponsorsFragment;

            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return 3;
    }


    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "PROGRAM";
            case 1:
                return "WORKSHOPS";
            case 2:
                return "SPONSORS";

        }
        return null;
    }


}
