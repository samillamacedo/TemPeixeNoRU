package com.samyvan.tempeixenoru;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class AboutActivity extends AppCompatActivity {

    private ImageView easterEgg;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        easterEgg = (ImageView) findViewById(R.id.easter_egg);

        setTitle("Sobre");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                easterEgg.setVisibility(View.VISIBLE);
            }
        }, 20000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                easterEgg.setVisibility(View.GONE);
            }
        }, 20200);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
