package com.samyvan.tempeixenoru;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by ivanseidel on 8/7/16.
 */
public class CardapioTabsAdapter extends FragmentPagerAdapter {

//    public static String DIAS_SEMANA[] = {"SEGUNDA", "TER", "QUA", "QUI", "SEX"};
    public CacheController cacheController;
    private JSONArray menus;

    public CardapioTabsAdapter (FragmentManager fragmentManager, CacheController cacheController){
        super(fragmentManager);
        this.cacheController = cacheController;

        notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetChanged() {
        try {
            menus = cacheController.getCachedCardapio().getJSONArray("menu");
        }catch(Exception e){
            menus = new JSONArray();
        }
        super.notifyDataSetChanged();
    }

    @Override
    public  CharSequence getPageTitle(int position){

        try {
            JSONObject menu = menus.getJSONObject(position);
            String day = menu.getString("day");
            String date = menu.getString("date");

            SpannableStringBuilder spanstr = new SpannableStringBuilder(day);
            spanstr.setSpan(new StyleSpan(Typeface.BOLD), 0, spanstr.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spanstr.append("\n");
            spanstr.append(date);
            return spanstr;
        }catch(Exception exc){
            return "Carregando...";
        }
    }

    @Override
    public Fragment getItem(int position) {
        try {
            return CardapioFragment.newInstance(position);
        } catch (Exception exc) {
            return null;
        }
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        try {
            if(menus.length() <= 0)
                // Return at least 1, so that we have a "loading" title
                return 1;
            else
                return menus.length();
        }catch (Exception exc) {
            return 0;
        }
    }


}

