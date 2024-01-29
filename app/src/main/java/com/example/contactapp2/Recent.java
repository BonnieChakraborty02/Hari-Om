package com.example.contactapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Recent extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);
        if (ContextCompat.checkSelfPermission(Recent.this,Manifest.permission.READ_CALL_LOG)!=PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(Recent.this,Manifest.permission.READ_CALL_LOG)){
                ActivityCompat.requestPermissions(Recent.this,new String[]{Manifest.permission.READ_CALL_LOG},1);
            }else {
                ActivityCompat.requestPermissions(Recent.this,new String[]{Manifest.permission.READ_CALL_LOG},1);
            }
        }else {

            TextView textView=findViewById(R.id.textView);
            textView.setText(getCallDetails());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(Recent.this, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                        TextView textView=findViewById(R.id.textView);
                        textView.setText(getCallDetails());
                    }
                } else {
                    Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private  String getCallDetails(){
        StringBuffer sb=new StringBuffer();
        Cursor managedCursor=getContentResolver().query(CallLog.Calls.CONTENT_URI,null,null,null,null);
        int number=managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type=managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date=managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration=managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        sb.append("Call Details:\n\n");
        while (managedCursor.moveToNext()){
            String phNumber=managedCursor.getString(number);
            String callType=managedCursor.getString(type); //incoming,outgoing,missed call
            String callDate=managedCursor.getString(date);
            Date callDayTime=new Date(Long.valueOf(callDate));
            SimpleDateFormat formatter=new SimpleDateFormat("dd-MM-yy HH:mm");
            String dateString=formatter.format(callDayTime);
            String callDuration=managedCursor.getString(duration);
            String dir=null;
            int dircode=Integer.parseInt(callType);
            switch (dircode){
                case CallLog.Calls.OUTGOING_TYPE:
                    dir="OUTGOING";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    dir="INCOMING";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    dir="MISSED";
                    break;
            }
            sb.append("\nPhone Number:"+ phNumber+" \nCallType: "+dir+" \nCall Date: "+dateString+" \nCall Duration: "+callDuration);
            sb.append("\n-----------------------------------------------");
        }
        managedCursor.close();
        return sb.toString();
    }
}