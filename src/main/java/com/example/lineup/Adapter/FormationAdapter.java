package com.example.lineup.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.util.Base64;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lineup.Activity.Formation;
import com.example.lineup.Fragment.FormationList;
import com.example.lineup.LIstData.FormationList_data;
import com.example.lineup.R;

import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;

public class FormationAdapter extends RecyclerView.Adapter<FormationAdapter.ItemViewHolder> {

    // Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;

    private int visible_count = 1;

    private ArrayList<FormationList_data> listData;

    private Context context;

    // 현재시간을 msec 으로 구한다.
    long now = System.currentTimeMillis();
    // 현재시간을 date 변수에 저장한다.
    Date date = new Date(now);
    // 시간을 나타냇 포맷을 정한다 ( yyyy/MM/dd 같은 형태로 변형 가능 )
    SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd");
    // nowDate 변수에 값을 저장한다.
    String formatDate = sdfNow.format(date);

    public FormationAdapter(ArrayList<FormationList_data> formation_list) {
        this.listData = formation_list;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_formation, parent, false);
        context = parent.getContext();
        //getPreferenceString();
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        if (holder != null) {

            holder.card.setImageResource(listData.get(position).getCard());
            // holder.item_profile.setImageResource(listData.get(position).getDrawableId());
            holder.item_title.setText(listData.get(position).getItem_title());
            holder.item_subTitle.setText(listData.get(position).getItem_subTitle());
            holder.item_date.setText(listData.get(position).getItem_date());


            SharedPreferences prefName = context.getSharedPreferences("name", MODE_PRIVATE);
            SharedPreferences prefDivision = context.getSharedPreferences("division", MODE_PRIVATE);
            SharedPreferences prefEmail = context.getSharedPreferences("email", MODE_PRIVATE);
            SharedPreferences prefTel = context.getSharedPreferences("tel", MODE_PRIVATE);

            String name = prefName.getString("nameString", "");
            String division = prefDivision.getString("divisionString", "");
            String email = prefEmail.getString("emailString", "");
            String tel = prefTel.getString("telString", "");

            if (isItemSelected(position)) {
                holder.card.setVisibility(View.VISIBLE);
            }
        }

    }

    // 값 불러오기
    private void getPreferenceString() {
        SharedPreferences prefName = context.getSharedPreferences("name", MODE_PRIVATE);
        SharedPreferences prefDivision = context.getSharedPreferences("division", MODE_PRIVATE);
        SharedPreferences prefEmail = context.getSharedPreferences("email", MODE_PRIVATE);
        SharedPreferences prefTel = context.getSharedPreferences("tel", MODE_PRIVATE);

        String name = prefName.getString("nameString", "");
        String division = prefDivision.getString("divisionString", "");
        String email = prefEmail.getString("emailString", "");
        String tel = prefTel.getString("telString", "");


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView item_profile, card;
        private TextView item_title, item_subTitle, item_date;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.card);
            // item_profile = itemView.findViewById(R.id.item_profile);
            item_title = itemView.findViewById(R.id.item_title);
            item_subTitle = itemView.findViewById(R.id.item_subTitle);
            item_date = itemView.findViewById(R.id.item_date);

            if (listData.size() == 0) {

            }

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    SharedPreferences pref1 = context.getSharedPreferences("image", MODE_PRIVATE);
                    String image = pref1.getString("imageString", "");
                    Bitmap bitmap = StringtoBitmap(image);

                    Intent intent = new Intent(context.getApplicationContext(), Formation.class);
                    context.startActivity(intent);

                    if (bitmap != null) {
                        card.setImageBitmap(bitmap);

                    }

                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION && visible_count % 2 != 0) {
                        listData.get(pos);
                        card.setVisibility(View.GONE);
                        visible_count++;

                    } else if (visible_count % 2 == 0) {
                        card.setVisibility(View.VISIBLE);
                        visible_count++;

                    }
                }
            });
        }
    }

//    // 값 불러오기
//    private void getPreferences() {
//        SharedPreferences pref1 = context.getSharedPreferences("image", MODE_PRIVATE);
//        String image = pref1.getString("imageString", "");
//        Bitmap bitmap = StringtoBitmap(image);
//
//        if (bitmap != null) {
//            card.setImageBitmap(bitmap);
//
//        }
//
//        bitmap = null;
//    }

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


    private boolean isItemSelected(int position) {
        return selectedItems.get(position, false);
    }

    public void clearSelectedItem() {
        int position;

        for (int i = 0; i < selectedItems.size(); i++) {
            position = selectedItems.keyAt(i);
            selectedItems.put(position, false);
            notifyItemChanged(position);
        }

        selectedItems.clear();
    }
}