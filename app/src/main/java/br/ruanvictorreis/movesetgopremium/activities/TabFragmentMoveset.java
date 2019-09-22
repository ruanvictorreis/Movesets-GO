package br.ruanvictorreis.movesetgopremium.activities;

import android.app.Activity;

import java.util.List;

import br.ruanvictorreis.movesetgopremium.model.Moveset;

/**
 * Interface for Movesets Fragments
 * Created by Ruan on 17/09/2017.
 */

public interface TabFragmentMoveset {

    void setMovesetList(List<Moveset> movesetList);

    void updateRecyclerView();

    Activity getActivity();

    String getString(int id);
}
