package com.example.mobilesf;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {
    static final String PROVIDER_NAME = "com.example.MobileSF.MyContentProvider"; //만들때랑 동일하게
    static final String URL = "content://" + PROVIDER_NAME + "/students"; //데이터베이스의 이름? 이 될거래
    static final Uri CONTENT_URI = Uri.parse(URL);
    static final String _ID = "_id";
    static final String restaurant = "restaurant";
    static final String picture = "picture";
    static final String food_name = "food_name";
    static final String taste = "taste";
    static final String date = "date";
    static final String cost = "cost";
    public FoodDBManager dbManager; // 디비 매니져 클래스 가져옴
    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return dbManager.delete(selection,selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowid = dbManager.insert(values);
        return null;
    }

    @Override
    public boolean onCreate() {
        dbManager = FoodDBManager.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return dbManager.query(projection,selection,selectionArgs,null,null,sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}