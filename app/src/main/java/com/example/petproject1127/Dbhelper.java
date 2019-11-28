package com.example.petproject1127;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Dbhelper extends SQLiteOpenHelper {
    public static final String KEY_ID = "id";
    public static final String KEY_IMG = "img";
    public static final String KEY_NAME = "name";
    public static final String KEY_DATE = "date";
    public static final String KEY_PLACE = "place";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_FEATURE = "feature";
    public static final String KEY_INFO = "info";
    public static final String DATABASE_NAME = "Pets";
    public static final String TABLE_NAME = "pet";
    public static final int DB_VERSION = 3;


    public Dbhelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                KEY_ID + " integer PRIMARY KEY autoincrement," + KEY_IMG + "," +
                 KEY_NAME + "," + KEY_DATE + "," + KEY_PLACE + "," +
                 KEY_PHONE + "," + KEY_FEATURE + "," + KEY_INFO + ");";
            Log.d("SQL",DATABASE_CREATE);
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public static class DbAdapter {
        public static final String KEY_ID = "id";
        public static final String KEY_IMG = "img";
        public static final String KEY_NAME = "name";
        public static final String KEY_DATE = "date";
        public static final String KEY_PLACE = "place";
        public static final String KEY_PHONE = "phone";
        public static final String KEY_FEATURE = "feature";
        public static final String KEY_INFO = "info";
        public static final String TABLE_NAME = "pet";
        private Dbhelper mdbhelper;
        private SQLiteDatabase mdb;
        private Context mCnx;
        public ContentValues values;

        public DbAdapter(Context mCnx){
            this.mCnx = mCnx;
            open();
        }

        public void open(){
            mdbhelper = new Dbhelper(mCnx);
            mdb = mdbhelper.getWritableDatabase();
        }

        //新增通報的方法
        public long CreatePet(int img, String name, String date, String place, String phone, String feature, String info){
            long id =0;
            try{
                values = new ContentValues();
                values.put(KEY_IMG, img);
                values.put(KEY_NAME, name);
                values.put(KEY_DATE, date);
                values.put(KEY_PLACE, place);
                values.put(KEY_PHONE, phone);
                values.put(KEY_FEATURE, feature);
                values.put(KEY_INFO, info);
                id = mdb.insert(TABLE_NAME, null, values);
                Log.d("name ", String.valueOf(img));
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                mdb.close();
                Toast.makeText(mCnx,"新增成功",Toast.LENGTH_SHORT).show();
            }
            return id;
        }//CreatePet結束
        //新增通方法結束

        // 設定Cursor的表格欄位
        public Cursor List_pet(){

            Cursor mcursor = mdb.query(TABLE_NAME , new String[]{ KEY_ID, KEY_IMG, KEY_NAME, KEY_DATE, KEY_PLACE,
                    KEY_PHONE, KEY_FEATURE, KEY_INFO},
                    null,null,null,null,null);

            if (mcursor != null){
                mcursor.moveToFirst();
            }
            return mcursor;
        }//Cursor結束
        //Cursor表格欄位設定結束

        public Cursor queryByID(int item_id){

            Cursor mcursor = mdb.query(TABLE_NAME , new String[]{KEY_ID,KEY_IMG,KEY_NAME,KEY_DATE,KEY_PLACE,
                    KEY_PHONE,KEY_FEATURE,KEY_INFO},
                    KEY_ID + "=" + item_id,null,null,null,null);

            if (mcursor != null){
                mcursor.moveToFirst();
            }
            return mcursor;
        }

        //編輯通報
        public long UpdataPet(int id, int img, String name, String date, String place, String phone, String feature, String info){
            long updata = 0;
            try{
                ContentValues values =new ContentValues();
                values.put(KEY_IMG,img);
                values.put(KEY_NAME,name);
                values.put(KEY_DATE,date);
                values.put(KEY_PLACE,place);
                values.put(KEY_PHONE,phone);
                values.put(KEY_FEATURE,feature);
                values.put(KEY_INFO,info);
                String [] ares = {Integer.toString(id)};
                id = mdb.update(TABLE_NAME,values,"id=?",ares);

            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                mdb.close();
                Toast.makeText(mCnx,"編輯成功",Toast.LENGTH_SHORT).show();
            }
            return updata;
        }//UpdataPet結束
        //編輯通報結束

        //刪除通報
        public boolean DeletePet(int id){
            String [] ares ={Integer.toString(id)};
            mdb.delete(TABLE_NAME,"id=?",ares);
            Toast.makeText(mCnx, "已刪除!", Toast.LENGTH_SHORT).show();
            return true;
        }//DeletePet結束
        //刪除通結束
    }
}
