package br.ruanvictorreis.movesetgo.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import br.ruanvictorreis.movesetgo.R;

public class GetPremiumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_premium);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView imageView = (ImageView) findViewById(R.id.get_premium_image);
        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openPlayStore();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPlayStore();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void openPlayStore(){
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("market://details?id=br.ruanvictorreis.movesetgopremium.app"));
        startActivity(intent);
    }
}
