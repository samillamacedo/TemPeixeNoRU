package com.samyvan.tempeixenoru;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;



public class MainActivity extends AppCompatActivity implements CacheController.UpdateCacheListener {
    Toolbar toolbar;
    ViewPager pageCardapio;
    CardapioTabsAdapter cardapioAdapter;
    CustomTabStrip tabStrip;
    AppSettings settings;
    CacheController cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.cardapio);
        toolbar.setNavigationIcon(R.drawable.ic_logo);
        setSupportActionBar(toolbar);

        pageCardapio = (ViewPager) findViewById(R.id.pager_cardapio);
        cardapioAdapter = new CardapioTabsAdapter(getSupportFragmentManager());
        pageCardapio.setAdapter(cardapioAdapter);

        tabStrip = (CustomTabStrip) findViewById(R.id.tab_strip);
        tabStrip.setTabIndicatorColor(0x2196F3);

        settings = new AppSettings(this);

        cache = new CacheController(this);
        cache.setUpdateCacheListener(this);
        cache.updateCache();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.notificarAlmoco).setChecked(settings.getNotifyLunch());
        menu.findItem(R.id.notificarJanta).setChecked(settings.getNotifyDinner());
        menu.findItem(R.id.vegetarian).setChecked(settings.getVegetarian());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Log.d("MainActivity", "clicked: "+id);

        //noinspection SimplifiableIfStatement
        if (id == R.id.vegetarian) {
            settings.setVegetarian(!settings.getVegetarian());
            item.setChecked(settings.getVegetarian());
            return true;
        }

        if (id == R.id.notificarAlmoco) {
            settings.setNotifyLunch(!settings.getNotifyLunch());
            item.setChecked(settings.getNotifyLunch());
            return true;
        }

        if (id == R.id.notificarJanta) {
            settings.setNotifyDinner(!settings.getNotifyDinner());
            item.setChecked(settings.getNotifyDinner());
            return true;
        }

        if (id == R.id.about){
            Intent myIntent = new Intent(this, AboutActivity.class);
            this.startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void onUpdateCacheEnded(boolean error) {
        if (error) {
            Log.d("MainActivity", "Erro(butOk)");
        } else {
            Log.d("MainActivity", "just Ok");
            Log.d("MainActivity", cache.getCachedCardapio().toString());
        }
    }

    public void onUpdateCacheStarted(){
        Log.d("MainActivity", "Started");
    }
}
