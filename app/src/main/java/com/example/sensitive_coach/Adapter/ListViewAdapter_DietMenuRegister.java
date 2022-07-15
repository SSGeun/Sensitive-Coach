package com.example.sensitive_coach.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sensitive_coach.Model.DietMenu;
import com.example.sensitive_coach.R;

import java.util.ArrayList;

public class ListViewAdapter_DietMenuRegister extends BaseAdapter {

    private ArrayList<DietMenu> dietMenuArrayList = new ArrayList<DietMenu>();

    public ListViewAdapter_DietMenuRegister() {

    }

    @Override
    public int getCount() {

        return dietMenuArrayList.size();
    }

    @Override
    public Object getItem(int position) {

        return dietMenuArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Context context = parent.getContext();

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.diet_menu_register_item_list, parent, false);
        }

        TextView tvFoodName = (TextView) convertView.findViewById(R.id.tvFoodName);
        TextView tvServings = (TextView) convertView.findViewById(R.id.tvServings);
        TextView tvTimeAte = (TextView) convertView.findViewById(R.id.tvTimeAte);
        ImageButton deleteDietMenu = (ImageButton) convertView.findViewById(R.id.deleteDietMenu);

        DietMenu dietMenu = dietMenuArrayList.get(position);

        tvFoodName.setText(dietMenu.getFoodName());
        tvServings.setText(String.valueOf(dietMenu.getServings()));
        tvTimeAte.setText(dietMenu.getTimeAte());

        deleteDietMenu.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                dietMenuArrayList.remove(position);

                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    public void addDietMenu(int foodID, String foodName, double servings, double calorie, double protein, double fat, double carbohydrate,  String timeAte) {

        DietMenu dietMenu = new DietMenu(foodID, foodName, servings, calorie, protein, fat, carbohydrate, timeAte);

        dietMenuArrayList.add(dietMenu);
        notifyDataSetChanged();
    }

    public void removeDietMenu() {

        dietMenuArrayList.clear();
        notifyDataSetChanged();
    }

    public ArrayList<DietMenu> getDietMenuArrayList() {

        return dietMenuArrayList;
    }
}