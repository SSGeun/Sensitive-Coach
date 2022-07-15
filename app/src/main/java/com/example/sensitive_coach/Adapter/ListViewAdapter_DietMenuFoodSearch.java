package com.example.sensitive_coach.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sensitive_coach.R;
import com.example.sensitive_coach.Room.Entity.Food;

import java.util.ArrayList;

public class ListViewAdapter_DietMenuFoodSearch extends BaseAdapter {

    private Context context;
    private ArrayList<Food> foodArrayList;
    private LayoutInflater layoutInflater;
    private ViewHolder viewHolder;


    public ListViewAdapter_DietMenuFoodSearch(ArrayList<Food> foodArrayList, Context context) {

        this.foodArrayList = foodArrayList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

        return foodArrayList.size();
    }

    @Override
    public Object getItem(int position) {

        return foodArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    public String getClickFoodName(int position) {

        return foodArrayList.get(position).getFoodName();
    }

    public int getClickFoodID(int position) {

        return foodArrayList.get(position).getFoodID();
    }

    public double getClickFoodCalorie(int position) {

        return foodArrayList.get(position).getCaloridPerAmount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.diet_menu_register_search_item_list, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.dietMenuSearchItem = (TextView) convertView.findViewById(R.id.dietMenuSearchItem);

            convertView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.dietMenuSearchItem.setText(foodArrayList.get(position).getFoodName());

        return convertView;
    }

    class ViewHolder {

        public TextView dietMenuSearchItem;
    }
}