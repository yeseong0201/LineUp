package com.example.lineup.LIstData;

public class FormationList_data {

    private int ball_type;
    private String item_title, item_subTitle, item_date;

    public int getCard() {
        return ball_type;
    }

    public String getItem_title() {
        return item_title;
    }

    public String getItem_subTitle() {
        return item_subTitle;
    }

    public String getItem_date() {
        return item_date;
    }

    public FormationList_data(int ball_type, String item_title, String item_subTitle, String item_date) {

        this.ball_type = ball_type;
        this.item_title = item_title;
        this.item_subTitle = item_subTitle;
        this.item_date = item_date;

    }

}
