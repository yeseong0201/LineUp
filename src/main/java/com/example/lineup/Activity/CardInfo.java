package com.example.lineup.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.lineup.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CardInfo extends AppCompatActivity {
    Toolbar info_toolbar;
    TextView card_info_name, card_info_email, card_info_tel, card_info_division;
    public static int degree = 0;

    public static ImageView card_info_image;
    public static Bitmap bitmap;
    public static byte[] byteArray;
    public static SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_info);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        card_info_name = findViewById(R.id.card_info_name);
        card_info_email = findViewById(R.id.card_info_email);
        card_info_tel = findViewById(R.id.card_info_tel);
        card_info_division = findViewById(R.id.card_info_division);

        customActionBars();
        getStrings();
        getPreferences();

    }

    // 값 저장하기
    private void savePreferences() {
        // String image = BitmapToString(bitmap);
        String image = BitmapToString(bitmap);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("image", MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = pref.edit();
        editor.putString("imageString", image);
        editor.commit();
    }

    // bitmap 문자열로 저장하기
    public String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    // 값 불러오기
    private void getPreferences() {
        SharedPreferences pref1 = getApplicationContext().getSharedPreferences("image", MODE_PRIVATE);
        String image = pref1.getString("imageString", "");
        Bitmap bitmap = StringtoBitmap(image);

        if (bitmap != null) {
            card_info_image.setImageBitmap(bitmap);
        }

        bitmap = null;
    }

    // String 값을 Bitmap으로 전환하기
    public Bitmap StringtoBitmap(String encodedString) {
        try {
            byte[] encodedByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodedByte, 0, encodedByte.length);
            return bitmap;

        } catch (Exception e) {
            e.getMessage();
            return null;

        }

    }

    public void customActionBars() {

//
//        info_toolbar.setTitle("명함 정보");
//        info_toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
//
//        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
//        actionBar.setDisplayShowTitleEnabled(false);
//        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김

    }

    public void getStrings() {

        Intent intent = getIntent();

        String user_name = intent.getStringExtra("putName");
        String user_email = intent.getStringExtra("putEmail");
        String user_tel = intent.getStringExtra("putTel");
        String user_division = intent.getStringExtra("putDivision");

        card_info_name.setText(user_name);
        card_info_email.setText(user_email);
        card_info_tel.setText(user_tel);
        card_info_division.setText(user_division);

    }




    public static Bitmap getRotatedBitmap(Bitmap bitmap, int degrees) throws Exception {
        if (bitmap == null) return null;
        if (degrees == 0) return bitmap;

        Matrix m = new Matrix();
        m.setRotate(degrees, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:

                if (bitmap != null) {
                    savePreferences();
                    finish();
                }
                else {
                    Toast.makeText(this, "명함을 스캔해주세요.", Toast.LENGTH_SHORT).show();
                }


        }
        return true;
    }


}
