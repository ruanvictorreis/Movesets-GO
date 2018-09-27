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
import br.ruanvictorreis.movesetgo.model.BasicMove;
import br.ruanvictorreis.movesetgo.model.ChargedMove;
import br.ruanvictorreis.movesetgo.model.Moveset;
import br.ruanvictorreis.movesetgo.model.MovesetKind;
import br.ruanvictorreis.movesetgo.model.Pokemon;

/**
 * Data Access Object
 * Created by Ruan on 08/10/2016.
 */
public class MovesetsDAO {

    private Context context;

    private BasicMovesDAO basicMovesDAO;

    private ChargedMovesDAO chargedMovesDAO;

    public MovesetsDAO(Context context) {
        this.context = context;
        this.basicMovesDAO = new BasicMovesDAO(context);
        this.chargedMovesDAO = new ChargedMovesDAO(context);
    }

    public String createNormalTable() {
        return String.format(getCreateScript(), MovesetKind.NORMAL_MOVESET.getSource());
    }

    public String createAlolaTable() {
        return String.format(getCreateScript(), MovesetKind.ALOLA_MOVESET.getSource());
    }

    private String getCreateScript() {
        return "CREATE TABLE %s( " +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "POKEMON_ID INTEGER NOT NULL, " +
                "QUICK_MOVE INTEGER NOT NULL, " +
                "MAIN_MOVE INTEGER NOT NULL, " +
                "UPDATE_DATE INTEGER NOT NULL, " +
                "FOREIGN KEY (QUICK_MOVE) REFERENCES QUICK_MOVES(ID), " +
                "FOREIGN KEY (MAIN_MOVE) REFERENCES MAIN_MOVES(ID), " +
                "FOREIGN KEY (POKEMON_ID) REFERENCES POKEMON_SPECIES(ID));";
    }

    public String dropNormalTable() {
        return getDropScript() + MovesetKind.NORMAL_MOVESET.getSource();
    }

    public String dropAlolaTable() {
        return getDropScript() + MovesetKind.ALOLA_MOVESET.getSource();
    }

    private String getDropScript() {
        return "DROP TABLE IF EXISTS ";
    }

    public String[] insertNormalMovesets() throws IOException {
        return readScript(R.raw.sql_pokemon_movesets_2);
    }

    public String[] insertAlolaMovesets() throws IOException {
        return readScript(R.raw.sql_pokemon_movesets_alola);
    }

    private String[] readScript(int resource) throws IOException {
        InputStream is = context.getResources().openRawResource(resource);
        byte[] buffer = new byte[is.available()];
        while (is.read(buffer) != -1) ;
        return new String(buffer).split(";");
    }

    private List<Moveset> selectMovesets(String query, Pokemon pokemon) {
        List<Moveset> result = new ArrayList<>();
        SQLiteDatabase sqlLite = new DatabaseHelper(context).getWritableDatabase();
        Cursor cursor = sqlLite.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Moveset moveset = new Moveset();
                moveset.setId(cursor.getInt(0));
                moveset.setPokemon(pokemon);
                moveset.setBasicMove(basicMovesDAO.selectOne(cursor.getInt(2)));
                moveset.setChargedMove(chargedMovesDAO.selectOne(cursor.getInt(3)));
                moveset.setUpdated(cursor.getInt(4) == 1);

                result.add(moveset);

            } while (cursor.moveToNext());
        }

        cursor.close();
        sqlLite.close();
        return result;
    }

    public List<Moveset> selectMovesetsAvailable(Pokemon pokemon) {
        String query = getCommonQuery(pokemon, true);
        return selectMovesets(query, pokemon);
    }

    public List<Moveset> selectMovesetsWithLegacy(Pokemon pokemon) {
        String query = getCommonQuery(pokemon, false);
        return selectMovesets(query, pokemon);
    }

    public List<Moveset> selectAllTMsCombinations(Pokemon pokemon) {
        List<Moveset> result = new ArrayList<>();
        List<BasicMove> basicMoves = new ArrayList<>();
        List<ChargedMove> chargedMoves = new ArrayList<>();

        for (Moveset moveset : selectMovesetsWithLegacy(pokemon)) {
            if (!basicMoves.contains(moveset.getBasicMove())) {
                basicMoves.add(moveset.getBasicMove());
            }

            if (!chargedMoves.contains(moveset.getChargedMove())) {
                chargedMoves.add(moveset.getChargedMove());
            }
        }

        for (BasicMove basicMove : basicMoves) {
            for (ChargedMove chargedMove : chargedMoves) {
                Moveset moveset = new Moveset();
                moveset.setPokemon(pokemon);
                moveset.setBasicMove(basicMove);
                moveset.setChargedMove(chargedMove);
                moveset.setUpdated(true);
                result.add(moveset);
            }
        }

        return result;
    }

    private String getCommonQuery(Pokemon pokemon, Boolean onlyUpdated) {
        String query = "SELECT * FROM " + pokemon.getMovesetKind().getSource() +
                " WHERE POKEMON_ID = " + String.valueOf(pokemon.getId());

        if (onlyUpdated) {
            query += " AND UPDATE_DATE = " + String.valueOf(1);
        }
        return query;
    }
}
