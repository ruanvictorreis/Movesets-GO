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
import br.ruanvictorreis.movesetgo.model.BasicMove;

/**
 * Data Access Object
 * Created by Ruan on 19/02/2017.
 */
public class BasicMovesDAO {

    private Context context;

    private TypeDAO typeDAO;

    private Map<Integer, BasicMove> cache;

    private static final String QUICK_MOVES_TABLE = "QUICK_MOVES";

    public BasicMovesDAO(Context context) {
        this.context = context;
        this.typeDAO = new TypeDAO(context);
        this.cache = new HashMap<>();
    }

    public String createTable() {
        return "CREATE TABLE " + QUICK_MOVES_TABLE + " ( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME_EN TEXT NOT NULL, " +
                "NAME_PT TEXT NOT NULL, " +
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
        InputStream is = context.getResources().openRawResource(R.raw.sql_pokemon_quick_moves);
        byte[] buffer = new byte[is.available()];
        while (is.read(buffer) != -1) ;
        return new String(buffer).split(";");
    }

    BasicMove selectOne(Integer id) {
        BasicMove basicMove = null;

        if (cache.containsKey(id)) {
            return cache.get(id);
        }

        String query = "SELECT * FROM " + QUICK_MOVES_TABLE + " WHERE ID = " + id;
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        Cursor cursor = sqlLite.rawQuery(query, null);

        Integer indexName = Locale.getDefault().toString().equals("pt_BR") ? 2 : 1;

        if (cursor.moveToFirst()) {
            do {
                basicMove = new BasicMove();
                basicMove.setId(cursor.getInt(0));
                basicMove.setName(cursor.getString(indexName));
                basicMove.setDamage(cursor.getDouble(3));
                basicMove.setDuration(cursor.getDouble(4));
                basicMove.setType(typeDAO.selectOne(cursor.getInt(5)));
                basicMove.setEnergy(cursor.getDouble(6));

            } while (cursor.moveToNext());
        }

        cursor.close();
        sqlLite.close();
        cache.put(id, basicMove);

        return basicMove;
    }
}
