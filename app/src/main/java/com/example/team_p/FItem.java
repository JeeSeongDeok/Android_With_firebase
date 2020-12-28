package com.example.team_p;

public class FItem {
    private int profile;
    private String name_text;
    private String product_text;

    public int getProfile() {
        return profile;
    }

    public String getName_text() {
        return name_text;
    }

    public String getProduct_text() {return product_text;}

    public FItem( String name_text, String product_text){
        this.name_text=name_text;
        this.product_text = product_text;
    }
}
