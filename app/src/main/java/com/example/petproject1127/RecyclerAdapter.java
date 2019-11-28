package com.example.petproject1127;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.Viewholder> {
    public ArrayList<Pet> pet_lis;
    Bundle bundle = new Bundle();
    private DbAdapter dbAdapter;
    public Cursor cursor;
    Context context;
    private AlertDialog dialog = null;
    AlertDialog.Builder builder =null;


    public RecyclerAdapter(Context context,ArrayList<Pet> pet_lis) {
        this.pet_lis = pet_lis;
        this.context = context;
    }

//    // 刪除 list
//    public void removeItem(int position){
//        pet_lis.remove(position);
//        notifyItemRemoved(position);
//    }//結束 removeItem

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        Viewholder vh = new Viewholder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        holder.i_img.setImageResource(pet_lis.get(position).getImg());
        holder.i_name.setText(pet_lis.get(position).getName());
        holder.i_date.setText(pet_lis.get(position).getDate());
        holder.i_place.setText(pet_lis.get(position).getPlace());
        holder.i_phone.setText(pet_lis.get(position).getPhone());
        holder.i_feature.setText(pet_lis.get(position).getFeature());
        holder.i_info.setText(pet_lis.get(position).getInfo());
    } //onBindViewHolder結束

    @Override
    public int getItemCount() {
        return pet_lis.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView i_img;
        private TextView i_name,i_date,i_place,i_phone,i_feature,i_info;
        private Button i_btn_data,i_btn_edit ;
        public int gotId;

        public Viewholder(@NonNull final View itemView) {
            super(itemView);
            i_img = itemView.findViewById(R.id.i_img);
            i_name = itemView.findViewById(R.id.i_name);
            i_date = itemView.findViewById(R.id.i_date);
            i_place = itemView.findViewById(R.id.i_place);
            i_phone = itemView.findViewById(R.id.i_phone);
            i_feature = itemView.findViewById(R.id.i_feature);
            i_info = itemView.findViewById(R.id.i_info);
            i_btn_data = itemView.findViewById(R.id.i_btn_data);
            i_btn_edit = itemView.findViewById(R.id.i_btn_edit);


            i_btn_data.setOnClickListener(this);//啟用監聽
            i_btn_edit.setOnClickListener(this);//啟用監聽


            dbAdapter = new DbAdapter(context); // 變數dbAdapter 初始化DbAdapter

            //設定長按刪除
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

//                    gotId = pet_lis.get(getAdapterPosition()).getId(); //取得 SQLite的 id
//                    dbAdapter.DeletePet(gotId); // 用DbAdapter的DeletePet方法刪除gotId資料
//                    Intent intent = new Intent(itemView.getContext(),MainActivity.class);
//                    itemView.getContext().startActivity(intent);
//                    ((Activity)itemView.getContext()).finish();
//                    removeItem(getAdapterPosition());

                    dialog = builder.create();
                    dialog.show();

                    return false;
                }
            });//長按刪除
            //建立dialog訊息窗
            builder = new AlertDialog.Builder(itemView.getContext());
            builder.setTitle("訊息")
                    .setMessage("確定刪除此通報?")
                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        //設定確定按鈕
                        @Override
                        public void onClick(DialogInterface dialogInterface,int i) {

                            gotId = pet_lis.get(getAdapterPosition()).getId(); //取得 SQLite的 id
                            dbAdapter.DeletePet(gotId); // 用DbAdapter的DeletePet方法刪除gotId資料

                            Intent intent = new Intent(itemView.getContext(),MainActivity.class);
                            itemView.getContext().startActivity(intent);
                            ((Activity)itemView.getContext()).finish();
//                            removeItem(getAdapterPosition());
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        //設定取消按鈕
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            // Dialog結束
        }// Viewholder結束

        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.i_btn_data: //設定連接詳細資料按鍵
                    // 使用id傳SQLite傳值
                    Intent intent = new Intent();
                    gotId = pet_lis.get(getAdapterPosition()).getId();
                    intent = new Intent();
                    intent.putExtra("item_id",gotId);
                    intent.setClass(v.getContext(),Info_Activity.class);
                    v.getContext().startActivity(intent);
                    ((Activity)v.getContext()).finish();

//                    //使用Bundle傳資料到 Info
//                    Intent intent = new Intent();
//                    intent.setClass(v.getContext(),InfoActivity.class);
//
//                    int i= pet_lis.get(getAdapterPosition()).getImg();
//                    String n = pet_lis.get(getAdapterPosition()).getName()
//                            ,d = pet_lis.get(getAdapterPosition()).getDate()
//                            ,pl = pet_lis.get(getAdapterPosition()).getPlace()
//                            ,ph = pet_lis.get(getAdapterPosition()).getPhone()
//                            ,f = pet_lis.get(getAdapterPosition()).getFeature()
//                            ,in = pet_lis.get(getAdapterPosition()).getInfo();
//
//                    bundle.putInt("img",i);
//                    bundle.putString("name",n);
//                    bundle.putString("date",d);
//                    bundle.putString("place",pl);
//                    bundle.putString("phone",ph);
//                    bundle.putString("feature",f);
//                    bundle.putString("info",in);
//
//                    intent.putExtras(bundle);
//                    v.getContext().startActivity(intent);
//                    ((Activity)v.getContext()).finish();
//                    //使用Bundle傳資料到 Info結束

                    break;//結束詳細資料按鍵

                case R.id.i_btn_edit: //設定編輯按鍵
                    gotId = pet_lis.get(getAdapterPosition()).getId();
                    intent = new Intent();
                    intent.putExtra("item_id",gotId);
                    intent.putExtra("type","Edit");

                    intent.setClass(v.getContext(),Edit_Activity.class);
                    v.getContext().startActivity(intent);
                    ((Activity)v.getContext()).finish();
                    break; //設定編輯按鍵
            }// switch結束
        }//onClick結束
    }// Viewholder結束
}
