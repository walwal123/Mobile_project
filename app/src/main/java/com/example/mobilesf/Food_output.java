package com.example.mobilesf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Food_output extends AppCompatActivity {

    private final List<String> mealData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_output);

        String[] columns = new String[]{"_id", "restaurant", "picture","food_name","taste","date","cost"};
        Cursor c = getContentResolver().query(MyContentProvider.CONTENT_URI,columns, null, null, null, null);
        if (c != null){
            while(c.moveToNext()){
                String name = c.getString(3);
                String date = c.getString(5);
                mealData.add("날짜: " + date + " // 음식: " + name); // ArrayList에 데이터 추가
            }
            c.close();
        }

        //arraylist에 있는 데이터 리스트뷰에 넣어주기
        ListView listView = findViewById(R.id.ListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mealData);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 클릭한 아이템의 id를 상세 화면으로 전달
                Intent intent = new Intent(Food_output.this, DetailActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

}
