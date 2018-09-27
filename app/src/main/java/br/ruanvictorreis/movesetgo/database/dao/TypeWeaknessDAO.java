package br.ruanvictorreis.movesetgo.database.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import br.ruanvictorreis.movesetgo.R;
import br.ruanvictorreis.movesetgo.database.DatabaseHelper;
import br.ruanvictorreis.movesetgo.model.Type;

/**
 * Data Access Object
 * Created by Ruan on 08/10/2016.
 */
public class TypeWeaknessDAO {

    private Context context;

    private static final String TABLE = "TYPE_WEAKNESS";

    public TypeWeaknessDAO(Context context) {
        this.context = context;
    }

    public String createTable() {
        return "CREATE TABLE " + TABLE + "(" +
                "TYPE_SOURCE INTEGER NOT NULL, " +
                "TYPE_DESTINY INTEGER NOT NULL, " +
                "FOREIGN KEY (TYPE_SOURCE) REFERENCES POKEMON_TYPE(ID), " +
                "FOREIGN KEY (TYPE_DESTINY) REFERENCES POKEMON_TYPE(ID), " +
                "PRIMARY KEY (TYPE_SOURCE, TYPE_DESTINY));";
    }

    public String dropTable() {
        return "DROP TABLE IF EXISTS " + TABLE;
    }

    public String[] insertAll() throws IOException {
        InputStream is = context.getResources().openRawResource(R.raw.sql_pokemon_type_weakness);
        byte[] buffer = new byte[is.available()];
        while (is.read(buffer) != -1) ;
        return new String(buffer).split(";");
    }

    public List<Type> selectSimpleWeaknessTypes(Integer typeId) {
        List<Type> result = new ArrayList<>();

        String query = "SELECT TYPE_DESTINY FROM " + TABLE + " WHERE TYPE_SOURCE = " + typeId;
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        Cursor cursor = sqlLite.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Type type = new Type();
                type.setId(cursor.getInt(0));

                result.add(type);

            } while (cursor.moveToNext());
        }

        cursor.close();
        sqlLite.close();
        return result;
    }
}
