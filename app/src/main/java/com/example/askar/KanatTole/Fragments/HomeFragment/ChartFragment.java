package com.example.askar.KanatTole.Fragments.HomeFragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.askar.task3bottomnavigationview.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;

import java.util.ArrayList;


public class ChartFragment extends Fragment {
    static TextView button_day,button_weak,button_month,button_year;
    static TextView  doxod_sum,prodazha_sum,rasxod_sum;
    TextView sum_of_product1,sum_of_product2,sum_of_product3,sum_of_product4;
    ArrayList<TextView> textViews=new ArrayList<>();;
    //LineChart
    static LineChart lineChart;

    public ChartFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_chart, container, false);

        doxod_sum=v.findViewById(R.id.doxod_sum);
        prodazha_sum=v.findViewById(R.id.prodazha_sum);
        rasxod_sum=v.findViewById(R.id.rasxod_sum);


        sum_of_product1=v.findViewById(R.id.sum_text1);
        sum_of_product2=v.findViewById(R.id.sum_text2);
        sum_of_product3=v.findViewById(R.id.sum_text3);
        sum_of_product4=v.findViewById(R.id.sum_text4);
        textViews.add(sum_of_product1);
        textViews.add(sum_of_product2);
        textViews.add(sum_of_product3);
        textViews.add(sum_of_product4);


        lineChart=v.findViewById(R.id.chart);
        lineChart.setDescription("");
        lineChart.getXAxis().setLabelsToSkip(0);
        lineChart.setBorderColor(Color.parseColor("#fafafa"));
        lineChart.setGridBackgroundColor(Color.parseColor("#fafafa"));
        lineChart.getAxisRight().setEnabled(false);
        lineChart.setTouchEnabled(false);
        lineChart.getXAxis().setTextSize(6f);
        button_day=v.findViewById(R.id.button_1);
        button_weak=v.findViewById(R.id.button_2);
        button_month=v.findViewById(R.id.button_3);
        button_year=v.findViewById(R.id.button_4);
        button_day.setBackgroundResource(R.drawable.tab_button_home_page);
        showDataOfDay();
        button_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDataOfDay();

            }
        });
        button_weak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDataOfWeak();
            }
        });
        button_month.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                showDataOfMOnth();

            }
        });
        button_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDataOfYear();
            }
        });
        return v;
    }

    public void showDataOfDay() {
        lineChart.removeAllViews();
        ArrayList<String> strings1=new ArrayList<>();
        strings1.add("25 999 тг");
        strings1.add("51");
        strings1.add("5 999 тг");
        strings1.add("2 545 тг");
        for(int i=0;i<textViews.size();i++){
            textViews.get(i).setText(strings1.get(i));
        }


        button_day.setBackgroundResource(R.drawable.tab_button_home_page);
        button_day.setTextColor(Color.parseColor("#fafafa"));
        button_month.setBackgroundResource(R.color.white);
        button_month.setTextColor(Color.parseColor("#635d5d"));
        button_weak.setBackgroundResource(R.color.white);
        button_weak.setTextColor(Color.parseColor("#635d5d"));
        button_year.setBackgroundResource(R.color.white);
        button_year.setTextColor(Color.parseColor("#635d5d"));
        //ArrayList<Entry> entries=getTimeData(getActivity(),"day").getEntries();
        ArrayList<Entry> entries=new ArrayList<>();
        entries.add(new Entry(120,0));
        entries.add(new Entry(120,1));
        entries.add(new Entry(120,2));
        entries.add(new Entry(120,3));
        entries.add(new Entry(120,4));
        entries.add(new Entry(120,5));
        entries.add(new Entry(120,6));
        entries.add(new Entry(120,7));

        //ArrayList<String> labels=getTimeData(getActivity(),"day").getLabels();
        ArrayList<String> labels=new ArrayList<>();
        labels.add("00 - 03");
        labels.add("03 - 06");
        labels.add("06 - 09");
        labels.add("09 - 12");
        labels.add("12 - 15");
        labels.add("15 - 18");
        labels.add("18 - 21");
        labels.add("21 - 24");

        LineDataSet dataset = new LineDataSet(entries, "День");

        LineData data = new LineData(labels,dataset);
        dataset.setDrawValues(false);
        dataset.setDrawCubic(true);
        dataset.setDrawFilled(true);
        dataset.setColor(Color.parseColor("#8be64d3c"));
        dataset.setFillColor(Color.parseColor("#a24ebac7"));

        lineChart.animateY(1000);
        lineChart.setScaleEnabled(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);

        lineChart.setData(data);
        TimeTole timeTole=new TimeTole(strings1,entries,labels);
        saveTimeData(getActivity().getApplicationContext(),"day",timeTole);



    }
    public  void showDataOfWeak(){
        lineChart.removeAllViews();
        ArrayList<String> strings2=new ArrayList<>();
        strings2.add("45 999 тг");
        strings2.add("45");
        strings2.add("5 554 тг");
        strings2.add("2 556 тг");
        for(int i=0;i<textViews.size();i++){
            textViews.get(i).setText(strings2.get(i));
        }


        button_weak.setBackgroundResource(R.drawable.tab_button_home_page);
        button_weak.setTextColor(Color.parseColor("#fafafa"));
        button_month.setBackgroundResource(R.color.white);
        button_month.setTextColor(Color.parseColor("#635d5d"));

        button_day.setBackgroundResource(R.color.white);
        button_day.setTextColor(Color.parseColor("#635d5d"));

        button_year.setBackgroundResource(R.color.white);
        button_year.setTextColor(Color.parseColor("#635d5d"));

        //ArrayList<Entry> entries=getTimeData(getActivity(),"weak").getEntries();
        ArrayList<Entry> entries=new ArrayList<>();
        entries.add(new Entry(120,0));
        entries.add(new Entry(120,1));
        entries.add(new Entry(120,2));
        entries.add(new Entry(120,3));
        entries.add(new Entry(120,4));
        entries.add(new Entry(120,5));
        entries.add(new Entry(120,6));
        ArrayList<String> labels=new ArrayList<>();
        labels.add("ПН");
        labels.add("ВТ");
        labels.add("СР");
        labels.add("ЧТ");
        labels.add("ПТ");
        labels.add("СБ");
        labels.add("ВС");

        LineDataSet dataset = new LineDataSet(entries, "Неделя");
        LineData data = new LineData(labels,dataset);
        dataset.setDrawValues(false);
        dataset.setDrawCubic(true);
        dataset.setDrawFilled(true);
        dataset.setColor(Color.parseColor("#8be64d3c"));
        dataset.setFillColor(Color.parseColor("#a24ebac7"));
        lineChart.animateY(1000);
        lineChart.setScaleEnabled(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.setData(data);

        TimeTole timeTole=new TimeTole(strings2,entries,labels);
        saveTimeData(getActivity().getApplicationContext(),"weak",timeTole);
    }
    public  void showDataOfMOnth(){
        lineChart.removeAllViews();
        //ArrayList<ProductDataInHomePage> products_month=getTimeData(getActivity(),"month").getProducts();

        ArrayList<String> strings3=new ArrayList<>();
        strings3.add("45 898 тг");
        strings3.add("45");
        strings3.add("5 454 тг");
        strings3.add("2 655 тг");
        for(int i=0;i<textViews.size();i++){
            textViews.get(i).setText(strings3.get(i));
        }
        button_month.setBackgroundResource(R.drawable.tab_button_home_page);
        button_month.setTextColor(Color.parseColor("#fafafa"));
        button_day.setBackgroundResource(R.color.white);
        button_day.setTextColor(Color.parseColor("#635d5d"));

        button_weak.setBackgroundResource(R.color.white);
        button_weak.setTextColor(Color.parseColor("#635d5d"));

        button_year.setBackgroundResource(R.color.white);
        button_year.setTextColor(Color.parseColor("#635d5d"));

        //ArrayList<Entry>  entries=getTimeData(getActivity(),"month").getEntries();
        ArrayList<Entry> entries=new ArrayList<>();
        entries.add(new Entry(0,0));
        entries.add(new Entry(45,1));
        entries.add(new Entry(12,2));
        entries.add(new Entry(230,3));


        //ArrayList<String> labels=getTimeData(getActivity(),"month").getLabels();
        ArrayList<String> labels=new ArrayList<>();
        labels.add("1");
        labels.add("2");
        labels.add("3");
        labels.add("4");

        LineDataSet dataset = new LineDataSet(entries,  "Месяц");


        LineData data = new LineData(labels,dataset);
        dataset.setDrawValues(false);
        dataset.setDrawCubic(true);
        dataset.setDrawFilled(true);
        dataset.setColor(Color.parseColor("#8be64d3c"));
        dataset.setFillColor(Color.parseColor("#a24ebac7"));

        lineChart.animateY(1000);
        lineChart.setScaleEnabled(false);
        lineChart.setHorizontalScrollBarEnabled(true);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.setData(data);

        TimeTole timeTole=new TimeTole(strings3,entries,labels);
        saveTimeData(getActivity().getApplicationContext(),"month",timeTole);
    }
    public  void showDataOfYear(){
        lineChart.removeAllViews();
        ArrayList<String> strings4=new ArrayList<>();
        strings4.add("45 898 тг");
        strings4.add("45");
        strings4.add("5 454 тг");
        strings4.add("2 655 тг");
        for(int i=0;i<textViews.size();i++){
            textViews.get(i).setText(strings4.get(i));
        }

        button_year.setBackgroundResource(R.drawable.tab_button_home_page);
        button_year .setTextColor(Color.parseColor("#fafafa"));
        button_month.setBackgroundResource(R.color.white);
        button_month.setTextColor(Color.parseColor("#635d5d"));

        button_weak.setBackgroundResource(R.color.white);
        button_weak.setTextColor(Color.parseColor("#635d5d"));

        button_day.setBackgroundResource(R.color.white);
        button_day.setTextColor(Color.parseColor("#635d5d"));
        ArrayList<Entry> entries=new ArrayList<>();
        entries.add(new Entry(0,0));
        entries.add(new Entry(45,1));
        entries.add(new Entry(12,2));
        entries.add(new Entry(230,3));
        entries.add(new Entry(560,4));
        entries.add(new Entry(890,5));
        entries.add(new Entry(120,6));
        entries.add(new Entry(560,7));
        entries.add(new Entry(100,8));
        entries.add(new Entry(890,9));
        entries.add(new Entry(450,10));
        entries.add(new Entry(150,11));

        ArrayList<String> labels=new ArrayList<>();
        labels.add("ЯНВ");
        labels.add("ФЕВ");
        labels.add("МРТ");
        labels.add("АПР");
        labels.add("МАЙ");
        labels.add("ИН");
        labels.add("ИЛ");
        labels.add("АВГ");
        labels.add("СЕН");
        labels.add("ОКТ");
        labels.add("НБР");
        labels.add("ДЕК");


        LineDataSet dataset = new LineDataSet(entries, "Год");

        LineData data = new LineData(labels,dataset);
        dataset.setDrawValues(false);
        dataset.setDrawCubic(true);
        dataset.setDrawFilled(true);
        dataset.setColor(Color.parseColor("#8be64d3c"));
        dataset.setFillColor(Color.parseColor("#a24ebac7"));
        lineChart.setData(data);
        lineChart.animateY(1000);
        lineChart.setScaleEnabled(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);

        TimeTole timeTole=new TimeTole(strings4,entries,labels);
        saveTimeData(getActivity().getApplicationContext(),"year",timeTole);

    }
    public static void saveTimeData(Context context, String title, TimeTole timeTole){
        SharedPreferences sharedPreferences=context.getSharedPreferences("Times",context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json=gson.toJson(timeTole);
        editor.putString(title,json);
        editor.commit();

    }
    public static TimeTole getTimeData(Context context, String title){
        SharedPreferences  sharedPreferences=context.getSharedPreferences("Times",context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json=sharedPreferences.getString(title,"");
        if(json.equalsIgnoreCase("")){
            return null;
        }
        TimeTole timeTole=gson.fromJson(json,TimeTole.class);
        return timeTole;
    }



}
