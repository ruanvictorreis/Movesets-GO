package br.ruanvictorreis.movesetgo.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.ruanvictorreis.movesetgo.R;
import br.ruanvictorreis.movesetgo.model.Pokemon;
import br.ruanvictorreis.movesetgo.model.Type;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * RecyclerView adapter for Pokemon
 * Created by Ruan on 08/10/2016.
 */
public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {

    private List<Pokemon> pokemonList;

    private Context context;

    private Boolean imageAllowed;

    public PokemonAdapter(List<Pokemon> pokemonList, Boolean imageAllowed) {
        this.pokemonList = pokemonList;
        this.imageAllowed = imageAllowed;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_pokemon_recycler_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        //
        context = v.getContext();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Pokemon pokemon = pokemonList.get(position);
        holder.pokemonName.setText(pokemon.getName());

        String pokeNumber = "#" + String.valueOf(pokemon.getNumber());
        holder.pokemonNumber.setText(pokeNumber);

        Type typeOne = pokemon.getTypeOne();
        holder.pokemonTypeOne.setImageResource(typeOne.getMipmapResource());

        Picasso.with(context)
                .load(pokemon.getPicture())
                .into(holder.circleImageView);

        if (imageAllowed) {
            holder.circleImageView.setVisibility(View.VISIBLE);
        }

        Type typeTwo = pokemon.getTypeTwo();
        if (typeTwo != null) {
            holder.pokemonTypeTwo.setImageResource(typeTwo.getMipmapResource());
            holder.pokemonTypeTwo.setVisibility(View.VISIBLE);
        } else {
            holder.pokemonTypeTwo.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView pokemonName, pokemonNumber;
        ImageView pokemonTypeOne, pokemonTypeTwo;
        CircleImageView circleImageView;

        ViewHolder(View view) {
            super(view);
            pokemonName = (TextView) view.findViewById(R.id.pokemon_name);
            pokemonNumber = (TextView) view.findViewById(R.id.pokemon_number);
            pokemonTypeOne = (ImageView) view.findViewById(R.id.pokemonTypeOne);
            pokemonTypeTwo = (ImageView) view.findViewById(R.id.pokemonTypeTwo);
            circleImageView = (CircleImageView) view.findViewById(R.id.pokemon_image);
        }
    }
}
