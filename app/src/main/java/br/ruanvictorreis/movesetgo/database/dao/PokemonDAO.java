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
import br.ruanvictorreis.movesetgo.factory.AlolaFactory;
import br.ruanvictorreis.movesetgo.factory.NormalFactory;
import br.ruanvictorreis.movesetgo.factory.PokemonFactory;
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

    public String createAlolaTable() {
        return String.format(getCreateScript(), PokemonForm.ALOLA.getSource());
    }

    private String getCreateScript() {
        return "CREATE TABLE %s(" +
                "NAME TEXT NOT NULL, " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "HP_RATIO INTEGER NOT NULL, " +
                "ATTACK_RATIO INTEGER NOT NULL, " +
                "DEFENSE_RATIO INTEGER NOT NULL, " +
                "MAX_CP_CAP INTEGER NOT NULL, " +
                "ID_TYPE_ONE INTEGER NOT NULL, " +
                "ID_TYPE_TWO INTEGER, " +
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

    public String[] insertAlolaSpecies() throws IOException {
        return readScript(R.raw.sql_pokemon_species_alola);
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
        pokemonList.addAll(selectNormalSpecies());
        pokemonList.addAll(selectAlolaSpecies());
        Collections.sort(pokemonList, comparator);

        return pokemonList;
    }

    private List<Pokemon> selectNormalSpecies() {
        String query = "SELECT * FROM " + PokemonForm.NORMAL.getSource();
        return select(query, new NormalFactory());
    }

    private List<Pokemon> selectAlolaSpecies() {
        String query = "SELECT * FROM " + PokemonForm.ALOLA.getSource();
        return select(query, new AlolaFactory());
    }

    public List<Pokemon> selectAllByType(Type type, Comparator<Pokemon> comparator) {
        if (comparator == null) {
            comparator = new NumberComparator();
        }

        List<Pokemon> pokemonList = new ArrayList<>();
        pokemonList.addAll(selectNormalSpeciesByType(type));
        pokemonList.addAll(selectAlolaSpeciesByType(type));
        Collections.sort(pokemonList, comparator);

        return pokemonList;
    }

    private List<Pokemon> selectNormalSpeciesByType(Type type) {
        String query = "SELECT * FROM " + PokemonForm.NORMAL.getSource() +
                " WHERE ID_TYPE_ONE = " + String.valueOf(type.getId()) +
                " OR ID_TYPE_TWO = " + String.valueOf(type.getId());
        return select(query, new NormalFactory());
    }

    private List<Pokemon> selectAlolaSpeciesByType(Type type) {
        String query = "SELECT * FROM " + PokemonForm.ALOLA.getSource() +
                " WHERE ID_TYPE_ONE = " + String.valueOf(type.getId()) +
                " OR ID_TYPE_TWO = " + String.valueOf(type.getId());
        return select(query, new AlolaFactory());
    }

    private List<Pokemon> select(String query, PokemonFactory factory) {
        List<Pokemon> result = new ArrayList<>();
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        Cursor cursor = sqlLite.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Pokemon pokemon = factory.createInstance();
                pokemon.setName(cursor.getString(0));
                pokemon.setId(cursor.getInt(1));
                pokemon.setHpRatio(cursor.getInt(2));
                pokemon.setAttackRatio(cursor.getInt(3));
                pokemon.setDefenseRatio(cursor.getInt(4));
                pokemon.setMaxCpCap(cursor.getInt(5));
                pokemon.setTypeOne(typeDAO.selectOne(cursor.getInt(6)));
                pokemon.setTypeTwo(typeDAO.selectOne(cursor.getInt(7)));

                result.add(pokemon);

            } while (cursor.moveToNext());
        }

        cursor.close();
        sqlLite.close();
        return result;
    }
}
