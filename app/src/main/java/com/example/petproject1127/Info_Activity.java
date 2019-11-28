package com.example.petproject1127;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Info_Activity extends AppCompatActivity implements View.OnClickListener{
    Intent intent;
    private Bundle get_bundle;
    private Button info_edit,info_back;
    private ImageView in_img;
    private TextView in_name,in_date,in_place,in_phone,in_feature,in_info;
    DbAdapter dbAdapter;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_);

        in_img = findViewById(R.id.in_img);
        in_name = findViewById(R.id.in_name);
        in_date = findViewById(R.id.in_date);
        in_place = findViewById(R.id.in_place);
        in_phone = findViewById(R.id.in_phone);
        in_feature = findViewById(R.id.in_feature);
        in_info = findViewById(R.id.in_info);
        info_back = findViewById(R.id.info_back);

        info_back.setOnClickListener(this);//啟用監聽

        dbAdapter = new DbAdapter(this);//初始化Dbadapter
        //SQLite傳詳細資料
        get_bundle = this.getIntent().getExtras();
        int index = get_bundle.getInt("item_id");
        cursor = dbAdapter.queryByID(index);
        in_img.setImageResource(cursor.getInt(cursor.getColumnIndexOrThrow("img")));
        in_name.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")));
        in_date.setText(cursor.getString(cursor.getColumnIndexOrThrow("date")));
        in_place.setText(cursor.getString(cursor.getColumnIndexOrThrow("place")));
        in_phone.setText(cursor.getString(cursor.getColumnIndexOrThrow("phone")));
        in_feature.setText(cursor.getString(cursor.getColumnIndexOrThrow("feature")));
        in_info.setText(cursor.getString(cursor.getColumnIndexOrThrow("info")));
        //SQLiter結束

//        // get_bundle開始
//        get_bundle = getIntent().getExtras();
//        int img = get_bundle.getInt("img");
//        in_img.setImageResource(img);
//        String n = get_bundle.getString("name");
//        in_name.setText(n);
//        String d = get_bundle.getString("date");
//        in_date.setText(d);
//        String pl = get_bundle.getString("place");
//        in_place.setText(pl);
//        String ph = get_bundle.getString("phone");
//        in_phone.setText(ph);
//        String f = get_bundle.getString("feature");
//        in_feature.setText(f);
//        String in = get_bundle.getString("info");
//        in_info.setText(in);
//        //get_bundle結束
    }//onCreate結束

    @Override
    public void onClick(View v) { //監聽效果
        switch(v.getId()){
            case R.id.info_back: //返回按鍵
                intent = new Intent();
                intent.setClass(Info_Activity.this,MainActivity.class);
                startActivity(intent);
                this.finish();
                break; //info_back結束
        }
    }//onClick結束
}
