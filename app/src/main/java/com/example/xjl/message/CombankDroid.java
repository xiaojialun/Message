package com.example.xjl.message;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import grpc.message.Client.User;


public class CombankDroid extends Application {

    private User user;
    private boolean isLogin=false;
    public boolean isLogin(){
        return isLogin;
    }
    public void setLogin(Boolean Login){
        isLogin=Login;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
