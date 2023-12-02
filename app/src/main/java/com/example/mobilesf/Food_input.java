package com.example.mobilesf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Food_input extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_input);
        System.out.println("시스템 시작");
    }

    public void addfood(View view){
        ContentValues addValues = new ContentValues();
        addValues.put(MyContentProvider.restaurant,((Spinner)findViewById(R.id.spinner)).getSelectedItem().toString());
        addValues.put(MyContentProvider.picture,((EditText)findViewById(R.id.editTextText)).getText().toString());
        addValues.put(MyContentProvider.food_name,((EditText)findViewById(R.id.editTextText2)).getText().toString());
        addValues.put(MyContentProvider.taste,((EditText)findViewById(R.id.editTextText3)).getText().toString());
        addValues.put(MyContentProvider.date,((EditText)findViewById(R.id.editTextText4)).getText().toString());
        addValues.put(MyContentProvider.cost,((EditText)findViewById(R.id.editTextText5)).getText().toString());

        getContentResolver().insert(MyContentProvider.CONTENT_URI, addValues);
        Toast.makeText(getBaseContext(), "기록 추가됨!", Toast.LENGTH_LONG).show();
    }

    public void test(View view){
        String[] columns = new String[]{"_id", "restaurant", "picture","food_name","taste","date","cost"};
        Cursor c = getContentResolver().query(MyContentProvider.CONTENT_URI,columns, null, null, null, null);
        if (c != null){
            while(c.moveToNext()){
                int id = c.getInt(0);
                String rest = c.getString(1);
                String pic = c.getString(2);
                String name = c.getString(3);
                String taste = c.getString(4);
                String date = c.getString(5);
                String cost = c.getString(6);
                System.out.println("id:"+id+"// 식당:"+rest+"// 사진주소:"+pic+"// 메뉴이름:"+name+"// 평가:"+taste+"// 날짜:"+date+"// 비용:"+cost);
                System.out.println();
            }
        }
    }

}