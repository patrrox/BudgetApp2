package com.example.asus.budgetapp2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Asus on 19.03.2018.
 */

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {
    private Context mContext;
    private List<Expense> mExpenseList;
    ExpenseAdapter(Context context, List<Expense> objects){
        this.mContext=context;
        mExpenseList = objects;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
      View view = layoutInflater.inflate(R.layout.rv_expense_item,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Expense ex = mExpenseList.get(position);

        TextView expense=holder.expense;
        TextView category=holder.category;
        ImageView categoryImage=holder.categoryImage;
        TextView price=holder.price;
        TextView date=holder.date;

        price.setText(String.valueOf(ex.getPrice()));
        date.setText(String.valueOf(ex.getDate()));
        expense.setText(ex.getName());
        category.setText(ex.getCategory());
        categoryImage.setImageResource(R.drawable.food);
    }


    @Override
    public int getItemCount() {
        return mExpenseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView expense;
        TextView category;
        ImageView categoryImage;
        TextView price;
        TextView date;
        public ViewHolder(View itemView) {
            super(itemView);

            expense = itemView.findViewById(R.id.expenseInfoTextView);
            category = itemView.findViewById(R.id.categoryTextView);
            categoryImage = itemView.findViewById(R.id.categoryImageView);
            price = itemView.findViewById(R.id.priceTextView);
            date = itemView.findViewById(R.id.dateTextView);
        }
    }

}
