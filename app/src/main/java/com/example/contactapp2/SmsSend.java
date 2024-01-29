package com.example.contactapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SmsSend extends AppCompatActivity {
    String phoneno;
    EditText message1;
    Button sendSms;
    static int REQUESTCODE=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_send);

        message1=findViewById(R.id.message1);
        sendSms=findViewById(R.id.sendSms);

        sendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check condition for permission
                if (ContextCompat.checkSelfPermission(SmsSend.this,Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                    //create a method if permission is granted
                    sendMessage();
                }else {
                    //when the permission is not granted then request for permission
                    ActivityCompat.requestPermissions(SmsSend.this,new String[]{Manifest.permission.SEND_SMS},REQUESTCODE);
                }
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //check condition
        if (requestCode == 100 && grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            //permission is granted
            sendMessage();
        }else {
            //when permission is denied then display the toast message
            Toast.makeText(this, "Permission Denied!!!", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendMessage() {
        Intent intent=getIntent();
        phoneno=intent.getStringExtra("phoneno:");
        String message=message1.getText().toString();

        //check condition if the string is empty or not
        if(!phoneno.isEmpty() && !message.isEmpty()){
            //initialize the SMS Manager
            SmsManager smsManager=SmsManager.getDefault();

            //send message
            smsManager.sendTextMessage(phoneno,null,message,null,null);
            Toast.makeText(this, "SMS sent successfully...", Toast.LENGTH_SHORT).show();
        }else {
            //if the string is empty then display the message
            Toast.makeText(this, "Please enter a text to send the message", Toast.LENGTH_SHORT).show();
        }
    }
}