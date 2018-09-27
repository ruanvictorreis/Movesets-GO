package br.ruanvictorreis.movesetgo.database.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import br.ruanvictorreis.movesetgo.R;
import br.ruanvictorreis.movesetgo.database.DatabaseHelper;
import br.ruanvictorreis.movesetgo.model.ChargedMove;

/**
 * Data Access Object
 * Created by Ruan on 19/02/2017.
 */
public class ChargedMovesDAO {

    private Context context;

    private TypeDAO typeDAO;

    private Map<Integer, ChargedMove> cache;

    private static final String MAIN_MOVES_TABLE = "MAIN_MOVES";

    public ChargedMovesDAO(Context context) {
        this.context = context;
        this.typeDAO = new TypeDAO(context);
        this.cache = new HashMap<>();
    }

    public String createTable() {
        return "CREATE TABLE " + MAIN_MOVES_TABLE + " ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME_EN TEXT NOT NULL, " +
                "NAME_PT TEXT NOT NULL, " +
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
        InputStream is = context.getResources().openRawResource(R.raw.sql_pokemon_main_moves);
        byte[] buffer = new byte[is.available()];
        while (is.read(buffer) != -1) ;
        return new String(buffer).split(";");
    }

    ChargedMove selectOne(Integer id) {
        ChargedMove chargedMove = null;

        if (cache.containsKey(id)) {
            return cache.get(id);
        }

        String query = "SELECT * FROM " + MAIN_MOVES_TABLE + " WHERE ID = " + id;
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        Cursor cursor = sqlLite.rawQuery(query, null);

        Integer indexName = Locale.getDefault().toString().equals("pt_BR") ? 2 : 1;

        if (cursor.moveToFirst()) {
            do {
                chargedMove = new ChargedMove();
                chargedMove.setId(cursor.getInt(0));
                chargedMove.setName(cursor.getString(indexName));
                chargedMove.setDamage(cursor.getDouble(3));
                chargedMove.setDuration(cursor.getDouble(4));
                chargedMove.setType(typeDAO.selectOne(cursor.getInt(5)));
                chargedMove.setCritical(cursor.getDouble(6));
                chargedMove.setSpentEnergy(cursor.getDouble(7));

            } while (cursor.moveToNext());
        }

        cursor.close();
        sqlLite.close();
        cache.put(id, chargedMove);

        return chargedMove;
    }
}
