package br.ruanvictorreis.movesetgo.activities;

import android.app.Activity;

import java.util.List;

import br.ruanvictorreis.movesetgo.model.Moveset;

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
