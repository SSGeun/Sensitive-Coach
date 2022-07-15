package com.example.sensitive_coach.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sensitive_coach.Model.Exercise;
import com.example.sensitive_coach.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017-08-07.
 */

public class SearchAdapter extends BaseAdapter {
    //private Context context;
    private ArrayList<Exercise> list = new ArrayList<Exercise>();

    //private LayoutInflater inflate;
    //private ViewHolder viewHolder;

//    public SearchAdapter(List<String> list, Context context){
//        this.list = list;
//        this.context = context;
//        this.inflate = LayoutInflater.from(context);
//    }

    public SearchAdapter() {
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.row_listview, viewGroup, false);
        }
        //viewHolder = new ViewHolder();
        //viewHolder.img = (ImageView) convertView.findViewById(R.id.img);
        //viewHolder.label = (TextView) convertView.findViewById(R.id.label);
        //viewHolder.desc = (TextView) convertView.findViewById(R.id.sub);


        ImageView img = (ImageView) convertView.findViewById(R.id.img);
        TextView label = (TextView) convertView.findViewById(R.id.label);
        //TextView desc = (TextView) convertView.findViewById(R.id.sub);

        Exercise exercise = list.get(position);

        img.setTag(exercise.getExerciseVideopath());// 태그
        if (position == 0) {
            img.setImageResource(R.drawable.squat_main);
        } else {
            img.setImageResource(R.drawable.ex2);
        }
        label.setText(exercise.getExerciseName()); // 리스트뷰에서 이름.. 뭐.. 이런거 나오는 부분
        //desc.setText(exercise.getExerciseClassification());

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBrowser(v);
            }
        });


//
//                }
//                                     });
        //convertView.setTag(viewHolder);
//        else{
//            viewHolder = (ViewHolder)convertView.getTag();
//        }

//        viewHolder.img.setImageResource(R.drawable.ex1);
//        viewHolder.label.setText("헤헤헤헤헤");
//        viewHolder.desc.setText("크크크크");

        // 리스트에 있는 데이터를 리스트뷰 셀에 뿌린다.
        //viewHolder.label.setText(list.get(position));
//        if (list.get(position).equals("업도미널 크런치"))
//        {
//            viewHolder.img.setImageResource(R.drawable.ex1);
//            viewHolder.desc.setText(Html.fromHtml("<strong>"+"분류"+"</strong>"+" 상체운동" + "<br />" + "<strong>" +"권장횟수"+"</strong>"+" 20회 1세트"));
//        }
//        if(list.get(position).equals("스케이트 런지"))
//        {
//            viewHolder.img.setImageResource(R.drawable.ex2);
//            viewHolder.desc.setText(Html.fromHtml("<strong>"+"분류"+"</strong>"+" 하체운동" + "<br />" + "<strong>" +"권장횟수"+"</strong>"+" 15회 3세트"));
//
//        }
        return convertView;
    }


    public void addExercise(ArrayList<Exercise> tmp) {

        list.addAll(tmp);
    }
    /*
    class ViewHolder{
        public TextView label;
        public TextView desc;
        public ImageView img;

    }
*/

    public void removeExercise() {

        list.clear();
    }

    public void openBrowser(View view) {

        //Get url from tag
        String url = (String) view.getTag();

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);

        //pass the url to intent data
        intent.setData(Uri.parse(url));

        view.getContext().startActivity(intent);
    }

    public void replaceFragment() {

        // 프레그먼트를 여기서 넘겨주나.. 잊지말자 이건 메인 어댑터


    }
}