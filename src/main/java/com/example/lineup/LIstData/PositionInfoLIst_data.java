package com.example.lineup.LIstData;

public class PositionInfoLIst_data {

    private int drawableId, card;
    private String item_name, item_division, item_email, item_tel;

    public int getDrawableId() {
        return drawableId;
    }

    public int getCard() {
        return card;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getItem_division() {
        return item_division;
    }

    public String getItem_Email() {
        return item_email;
    }

    public String getItem_tel() {
        return item_tel;
    }

    public PositionInfoLIst_data( int card, String item_name, String item_division, String item_email, String item_tel) {
        this.drawableId = drawableId;
        this.card = card;
        this.item_name = item_name;
        this.item_division = item_division;
        this.item_email = item_email;
        this.item_tel = item_tel;
    }

}
