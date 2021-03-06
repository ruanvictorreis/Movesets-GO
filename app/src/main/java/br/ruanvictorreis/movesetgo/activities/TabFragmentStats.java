package br.ruanvictorreis.movesetgo.activities;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;

import br.ruanvictorreis.movesetgo.R;
import br.ruanvictorreis.movesetgo.model.Pokemon;

public class TabFragmentStats extends Fragment {

    private AdView mAdView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_stats, container, false);

        Bundle extras = new Bundle();
        extras.putString("max_ad_content_rating", "G");

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                .build();

        mAdView.loadAd(adRequest);

        Pokemon pokemon = getActivity().getIntent().getExtras().getParcelable("pokemon");
        Boolean isImageAllowed = getActivity().getIntent().getExtras().getBoolean("imageAllowed");

        TextView pokemonNumber = view.findViewById(R.id.pokemon_number);

        TextView attack = view.findViewById(R.id.attack);
        TextView defense = view.findViewById(R.id.defense);
        TextView stamina = view.findViewById(R.id.stamina);
        TextView overall = view.findViewById(R.id.overall);
        TextView maxCp = view.findViewById(R.id.maxCp);
        ImageView circleImageView = view.findViewById(R.id.pokemon_image);

        if (pokemon != null) {
            String labelDialog = getString(R.string.hashtag) + String.valueOf(pokemon.getNumber());
            pokemonNumber.setText(labelDialog);

            attack.setText(String.valueOf(pokemon.getAttackRatio()));
            defense.setText(String.valueOf(pokemon.getDefenseRatio()));
            stamina.setText(String.valueOf(pokemon.getHpRatio()));
            overall.setText(String.valueOf(pokemon.getOverall()));
            maxCp.setText(String.valueOf(pokemon.getMaxCpCap()));

            Picasso.with(view.getContext())
                    .load(pokemon.getPicture())
                    .into(circleImageView);

            if (isImageAllowed) {
                circleImageView.setVisibility(View.VISIBLE);
            }
        }

        return view;
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
}
