package com.example.contactapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Keypad extends AppCompatActivity {
    EditText phone;
    FloatingActionButton callbtn;
     int PERMISSION_CODE=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keypad);
        phone=findViewById(R.id.editTextPhone);
        callbtn=findViewById(R.id.callbtn);
        if (ContextCompat.checkSelfPermission(Keypad.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Keypad.this,new String[]{Manifest.permission.CALL_PHONE},PERMISSION_CODE);

        }
        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneno=phone.getText().toString();
                Intent i=new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:"+phoneno));
                startActivity(i);
            }
        });

    }
}