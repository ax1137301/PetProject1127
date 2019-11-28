package com.example.petproject1127;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class Edit_Activity extends AppCompatActivity implements View.OnClickListener {
    Intent intent;
    private Bundle bundle;
    int index,new_img;
    private Button e_btn,ok_btn,back_btn;
    private ImageView e_img;
    private TextView txt_title;
    private EditText e_name,e_date,e_place,e_phone,e_feature,e_info;
    private DbAdapter dbAdapter;
    String new_name,new_date,new_place,new_phone,new_feature,new_info,picturePath;
    private static final int PICK_IMAGE = 1;
    private Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_);
        initView();
        dbAdapter = new DbAdapter(this); //初始化DbAdapter
        bundle = this.getIntent().getExtras();

        //判斷是否為編輯
        if (bundle.getString("type").equals("Edit")){
            txt_title.setText("編輯通報");
            index = bundle.getInt("item_id");
            Cursor cursor = dbAdapter.queryByID(index);
            e_img.setImageResource(cursor.getInt(cursor.getColumnIndexOrThrow("img")));
            e_name.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")));
            e_date.setText(cursor.getString(cursor.getColumnIndexOrThrow("date")));
            e_place.setText(cursor.getString(cursor.getColumnIndexOrThrow("place")));
            e_phone.setText(cursor.getString(cursor.getColumnIndexOrThrow("phone")));
            e_feature.setText(cursor.getString(cursor.getColumnIndexOrThrow("feature")));
            e_info.setText(cursor.getString(cursor.getColumnIndexOrThrow("info")));
        }//if結束
    }//onCreate結束

    //initView方法
    public void initView(){
        e_btn = findViewById(R.id.e_btn);
        ok_btn = findViewById(R.id.ok_btn);
        back_btn = findViewById(R.id.back_btn);
        txt_title = findViewById(R.id.txt_title);
        e_img = findViewById(R.id.e_img);
        e_name = findViewById(R.id.e_name);
        e_date = findViewById(R.id.e_date);
        e_place = findViewById(R.id.e_place);
        e_phone = findViewById(R.id.e_phone);
        e_feature = findViewById(R.id.e_feature);
        e_info = findViewById(R.id.e_info);

        //啟用監聽
        back_btn.setOnClickListener(this);
        ok_btn.setOnClickListener(this);
        e_btn.setOnClickListener(this);

    }//initView結束

    //選擇圖片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) return;
        if (resultCode == Activity.RESULT_OK && data != null) {
            selectedImage = data.getData();
            e_img.setImageURI(selectedImage);
            Log.i("data",data.getData().toString());
            String id = selectedImage.getLastPathSegment().split(":")[1];
            final String[] imageColumns = {MediaStore.Images.Media.DATA};
            if (Build.VERSION.SDK_INT >= 23) {
                int REQUEST_CODE_IMAGE = 101;
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                //驗證是否許可權限
                for (String str : permissions) {
                    if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                        //申請權限
                        this.requestPermissions(permissions, REQUEST_CODE_IMAGE);
                        return;
                    }
                }
            }
            Uri uri = getUri();
            picturePath = "path";

            Cursor imageCursor = getContentResolver().query(uri, imageColumns,
                    MediaStore.Images.Media._ID + "="+id, null, null);

            if (imageCursor.moveToFirst()) {
                picturePath = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            Log.v("test=",picturePath);
        }
    }
    private Uri getUri() {
        String state = Environment.getExternalStorageState();
        if(!state.equalsIgnoreCase(Environment.MEDIA_MOUNTED))
            return MediaStore.Images.Media.INTERNAL_CONTENT_URI;

        return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    }//選擇圖片結束

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.e_btn:
                if (Build.VERSION.SDK_INT < 19) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "請選擇圖片"), PICK_IMAGE);
                } else {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    startActivityForResult(intent, PICK_IMAGE);
                }
                break;
            // case R.id.e_btn結束

            case R.id.back_btn:
                intent = new Intent(Edit_Activity.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;
            // case R.id.back_btn結束

            case R.id.ok_btn:
//                new_img = e_img;
                new_name = e_name.getText().toString();
                new_date = e_date.getText().toString();
                new_place = e_place.getText().toString();
                new_phone = e_phone.getText().toString();
                new_feature = e_feature.getText().toString();
                new_info = e_info.getText().toString();

                //斷判是為編輯還是新增
                if (bundle.getString("type").equals("Edit")){
                    //編輯通報
                    try {
                        dbAdapter.UpdataPet(index,new_img,new_name,new_date,new_place,new_phone,new_feature,new_info);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        intent = new Intent(Edit_Activity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }else {
                    //新增通報
                    try {
                        dbAdapter.CreatePet(new_img,new_name,new_date,new_place,new_phone,new_feature,new_info);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        intent = new Intent(Edit_Activity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }//新增通報結束
                break;
            // case R.id.ok_btn結束

        }// switch結束
    }
}
