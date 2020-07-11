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
import android.widget.Toast;

import com.example.lineup.Activity.MainActivity;
import com.example.lineup.Activity.PositionInfo;
import com.example.lineup.LIstData.FormationList_data;
import com.example.lineup.LIstData.PositionInfoLIst_data;
import com.example.lineup.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;

public class PositionInfoAdapter extends RecyclerView.Adapter<PositionInfoAdapter.ItemViewHolder> {

    // Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();

    // 직전에 클릭됐던 Item의 position
    private int prePosition = -1;

    private int visible_count = 1;

    private ArrayList<PositionInfoLIst_data> listData;

    private Context context;
    MainActivity mainActivity;




    public PositionInfoAdapter(ArrayList<PositionInfoLIst_data> position_info_list, Context context) {
        this.listData = position_info_list;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_position_info, parent, false);
        context = parent.getContext();
        //getPreferenceString();
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        if (holder != null) {

            holder.card.setImageResource(listData.get(position).getCard());
            // holder.item_profile.setImageResource(listData.get(position).getDrawableId());
            holder.item_name.setText(listData.get(position).getItem_name());
            holder.item_division.setText(listData.get(position).getItem_division());
            holder.item_email.setText(listData.get(position).getItem_Email());
            holder.item_tel.setText(listData.get(position).getItem_tel());

            SharedPreferences prefName = context.getSharedPreferences("name", MODE_PRIVATE);
            SharedPreferences prefDivision = context.getSharedPreferences("division", MODE_PRIVATE);
            SharedPreferences prefEmail = context.getSharedPreferences("email", MODE_PRIVATE);
            SharedPreferences prefTel = context.getSharedPreferences("tel", MODE_PRIVATE);

            String name = prefName.getString("nameString", "");
            String division = prefDivision.getString("divisionString", "");
            String email = prefEmail.getString("emailString", "");
            String tel = prefTel.getString("telString", "");


            if (isItemSelected(position)) {


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
        private TextView item_name, item_division, item_email, item_tel;

        public ItemViewHolder(@NonNull final View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.card);
            // item_profile = itemView.findViewById(R.id.item_profile);
            item_name = itemView.findViewById(R.id.item_name);
            item_division = itemView.findViewById(R.id.item_division);
            item_email = itemView.findViewById(R.id.item_email);
            item_tel = itemView.findViewById(R.id.item_tel);

            if (listData.size() == 0) {

            }

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    SharedPreferences pref1 = context.getSharedPreferences("image", MODE_PRIVATE);
                    String image = pref1.getString("imageString", "");
                    Bitmap bitmap = StringtoBitmap(image);
                    //Array exercise[] ={'soccer','baseball','baksetball','vollyball'};
                    // String[] exercise = new String[]{"soccer", "baseball", "basketball", "vollyball"};


                    int[] arr = new int[]{1, 2, 3, 4};

                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {

                        Intent intent = new Intent(context, PositionInfo.class);
//                        intent.putExtra("soccer", 1);
//                        intent.putExtra("baseball", 2);
//                        intent.putExtra("basketball", 3);
//                        intent.putExtra("vollyball", 4);
//                        intent.putExtra("test", 100);
                        intent.putExtra("result", arr[pos]);

                        context.startActivity(intent);
                    }

//                    if (pos != RecyclerView.NO_POSITION && visible_count % 2 != 0) {
//                        listData.get(pos);
//                        card.setVisibility(View.GONE);
//                        visible_count++;
//
//                    } else if (visible_count % 2 == 0) {
//                        card.setVisibility(View.VISIBLE);
//                        visible_count++;
//
//                    }
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