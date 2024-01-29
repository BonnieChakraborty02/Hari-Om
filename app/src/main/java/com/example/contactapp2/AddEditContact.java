package com.example.contactapp2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;

public class AddEditContact extends AppCompatActivity
{

    private ImageView profileIv;
    private EditText nameEt,phoneEt,emailEt,noteET,birthDayEt;
    private FloatingActionButton floatingActionButton;

    //using for voice assistant
    private RecognitionListener recognitionListener;
    private SpeechRecognizer speechRecognizer,speechRecognizer1;
    private Intent intentRecognition,intentRecognition1;


    //string variables;
    private String id,name,email,phone,note,addedTime,updatedTime,image,birthday;

    private Boolean isEditMode;
    //action bar
    private ActionBar actionBar;
    //permission constants
    private static final int CAMERA_PERMISSION_CODE=100;
    private static final int STORAGE_PERMISSION_CODE=200;
    private static final int IMAGE_FROM_GALLERY_CODE =300;
    private static final int IMAGE_FROM_CAMERA_CODE =400;

    //string array of permission
    private String[] cameraPermission;
    private String[] storagePermission;

    //image uri var
    Uri imageuri;

    //database
    private DbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_contact);

        //init permission for the voice assistant
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},PackageManager.PERMISSION_GRANTED);
        //init db
        dbHelper=new DbHelper(this);

        //init permission
        cameraPermission=new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        //init actionbar
        actionBar=getSupportActionBar();

        //back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        profileIv=findViewById(R.id.profileIv);
        nameEt=findViewById(R.id.nameEt);
        phoneEt=findViewById(R.id.phoneEt);
        emailEt=findViewById(R.id.emailEt);
        noteET=findViewById(R.id.noteEt);
        birthDayEt=findViewById(R.id.birthdayEt);
        floatingActionButton=findViewById(R.id.fab);

        //get intent data
        Intent intent=getIntent();
        isEditMode= intent.getBooleanExtra("isEditMode",false);

        if (isEditMode){
            //set toolbar title
            actionBar.setTitle("Update Contact");

            //get the other values from intent
            id=intent.getStringExtra("ID");
            name=intent.getStringExtra("NAME");
            email=intent.getStringExtra("EMAIL");
            note=intent.getStringExtra("NOTE");
            phone=intent.getStringExtra("PHONE");
            image=intent.getStringExtra("IMAGE");
            addedTime=intent.getStringExtra("ADDEDTIME");
            updatedTime=intent.getStringExtra("UPDATEDTIME");
            birthday=intent.getStringExtra("BIRTHDAY");

            //set values in the EditText field
            nameEt.setText(name);
            phoneEt.setText(phone);
            emailEt.setText(email);
            birthDayEt.setText(birthday);
            noteET.setText(note);


            imageuri=Uri.parse(image);

            if (image.equals("")){
                profileIv.setImageResource(R.drawable.baseline_person_24);
            }else {
                profileIv.setImageURI(imageuri);
            }
        }else {
            //add mode on
            actionBar.setTitle("Add Contact");
        }

        //add event handler
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        profileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImagePickerDialog();
            }
        });

        //voice Recognization
        intentRecognition= new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intentRecognition.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intentRecognition1=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intentRecognition1.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);



        speechRecognizer=SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {

                ArrayList<String > results=bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                String str="";
                if(results!=null){
                    str=results.get(0);
                    nameEt.setText(str);
                }

            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });
        speechRecognizer1=SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer1.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {

                ArrayList<String > results=bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                String str="";
                if(results!=null){
                    str=results.get(0);
                    phoneEt.setText(str);
                }

            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });
    }



    private void showImagePickerDialog() {
        //options for dialog
        String options[]={"Camera","Gallery"};
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Choose an option");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i==0){
                    //handle item click
                    if (!checkCameraPermission()){
                        //request camera permission
                        requestCameraPermission();
                    }else {
                        pickFromCamera();
                    }
                } else if (i==1) {
                    if (checkStoragePermission()){
                        //request storage permission
                        requestStoragePermission();
                    }else {
                        pickFromGallery();
                    }
                }
            }
        }).create().show();;

    }

    private void pickFromGallery() {
        //intent or taking image rom gallery
        Intent galleryIntent=new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*"); //only images
        startActivityForResult(galleryIntent,IMAGE_FROM_GALLERY_CODE);
    }
    private void pickFromCamera() {
        //ContentValues for image info
        ContentValues values=new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"IMAGE_TITLE");
        values.put(MediaStore.Images.Media.DESCRIPTION,"IMAGE_DETAILS");

        //save imageuri
        imageuri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        //intent to open camera
        Intent cameraIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageuri);

        startActivityForResult(cameraIntent,IMAGE_FROM_CAMERA_CODE);
    }

    private void saveData() {
        //take user data in a variable
        name=nameEt.getText().toString();
        phone=phoneEt.getText().toString();
        email=emailEt.getText().toString();
        birthday=birthDayEt.getText().toString();
        note=noteET.getText().toString();

        //get current time to save as added time
        String timeStamp=""+System.currentTimeMillis();

        //check filed data is empty or not
        if (!name.isEmpty() || !phone.isEmpty()|| !email.isEmpty()||!birthday.isEmpty()|| !note.isEmpty())
        {

            //check edit or add mode to save the data in sql
            if (isEditMode){
                //edit mode
                dbHelper.updateContact(
                        ""+id,
                        ""+image,
                        ""+name,
                        ""+phone,
                        ""+email,
                        ""+note,
                        ""+addedTime,
                        ""+timeStamp ,//updated time will new time
                        ""+birthday
                );

                Toast.makeText(getApplicationContext(), "Updated Successfully...", Toast.LENGTH_SHORT).show();
            }else{
                //add mode
                long id=dbHelper.insertContact(
                        ""+imageuri,
                        ""+name,
                        ""+phone,
                        ""+email,
                        ""+note,
                        ""+timeStamp,
                        ""+timeStamp,
                        ""+birthday
                );
                Toast.makeText(getApplicationContext(), "Inserted Successfully ", Toast.LENGTH_SHORT).show();
            }


        }else {
            Toast.makeText(getApplicationContext(), "Nothing to save..", Toast.LENGTH_SHORT).show();
        }
    }

    //back button clicked
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    //check camera permission
    private boolean checkCameraPermission(){
        boolean result= ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        boolean result1=ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result & result1;
    }

    //request for camera permission
    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this,cameraPermission,CAMERA_PERMISSION_CODE);

    }

    //check storage permission
    private boolean checkStoragePermission(){
        boolean result1=ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return  result1;
    }

    //handle request permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CAMERA_PERMISSION_CODE:
                if (grantResults.length>0){
                    boolean cameraAccepted= grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted= grantResults[1]==PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && storageAccepted){
                        pickFromCamera();
                    }else {
                        Toast.makeText(getApplicationContext(), "Camera and Storage permission needed...", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case STORAGE_PERMISSION_CODE:
                if (grantResults.length>0){

                    boolean storageAccepted= grantResults[1]==PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted){
                        pickFromGallery();
                    }else {
                        Toast.makeText(getApplicationContext(), "Storage permission needed...", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    //request for camera permission
    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this,storagePermission,STORAGE_PERMISSION_CODE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode==IMAGE_FROM_GALLERY_CODE){
                //picked image rom gallery
                //crop image
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);

            }else if(requestCode==IMAGE_FROM_CAMERA_CODE){
                //pick image from ca,era
                //crop Image
                CropImage.activity(imageuri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(this);
            }else if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                //cropped image will received here
                CropImage.ActivityResult result= CropImage.getActivityResult(data);
                Uri resulturi=result.getUri();
                imageuri=resulturi;
                profileIv.setImageURI(imageuri);
            }else if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //methods for starting and stopping the voice assistant of name and phone number field
    public void startRecognition(View view){
        speechRecognizer.startListening(intentRecognition);
    }
    public void stopRecognition(View view){
        speechRecognizer.stopListening();
    }
    public void startRecognition1(View view){
        speechRecognizer1.startListening(intentRecognition);
    }
    public void stopRecognition1(View view){
        speechRecognizer1.stopListening();
    }
    //profile image taking with user permission and crop functionality
    //we have to take permission from manifest

}