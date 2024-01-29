package com.example.contactapp2;

import static android.os.Build.VERSION_CODES.R;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterContact extends RecyclerView.Adapter<AdapterContact.ContactViewHolder> {

    private Context context;
    private ArrayList<ModelContact> contactList;

    private DbHelper dbHelper;

    //add constructor

    public AdapterContact(Context context, ArrayList<ModelContact> contactList) {
        this.context = context;
        this.contactList = contactList;
        dbHelper=new DbHelper(context);
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.row_contact_item,parent,false);
        ContactViewHolder vh=new ContactViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        ModelContact modelContact=contactList.get(position);

        //get data
        String id=modelContact.getId();
        String image=modelContact.getImage();
        String name=modelContact.getName();
        String phone=modelContact.getPhone();
        String birthday=modelContact.getBirthday();
        String note=modelContact.getNote();
        String email=modelContact.getEmail();
        String addedTime=modelContact.getAddedTime();
        String updatedTime=modelContact.getUpdatedTime();

        //Toast.makeText(context, image, Toast.LENGTH_SHORT).show();
        //set data view
        holder.contactName.setText(name);

        if (image.equals("")){
            holder.contactImage.setImageResource(R.drawable.baseline_person_24);
        }else {
            holder.contactImage.setImageURI(Uri.parse(image));
        }

        holder.contactDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //handle item click and show contact details
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create intent to move to the contactsDetails Activity with contact id as reference
                Intent intent=new Intent(context,ContactDetail.class);
                intent.putExtra("contactId",id);
                context.startActivity(intent);
            }
        });

        //handle edition click
        holder.contactEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create a new intent to move to the AddEditActivity to update data
                Intent intent=new Intent(context,AddEditContact.class);
                intent.putExtra("ID",id);
                intent.putExtra("NAME",name);
                intent.putExtra("PHONE",phone);
                intent.putExtra("EMAIL",email);
                intent.putExtra("BIRTHDAY",birthday);
                intent.putExtra("NOTE",note);
                intent.putExtra("ADDEDTIME",addedTime);
                intent.putExtra("UPDATEDTIME",updatedTime);
                intent.putExtra("IMAGE",image);

                //pass boolean data to define it is for edit purpose
                intent.putExtra("isEditMode",true);

                //start intent
                context.startActivity(intent);

            }
        });

        //handle deletion click
        holder.contactDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteContact(id);

                //refresh data by calling resume state of mainactivity
                ((MainActivity)context).onResume();


            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder{


        ImageView contactImage,contactDial;
        TextView contactName,contactEdit,contactDelete;
        RelativeLayout mainLayout;


        Context context;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            //init views

            this.context= AdapterContact.this.context.getApplicationContext();
            contactImage=itemView.findViewById(R.id.contact_image);
            contactDial=itemView.findViewById(R.id.contact_number_dial);
            contactName=itemView.findViewById(R.id.contact_name);
            contactEdit=itemView.findViewById(R.id.contact_edit);
            contactDelete=itemView.findViewById(R.id.contact_delete);
            mainLayout=itemView.findViewById(R.id.mainLayout);


        }
    }
}
