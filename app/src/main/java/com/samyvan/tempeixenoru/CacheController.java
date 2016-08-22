package com.samyvan.tempeixenoru;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ivanseidel on 8/9/16.
 */
public class CacheController {

    JSONObject _cachedJSON = null;
    UpdateCacheListener listener;
    Activity act;

    public static final String URL_CARDAPIO = "http://tempeixenoru.herokuapp.com/api/v1/cardapio.json";

    private static CacheController _cacheController;
    public static CacheController getSigleton(){
        return _cacheController;
    }

    public static interface UpdateCacheListener {
        void onUpdateCacheStarted();
        void onUpdateCacheEnded(boolean error);
    }

    public class FetchDataTask extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected Boolean doInBackground(String... URLs){

            try {
                URL url= new URL(URLs[0]);
                HttpURLConnection httpClient = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(httpClient.getInputStream());
                String json = IOUtils.toString(in, "UTF-8");
                JSONObject parsedJSON = new JSONObject(json);
                writeJSON("cardapio", parsedJSON);

            }catch (Exception exc){
                Log.e("CatcheController", exc.toString());
                return false;
            }
            return true;
        }
        @Override
        protected void onPostExecute (Boolean sucess) {
            _cachedJSON = null;
            if(listener != null) {
                listener.onUpdateCacheEnded(!sucess);
            }
        }
    }


    private void writeJSON(String key, JSONObject jsonObject) throws IOException {
        FileOutputStream fos = act.openFileOutput(key, Context.MODE_PRIVATE);
        fos.write(jsonObject.toString().getBytes());
        fos.flush();
        fos.close();
    }

    private JSONObject readJSON(String key) throws IOException, JSONException {
        if(_cachedJSON != null)
            return _cachedJSON;

        FileInputStream fis = act.openFileInput(key);
        int size = fis.available();
        byte[] buffer = new byte[size];
        fis.read(buffer);
        fis.close();
        String data = new String(buffer, "UTF-8");

        _cachedJSON = new JSONObject(data);
        return _cachedJSON;
    }

    public CacheController(Activity act) {
        this.act = act;
        _cacheController = this;
    }
    public JSONObject getCachedCardapio() {
        try {
            return readJSON("cardapio");
        }catch (Exception exc){
            Log.e("CatcheController", exc.toString());
            return null;
        }
    }

    public void updateCache() {
        if(listener != null)
            listener.onUpdateCacheStarted();
        new FetchDataTask().execute(URL_CARDAPIO);
    }

    public void setUpdateCacheListener(UpdateCacheListener list){
        listener = list;
    }

}
