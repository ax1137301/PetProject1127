package com.example.petproject1127;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    FloatingActionButton f_btn;
    RecyclerView petList;
    private DbAdapter dbAdapter;
    ArrayList<Pet> arr_pet = new ArrayList<>();
    Cursor cursor;
    private RecyclerAdapter dataSimpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        f_btn = findViewById(R.id.f_btn);
        petList = findViewById(R.id.pet_list);
        dbAdapter = new DbAdapter(this);

        display();

        //新增通報按鍵
        f_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent();
                intent.setClass(MainActivity.this, Edit_Activity.class);
                intent.putExtra("type", "add");
                startActivity(intent);
                finish();
            }
        });
        //新增通報按鍵結束
    }//onCreate結束

    // display開始
    public void display() {
        cursor = dbAdapter.List_pet();
        if (cursor.moveToFirst()) {
            do {
                arr_pet.add(new Pet(cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("img")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("date")),
                        cursor.getString(cursor.getColumnIndexOrThrow("place")),
                        cursor.getString(cursor.getColumnIndexOrThrow("phone")),
                        cursor.getString(cursor.getColumnIndexOrThrow("feature")),
                        cursor.getString(cursor.getColumnIndexOrThrow("info"))));
            } while (cursor.moveToNext());
        }// if結束
        cursor.moveToFirst();
        dataSimpleAdapter = new RecyclerAdapter(this,arr_pet);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        petList.setLayoutManager(layoutManager);
        petList.setAdapter(dataSimpleAdapter);
    }//desplay結束
}
