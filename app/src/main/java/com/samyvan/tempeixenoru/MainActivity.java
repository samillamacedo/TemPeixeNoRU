package com.samyvan.tempeixenoru;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements CacheController.UpdateCacheListener {
    Toolbar toolbar;
    ViewPager pageCardapio;
    CardapioTabsAdapter cardapioAdapter;
    CustomTabStrip tabStrip;
    AppSettings settings;
    CacheController cache;
    int date = date(day(),month());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.cardapio);
        toolbar.setNavigationIcon(R.drawable.ic_logo);
        setSupportActionBar(toolbar);

        if(cache == null) {
            cache = new CacheController(this);
            cache.setUpdateCacheListener(this);
            cache.updateCache();

        }

        pageCardapio = (ViewPager) findViewById(R.id.pager_cardapio);
        cardapioAdapter = new CardapioTabsAdapter(getSupportFragmentManager(), cache);
        pageCardapio.setAdapter(cardapioAdapter);
        pageCardapio.setCurrentItem(nearDatePosition(date));

        tabStrip = (CustomTabStrip) findViewById(R.id.tab_strip);
        tabStrip.setTabIndicatorColor(0x2196F3);

        settings = new AppSettings(this);

//        FirebaseMessaging.getInstance().subscribeToTopic("developer");
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
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

        if (id == R.id.refresh) {
            cache.updateCache();
        }
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
            Toast.makeText(getApplicationContext(), "Falha ao atualizar o cardápio", Toast.LENGTH_LONG).show();
        } else {
            cardapioAdapter.notifyDataSetChanged();
            Toast.makeText(getApplicationContext(), "Cardapio Atualizado", Toast.LENGTH_LONG).show();
            pageCardapio.setCurrentItem(nearDatePosition(date));
        }
    }

    public void onUpdateCacheStarted(){

    }

    public int day() {
        SimpleDateFormat df = new SimpleDateFormat("dd");
        Calendar c = Calendar.getInstance();
        String todayDate = df.format(c.getTime());
        int parsedDay = Integer.parseInt(todayDate);
        // formattedDate have current date/time
        //Toast.makeText(this, todayDate, Toast.LENGTH_SHORT).show();
        return parsedDay;
    }

    public int month() {
        SimpleDateFormat df = new SimpleDateFormat("MM");
        Calendar c = Calendar.getInstance();
        String todayDate = df.format(c.getTime());
        int parsedMonth = Integer.parseInt(todayDate);
        return parsedMonth;
    }

    public int date(int day, int month){
        return month*100+day;
    }

    public int parseIntDate(String strDate){
        String delims = "[/]";
        String[] tokens = strDate.split(delims);
        int day = Integer.parseInt(tokens[0]);
        int month = Integer.parseInt(tokens[1]);
        int parseIntDate = date(day, month);
        return parseIntDate;
    }
    //retorna a Data posicao da data mais proxima do cardapio da data atual
    public int nearDatePosition(int date) {

        try {
            JSONArray menu = cache.getCachedCardapio().getJSONArray("menu");
            int nearDatePosition = 10;
            int length = menu.length();
            String lastDate = menu.getJSONObject(length - 1).getString("date");

            int intLastDate = parseIntDate(lastDate);
            //compara as datas para retornar a data mais proxima
            if (date >= intLastDate) {
                nearDatePosition = length;
                Log.d("SABADO", String.valueOf(nearDatePosition));
            } else if(date < intLastDate) {
                for (int i = 0; i < length; i++) {
                    String menuDate = menu.getJSONObject(i).getString("date");
                    int intDate = parseIntDate(menuDate);
                    if (date == intDate) {
                        nearDatePosition = i;
                        Log.d("PROXIMA DATA", String.valueOf(nearDatePosition));
                    }
                }
                if (nearDatePosition == 10){
                    nearDatePosition = 0;
                    Log.d("OUTRA DATA", String.valueOf(nearDatePosition));
                }
            }
            return nearDatePosition;

        } catch (Exception exc) {}return 0;
    }

}