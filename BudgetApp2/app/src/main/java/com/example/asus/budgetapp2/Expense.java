package com.example.asus.budgetapp2;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Asus on 19.03.2018.
 */


public class Expense implements Serializable {

    private String name;
    private String category;
    private double price;
    private Date date;

    public Expense() {

    }

    public Expense(String name, String category, double price, Date Date) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.date = Date;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public Date getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
