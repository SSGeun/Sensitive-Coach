package com.example.sensitive_coach.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sensitive_coach.R;
import com.example.sensitive_coach.Room.Entity.DietMenu;

import java.util.ArrayList;

public class ListViewAdapter_DietMenuInquiry extends BaseAdapter {

    private ArrayList<DietMenu> dietMenuArrayList;
    private Context context;

    public ListViewAdapter_DietMenuInquiry(ArrayList<DietMenu> dietMenuArrayList, Context context) {

        this.dietMenuArrayList = dietMenuArrayList;
        this.context = context;
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
            convertView = layoutInflater.inflate(R.layout.diet_menu_inquiry_item_list, parent, false);
        }

        TextView inquiryTimeAte = (TextView) convertView.findViewById(R.id.inquiryTimeAte);
        ImageView inquiryFoodImage = (ImageView) convertView.findViewById(R.id.inquiryFoodImage);
        TextView inquiryFoodNamePerServings = (TextView) convertView.findViewById(R.id.inquiryFoodNamePerServings);

        DietMenu dietMenu = dietMenuArrayList.get(position);

        if (position == 0) {

            inquiryTimeAte.setText(dietMenu.getIntakeTime());
            // 이미지뷰는 식품군에 따라 고기, 채소 분류하기

            String foodNamePerServings;
            foodNamePerServings = dietMenu.getFoodName() + " / " + dietMenu.getTotalCalorie() + "kcal";

            inquiryFoodNamePerServings.setText(foodNamePerServings);
        }

        else {

            if (dietMenu.getIntakeTime().equals(dietMenuArrayList.get(position - 1).getIntakeTime())) {

                inquiryTimeAte.setVisibility(View.GONE);

                String foodNamePerServings;
                foodNamePerServings = dietMenu.getFoodName() + " / " + dietMenu.getTotalCalorie() + "kcal";

                inquiryFoodNamePerServings.setText(foodNamePerServings);
            }

            else {

                inquiryTimeAte.setText(dietMenu.getIntakeTime());
                // 이미지뷰는 식품군에 따라 고기, 채소 분류하기

                String foodNamePerServings;
                foodNamePerServings = dietMenu.getFoodName() + " / " + dietMenu.getTotalCalorie() + "kcal";

                inquiryFoodNamePerServings.setText(foodNamePerServings);
            }
        }

        return convertView;
    }
}
