package br.ruanvictorreis.movesetgo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;

import br.ruanvictorreis.movesetgo.database.dao.FastMovesDAO;
import br.ruanvictorreis.movesetgo.database.dao.ChargeMovesDAO;
import br.ruanvictorreis.movesetgo.database.dao.MovesetsDAO;
import br.ruanvictorreis.movesetgo.database.dao.PokemonDAO;
import br.ruanvictorreis.movesetgo.database.dao.TypeDAO;
import br.ruanvictorreis.movesetgo.database.dao.TypeStrengthDAO;
import br.ruanvictorreis.movesetgo.database.dao.TypeWeaknessDAO;

/**
 * Database manager
 * Created by Ruan on 08/10/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MovesetPokemonGoPremium.db";
    private static final int DATABASE_VERSION = 42;

    private TypeDAO typeDAO;

    private PokemonDAO pokemonDAO;

    private FastMovesDAO fastMovesDAO;

    private ChargeMovesDAO chargeMovesDAO;

    private MovesetsDAO movesetsDAO;

    private TypeStrengthDAO typeStrengthDAO;

    private TypeWeaknessDAO typeWeaknessDAO;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.typeDAO = new TypeDAO(context);
        this.pokemonDAO = new PokemonDAO(context);
        this.fastMovesDAO = new FastMovesDAO(context);
        this.chargeMovesDAO = new ChargeMovesDAO(context);
        this.movesetsDAO = new MovesetsDAO(context);
        this.typeStrengthDAO = new TypeStrengthDAO(context);
        this.typeWeaknessDAO = new TypeWeaknessDAO(context);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(typeDAO.createTable());
            sqLiteDatabase.execSQL(pokemonDAO.createSpeciesTable());
            sqLiteDatabase.execSQL(pokemonDAO.createAlolaTable());
            sqLiteDatabase.execSQL(fastMovesDAO.createTable());
            sqLiteDatabase.execSQL(chargeMovesDAO.createTable());
            sqLiteDatabase.execSQL(movesetsDAO.createNormalTable());
            sqLiteDatabase.execSQL(movesetsDAO.createAlolaTable());
            sqLiteDatabase.execSQL(typeStrengthDAO.createTable());
            sqLiteDatabase.execSQL(typeWeaknessDAO.createTable());

            for (String sql : typeDAO.insertAll()) {
                sqLiteDatabase.execSQL(sql);
            }

            for (String sql : pokemonDAO.insertSpecies()) {
                sqLiteDatabase.execSQL(sql);
            }

            for (String sql : pokemonDAO.insertAlolaSpecies()) {
                sqLiteDatabase.execSQL(sql);
            }

            for (String sql : fastMovesDAO.insertAll()) {
                sqLiteDatabase.execSQL(sql);
            }

            for (String sql : chargeMovesDAO.insertAll()) {
                sqLiteDatabase.execSQL(sql);
            }

            for (String sql : movesetsDAO.insertNormalMovesets()) {
                sqLiteDatabase.execSQL(sql);
            }

            for (String sql : movesetsDAO.insertAlolaMovesets()) {
                sqLiteDatabase.execSQL(sql);
            }

            for (String sql : typeStrengthDAO.insertAll()) {
                sqLiteDatabase.execSQL(sql);
            }

            for (String sql : typeWeaknessDAO.insertAll()) {
                sqLiteDatabase.execSQL(sql);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(this.typeStrengthDAO.dropTable());
        sqLiteDatabase.execSQL(this.typeWeaknessDAO.dropTable());
        sqLiteDatabase.execSQL(this.movesetsDAO.dropNormalTable());
        sqLiteDatabase.execSQL(this.movesetsDAO.dropAlolaTable());
        sqLiteDatabase.execSQL(this.chargeMovesDAO.dropTable());
        sqLiteDatabase.execSQL(this.fastMovesDAO.dropTable());
        sqLiteDatabase.execSQL(this.pokemonDAO.dropSpeciesTable());
        sqLiteDatabase.execSQL(this.pokemonDAO.dropAlolaTable());
        sqLiteDatabase.execSQL(this.typeDAO.dropTable());

        onCreate(sqLiteDatabase);
    }
}
