package br.ruanvictorreis.movesetgo.adapters;

import android.graphics.drawable.Drawable;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import br.ruanvictorreis.movesetgo.R;
import br.ruanvictorreis.movesetgo.model.FastMove;
import br.ruanvictorreis.movesetgo.model.ChargeMove;
import br.ruanvictorreis.movesetgo.model.DamageCalculator;
import br.ruanvictorreis.movesetgo.model.Moveset;
import br.ruanvictorreis.movesetgo.util.Formatter;

/**
 * RecyclerView adapter for Moveset
 * Created by Ruan on 19/02/2017.
 */
public class MovesetDefenseAdapter extends RecyclerView.Adapter<MovesetDefenseAdapter.ViewHolder> {

    private List<Moveset> movesetList;

    private Double highestDamageOnList;

    private DamageCalculator damageCalculator;

    public MovesetDefenseAdapter(List<Moveset> movesetList) {
        this.movesetList = movesetList;
        this.damageCalculator = new DamageCalculator();
        if (movesetList.size() > 0) prepareMovesetList();
    }

    private void prepareMovesetList() {
        for (int i = 0; i < movesetList.size(); i++) {
            Moveset moveset = movesetList.get(i);
            moveset.setMovesetDamage(getDefenseDamage(moveset));
            movesetList.set(i, moveset);
        }

        Collections.sort(movesetList);
        highestDamageOnList = movesetList.get(0).getMovesetDamage();
    }

    private Double getDefenseDamage(Moveset moveset) {
        return damageCalculator.gymWeaveDamage(moveset);
    }

    private String getPercentageFormatted(Double movesetDamage) {
        Double percentage = (movesetDamage / highestDamageOnList) * 100;
        return Formatter.doubleWithDecimals(percentage) + "%";
    }

    private String getDamagePerSecondFormatted(Double movesetDamage) {
        return Formatter.doubleWithDecimals(movesetDamage / 100) + " DPS";
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_moveset_recycler_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        //
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Moveset moveset = movesetList.get(position);
        FastMove fastMove = moveset.getFastMove();
        ChargeMove chargeMove = moveset.getChargeMove();

        holder.quickMoveName.setText(fastMove.getName());
        holder.mainMoveName.setText(chargeMove.getName());
        holder.quickMoveType.setImageResource(fastMove.getType().getMipmapResource());
        holder.mainMoveType.setImageResource(chargeMove.getType().getMipmapResource());

        int ranking = position + 1;
        String rankingPositionFormatted = String.valueOf(ranking + "º");
        holder.rankingPosition.setText(rankingPositionFormatted);

        holder.damagePercent.setText(getPercentageFormatted(moveset.getMovesetDamage()));
        holder.damagePerSecond.setText(getDamagePerSecondFormatted(moveset.getMovesetDamage()));

        if (!moveset.getUpdated()) {
            holder.movesetContainer.setBackgroundColor(holder.colorGray);
        } else {
            holder.movesetContainer.setBackground(holder.containerBackground);
        }
    }

    @Override
    public int getItemCount() {
        return movesetList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView quickMoveName, mainMoveName, rankingPosition;
        TextView damagePercent, damagePerSecond;
        ImageView quickMoveType, mainMoveType;
        RelativeLayout movesetContainer;

        Drawable containerBackground;
        int colorGray;

        ViewHolder(View view) {
            super(view);
            quickMoveName = (TextView) view.findViewById(R.id.basic_attack_name);
            mainMoveName = (TextView) view.findViewById(R.id.charge_attack_name);
            rankingPosition = (TextView) view.findViewById(R.id.ranking_position);
            damagePercent = (TextView) view.findViewById(R.id.damage_percent);
            damagePerSecond = (TextView) view.findViewById(R.id.moveset_dps);
            quickMoveType = (ImageView) view.findViewById(R.id.basicMoveType);
            mainMoveType = (ImageView) view.findViewById(R.id.chargeMoveType);
            movesetContainer = (RelativeLayout) view.findViewById(R.id.moveset_container);
            containerBackground = movesetContainer.getBackground();
            colorGray = view.getResources().getColor(R.color.colorGrayLight);
        }
    }
}
