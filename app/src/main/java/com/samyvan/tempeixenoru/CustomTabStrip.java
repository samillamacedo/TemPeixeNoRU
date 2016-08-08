package com.samyvan.tempeixenoru;

import android.content.Context;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import java.text.AttributedCharacterIterator;

/**
 * Created by ivanseidel on 8/7/16.
 */
public class CustomTabStrip extends PagerTabStrip {
    public CustomTabStrip(Context context){
        this(context, null);

    }

    public CustomTabStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
        ((TextView) getChildAt(0)).setSingleLine(false);
        ((TextView) getChildAt(1)).setSingleLine(false);
        ((TextView) getChildAt(2)).setSingleLine(false);
        ((TextView) getChildAt(0)).setGravity(Gravity.CENTER_HORIZONTAL);
        ((TextView) getChildAt(1)).setGravity(Gravity.CENTER_HORIZONTAL);
        ((TextView) getChildAt(2)).setGravity(Gravity.CENTER_HORIZONTAL);


    }
}
