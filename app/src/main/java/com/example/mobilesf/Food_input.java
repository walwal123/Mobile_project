package com.example.mobilesf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Food_input extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1; //이미지 주소
    private EditText imagePathEditText; // 이미지 주소
    int k = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_input);
        System.out.println("시스템 시작");

        //이미지 주소
        imagePathEditText = findViewById(R.id.editTextText);
        Button selectImageButton = findViewById(R.id.button);
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openFileChooser();
                chooseImage();
            }
        });
    }
/* 구 이미지 주소 불러오는법 .... 이제 안쓸듯
    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            imagePathEditText.setText(imageUri.toString());
        }
    }*/

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

    //이미지 내부 저장 코드
    public void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();

            // 이미지 복사 및 경로 가져오기
            String copiedImagePath = copyImageToInternalStorage(selectedImageUri);

            // EditText에 경로 설정
            imagePathEditText.setText(copiedImagePath);
        }
    }

    private String copyImageToInternalStorage(Uri sourceUri) {
        String[] columns = new String[]{"_id", "restaurant", "picture","food_name","taste","date","cost"};
        Cursor c = getContentResolver().query(MyContentProvider.CONTENT_URI,columns, null, null, null, null);
        if (c != null){
            while(c.moveToNext()){
                k = c.getCount();
            }
        }
        // 내부 저장소 경로 설정
        String destinationPath = getFilesDir() + File.separator + "copied_image"+k+".jpg" ;

        try {
            InputStream inputStream = getContentResolver().openInputStream(sourceUri);
            OutputStream outputStream = new FileOutputStream(destinationPath);

            // 이미지 복사
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return destinationPath;
    }

}