package com.example.asus.budgetapp2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 19.03.2018.
 */


public class Month  implements Serializable {

    private int name;
    private int year;
    private double budget;
    private List<Expense> expenses=new ArrayList<Expense>();

    public Month()
    {

    }

    public Month(int name, int year, double budget) {
        this.name = name;
        this.year = year;
        this.budget = budget;

    }

    public void addExpense(Expense expense){

        expenses.add(expense);
    }

    public void deleteExpense (Expense expense){

        expenses.remove(expense);
    }


    public int getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public Double[] getBudget() {

        double expense=0;
        double incomes=0;
        double balance=0;

        double budget=0;

        for (int i=0;i<expenses.size();i++)
        {
            if (expenses.get(i).getPrice()<0)
            {
                expense =expense+expenses.get(i).getPrice();
            }
            else
            {
                incomes =incomes+expenses.get(i).getPrice();
            }
        }

        balance = expense + incomes;

        Double [] results = new Double[3];
        results[0]=expense;
        results[1]=incomes;
        results[2]=balance;
        return results;
    }



    public List<Expense> getExpenses() {
        return expenses;
    }

    public Expense getExpense(int index){return expenses.get(index);}

    public void setName(int name) {
        this.name = name;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }
}
