package com.example.mobilesf;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Food_Analyze extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_analyze);


        //한달전 날짜를 구하는 code
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdfDatabase = new SimpleDateFormat("MM/dd", Locale.getDefault());
        String currentDate = sdfDatabase.format(calendar.getTime());

        calendar.add(Calendar.DAY_OF_MONTH, -30);
        String thirtyDaysAgo = sdfDatabase.format(calendar.getTime());

        String[] columns2 = new String[]{"_id", "restaurant", "picture", "food_name", "taste", "date", "cost"};
        String selection = "date BETWEEN ? AND ?";
        String[] selectionArgs = new String[]{thirtyDaysAgo, currentDate};
        Cursor c2 = getContentResolver().query(MyContentProvider.CONTENT_URI, columns2, selection, selectionArgs, null, null);
        if (c2!= null) {
            while (c2.moveToNext()) {
                // 결과 처리
                String name = c2.getString(3);
                String date = c2.getString(5);
                String rest = c2.getString(1);
                System.out.println(name+" "+ date +" "+rest);
            }
            c2.close();
        } //코드 끝
    }
}