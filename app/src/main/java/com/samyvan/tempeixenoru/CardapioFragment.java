package com.samyvan.tempeixenoru;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ivanseidel on 8/7/16.
 */
public class CardapioFragment extends Fragment{
    private String dia;

    public static CardapioFragment newInstance(String dia){
        Bundle args = new Bundle();
        args.putString("dia", dia);

        CardapioFragment fragment = new CardapioFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        dia = getArguments().getString("dia");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.content_card, container, false);
//        TextView textView = (TextView) view.findViewById(R.id.textView);
//        textView.setText(dia);
        return view;
    }
}
