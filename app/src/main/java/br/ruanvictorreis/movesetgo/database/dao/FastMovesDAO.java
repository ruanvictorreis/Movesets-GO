package br.ruanvictorreis.movesetgo.database.dao;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import br.ruanvictorreis.movesetgo.R;
import br.ruanvictorreis.movesetgo.database.DatabaseHelper;
import br.ruanvictorreis.movesetgo.model.FastMove;

/**
 * Data Access Object
 * Created by Ruan on 19/02/2017.
 */
public class FastMovesDAO {

    private Context context;

    private TypeDAO typeDAO;

    private Map<Integer, FastMove> cache;

    private static final String QUICK_MOVES_TABLE = "FAST_MOVES";

    public FastMovesDAO(Context context) {
        this.context = context;
        this.typeDAO = new TypeDAO(context);
        this.cache = new HashMap<>();
    }

    public String createTable() {
        return "CREATE TABLE " + QUICK_MOVES_TABLE + " ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT NOT NULL, " +
                "BASE_DAMAGE REAL NOT NULL, " +
                "DURATION REAL NOT NULL, " +
                "TYPE_ID INTEGER NOT NULL, " +
                "ENERGY REAL NOT NULL, " +
                "FOREIGN KEY (TYPE_ID) REFERENCES POKEMON_TYPE(ID));";
    }

    public String dropTable() {
        return "DROP TABLE IF EXISTS " + QUICK_MOVES_TABLE;
    }

    public String[] insertAll() throws IOException {
        InputStream is = context.getResources().openRawResource(R.raw.sql_pokemon_fast_moves);
        byte[] buffer = new byte[is.available()];
        while (is.read(buffer) != -1) ;
        return new String(buffer).split(";");
    }

    FastMove selectOne(Integer id) {
        FastMove fastMove = null;

        if (cache.containsKey(id)) {
            return cache.get(id);
        }

        String query = "SELECT * FROM " + QUICK_MOVES_TABLE + " WHERE ID = " + id;
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        Cursor cursor = sqlLite.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                fastMove = new FastMove();
                fastMove.setId(cursor.getInt(0));
                fastMove.setName(cursor.getString(1));
                fastMove.setDamage(cursor.getDouble(2));
                fastMove.setDuration(cursor.getDouble(3));
                fastMove.setType(typeDAO.selectOne(cursor.getInt(4)));
                fastMove.setEnergy(cursor.getDouble(5));

                try {
                    String pattern = fastMove.getPattern();
                    String moveTranslation = getTranslation(pattern);

                    if (moveTranslation != null) {
                        fastMove.setName(moveTranslation);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } while (cursor.moveToNext());
        }

        cursor.close();
        sqlLite.close();
        cache.put(id, fastMove);

        return fastMove;
    }

    private String getTranslation(String pattern) throws IOException, JSONException {
        String language = Locale.getDefault().getLanguage();
        String file = language + "_moves.json";

        AssetManager assetManager = context.getAssets();
        if (!Arrays.asList(assetManager.list("")).contains(file)) {
            return null;
        }

        InputStream is = assetManager.open(file);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();

        String jsonStr = new String(buffer, "UTF-8");
        JSONObject JSONLanguage = new JSONObject(jsonStr);
        JSONObject JSONMove = JSONLanguage.getJSONObject(pattern);
        return JSONMove.getString("name");
    }
}
