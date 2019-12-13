package br.ruanvictorreis.movesetgo.database.dao;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.ruanvictorreis.movesetgo.model.CommunityMove;

public class CommunityDayDAO {

    private static CommunityDayDAO instance;

    private List<CommunityMove> communityMoveList = new ArrayList<>();

    public static synchronized CommunityDayDAO getInstance() {
        if (instance == null) {
            instance = new CommunityDayDAO();
        }

        return instance;
    }

    private CommunityDayDAO() {
        loadCommunityMoveList();
    }

    private void loadCommunityMoveList() {
        communityMoveList.clear();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);

        String resourcePath = "community-day";
        DatabaseReference reference = database.getReference(resourcePath);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    CommunityMove move = snapshot.getValue(CommunityMove.class);
                    communityMoveList.add(move);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    public Boolean isCommunityDayMove(String pokemonName, String moveName) {
        for (CommunityMove move: communityMoveList) {
            if (move.getPokemon().equals(pokemonName) && move.getMove().equals(moveName)) {
                return true;
            }
        }

        return false;
    }
}
