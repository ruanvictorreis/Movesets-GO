package br.ruanvictorreis.movesetgo.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import br.ruanvictorreis.movesetgo.R;
import br.ruanvictorreis.movesetgo.model.Pokemon;

/**
 * PokeDex Dialog - To show Pokemon information.
 * Created by Ruan on 10/01/2017.
 */

public class PokeDexDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_pokedex_information, null);
        builder.setView(view);
        setCancelable(false);

        Pokemon pokemon = getActivity().getIntent().getExtras().getParcelable("pokemon");

        TextView pokemonName = (TextView) view.findViewById(R.id.pokemon_name);
        TextView pokemonNumber = (TextView) view.findViewById(R.id.pokemon_number);

        TextView attack = (TextView) view.findViewById(R.id.attack);
        TextView defense = (TextView) view.findViewById(R.id.defense);
        TextView stamina = (TextView) view.findViewById(R.id.stamina);
        TextView overall = (TextView) view.findViewById(R.id.overall);
        TextView maxCp = (TextView) view.findViewById(R.id.maxCp);

        if (pokemon != null) {
            pokemonName.setText(pokemon.getName());

            String labelDialog = getString(R.string.hashtag) + String.valueOf(pokemon.getId());
            pokemonNumber.setText(labelDialog);

            attack.setText(String.valueOf(pokemon.getAttackRatio()));
            defense.setText(String.valueOf(pokemon.getDefenseRatio()));
            stamina.setText(String.valueOf(pokemon.getHpRatio()));
            overall.setText(String.valueOf(pokemon.getOverall()));
            maxCp.setText(String.valueOf(pokemon.getMaxCpCap()));
        }

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });

        return builder.create();
    }
}
