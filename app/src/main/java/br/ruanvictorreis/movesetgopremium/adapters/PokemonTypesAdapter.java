package br.ruanvictorreis.movesetgopremium.adapters;

import android.content.Context;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.ruanvictorreis.movesetgopremium.R;
import br.ruanvictorreis.movesetgopremium.model.Type;

/**
 * RecyclerView adapter for Pokemon Types
 * Created by Ruan on 29/11/2016.
 */
public class PokemonTypesAdapter extends RecyclerView.Adapter<PokemonTypesAdapter.ViewHolder> {

    private Context context;

    private List<Type> typeList;

    public PokemonTypesAdapter(Context context, List<Type> typeList) {
        this.context = context;
        this.typeList = typeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_type_recycler_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        //
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Type type = typeList.get(position);
        holder.typeName.setText(type.getName());
        holder.typeImage.setImageResource(type.getMipmapResource());

        RecyclerView.Adapter strongTypesAdapter = new TypePropertyAdapter(context,
                type.getStrengthChart());
        RecyclerView.Adapter weakTypesAdapter = new TypePropertyAdapter(context,
                type.getWeaknessChart());

        holder.strongTypesList.setAdapter(strongTypesAdapter);
        holder.weakTypesList.setAdapter(weakTypesAdapter);

        strongTypesAdapter.notifyDataSetChanged();
        weakTypesAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView typeImage;
        TextView typeName;

        RecyclerView strongTypesList;
        RecyclerView.LayoutManager strongTypesLayoutManager;

        RecyclerView weakTypesList;
        RecyclerView.LayoutManager weakTypesLayoutManager;

        ViewHolder(View view) {
            super(view);
            typeName = (TextView) view.findViewById(R.id.type_name);
            typeImage = (ImageView) view.findViewById(R.id.type_image);

            strongTypesLayoutManager = new LinearLayoutManager(context,
                    LinearLayoutManager.HORIZONTAL, false);

            weakTypesLayoutManager = new LinearLayoutManager(context,
                    LinearLayoutManager.HORIZONTAL, false);

            strongTypesList = (RecyclerView) view.findViewById(R.id.types_strength_recycler);
            strongTypesList.setLayoutManager(strongTypesLayoutManager);
            strongTypesList.setHasFixedSize(true);

            weakTypesList = (RecyclerView) view.findViewById(R.id.types_weakness_recycler);
            weakTypesList.setLayoutManager(weakTypesLayoutManager);
            weakTypesList.setHasFixedSize(true);
        }
    }
}
