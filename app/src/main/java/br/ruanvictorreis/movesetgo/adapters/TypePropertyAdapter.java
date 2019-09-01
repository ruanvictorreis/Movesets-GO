package br.ruanvictorreis.movesetgo.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import br.ruanvictorreis.movesetgo.R;
import br.ruanvictorreis.movesetgo.model.Type;

/**
 * RecyclerView adapter for Pokemon Types
 * Created by Ruan on 29/11/2016.
 */
public class TypePropertyAdapter extends RecyclerView.Adapter<TypePropertyAdapter.ViewHolder> {

    private Context context;

    private List<Type> typeList;

    public TypePropertyAdapter(Context context, List<Type> typeList) {
        this.context = context;
        this.typeList = typeList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_type_properties, parent, false);
        // set the view's size, margins, paddings and layout parameters
        //
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Type type = typeList.get(position);
        holder.typeImage.setImageResource(type.getMipmapResource());
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView typeImage;

        public ViewHolder(View view) {
            super(view);
            typeImage = (ImageView) view.findViewById(R.id.typeProperty);
        }
    }
}
