package com.samyvan.tempeixenoru;

import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;

/**
 * Created by ivanseidel on 8/7/16.
 */
public class CardapioTabsAdapter extends FragmentPagerAdapter {

    public static final String[] DIAS_SEMANA = {
        "SEGUNDA", "TERÇA", "QUARTA", "QUINTA", "SEXTA", "SÁBADO"
    };

    public CardapioTabsAdapter (FragmentManager fragmentManager){
        super(fragmentManager);
    }


    @Override
    public  CharSequence getPageTitle(int position){
        SpannableStringBuilder spanstr = new SpannableStringBuilder(DIAS_SEMANA[position]);
        spanstr.setSpan(new StyleSpan(Typeface.BOLD), 0, spanstr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanstr.append("\n");
        spanstr.append("08/09");

        return spanstr;
    }

    @Override
    public Fragment getItem(int position) {
        return CardapioFragment.newInstance(DIAS_SEMANA[position]);
    }

    @Override
    public int getCount() {
        return DIAS_SEMANA.length;
    }


}

