package br.ruanvictorreis.movesetgo.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.ruanvictorreis.movesetgo.R;
import br.ruanvictorreis.movesetgo.model.FastMove;
import br.ruanvictorreis.movesetgo.model.ChargeMove;
import br.ruanvictorreis.movesetgo.model.Moveset;
import br.ruanvictorreis.movesetgo.model.Pokemon;
import br.ruanvictorreis.movesetgo.util.Formatter;

/**
 * PokeDex Dialog - To show Pokemon information.
 * Created by Ruan on 10/01/2017.
 */

public class MovesetDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_moveset_detail, null);
        builder.setView(view);
        setCancelable(false);

        Moveset moveset = getActivity().getIntent().getExtras().getParcelable("moveset");
        FastMove fastMove = moveset.getFastMove();
        ChargeMove chargeMove = moveset.getChargeMove();

        Pokemon pokemon = getActivity().getIntent().getExtras().getParcelable("pokemon");

        TextView pokemonName = (TextView) view.findViewById(R.id.pokemon_name);
        TextView pokemonNumber = (TextView) view.findViewById(R.id.pokemon_number);

        String numberLabel = "#" + String.valueOf(pokemon.getNumber());
        pokemonNumber.setText(numberLabel);
        pokemonName.setText(pokemon.getName());

        ImageView quickMoveImage = (ImageView) view.findViewById(R.id.basicMoveType);
        ImageView mainMoveImage = (ImageView) view.findViewById(R.id.chargeMoveType);
        quickMoveImage.setImageResource(fastMove.getType().getMipmapResource());
        mainMoveImage.setImageResource(chargeMove.getType().getMipmapResource());

        TextView quickMoveName = (TextView) view.findViewById(R.id.basic_attack_name);
        TextView mainMoveName = (TextView) view.findViewById(R.id.charge_attack_name);
        quickMoveName.setText(fastMove.getName());
        mainMoveName.setText(chargeMove.getName());

        TextView quickMoveDps = (TextView) view.findViewById(R.id.move_quick_dps);
        TextView mainMoveDps = (TextView) view.findViewById(R.id.move_main_dps);
        quickMoveDps.setText(Formatter.doubleWithoutDecimals(fastMove.getDamage()));
        mainMoveDps.setText(Formatter.doubleWithoutDecimals(chargeMove.getDamage()));

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });

        return builder.create();
    }
}
