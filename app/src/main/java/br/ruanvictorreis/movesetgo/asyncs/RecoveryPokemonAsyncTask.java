package br.ruanvictorreis.movesetgo.asyncs;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Comparator;
import java.util.List;

import br.ruanvictorreis.movesetgo.R;
import br.ruanvictorreis.movesetgo.activities.MainActivity;
import br.ruanvictorreis.movesetgo.database.dao.PokemonDAO;
import br.ruanvictorreis.movesetgo.model.Pokemon;
import br.ruanvictorreis.movesetgo.model.Type;

/**
 * AsyncTask to recovery Pokemons from database.
 * Created by Ruan on 08/10/2016.
 */
public class RecoveryPokemonAsyncTask extends AsyncTask<String, String, Boolean> {

    private MainActivity activity;

    private ProgressDialog progressDialog;

    private List<Pokemon> pokemonList;

    private Comparator<Pokemon> comparator;

    private Type type;

    public RecoveryPokemonAsyncTask(MainActivity activity, Comparator<Pokemon> comparator, Type type) {
        this.activity = activity;
        this.comparator = comparator;
        this.type = type;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        String message = activity.getString(R.string.loading);
        progressDialog = new ProgressDialog(activity,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.show();
    }


    @Override
    protected Boolean doInBackground(String... strings) {
        try {
            PokemonDAO pokemonDao = new PokemonDAO(activity);

            if (type != null) {
                pokemonList = pokemonDao.selectAllByType(type, comparator);
                return true;
            } else {
                pokemonList = pokemonDao.selectAll(comparator);
                return true;
            }

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
            activity.setPokemonList(pokemonList);
            activity.setAllPokemonList(pokemonList);
            activity.updateRecyclerView();
        } else {
            showToast("Error");
        }
    }

    private void showToast(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }
}
