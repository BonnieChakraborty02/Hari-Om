package com.example.contactapp2;

public class ModelContact {
    private String id,name,image,phone,email,note,birthday,addedTime,updatedTime;
    //create constructor

    public ModelContact(String id, String name, String image, String phone, String email, String note, String addedTime, String updatedTime,String birthday) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.phone = phone;
        this.email = email;
        this.note = note;
        this.addedTime = addedTime;
        this.updatedTime = updatedTime;
        this.birthday=birthday;
    }

    //create getter and setter methods

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getNote() {
        return note;
    }

    public String getAddedTime() {
        return addedTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public String getBirthday() {
        return birthday;
    }


    //setters

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setAddedTime(String addedTime) {
        this.addedTime = addedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }
}
