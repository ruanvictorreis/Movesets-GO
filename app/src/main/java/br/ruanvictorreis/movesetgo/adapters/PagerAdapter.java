package br.ruanvictorreis.movesetgo.adapters;

/**
 * Pager  Adapter
 * Created by Ruan on 09/10/2016.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import br.ruanvictorreis.movesetgo.activities.TabFragmentAttack;
import br.ruanvictorreis.movesetgo.activities.TabFragmentDefense;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabFragmentAttack tab1 = new TabFragmentAttack();
                return tab1;
            case 1:
                TabFragmentDefense tab2 = new TabFragmentDefense();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}