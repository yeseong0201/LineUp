package com.example.lineup.Fragment;

import android.content.Intent;
import android.graphics.Canvas;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lineup.Activity.CardInfo;
import com.example.lineup.Activity.Formation;
import com.example.lineup.Adapter.FormationAdapter;
import com.example.lineup.Activity.MainActivity;
import com.example.lineup.LIstData.FormationList_data;
import com.example.lineup.R;
import com.example.lineup.controllers.SwipeController;
import com.example.lineup.controllers.SwipeControllerActions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;


public class FormationList extends Fragment {

    ImageView card;

    SwipeController swipeController = null;

    FloatingActionButton fab;

    RecyclerView recyclerView;

    String get_user_title;
    String get_user_subTitle;
    String get_user_date;
    // String get_user_division;

    // 현재시간을 msec 으로 구한다.
    long now = System.currentTimeMillis();
    // 현재시간을 date 변수에 저장한다.
    Date date = new Date(now);
    // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
    SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd");
    // nowDate 변수에 값을 저장한다.
    String formatDate = sdfNow.format(date);

    RecyclerView.LayoutManager layoutManager;


    //MainActivity mainActivity = (MainActivity) getActivity();
    MainActivity mainActivity = (MainActivity) getActivity();
    public ArrayList<FormationList_data> formation_list;

    FormationAdapter formationAdapter;

    public FormationList() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FormationData();

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_formation_list, container, false);

        fab = v.findViewById(R.id.fab);

        recyclerView = v.findViewById(R.id.recyclerview);

        card = v.findViewById(R.id.card);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(mainActivity);

        recyclerView.setLayoutManager(layoutManager);

        formationAdapter = new FormationAdapter(formation_list);

        recyclerView.setAdapter(formationAdapter);

        formationAdapter.notifyDataSetChanged();

        formation_list.size();

        setupRecyclerView();

        fabClicked();

        return v;
    }

    public void fabClicked() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                View view = LayoutInflater.from(getContext())
                        .inflate(R.layout.option_dialog, null, false);
                builder.setView(view);

                final EditText set_user_title = view.findViewById(R.id.user_set_title);
                final EditText set_user_subTitle = view.findViewById(R.id.user_set_subTitle);
                // final EditText set_user_date = view.findViewById(R.id.user_set_email);

                set_user_title.setInputType(InputType.TYPE_CLASS_TEXT);
                set_user_subTitle.setInputType(InputType.TYPE_CLASS_TEXT);
                //  set_user_email.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                final Button cancel_btn = view.findViewById(R.id.cancel_btn);
                Button add_btn = view.findViewById(R.id.add_btn);

                final AlertDialog dialog = builder.create();

                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

                cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                add_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        get_user_title = set_user_title.getText().toString();
                        get_user_subTitle = set_user_subTitle.getText().toString();
                        get_user_date = formatDate;


                        //   get_user_date = formatDate;

                        if (get_user_title.length() <= 0 || get_user_subTitle.length() <= 0) {
                            Toast.makeText(getContext(), "필수 작성 목록입니다.", Toast.LENGTH_SHORT).show();
                        } else {

                            FormationList_data list = new FormationList_data(R.drawable.soccer_ball, get_user_title, get_user_subTitle, formatDate);
                            formation_list.add(list);

                            //   savePreferences();
                            formationAdapter.notifyDataSetChanged();

                            Intent intent = new Intent(getActivity().getApplicationContext(), Formation.class);
                            intent.putExtra("info_title", get_user_title);
                            intent.putExtra("info_subTitle", get_user_subTitle);
                            intent.putExtra("info_date", get_user_date);

                            startActivity(intent);

                            dialog.dismiss();

                        }
                    }
                });
                dialog.show();

            }
        });
    }

    public void FormationData() {
        formation_list = new ArrayList<FormationList_data>();
    }

    private void setupRecyclerView() {

        swipeController = new SwipeController(new SwipeControllerActions() {

            @Override
            public void onRightClicked(int position) {
                //리스트 지우
                formation_list.remove(position);
                formationAdapter.notifyItemRemoved(position);
                formationAdapter.notifyItemRangeChanged(position, formationAdapter.getItemCount());

            }

            @Override
            public void onLeftClicked(int position) {
            }

        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }
}