package com.example.sensitive_coach.Adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sensitive_coach.Model.Exercise;
import com.example.sensitive_coach.R;


import java.util.ArrayList;

public class ListViewAdapter_ExerciseSearch extends BaseAdapter {

    private Context context;
    private ArrayList<Exercise> exerciseArrayList;
    private LayoutInflater layoutInflater;
    private ViewHolder viewHolder;

    public ListViewAdapter_ExerciseSearch(ArrayList<Exercise> exerciseArrayList,Context context){

        this.exerciseArrayList=exerciseArrayList;
        this.context=context;
        this.layoutInflater=LayoutInflater.from(context);

    }
    @Override
    public int getCount(){
        return exerciseArrayList.size();
    }
    @Override
    public Object getItem(int position){
        return exerciseArrayList.get(position);
    }
    @Override
    public long getItemId(int position){
        return position;
    }
    public String getClickExerciseName(int position){
        return exerciseArrayList.get(position).getExerciseName();
    }
    //    public int getClickExerciseID(int position){
//        return exerciseArrayList.get(position).getExerciseID();
//
//    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView==null){

            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.exercises_search_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.exerciseSearchItem=(TextView)convertView.findViewById(R.id.exerciseSearchItem);
            convertView.setTag(viewHolder);
        }
        else{

            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.exerciseSearchItem.setText(exerciseArrayList.get(position).getExerciseName());
        return convertView;
    }

    class ViewHolder{
        public TextView exerciseSearchItem;
    }
}