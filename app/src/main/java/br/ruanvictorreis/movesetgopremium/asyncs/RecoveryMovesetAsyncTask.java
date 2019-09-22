package br.ruanvictorreis.movesetgopremium.asyncs;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.ruanvictorreis.movesetgopremium.R;
import br.ruanvictorreis.movesetgopremium.activities.TabFragmentMoveset;
import br.ruanvictorreis.movesetgopremium.model.Moveset;
import br.ruanvictorreis.movesetgopremium.model.Pokemon;
import br.ruanvictorreis.movesetgopremium.strategy.MovesetQuery;

/**
 * AsyncTask to recovery Moveset from database.
 * Created by Ruan on 08/10/2016.
 */
public class RecoveryMovesetAsyncTask extends AsyncTask<String, String, Boolean> {

    private TabFragmentMoveset fragment;

    private ProgressDialog progressDialog;

    private List<Moveset> movesetList;

    private MovesetQuery movesetQuery;

    private Pokemon pokemon;

    public RecoveryMovesetAsyncTask(TabFragmentMoveset activity, Pokemon pokemon,
                                    MovesetQuery movesetQuery) {
        this.fragment = activity;
        this.pokemon = pokemon;
        this.movesetQuery = movesetQuery;
    }

    @Override

    protected void onPreExecute() {
        super.onPreExecute();

        String message = fragment.getString(R.string.loading);
        progressDialog = new ProgressDialog(fragment.getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.show();
    }


    @Override
    protected Boolean doInBackground(String... params) {
        try {
            movesetList = movesetQuery.executeSelectQuery(pokemon);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean successful) {
        super.onPostExecute(successful);
        progressDialog.dismiss();

        if (successful) {
            fragment.setMovesetList(movesetList);
            fragment.updateRecyclerView();
        } else {
            showToast();
        }
    }

    private void showToast() {
        Toast.makeText(fragment.getActivity(), "Error", Toast.LENGTH_SHORT).show();
    }
}
