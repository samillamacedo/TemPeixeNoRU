package com.samyvan.tempeixenoru;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ivanseidel on 8/9/16.
 */
public class AppSettings {

    Activity act;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public AppSettings(Activity act){
        this.act = act;
        sharedPreferences = act.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public boolean getVegetarian(){
        return sharedPreferences.getBoolean("vegetarian", false);
    }

    public void setVegetarian(boolean isVegetarian){
        editor.putBoolean("vegetarian", isVegetarian);
        editor.commit();
    }

    public boolean getNotifyLunch(){
        return sharedPreferences.getBoolean("notifyLunch", true);
    }

    public void setNotifyLunch(boolean notifyLunch){
        editor.putBoolean("notifyLunch", notifyLunch);
        editor.commit();
    }

    public boolean getNotifyDinner(){
        return sharedPreferences.getBoolean("notifyDinner", true);
    }

    public void setNotifyDinner(boolean notifyDinner){
        editor.putBoolean("notifyDinner", notifyDinner);
        editor.commit();
    }
}
