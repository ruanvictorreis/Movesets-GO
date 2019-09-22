package br.ruanvictorreis.movesetgopremium.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import br.ruanvictorreis.movesetgopremium.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
