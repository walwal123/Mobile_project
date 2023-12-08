package com.example.mobilesf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int OPEN_DOCUMENT_REQUEST_CODE = 2;
    private ImageView imageView;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        MyApplication myApp = (MyApplication) getApplication();

        ArrayList<String> retrievedData = myApp.getMyDataList();
        TextView detailTextView = findViewById(R.id.textView5);
        TextView textViewtaste = findViewById(R.id.text15);
        TextView textViewrest = findViewById(R.id.textView16);
        TextView textViewname = findViewById(R.id.textView17);
        TextView textViewdate = findViewById(R.id.textView18);
        TextView textViewcost = findViewById(R.id.textView19);
        TextView textViewcal = findViewById(R.id.textView21);
        int position = 0;

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("position")) {
            position = intent.getIntExtra("position", -1);
            String dataAtIndex = retrievedData.get(position);
            textViewcal.setText(dataAtIndex);
            position += 1; //임시 조치 , 데이터 베이스 초기화 시켜서 포지션 값 시작이랑 id시작값이 달라짐, 현재 id=9가 1번 데이터라 포지션값이 9부터 올라감
            if (position != -1) {
                // position을 사용하여 원하는 동작 수행
                detailTextView.setText("Clicked position: " + position);
            }
        }

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // 권한이 있으면 이미지 로딩
            //loadImage();
        } else {
            // 권한이 없으면 권한 요청
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }

        //주어진 position값으로 쿼리 검색
        String[] columns = new String[]{"_id", "restaurant", "picture", "food_name", "taste", "date", "cost"};
       // String selection = "_id=?";
       // String[] selectionArgs = new String[]{String.valueOf(position)};
       // Cursor c = getContentResolver().query(MyContentProvider.CONTENT_URI, columns, selection, selectionArgs, null, null);
        Cursor c = getContentResolver().query(MyContentProvider.CONTENT_URI,columns, null, null, null, null);
        if (c != null && c.moveToPosition(position - 1)) {
            EditText editMultipleText = findViewById(R.id.editTextText6);
            editMultipleText.setText("");

                int id = c.getInt(0);
                String rest = c.getString(1);
                String pic = c.getString(2);
                imageUri = Uri.parse(pic);
                String name = c.getString(3);
                String taste = c.getString(4);
                String date = c.getString(5);
                String cost = c.getString(6);
                editMultipleText.append("id:" + id + "// 식당:" + rest + "// 사진주소:" + pic + "// 메뉴이름:" + name + "// 평가:" + taste + "// 날짜:" + date + "// 비용:" + cost);
                textViewrest.setText(rest);
                textViewname.setText(name);
                textViewtaste.setText(taste);
                textViewcost.setText(cost);
                textViewdate.setText(date);

            c.close();
        }
        imageView = findViewById(R.id.imageView);
        //loadImage();
        loadImage2();
    }

    private void loadImage2() {

        String copiedImagePath = String.valueOf(imageUri);

        Glide.with(this)
                .load(copiedImagePath)
                .into(imageView);
    }

    //로드 이미지 불러오기 구버전 ... 이제 안씀
    private void loadImage() {
        if (imageUri != null) {
            // 이미지 주소가 유효하다면 이미지를 표시
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, OPEN_DOCUMENT_REQUEST_CODE);
            } else {
                // 권한이 없으면 권한을 요청
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_DOCUMENT_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    imageView.setImageURI(selectedImageUri);
                }
            }
        }
    }
}