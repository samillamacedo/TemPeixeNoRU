package com.samyvan.tempeixenoru;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

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

        // Make sure topics are syncronized
        updateTopicSubscription();
    }

    public boolean getVegetarian(){
        return sharedPreferences.getBoolean("vegetarian", false);
    }

    public void setVegetarian(boolean isVegetarian){
        editor.putBoolean("vegetarian", isVegetarian);
        editor.commit();
        updateTopicSubscription();
    }

    public boolean getNotifyLunch(){
        return sharedPreferences.getBoolean("notifyLunch", true);
    }

    public void setNotifyLunch(boolean notifyLunch){
        editor.putBoolean("notifyLunch", notifyLunch);
        editor.commit();
        updateTopicSubscription();
    }

    public boolean getNotifyDinner(){
        return sharedPreferences.getBoolean("notifyDinner", true);
    }

    public void setNotifyDinner(boolean notifyDinner){
        editor.putBoolean("notifyDinner", notifyDinner);
        editor.commit();
        updateTopicSubscription();
    }

    private void updateTopicSubscription(){
        if(getNotifyLunch() && getVegetarian()){
            FirebaseMessaging.getInstance().subscribeToTopic("lunch_vegetarian");
            FirebaseMessaging.getInstance().unsubscribeFromTopic("lunch_meat");
        }else if(getNotifyLunch() && !getVegetarian()){
            FirebaseMessaging.getInstance().subscribeToTopic("lunch_meat");
            FirebaseMessaging.getInstance().unsubscribeFromTopic("lunch_vegetarian");
        }else{
            FirebaseMessaging.getInstance().unsubscribeFromTopic("lunch_vegetarian");
            FirebaseMessaging.getInstance().unsubscribeFromTopic("lunch_meat");
        }

        if(getNotifyDinner() && getVegetarian()){
            FirebaseMessaging.getInstance().subscribeToTopic("dinner_vegetarian");
            FirebaseMessaging.getInstance().unsubscribeFromTopic("dinner_meat");
        }else if(getNotifyDinner() && !getVegetarian()){
            FirebaseMessaging.getInstance().subscribeToTopic("dinner_meat");
            FirebaseMessaging.getInstance().unsubscribeFromTopic("dinner_vegetarian");
        }else{
            FirebaseMessaging.getInstance().unsubscribeFromTopic("dinner_vegetarian");
            FirebaseMessaging.getInstance().unsubscribeFromTopic("dinner_meat");
        }
    }

//    public boolean getIsCached() { return sharedPreferences.getBoolean("isCached", true);}
//
//    public void setIsCached(boolean isCached){
//        editor.putBoolean("isCached", isCached);
//        editor.commit();
//    }

}
