package com.example.clinicaldiagnosissystem;

public class UserProfile {

    public String Email;
    public String Sex;
    public String Name;

    public UserProfile(String userEmail, String userSex, String userName) {
        this.Email = userEmail;
        this.Sex = userSex;
        this.Name = userName;
    }
}
