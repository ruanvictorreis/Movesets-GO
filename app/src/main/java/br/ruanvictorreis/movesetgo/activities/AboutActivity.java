package br.ruanvictorreis.movesetgo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.ruanvictorreis.movesetgo.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
