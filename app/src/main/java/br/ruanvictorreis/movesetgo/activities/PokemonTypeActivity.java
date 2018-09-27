package br.ruanvictorreis.movesetgo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

import br.ruanvictorreis.movesetgo.R;
import br.ruanvictorreis.movesetgo.adapters.PokemonTypesAdapter;
import br.ruanvictorreis.movesetgo.asyncs.RecoveryTypesPokemonAsyncTask;
import br.ruanvictorreis.movesetgo.model.Type;
import br.ruanvictorreis.movesetgo.util.ClickListener;
import br.ruanvictorreis.movesetgo.util.DividerItemDecoration;
import br.ruanvictorreis.movesetgo.util.RecyclerTouchListener;

public class PokemonTypeActivity extends AppCompatActivity {

    private AdView mAdView;

    private RecyclerView mRecyclerView;

    private List<Type> typeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_type);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.types_recycler_view);
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

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        (new RecoveryTypesPokemonAsyncTask(this)).execute();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
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
