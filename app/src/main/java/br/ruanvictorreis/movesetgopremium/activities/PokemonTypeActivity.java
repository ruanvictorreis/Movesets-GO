package br.ruanvictorreis.movesetgopremium.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

import br.ruanvictorreis.movesetgopremium.R;
import br.ruanvictorreis.movesetgopremium.adapters.PokemonTypesAdapter;
import br.ruanvictorreis.movesetgopremium.asyncs.RecoveryTypesPokemonAsyncTask;
import br.ruanvictorreis.movesetgopremium.model.Type;
import br.ruanvictorreis.movesetgopremium.util.ClickListener;
import br.ruanvictorreis.movesetgopremium.util.DividerItemDecoration;
import br.ruanvictorreis.movesetgopremium.util.RecyclerTouchListener;

public class PokemonTypeActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private List<Type> typeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_type);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = findViewById(R.id.types_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                mRecyclerView, new ClickListener() {

            @Override
            public void onClick(View view, int position) {
                Type type = getTypeList().get(position);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("type", type);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                //TODO
            }
        }));

        (new RecoveryTypesPokemonAsyncTask(this)).execute();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public List<Type> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<Type> typeList) {
        this.typeList = typeList;
    }

    public void updateRecyclerView() {
        RecyclerView.Adapter mAdapter = new PokemonTypesAdapter(getApplicationContext(), getTypeList());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
