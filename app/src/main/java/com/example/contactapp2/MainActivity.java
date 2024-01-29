package com.example.contactapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    BottomNavigationView nav;
    private RecyclerView contactRv;
    //db
    private DbHelper dbHelper;
    //adapter
    private AdapterContact adapterContact;

    //sort category
    private String sortByNewest=Constants.C_ADDED_TIME+" DESC";
    private String sortByOldest=Constants.C_ADDED_TIME+" ASC";
    private String sortByNameAsc=Constants.C_NAME+" ASC";
    private String sortByNameDesc=Constants.C_NAME+" DESC";

    //set current sort query order
    private String currentSort=sortByNewest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nav=findViewById(R.id.nav_bar);

        //init db
        dbHelper=new DbHelper(this);

         floatingActionButton = findViewById(R.id.fab);
         contactRv=findViewById(R.id.contactRv);
         contactRv.setHasFixedSize(true);


         //menu for the navigation bar
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.contacts:
                        Intent intent=new Intent(MainActivity.this,MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.recents:
                        Intent intent1=new Intent(MainActivity.this,Recent.class);
                        startActivity(intent1);
                        break;
                    case R.id.keypad:
                        Intent intent2=new Intent(MainActivity.this,Keypad.class);
                        startActivity(intent2);
                        break;
                }
                return false;
            }
        });

        //add listener
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //move to new activity to add contact
                Intent intent=new Intent(MainActivity.this,AddEditContact.class);
                intent.putExtra("isEditMode",false);
                startActivity(intent);

            }
        });

        loadData(currentSort);//refresh data

        //AdapterContact.ContactViewHolder contactViewHolder= new AdapterContact(this,dbHelper.getAllData(currentSort)).ContactViewHolder.contactDial;
    }

    private void loadData(String currentSort) {
        adapterContact=new AdapterContact(this,dbHelper.getAllData(currentSort));
        contactRv.setAdapter(adapterContact);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(currentSort); //refresh data
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_top_menu,menu);

        //get search item from menu
        MenuItem item=menu.findItem(R.id.searchContact);
        //search area
        SearchView searchView=(SearchView) item.getActionView();
        //set max value for width
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchContact(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String nextText) {
                searchContact(nextText);
                return true;
            }
        });
        return true;
    }

    private void searchContact(String query) {

        adapterContact=new AdapterContact(this,dbHelper.getSearchContact(query));
        contactRv.setAdapter(adapterContact);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.deleteAllContact:
                dbHelper.deleteAllContacts();
                onResume();
                break;
            case R.id.sortContact:
                sortDialog();
                break;
        }

        return true;
    }

    private void sortDialog() {
        //options for alert dialog
        String[] option={"Newest","Oldest","Name Asc","Name Desc"};

        //alert dialog builder
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Sort By");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which==0){
                    loadData(sortByNewest);
                } else if (which==1) {
                    loadData(sortByOldest);
                } else if (which==2) {
                    loadData(sortByNameAsc);
                }else if (which==3){
                    loadData(sortByNameDesc);
                }
            }
        });
        builder.create().show();
    }

}