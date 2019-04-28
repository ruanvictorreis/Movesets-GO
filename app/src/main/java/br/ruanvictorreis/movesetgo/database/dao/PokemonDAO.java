package br.ruanvictorreis.movesetgo.database.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.ruanvictorreis.movesetgo.R;
import br.ruanvictorreis.movesetgo.comparators.NumberComparator;
import br.ruanvictorreis.movesetgo.database.DatabaseHelper;
import br.ruanvictorreis.movesetgo.model.Pokemon;
import br.ruanvictorreis.movesetgo.model.PokemonForm;
import br.ruanvictorreis.movesetgo.model.Type;

/**
 * Data Access Object
 * Created by Ruan on 08/10/2016.
 */
public class PokemonDAO {

    private Context context;

    private TypeDAO typeDAO;

    public PokemonDAO(Context context) {
        this.context = context;
        this.typeDAO = new TypeDAO(context);
    }

    public String createSpeciesTable() {
        return String.format(getCreateScript(), PokemonForm.NORMAL.getSource());
    }

    private String getCreateScript() {
        return "CREATE TABLE %s(" +
                "ID TEXT PRIMARY KEY, " +
                "NAME TEXT NOT NULL, " +
                "NUMBER INTEGER NOT NULL, " +
                "HP_RATIO INTEGER NOT NULL, " +
                "ATTACK_RATIO INTEGER NOT NULL, " +
                "DEFENSE_RATIO INTEGER NOT NULL, " +
                "MAX_CP_CAP INTEGER NOT NULL, " +
                "ID_TYPE_ONE INTEGER NOT NULL, " +
                "ID_TYPE_TWO INTEGER, " +
                "FORM TEXT NOT NULL, " +
                "PICTURE TEXT NOT NULL, " +
                "FOREIGN KEY (ID_TYPE_ONE) REFERENCES POKEMON_TYPE(ID), " +
                "FOREIGN KEY (ID_TYPE_TWO) REFERENCES POKEMON_TYPE(ID));";
    }

    public String dropSpeciesTable() {
        return getDropScript() + PokemonForm.NORMAL.getSource();
    }

    public String dropAlolaTable() {
        return getDropScript() + PokemonForm.ALOLA.getSource();
    }

    private String getDropScript() {
        return "DROP TABLE IF EXISTS ";
    }

    public String[] insertSpecies() throws IOException {
        return readScript(R.raw.sql_pokemon_species);
    }

    private String[] readScript(int resource) throws IOException {
        InputStream is = context.getResources().openRawResource(resource);
        byte[] buffer = new byte[is.available()];
        while (is.read(buffer) != -1) ;
        return new String(buffer).split(";");
    }

    public List<Pokemon> selectAll(Comparator<Pokemon> comparator) {
        if (comparator == null) {
            comparator = new NumberComparator();
        }

        List<Pokemon> pokemonList = new ArrayList<>();
        pokemonList.addAll(selectSpecies());
        Collections.sort(pokemonList, comparator);

        return pokemonList;
    }

    private List<Pokemon> selectSpecies() {
        String query = "SELECT * FROM " + PokemonForm.NORMAL.getSource();
        return select(query);
    }

    public List<Pokemon> selectAllByType(Type type, Comparator<Pokemon> comparator) {
        if (comparator == null) {
            comparator = new NumberComparator();
        }

        List<Pokemon> pokemonList = new ArrayList<>();
        pokemonList.addAll(selectSpeciesByType(type));
        Collections.sort(pokemonList, comparator);

        return pokemonList;
    }

    private List<Pokemon> selectSpeciesByType(Type type) {
        String query = "SELECT * FROM " + PokemonForm.NORMAL.getSource() +
                " WHERE ID_TYPE_ONE = " + String.valueOf(type.getId()) +
                " OR ID_TYPE_TWO = " + String.valueOf(type.getId());
        return select(query);
    }

    private List<Pokemon> select(String query) {
        List<Pokemon> result = new ArrayList<>();
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        Cursor cursor = sqlLite.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Pokemon pokemon = new Pokemon();
                pokemon.setId(cursor.getString(0));
                pokemon.setName(cursor.getString(1));
                pokemon.setNumber(cursor.getInt(2));
                pokemon.setHpRatio(cursor.getInt(3));
                pokemon.setAttackRatio(cursor.getInt(4));
                pokemon.setDefenseRatio(cursor.getInt(5));
                pokemon.setMaxCpCap(cursor.getInt(6));
                pokemon.setTypeOne(typeDAO.selectOne(cursor.getInt(7)));
                pokemon.setTypeTwo(typeDAO.selectOne(cursor.getInt(8)));
                pokemon.setForm(cursor.getString(9));
                pokemon.setPicture(cursor.getString(10));

                result.add(pokemon);

            } while (cursor.moveToNext());
        }

        cursor.close();
        sqlLite.close();
        return result;
    }
}
