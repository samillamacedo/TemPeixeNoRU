package com.samyvan.tempeixenoru;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by ivanseidel on 8/20/16.
 */
public class MenuListAdapter extends ArrayAdapter<JSONObject>{

    private FragmentActivity context;

    public MenuListAdapter(FragmentActivity context, List<JSONObject> list ){
        super(context, R.layout.content_card, list);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = context.getLayoutInflater();

        FrameLayout card = (FrameLayout) inflater.inflate(R.layout.content_card, parent, false);
        LinearLayout layout = (LinearLayout) card.getChildAt(0);
        TextView title = (TextView) card.findViewById(R.id.refeicao);

        try {
            JSONObject data = getItem(position);
            title.setText(data.getString("group"));
            JSONArray itens = data.getJSONArray("items");

            for(int i = 0; i < itens.length(); i++) {
                LinearLayout foodItem =
                        (LinearLayout) inflater.inflate(R.layout.food_item, layout, false);
                final JSONObject item = itens.getJSONObject(i);

                final String foodTitle = item.getString("name");
                TextView itemTitle = (TextView) foodItem.findViewById(R.id.title);
                itemTitle.setText(foodTitle);

                ImageButton infoBtn = (ImageButton) foodItem.findViewById(R.id.infoIcon);
                if (item.isNull("info")) {
                    infoBtn.setVisibility(View.GONE);
                }else {
                    final String descricao = item.getString("info");
                    infoBtn.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            Bundle args = new Bundle();
                            args.putString("dialogTitle", foodTitle);
                            args.putString("descricao", descricao);
                            DialogFragment dialog = new InfoDialogFragment();
                            dialog.setArguments(args);
                            dialog.show(context.getFragmentManager(), "dialog");
                        }
                    });
                }

                layout.addView(foodItem);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return card;
    }


    public static class InfoDialogFragment extends DialogFragment
    {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            String descricao = getArguments().getString("descricao");
            String foodTitle = getArguments().getString("dialogTitle");
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(descricao).setTitle(foodTitle).setPositiveButton("OK", null);
            return builder.create();
        }
    }
}
