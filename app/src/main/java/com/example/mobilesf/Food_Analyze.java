package com.example.mobilesf;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Food_Analyze extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_analyze);
        MyApplication myApp = (MyApplication) getApplication();
        TextView totalView = findViewById(R.id.textView25);
        TextView totalView2 = findViewById(R.id.textView34);
        TextView totalView3 = findViewById(R.id.textView35);
        TextView totalView4 = findViewById(R.id.textView36);
        TextView totalView5 = findViewById(R.id.textView37);
        ArrayList<String> retrievedData = myApp.getMyDataList();
        int total_c = 0;
        int morning=0, lunch=0, dinner=0, drink =0;
        int index=0;

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
                int id = c2.getInt(0);
                int cost = c2.getInt(6);
                if (containsKeyword(rest, "상록원")) {
                   lunch += cost;
                } else if (containsKeyword(rest, "기숙사")) {
                    dinner += cost;
                } else if (containsKeyword(rest, "가든쿡")) {
                    morning += cost;
                } else if (containsKeyword(rest, "카페")) {
                    drink += cost;
                } else {

                }
                int position2 = c2.getPosition();
                int position = 0;
                if (position2 == 0){
                    index = id;
                }
                position = id-index;
                int dataAtIndex = Integer.parseInt(retrievedData.get(position));
                System.out.println(dataAtIndex);
                total_c += dataAtIndex;
                System.out.println(name+" "+ date +" "+rest + " "+id);
            }
            c2.close();
        } //코드 끝
        String result = "";
        result = String.valueOf(total_c);
        totalView.setText(result);
        totalView2.setText(morning + " 원");
        totalView3.setText(lunch + " 원");
        totalView4.setText(dinner + " 원");
        totalView5.setText(drink + " 원");
        System.out.println(total_c);
    }

    private static boolean containsKeyword(String sourceString, String keyword) {
        // contains 메서드를 사용하여 특정 단어가 포함되는지 확인
        return sourceString.contains(keyword);
    }
}