package com.example.team_p;

import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String product;
    private String positive;
    private String negative;

    public User() {} // 빈생성자

    public User(String product, String positive, String negative) {
        this.product = product;
        this.positive = positive;
        this.negative = negative;
    }

    public String getProduct() {
        return product;
    }

    public String getPositive() {
        return positive;
    }

    public String getNegative() {
        return negative;
    }

    public void setNegative(String negative) {
        this.negative = negative;
    }

    public void setPositive(String positive) {
        this.positive = positive;
    }

    public void setProduct(String product) {
        this.product = product;
    }
}


