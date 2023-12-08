package com.example.mobilesf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void input(View view){
        Intent intent = new Intent(this, Food_input.class);
        startActivity(intent);
    }

    public void output(View view){
        Intent intent = new Intent(this, Food_output.class);
        startActivity(intent);
    }
    public void analyze(View view){
        Intent intent = new Intent(this, Food_Analyze.class);
        startActivity(intent);
    }

    public void deleteAll(View view){
        getContentResolver().delete(MyContentProvider.CONTENT_URI,null,null);
    }
}