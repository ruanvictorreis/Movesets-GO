package br.ruanvictorreis.movesetgo.asyncs;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.ruanvictorreis.movesetgo.R;
import br.ruanvictorreis.movesetgo.activities.PokemonTypeActivity;
import br.ruanvictorreis.movesetgo.database.dao.TypeDAO;
import br.ruanvictorreis.movesetgo.model.Type;

/**
 * AsyncTask to recovery Pokemons from database.
 * Created by Ruan on 08/10/2016.
 */
public class RecoveryTypesPokemonAsyncTask extends AsyncTask<String, String, Boolean> {

    private PokemonTypeActivity activity;

    private ProgressDialog progressDialog;

    private List<Type> typeList;

    public RecoveryTypesPokemonAsyncTask(PokemonTypeActivity activity) {
        this.activity = activity;
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
            TypeDAO typeDao = new TypeDAO(activity);
            typeList = typeDao.selectTypesWithProperties();
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
            activity.setTypeList(typeList);
            activity.updateRecyclerView();
        } else {
            showToast("Error");
        }
    }

    private void showToast(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }
}
