package com.example.askar.KanatTole.Utils.adapters;


import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.askar.KanatTole.Models.Employer;
import com.example.askar.task3bottomnavigationview.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployersAdapter extends RecyclerView.Adapter<EmployersAdapter.ViewHolder>{
    private List<Employer> listItems;
    ViewHolder holder;
    ArrayList<String> numbers=new ArrayList<>();
    View view;
    ViewGroup viewGroup;
    TextView textView;
    RecyclerView recyclerView;
    Context context;
    Button adding_button;
    Window window;
    public EmployersAdapter(List<Employer> listItems, Context context, TextView textView,RecyclerView recyclerView,Button adding_button,Window window){
        this.listItems = listItems;
        this.textView=textView;
        this.recyclerView=recyclerView;
        this.context=context;
        this.adding_button=adding_button;
        this.window=window;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employers_list,parent,false);
        viewGroup=parent;
        holder=new ViewHolder(view);

        return  holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Employer data=listItems.get(position);
        if(data.isAccess()){
            holder.rl_start.setVisibility(View.INVISIBLE);
            holder.rl_middle.setVisibility(View.VISIBLE);
            holder.phone_1.setText("");
        }
        else if(!data.isAccess()){
            holder.rl_start.setVisibility(View.VISIBLE);
            holder.rl_middle.setVisibility(View.INVISIBLE);
            holder.phone_1.setText("");
        }
        holder.edit_name.setText(data.getName());
        holder.edit_phone.setText(data.getPhone());

        int index=holder.getLayoutPosition();
        holder.position.setText(index+1+"");

        holder.phone_1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(holder.phone_1.getText().toString().isEmpty()){
                    holder.phone_1.append("+7");
                }
                return false;
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listItems.remove(position);
                deleteEmployerData(context,position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, listItems.size());
                ArrayList<Employer> items=new ArrayList<>();
                for(int i=0;i<listItems.size();i++){
                    items.add(listItems.get(i));
                }
                deleteAllItems();
                for(int i=0;i<items.size();i++){
                    saveEmployerData(context,i,items.get(i).isAccess(),items.get(i));
                }

                Toast.makeText(context,getAllEntries(context.getSharedPreferences("DataEmployers",context.MODE_PRIVATE)).toString(),Toast.LENGTH_LONG).show();
            }
        });
        holder.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phonenumber=holder.phone_1.getText().toString();
                if(phonenumber.length()!=12||phonenumber.isEmpty()){
                    holder.phone_1.setError("Пустой текст или текст не сответствует формату");
                    holder.phone_1.requestFocus();
                }
                else if(!PhoneDetector(phonenumber)){
                    holder.phone_1.setError("Этот сотрудник введен в список");
                    holder.phone_1.requestFocus();
                }
                else{
                    final Dialog dialog=new Dialog(view.getContext());
                    View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dialog_item,viewGroup,false);
                    dialog.setContentView(v);
                    Rect displayRectangle = new Rect();
                    window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                    dialog.getWindow().setLayout((int)(displayRectangle.width()*0.95f), (int)(displayRectangle.height() * 0.5f));

                    dialog.setCancelable(false);
                    Button button = v.findViewById(R.id.button);
                    TextView textView= dialog.findViewById(R.id.textView2);
                    String s=holder.phone_1.getText().toString();
                    String s1=s.substring(0,4);
                    String s2=s.substring(9);
                    s=s1+"*****"+s2;
                    textView.setText("На номер "+s+" код для регистрации сотрудника успешно отправлен");
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.cancel();
                            adding_button.setEnabled(true);
                            holder.rl_start.setVisibility(View.INVISIBLE);
                            holder.rl_middle.setVisibility(View.VISIBLE);
                            holder.edit_phone.setText(holder.phone_1.getText().toString());
                            listItems.get(position).setPhone(holder.phone_1.getText().toString());
                            saveEmployerData(context,position,true,listItems.get(position));


                        }
                    });
                    dialog.show();
                }
            }
        });
    }
    public void addItem(Employer item) {
        listItems.add(item);
        notifyItemInserted(listItems.indexOf(item));
        numbers.add(item.getPhone());
    }
    @Override
    public int getItemCount() {
        return listItems.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        EditText phone_1;
        EditText edit_name;
        EditText edit_phone;
        RelativeLayout rl_start,rl_middle;
        Button delete,send;
        TextView position;
        public ViewHolder(View itemView) {
            super(itemView);
            phone_1=itemView.findViewById(R.id.edit_phone_start);
            edit_name=itemView.findViewById(R.id.name_edit);
            edit_phone=itemView.findViewById(R.id.edit_phone_number);
            rl_start=itemView.findViewById(R.id.rl_start);
            rl_middle=itemView.findViewById(R.id.rl_middle);
            delete=itemView.findViewById(R.id.delete);
            send=itemView.findViewById(R.id.send);
            position=itemView.findViewById(R.id.position_text);

        }
    }
    public static void saveEmployerData(Context context,int id,boolean access,Employer employer){
        SharedPreferences  sharedPreferences=context.getSharedPreferences("DataEmployers",context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        employer.setId(id);
        employer.setAccess(access);
        String json=gson.toJson(employer);
        editor.putString(String.valueOf(id),json);
        editor.commit();
    }
    public static Employer getEmployerData(Context context,int id){
        SharedPreferences  sharedPreferences=context.getSharedPreferences("DataEmployers",context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json=sharedPreferences.getString(String.valueOf(id),"");
        if(json.equalsIgnoreCase("")){
            return null;
        }
        Employer employer=gson.fromJson(json,Employer.class);
        return employer;
    }
    public static boolean isInDatabase(Context context,int id){
        Employer employer=getEmployerData(context,id);
        if(employer!=null){
            return true;
        }
        return false;
    }
    public    Map<String,String> getAllEntries(SharedPreferences sharedPreferences){
        Map<String,String> allEntries = (Map<String, String>) sharedPreferences.getAll();
        return allEntries;
    }
    public void deleteEmployerData(Context context,int id){
        SharedPreferences mySPrefs =context.getSharedPreferences("DataEmployers",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySPrefs.edit();
        editor.remove(String.valueOf(id));
        editor.apply();
    }
    public void deleteAllItems(){
        SharedPreferences mySPrefs =context.getSharedPreferences("DataEmployers",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySPrefs.edit();
        editor.clear();
        editor.apply();
    }
    public boolean PhoneDetector(String phone){
        ArrayList<Employer> data=getAllEmployers();
        boolean tr=true;
        for(int i=0;i<data.size();i++){
            if(data.get(i).getPhone().equals(phone)){
                tr=false;
                break;
            }
        }
        return tr;
    }
    public ArrayList<Employer> getAllEmployers(){
        Map<String,String> map=getAllEntries(context.getSharedPreferences("DataEmployers",context.MODE_PRIVATE));
        ArrayList<String> strings=new ArrayList<>(map.values());
        ArrayList<Employer> emps=new ArrayList<>();
        Gson gson=new Gson();
        for(int i=0;i<strings.size();i++){
            emps.add(gson.fromJson(strings.get(i),Employer.class));
        }
        return emps;
    }
}
