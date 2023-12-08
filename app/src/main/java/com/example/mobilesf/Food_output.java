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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Food_output extends AppCompatActivity {
    private final List<String> mealData = new ArrayList<>();
    int index = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_output);
        MyApplication myApp = (MyApplication) getApplication();
        ArrayList<String> myData = new ArrayList<>();

        String[] columns = new String[]{"_id", "restaurant", "picture","food_name","taste","date","cost"};
        Cursor c = getContentResolver().query(MyContentProvider.CONTENT_URI,columns, null, null, null, null);
        if (c != null){
            while(c.moveToNext()) {
                Random random = new Random();
                int id = c.getInt(0);
                String name = c.getString(3);
                String date = c.getString(5);
                String rest = c.getString(1);
                mealData.add("날짜: " + date + " // 음식: " + name); // ArrayList에 데이터 추가

                //칼로리 계산 후 외부클래스 배열에 입력시킨다
                //여기다 쓰면 매번 페이지 불러와질때마다 계산하고 저장시켜서 칼리로배열에 값이 계속 들어가지만 초기값만 검색해서 보여줄거니깐 일단...이대로함
                //그래서 아마 보여주기 페이지 들어갔다가 나와서 입력시키고 자세히보기 들어가면 새로 입력시킨애는 자기값이 아니긴 한데 어처피 칼로리값은 임의값이니깐 상관없을듯
                if (index == 0) {
                    if (containsKeyword(rest, "상록원")) {
                        int randomValue = random.nextInt(101) + 400;
                        myData.add(String.valueOf(randomValue));
                    } else if (containsKeyword(rest, "기숙사")) {
                        int randomValue = random.nextInt(101) + 500;
                        myData.add(String.valueOf(randomValue));
                    } else if (containsKeyword(rest, "가든쿡")) {
                        int randomValue = random.nextInt(101) + 300;
                        myData.add(String.valueOf(randomValue));
                    } else if (containsKeyword(rest, "카페")) {
                        int randomValue = random.nextInt(101) + 50;
                        myData.add(String.valueOf(randomValue));
                    } else {
                        myData.add(String.valueOf(300));
                    }
                }
            }
            c.close();
            myApp.setMyDataList(myData);
            index =1;
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

    // 문자열에 특정 단어가 포함되는지 확인하는 메서드
    private static boolean containsKeyword(String sourceString, String keyword) {
        // contains 메서드를 사용하여 특정 단어가 포함되는지 확인
        return sourceString.contains(keyword);
    }

    public void go_month_page(View view){
        Intent intent = new Intent(Food_output.this, month_View.class);
        startActivity(intent);
    }


}
