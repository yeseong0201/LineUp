package com.example.lineup.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.lineup.R;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class Formation extends AppCompatActivity {
    Toolbar toolbar;
    ImageView imageView;
    ImageButton soccer, baseball, basketball, vollyball, add_object, remove_btn;
    LinearLayout  viewLayout, whiteLayout;
    ImageButton btn;
    FrameLayout frameLayout;

    int cnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formation);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = findViewById(R.id.field_background);

        soccer = findViewById(R.id.soccer_btn);
        baseball = findViewById(R.id.baseball_btn);
        basketball = findViewById(R.id.basketball_btn);
        vollyball = findViewById(R.id.vollyball_btn);
        add_object = findViewById(R.id.add_object_btn);
        remove_btn = findViewById(R.id.remove_btn);

        viewLayout = findViewById(R.id.viewLayout);
        frameLayout = findViewById(R.id.framelayout);
        whiteLayout = findViewById(R.id.whiteLayout);

        addObject();
        removeObject();

        setBackgroundField();

    }

    public void addObject() {
        add_object.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cnt++; // 버튼 제거시 null처리 방지하기 위해서.

                //버튼 생성하기
                btn = new ImageButton(Formation.this);
                btn.setLayoutParams(new LinearLayout.LayoutParams(130, 130));
                btn.setBackgroundResource(R.drawable.object);
                ViewGroup.MarginLayoutParams margin3 = new ViewGroup.MarginLayoutParams(btn.getLayoutParams());
                margin3.setMargins(0, 0, 0, 0);
                margin3.setMarginStart(0);
                margin3.setMarginEnd(0);
                btn.setLayoutParams(new LinearLayout.LayoutParams(margin3));
                viewLayout.addView(btn);

                // 버튼 움직이기
                btn.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        int parentWidth = ((ViewGroup) v.getParent()).getWidth();    // 부모 View 의 Width
                        int parentHeight = ((ViewGroup) v.getParent()).getHeight();    // 부모 View 의 Height

                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            // 뷰 누름
                            oldXvalue = event.getX();
                            oldYvalue = event.getY();
                            Log.d("viewTest", "oldXvalue : " + oldXvalue + " oldYvalue : " + oldYvalue);    // View 내부에서 터치한 지점의 상대 좌표값.
                            Log.d("viewTest", "v.getX() : " + v.getX());    // View 의 좌측 상단이 되는 지점의 절대 좌표값.
                            Log.d("viewTest", "RawX : " + event.getRawX() + " RawY : " + event.getRawY());    // View 를 터치한 지점의 절대 좌표값.
                            Log.d("viewTest", "v.getHeight : " + v.getHeight() + " v.getWidth : " + v.getWidth());    // View 의 Width, Height

                        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                            // 뷰 이동 중
                            v.setX(v.getX() + (event.getX()) - (v.getWidth() / 2));
                            v.setY(v.getY() + (event.getY()) - (v.getHeight() / 2));

                        } else if (event.getAction() == MotionEvent.ACTION_UP) {
                            // 뷰에서 손을 뗌

                            if (v.getX() < 0) {
                                v.setX(0);
                            } else if ((v.getX() + v.getWidth()) > parentWidth) {
                                v.setX(parentWidth - v.getWidth());
                            }

                            if (v.getY() < 0) {
                                v.setY(0);
                            } else if ((v.getY() + v.getHeight()) > parentHeight) {
                                v.setY(parentHeight - v.getHeight());
                            }

                        }
                        return true;
                    }
                });
            }
        });
    }

    public void removeObject() {
        //버튼 제거하기
        remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < cnt; i++) {
                    btn.setVisibility(View.GONE);

                }
            }
        });


    }

    // 배경 설정하
    public void setBackgroundField() {

        ImageButton.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.soccer_btn:
                        imageView.setImageResource(R.drawable.soccer_field);
                        break;
                    case R.id.baseball_btn:
                        imageView.setImageResource(R.drawable.baseball_field);
                        break;
                    case R.id.basketball_btn:
                        imageView.setImageResource(R.drawable.basket_ball_field);
                        break;
                    case R.id.vollyball_btn:
                        imageView.setImageResource(R.drawable.vollyball_field);
                        break;
                    case R.id.add_object_btn:


                        //  Toast.makeText(Formation.this, "yes", Toast.LENGTH_SHORT).show();


                }
            }
        };

        soccer.setOnClickListener(onClickListener);
        baseball.setOnClickListener(onClickListener);
        basketball.setOnClickListener(onClickListener);
        vollyball.setOnClickListener(onClickListener);

    }

    float oldXvalue;
    float oldYvalue;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.tab_layout_menu, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            case R.id.done:
                onBackPressed();
                break;
        }


        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



}