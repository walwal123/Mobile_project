package com.example.mobilesf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FoodDBManager extends SQLiteOpenHelper {
    static final String my_DB = "Project.db"; // 데베 이름
    static final String FOOD_TABLE = "food"; // 데베의 테이블 이름
    Context context = null;
    private static FoodDBManager dbManager = null;

    static final String CREATE_DB = " CREATE TABLE " + FOOD_TABLE +
            " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + " restaurant TEXT NOT NULL, picture TEXT NOT NULL, food_name TEXT, taste TEXT, date TEXT,cost TEXT);";

    public static FoodDBManager getInstance(Context context) {
        if(dbManager == null) {
            dbManager = new FoodDBManager(context, my_DB, null, 1);
        }
        return dbManager;
    }
    public FoodDBManager(Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbName, factory, version);
        this.context = context;
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DB);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {

    }

    //이하 DB control 함수들
    public long insert(ContentValues addValue) {
        return getWritableDatabase().insert(FOOD_TABLE, null, addValue);
    }
    public Cursor query(String [] columns, String selection,
                        String[] selectionArgs, String groupBy, String having, String orderBy){
        return getReadableDatabase().query(FOOD_TABLE, columns, selection, selectionArgs, groupBy, having, orderBy);
    }
    public int delete(String whereClause, String[] whereArgs)
    {
        return getWritableDatabase().delete(FOOD_TABLE, whereClause, whereArgs);
    }

}


