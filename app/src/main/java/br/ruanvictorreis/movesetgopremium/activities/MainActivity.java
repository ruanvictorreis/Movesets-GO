package br.ruanvictorreis.movesetgopremium.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import br.ruanvictorreis.movesetgopremium.R;
import br.ruanvictorreis.movesetgopremium.adapters.PokemonAdapter;
import br.ruanvictorreis.movesetgopremium.asyncs.RecoveryPokemonAsyncTask;
import br.ruanvictorreis.movesetgopremium.comparators.ListOrder;
import br.ruanvictorreis.movesetgopremium.model.Pokemon;
import br.ruanvictorreis.movesetgopremium.model.Type;
import br.ruanvictorreis.movesetgopremium.util.ClickListener;
import br.ruanvictorreis.movesetgopremium.util.DividerItemDecoration;
import br.ruanvictorreis.movesetgopremium.util.RecyclerTouchListener;
import br.ruanvictorreis.movesetgo.database.dao.CommunityDayDAO;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener {

    private List<Pokemon> pokemonList;

    private List<Pokemon> allPokemonList;

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pokemonList = new ArrayList<>();
        allPokemonList = new ArrayList<>();

        mRecyclerView = findViewById(R.id.pokemon_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                mRecyclerView, new ClickListener() {

            @Override
            public void onClick(View view, int position) {
                Pokemon pokemon = getPokemonList().get(position);
                Intent intent = new Intent(getApplicationContext(), MovesetActivity.class);
                intent.putExtra("pokemon", pokemon);
                intent.putExtra("imageAllowed", isImageAllowed());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                //TODO
            }
        }));

        setUpNavigation(toolbar);
        getCommunityDayMoves();
        getAllPokemon();

        if (!isImageAllowed()) {
            handlerAccessNummber();
        }
    }

    private void handlerAccessNummber() {
        Integer currentAccess = getNumberAccess();
        if (currentAccess >= 10) {
            allowPokemonImage();
        } else {
            saveNumberAccess(++currentAccess);
        }
    }

    private void getAllPokemon() {
        Type type = null;

        if (getIntent().hasExtra("type")) {
            type = getIntent().getExtras().getParcelable("type");
        }

        ListOrder listOrder = getPokemonOrderPreference();
        new RecoveryPokemonAsyncTask(this, listOrder.getComparator(), type).execute();
    }

    private void getAllPokemon(Comparator<Pokemon> comparator) {
        Type type = null;

        if (getIntent().hasExtra("type")) {
            type = getIntent().getExtras().getParcelable("type");
        }

        new RecoveryPokemonAsyncTask(this, comparator, type).execute();
    }

    private void getCommunityDayMoves() {
        CommunityDayDAO.getInstance();
    }

    private void setUpNavigation(Toolbar toolbar) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            getIntent().removeExtra("type");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_order_by_number) {
            getAllPokemon(ListOrder.NUMBER.getComparator());
            savePokemonOrderPreference(ListOrder.NUMBER);
        } else if (id == R.id.action_order_by_name) {
            getAllPokemon(ListOrder.NAME.getComparator());
            savePokemonOrderPreference(ListOrder.NAME);
        } else if (id == R.id.action_order_max_cp) {
            getAllPokemon(ListOrder.MAX_CP.getComparator());
            savePokemonOrderPreference(ListOrder.MAX_CP);
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateRecyclerView() {
        RecyclerView.Adapter mAdapter = new PokemonAdapter(getPokemonList(), isImageAllowed());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    public List<Pokemon> getPokemonList() {
        return pokemonList;
    }

    public void setPokemonList(List<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
    }

    private void startActivity(Class<?> class_) {
        Intent intent = new Intent(this, class_);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        setPokemonList(search(newText));
        updateRecyclerView();
        return false;
    }

    private List<Pokemon> search(String query) {
        List<Pokemon> searchResult = new ArrayList<>();

        for (Pokemon pokemon : getAllPokemonList()) {
            if (pokemon.getName().toLowerCase().contains(query.toLowerCase())) {
                searchResult.add(pokemon);
            }
        }

        return searchResult;
    }

    public List<Pokemon> getAllPokemonList() {
        return allPokemonList;
    }

    public void setAllPokemonList(List<Pokemon> allPokemonList) {
        this.allPokemonList = allPokemonList;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_pokemon_type) {
            startActivity(PokemonTypeActivity.class);
        } else if (id == R.id.nav_about) {
            startActivity(AboutActivity.class);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void savePokemonOrderPreference(ListOrder pokemonListOrder) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.order_preference), pokemonListOrder.getIndex());
        editor.apply();
    }

    private ListOrder getPokemonOrderPreference() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        int index = sharedPref.getInt(getString(R.string.order_preference), 0);
        return ListOrder.asList().get(index);
    }

    private void saveNumberAccess(Integer number) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.number_access), number);
        editor.apply();
    }

    private Integer getNumberAccess() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getInt(getString(R.string.number_access), 0);
    }

    private void allowPokemonImage() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.allow_pokemon_image), true);
        editor.apply();
    }

    private Boolean isImageAllowed() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getBoolean(getString(R.string.allow_pokemon_image), false);
    }
}
