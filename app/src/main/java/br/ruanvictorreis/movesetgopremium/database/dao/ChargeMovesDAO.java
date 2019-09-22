package br.ruanvictorreis.movesetgopremium.database.dao;

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

import br.ruanvictorreis.movesetgopremium.R;
import br.ruanvictorreis.movesetgopremium.database.DatabaseHelper;
import br.ruanvictorreis.movesetgopremium.model.ChargeMove;

/**
 * Data Access Object
 * Created by Ruan on 19/02/2017.
 */
public class ChargeMovesDAO {

    private Context context;

    private TypeDAO typeDAO;

    private Map<Integer, ChargeMove> cache;

    private static final String MAIN_MOVES_TABLE = "CHARGE_MOVES";

    public ChargeMovesDAO(Context context) {
        this.context = context;
        this.typeDAO = new TypeDAO(context);
        this.cache = new HashMap<>();
    }

    public String createTable() {
        return "CREATE TABLE " + MAIN_MOVES_TABLE + " ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT NOT NULL, " +
                "BASE_DAMAGE REAL NOT NULL, " +
                "DURATION REAL NOT NULL, " +
                "TYPE_ID INTEGER NOT NULL, " +
                "CRITICAL REAL NOT NULL, " +
                "ENERGY REAL NOT NULL, " +
                "FOREIGN KEY (TYPE_ID) REFERENCES POKEMON_TYPE(ID));";
    }

    public String dropTable() {
        return "DROP TABLE IF EXISTS " + MAIN_MOVES_TABLE;
    }

    public String[] insertAll() throws IOException {
        InputStream is = context.getResources().openRawResource(R.raw.sql_pokemon_charge_moves);
        byte[] buffer = new byte[is.available()];
        while (is.read(buffer) != -1) ;
        return new String(buffer).split(";");
    }

    ChargeMove selectOne(Integer id) {
        ChargeMove chargeMove = null;

        if (cache.containsKey(id)) {
            return cache.get(id);
        }

        String query = "SELECT * FROM " + MAIN_MOVES_TABLE + " WHERE ID = " + id;
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        Cursor cursor = sqlLite.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                chargeMove = new ChargeMove();
                chargeMove.setId(cursor.getInt(0));
                chargeMove.setName(cursor.getString(1));
                chargeMove.setDamage(cursor.getDouble(2));
                chargeMove.setDuration(cursor.getDouble(3));
                chargeMove.setType(typeDAO.selectOne(cursor.getInt(4)));
                chargeMove.setCritical(cursor.getDouble(5));
                chargeMove.setSpentEnergy(cursor.getDouble(6));

                try {
                    String pattern = chargeMove.getPattern();
                    String moveTranslation = getTranslation(pattern);

                    if (moveTranslation != null) {
                        chargeMove.setName(moveTranslation);
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
        cache.put(id, chargeMove);

        return chargeMove;
    }

    private String getTranslation(String pattern) throws IOException, JSONException {
        String language = Locale.getDefault().toString();
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
