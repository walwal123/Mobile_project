package com.example.mobilesf;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class month_View extends AppCompatActivity {
    String input_month = " ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_view);
    }

    public void setmonth(View view){
        EditText month = findViewById(R.id.editTextText7);
        TextView viewmonth = findViewById(R.id.textView15);
        input_month = String.valueOf(month.getText());
        find_month_data(input_month);
        viewmonth.setText(input_month+"월");
    }

    public void find_month_data(String p){
        //월 입력받으면 그 달만 찾아주는 쿼리문
        ListView listView2 = findViewById(R.id.ListView2);
        List<String> monthData = new ArrayList<>();
        String targetMonth = p;
        String[] columns2 = new String[]{"_id", "restaurant", "picture", "food_name", "taste", "date", "cost"};
        String selection = "date LIKE ?";
        String[] selectionArgs = new String[]{targetMonth + "/%"};
        Cursor c2 = getContentResolver().query(MyContentProvider.CONTENT_URI, columns2, selection, selectionArgs, null, null);
        if (c2 != null){
            while(c2.moveToNext()){
                String name = c2.getString(3);
                String date = c2.getString(5);
                String rest = c2.getString(1);
                String cost = c2.getString(6);
                System.out.println(name+" "+ date +" "+rest);
                monthData.add(date + " ===  (음식: " + name+ ") (비용: "+cost+")"); // ArrayList에 데이터 추가
            }
            c2.close();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, monthData);
            listView2.setAdapter(adapter);
        }
    }
}