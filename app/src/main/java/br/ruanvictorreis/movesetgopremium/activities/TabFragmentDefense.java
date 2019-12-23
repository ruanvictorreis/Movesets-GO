package br.ruanvictorreis.movesetgopremium.activities;

/*
 * Tab Fragment Defense
 * Created by Ruan on 09/10/2016.
 */

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

import br.ruanvictorreis.movesetgopremium.R;
import br.ruanvictorreis.movesetgopremium.adapters.MovesetDefenseAdapter;
import br.ruanvictorreis.movesetgopremium.asyncs.RecoveryMovesetAsyncTask;
import br.ruanvictorreis.movesetgopremium.dialogs.MovesetDialog;
import br.ruanvictorreis.movesetgopremium.model.Moveset;
import br.ruanvictorreis.movesetgopremium.model.Pokemon;
import br.ruanvictorreis.movesetgopremium.strategy.LegacyMovesets;
import br.ruanvictorreis.movesetgopremium.strategy.MovesetQuery;
import br.ruanvictorreis.movesetgopremium.util.ClickListener;
import br.ruanvictorreis.movesetgopremium.util.DividerItemDecoration;
import br.ruanvictorreis.movesetgopremium.util.RecyclerTouchListener;

public class TabFragmentDefense extends Fragment implements TabFragmentMoveset {

    private List<Moveset> movesetList;

    private RecyclerView mRecyclerView;

    private static final String MOVESET_DIALOG = "MOVESET_DIALOG";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_defense, container, false);
        //setHasOptionsMenu(true);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.moveset_defense_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                mRecyclerView, new ClickListener() {

            @Override
            public void onClick(View view, int position) {
                MovesetDialog dialog = new MovesetDialog();
                Moveset moveset = getMovesetList().get(position);
                getActivity().getIntent().putExtra("moveset", moveset);
                dialog.show(getActivity().getFragmentManager(), MOVESET_DIALOG);
            }

            @Override
            public void onLongClick(View view, int position) {
                //TODO
            }
        }));

        request();
        return view;
    }

    private void request(MovesetQuery movesetQuery) {
        Pokemon pokemon = getActivity().getIntent().getExtras().getParcelable("pokemon");
        new RecoveryMovesetAsyncTask(this, pokemon, movesetQuery).execute();
    }

    private void request() {
        MovesetQuery movesetQuery = new LegacyMovesets(this);
        request(movesetQuery);
    }

    /*
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_show_available:
                MovesetQuery availableMovesets = new AvailableMovesets(this);
                request(availableMovesets);
                return true;
            case R.id.action_show_legacy:
                MovesetQuery legacyMovesets = new LegacyMovesets(this);
                request(legacyMovesets);
                return true;
            case R.id.action_show_tms:
                MovesetQuery tmsMovesets = new TMsMovesets(this);
                request(tmsMovesets);
            default:
                break;
        }

        return false;
    }*/

    public void setMovesetList(List<Moveset> movesetList) {
        this.movesetList = movesetList;
    }

    public List<Moveset> getMovesetList() {
        return movesetList;
    }

    public void updateRecyclerView() {
        RecyclerView.Adapter mAdapter = new MovesetDefenseAdapter(getMovesetList());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
