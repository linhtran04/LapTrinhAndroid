package com.example.loginnote.ui.editprofile;

public class ProfileOJ {
    private int id;
    private String Name;
    private String Email;
    private String Password;

    public ProfileOJ(int id, String name,  String email, String password) {
        this.id = id;
        Name = name;
        Email = email;
        Password = password;
    }

    public ProfileOJ() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }


    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return "ProfileOJ{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                ", Email='" + Email + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}