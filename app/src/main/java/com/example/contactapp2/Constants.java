package com.example.contactapp2;

public class Constants {
    //database name
    public static final String DATABASE_NAME="CONTACTDB";
    public static final int DATABASE_VERSION=2;

    //table name
    public static final String TABLE_NAME="CONTACT_TABLE";

    //table field
    public static final String C_ID="ID";
    public static final String C_IMAGE="IMAGE";
    public static final String C_NAME="NAME";
    public static final String C_PHONE="PHONE";
    public static final String C_EMAIL="EMAIL";
    public static final String C_NOTE="NOTE";
    public static final String C_ADDED_TIME="ADDED_TIME";
    public static final String C_UPDATED_TIME="UPDATED_TIME";
    public static final String C_BIRTHDAY="BIRTHDAY";

    //query for create table
    public static final String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+"( "+C_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + C_IMAGE +" TEXT, "
            +C_NAME +" TEXT,"
            +C_PHONE +" TEXT,"
            +C_EMAIL +" TEXT,"
            +C_BIRTHDAY +" TEXT,"
            +C_NOTE +" TEXT,"
            +C_ADDED_TIME +" TEXT,"
            +C_UPDATED_TIME +" TEXT"
            +" );";
    //create database helper class for CRUD(CREATE,R,UPDATE,DELETE) queries and database creation;

}
