package com.hyperdesign.alabbadauto.classes.user;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Hyper Design Hussien on 1/10/2018.
 */

public class UserLocalStore {

    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context) {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storedUserData(User user){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putInt("id", user.id);
        spEditor.putString("name", user.email);
        spEditor.putString("email", user.name);
        spEditor.commit();
    }

    public User getLoggedInUser(){
        int id = userLocalDatabase.getInt("id", 0);
        String name = userLocalDatabase.getString("name", "");
        String email = userLocalDatabase.getString("email", "");

        User storedUser = new User(id, name, email);
        return storedUser;
    }

    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("LoggedIn",loggedIn);
        spEditor.commit();
    }

    public boolean getUserLoggedIn(){
        if(userLocalDatabase.getBoolean("LoggedIn", false) == true){
            return true;
        } else {
            return false;
        }
    }

    public void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }

 }
