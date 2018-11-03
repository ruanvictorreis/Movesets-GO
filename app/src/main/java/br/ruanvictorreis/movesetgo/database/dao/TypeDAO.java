package br.ruanvictorreis.movesetgo.database.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ruanvictorreis.movesetgo.R;
import br.ruanvictorreis.movesetgo.database.DatabaseHelper;
import br.ruanvictorreis.movesetgo.model.Type;

/**
 * Data Access Object
 * Created by Ruan on 08/10/2016.
 */
public class TypeDAO {

    private Context context;

    private TypeResistanceDAO typeResistanceDAO;

    private TypeWeaknessDAO typeWeaknessDAO;

    private Map<Integer, Type> cache;

    private static final String TYPE_TABLE = "POKEMON_TYPE";

    public TypeDAO(Context context) {
        this.context = context;
        this.typeResistanceDAO = new TypeResistanceDAO(context);
        this.typeWeaknessDAO = new TypeWeaknessDAO(context);
        this.cache = new HashMap<>();
    }

    public String createTable() {
        return "CREATE TABLE " + TYPE_TABLE + "(" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT NOT NULL);";
    }

    public String dropTable() {
        return "DROP TABLE IF EXISTS " + TYPE_TABLE;
    }

    public String[] insertAll() throws IOException {
        InputStream is = context.getResources().openRawResource(R.raw.sql_pokemon_types);
        byte[] buffer = new byte[is.available()];
        while (is.read(buffer) != -1) ;
        return new String(buffer).split(";");
    }

    public List<Type> selectTypesWithProperties() {
        List<Type> result = new ArrayList<>();

        String query = "SELECT * FROM " + TYPE_TABLE + " ORDER BY NAME";
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        Cursor cursor = sqlLite.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Type type = new Type();
                type.setId(cursor.getInt(0));
                type.setName(cursor.getString(1));
                type.setStrengthChart(typeResistanceDAO.selectSimpleStrengthTypes(type.getId()));
                type.setWeaknessChart(typeWeaknessDAO.selectSimpleWeaknessTypes(type.getId()));

                result.add(type);

            } while (cursor.moveToNext());
        }

        cursor.close();
        sqlLite.close();
        return result;
    }

    public Type selectOne(Integer id) {
        Type type = null;

        if (cache.containsKey(id)) {
            return cache.get(id);
        }

        String query = "SELECT * FROM " + TYPE_TABLE + " WHERE ID = " + id;
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        Cursor cursor = sqlLite.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                type = new Type();
                type.setId(cursor.getInt(0));
                type.setName(cursor.getString(1));

            } while (cursor.moveToNext());
        }

        cursor.close();
        sqlLite.close();
        cache.put(id, type);
        return type;
    }
}
