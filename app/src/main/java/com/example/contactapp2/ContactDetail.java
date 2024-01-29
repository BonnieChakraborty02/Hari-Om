package com.example.contactapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ContactDetail extends AppCompatActivity {

    private TextView nameTv,phoneTv,emailTv,addedTimeTv,updatedTimeTv,noteTv,birthdayTv;
    private FloatingActionButton callfab,smsFab;
    static int PERMISSION_CODE=100;
    private ImageView profileIv;
    private String  id;

    //db helper
    private DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        //init database
        dbHelper=new DbHelper(this);
        //get data from intent
        Intent intent=getIntent();
        id=intent.getStringExtra("contactId");

        //init view
        nameTv=findViewById(R.id.nameTv);
        phoneTv=findViewById(R.id.phoneTv);
        emailTv=findViewById(R.id.emailTv);
        birthdayTv=findViewById(R.id.birthdayTv);
        //addedTimeTv=findViewById(R.id.addedTimeTv);
        //updatedTimeTv=findViewById(R.id.updatedTimeTv);
        noteTv=findViewById(R.id.noteTv);
        profileIv=findViewById(R.id.profileIv);
        callfab=findViewById(R.id.callfab);
        smsFab=findViewById(R.id.messagefab);


        if (ContextCompat.checkSelfPermission(ContactDetail.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},PERMISSION_CODE);
        }
        callfab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneno=phoneTv.getText().toString();
                Intent i=new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:"+phoneno));
                startActivity(i);
            }
        });

        //For sending the birthday message
        Calendar calendar=Calendar.getInstance();
        int dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
        int month=calendar.get(Calendar.MONTH)+1;//added 1 because january is at 0 position
        String currentDateAndMonth=dayOfMonth+"/"+month;

        //String b= (String) dbHelper.birthDay(currentDateAndMonth);
        //Toast.makeText(this, b, Toast.LENGTH_SHORT).show();
        /*if (currentDateAndMonth==Constants.C_BIRTHDAY)
        {

            Toast.makeText(this, "Birthday hello hello "+b, Toast.LENGTH_SHORT).show();
        }*/

        //or sending normal SMS
        smsFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phno=phoneTv.getText().toString();
                Intent intent1=new Intent(ContactDetail.this,SmsSend.class);
                intent1.putExtra("phoneno:",phno);
                startActivity(intent1);
            }
        });

        loadDataById();

    }

    private void loadDataById() {
        //get data from database
        //query for finding the data by id
        String selectQuery="SELECT * FROM "+Constants.TABLE_NAME+" WHERE "+Constants.C_ID+" =\""+ id+"\"";
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()){
            do {
                //get data

                        String name=""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NAME));
                        String image=""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_IMAGE));
                        String phone=""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PHONE));
                        String email=""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_EMAIL));
                        String birtday=""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_BIRTHDAY));
                        String note=""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NOTE));
                        String addTime=""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ADDED_TIME));
                        String updateTime=""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_UPDATED_TIME));

                        //convert time to dd/mm/yy hh:mm:aa format
                /*Calendar calendar=Calendar.getInstance(Locale.getDefault());
                calendar.setTimeInMillis(Long.parseLong(addTime));
                public static final String DATE_FORMAT="dd-MMM-yyyy";
                String timeAdd=""+ DateFormat.getDateInstance().format(DATE_FORMAT);

                calendar.setTimeInMillis(Long.parseLong(updateTime));
                String timeUpdate=""+ DateFormat.getInstance().format("dd/MM/yy hh:mm:aa", calendar);*/

                //set data
                nameTv.setText(name);
                phoneTv.setText(phone);
                emailTv.setText(email);
                birthdayTv.setText(birtday);
                noteTv.setText(note);
               /* addedTimeTv.setText(timeAdd);
                updatedTimeTv.setText(timeUpdate);*/


                if (image==null){
                    profileIv.setImageResource(R.drawable.baseline_person_24);
                }else {
                    profileIv.setImageURI(Uri.parse(image));
                }

            }while (cursor.moveToNext());
        }
        db.close();
    }

}