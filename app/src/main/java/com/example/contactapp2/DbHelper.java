package com.example.contactapp2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    Context context;
    public DbHelper(@Nullable Context context) {
        super(context,Constants.DATABASE_NAME,null,Constants.DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table on database
        db.execSQL(Constants.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        //update table if any structure change in database
        //drop table if exists
        db.execSQL("DROP TABLE IF EXISTS "+Constants.TABLE_NAME);

        //create table again
        onCreate(db);
    }

    //insert function for inserting the data in the database
    public long insertContact(String image,String name,String phone,String email,String note,String addedTime,String updatedTime,String birthday){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Constants.C_IMAGE,image);
        values.put(Constants.C_NAME,name);
        values.put(Constants.C_PHONE,phone);
        values.put(Constants.C_EMAIL,email);
        values.put(Constants.C_BIRTHDAY,birthday);
        values.put(Constants.C_NOTE,note);
        values.put(Constants.C_ADDED_TIME,addedTime);
        values.put(Constants.C_UPDATED_TIME,updatedTime);

        //insert data in the row
        long id=db.insert(Constants.TABLE_NAME,null,values);

        //close db
        //db.close();

        return id;

    }

    //update function to update data in database
    public void updateContact(String id,String image,String name,String phone,String email,String note,String addedTime,String updatedTime,String birthday){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Constants.C_IMAGE,image);
        values.put(Constants.C_NAME,name);
        values.put(Constants.C_PHONE,phone);
        values.put(Constants.C_EMAIL,email);
        values.put(Constants.C_BIRTHDAY,birthday);
        values.put(Constants.C_NOTE,note);
        values.put(Constants.C_ADDED_TIME,addedTime);
        values.put(Constants.C_UPDATED_TIME,updatedTime);

        //update data in the row
        db.update(Constants.TABLE_NAME,values,Constants.C_ID+" =? ",new String[]{id});

        //close db
        //db.close();



    }

    //delete specific data from the database
    public void deleteContact(String id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME," ID =? ",new String[]{id});
        //db.close();
    }

    //delete all data from the database
    public void deleteAllContacts(){
        SQLiteDatabase db=this.getWritableDatabase();
        //db.delete("DELETE * FROM "+Constants.TABLE_NAME,null,null);
        db.execSQL("DELETE FROM "+Constants.TABLE_NAME);
        //db.close();
    }
    //get data
    public ArrayList<ModelContact> getAllData(String orderBy){
        ArrayList<ModelContact> arrayList=new ArrayList<>();
        //sql command for finding data
        String selectQuery="SELECT * FROM "+Constants.TABLE_NAME+" ORDER BY "+orderBy;

        //get readable db
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor= db.rawQuery(selectQuery,null);
        //looping through all records and add to list
        if (cursor.moveToFirst()){
            do {
                ModelContact modelContact=new ModelContact(
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow(Constants.C_ID)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NAME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PHONE)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_EMAIL)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NOTE)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ADDED_TIME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_UPDATED_TIME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_BIRTHDAY))
                );
                arrayList.add(modelContact);
            }while (cursor.moveToNext());
        }
        //db.close();
        return arrayList;
    }
    //search data in sql database
    public  ArrayList<ModelContact> getSearchContact(String query){
        ArrayList<ModelContact> contactList=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        //query or search
        String queryToSearch=" SELECT * FROM CONTACT_TABLE WHERE "+Constants.C_NAME+" LIKE '%"+query+"%'";
        Cursor cursor=db.rawQuery(queryToSearch,null);
        if (cursor.moveToFirst()){
            do {
                ModelContact modelContact=new ModelContact(
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow(Constants.C_ID)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NAME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PHONE)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_EMAIL)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NOTE)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ADDED_TIME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_UPDATED_TIME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_BIRTHDAY))
                );
                contactList.add(modelContact);
            }while (cursor.moveToNext());
        }
        //db.close();
        return contactList;
    }
    public  ArrayList<ModelContact> getSearchContactByRelationship(String query){
        ArrayList<ModelContact> contactList=new ArrayList<>();
        SQLiteDatabase db=this.getReadableDatabase();
        //query or search
        String queryToSearch=" SELECT * FROM CONTACT_TABLE WHERE "+Constants.C_NOTE
                +" LIKE '%"+query+"%'";
        Cursor cursor=db.rawQuery(queryToSearch,null);
        if (cursor.moveToFirst()){
            do {
                ModelContact modelContact=new ModelContact(
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow(Constants.C_ID)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NAME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PHONE)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_EMAIL)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NOTE)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ADDED_TIME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_UPDATED_TIME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_BIRTHDAY))
                );
                contactList.add(modelContact);
            }while (cursor.moveToNext());
        }
        //db.close();
        return contactList;
    }

    /*public CharSequence birthDay(String date){
        List<String> contactList=new ArrayList<>();
        String date1 = "";
        SQLiteDatabase db=this.getReadableDatabase();
        String queryToSearch=" SELECT * FROM CONTACT_TABLE WHERE "+Constants.C_BIRTHDAY+" LIKE '%"+date+"%'";
        Cursor cursor=db.rawQuery(queryToSearch,null);
        int columnIndex=cursor.getColumnIndex("BIRTHDAY");
        Toast.makeText(context, columnIndex, Toast.LENGTH_SHORT).show();
        if (columnIndex!=-1){
            String data=cursor.getString(columnIndex);
            Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
        }
        else {
            Log.e("TAG","column does not exist");
        }
        if (cursor.getCount()>0)
        {
            while (cursor.moveToNext()){
                date1=cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_BIRTHDAY));
                contactList.add(date1);
                Toast.makeText(context, date1, Toast.LENGTH_SHORT).show();

           }


        }

        cursor.close();
        db.close();
        //return contactList;
        return date1;
    }*/

}
