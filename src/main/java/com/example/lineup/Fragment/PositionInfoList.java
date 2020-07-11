package com.example.lineup.Fragment;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lineup.Activity.Formation;
import com.example.lineup.Activity.PositionInfo;
import com.example.lineup.Adapter.FormationAdapter;
import com.example.lineup.Adapter.PositionInfoAdapter;
import com.example.lineup.LIstData.FormationList_data;
import com.example.lineup.Activity.MainActivity;
import com.example.lineup.LIstData.PositionInfoLIst_data;
import com.example.lineup.R;
import com.example.lineup.controllers.SwipeController;
import com.example.lineup.controllers.SwipeControllerActions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class PositionInfoList extends Fragment {

    ImageView card;

    SwipeController swipeController = null;

    RecyclerView recyclerView;

    String get_user_name;
    String get_user_tel;
    String get_user_email;
    String get_user_division;


    RecyclerView.LayoutManager layoutManager;

    //MainActivity mainActivity = (MainActivity) getActivity();
    MainActivity mainActivity = (MainActivity) getActivity();
    public ArrayList<PositionInfoLIst_data> position_list;

    PositionInfoAdapter positionInfoAdapter;

    public PositionInfoList() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PositionInfoData();

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_position_info_list, container, false);

        recyclerView = v.findViewById(R.id.recyclerview);

        card = v.findViewById(R.id.card);


        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(mainActivity);

        recyclerView.setLayoutManager(layoutManager);

        positionInfoAdapter = new PositionInfoAdapter(position_list, getContext());

        recyclerView.setAdapter(positionInfoAdapter);

        // cardAdapter.notifyDataSetChanged();

        position_list.size();

        PositionInfoLIst_data soccer = new PositionInfoLIst_data(R.drawable.soccer_ball, "축구", get_user_division, get_user_email, get_user_tel);
        position_list.add(soccer);

        PositionInfoLIst_data baseball = new PositionInfoLIst_data(R.drawable.baseball_ball, "야구", get_user_division, get_user_email, get_user_tel);
        position_list.add(baseball);

        PositionInfoLIst_data basketball = new PositionInfoLIst_data(R.drawable.basketball, "농구", get_user_division, get_user_email, get_user_tel);
        position_list.add(basketball);

        PositionInfoLIst_data vollyball = new PositionInfoLIst_data(R.drawable.volleyball, "배구", get_user_division, get_user_email, get_user_tel);
        position_list.add(vollyball);

        position_list.size();

        positionInfoAdapter.notifyItemInserted(position_list.size());
        positionInfoAdapter.notifyDataSetChanged();

        return v;
    }


    public void PositionInfoData() {
        position_list = new ArrayList<>();
    }

}