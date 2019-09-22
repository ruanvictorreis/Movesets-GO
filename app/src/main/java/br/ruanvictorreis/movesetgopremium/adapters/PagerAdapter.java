package br.ruanvictorreis.movesetgopremium.adapters;

/*
 * Pager  Adapter
 * Created by Ruan on 09/10/2016.
 */

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import br.ruanvictorreis.movesetgopremium.activities.TabFragmentAttack;
import br.ruanvictorreis.movesetgopremium.activities.TabFragmentDefense;
import br.ruanvictorreis.movesetgopremium.activities.TabFragmentStats;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new TabFragmentAttack();
            case 1:
                return new TabFragmentDefense();
            case 2:
                return new TabFragmentStats();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}