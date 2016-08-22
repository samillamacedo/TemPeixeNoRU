package com.samyvan.tempeixenoru;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ivanseidel on 8/7/16.
 */
public class CardapioFragment extends Fragment{
    private int position;
    MenuListAdapter menuListAdapter;
    ListView listView;
    View view;

    public static CardapioFragment newInstance(int position){
        Bundle args = new Bundle();
        args.putInt("dia", position);

        CardapioFragment fragment = new CardapioFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("dia");


        try {
            ArrayList<JSONObject> list = new ArrayList<JSONObject>();
            JSONArray menus = CacheController.getSigleton().getCachedCardapio().getJSONArray("menu");
            JSONObject menu = menus.getJSONObject(position);
            JSONArray meals = menu.getJSONArray("meals");

            for(int i = 0; i < meals.length(); i++){
                list.add(meals.getJSONObject(i));
            }

            listView = (ListView) view.findViewById(R.id.listView);
            menuListAdapter = new MenuListAdapter(getActivity(), list);
            listView.setAdapter(menuListAdapter);
            listView.setDivider(null);
            listView.setDividerHeight(0);


        }catch (Exception exc){
            exc.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_cardapio, container, false);
        return view;
    }

}
