package com.example.mobilesf;

import android.app.Application;

import java.util.ArrayList;

public class MyApplication extends Application {
    private ArrayList<String> myDataList;

    public ArrayList<String> getMyDataList() {
        return myDataList;
    }

    public void setMyDataList(ArrayList<String> myDataList) {
        this.myDataList = myDataList;
    }
}
