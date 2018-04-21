package com.example.asus.budgetapp2;

/**
 * Created by Asus on 19.03.2018.
 */


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ExpenseAdapterWithSwipe extends RecyclerSwipeAdapter<ExpenseAdapterWithSwipe.SimpleViewHolder> {

    private static final int EDIT_EXPENSE_ACTIVITY_REQUEST_CODE = 1;
    private Context mContext;
    private List<Expense> mExpenseList;
    String [] categories;
    int [] categioriesImageId;
    String currency;

    public ExpenseAdapterWithSwipe(Context context, List<Expense> objects,String [] categories,int [] categioriesImageId,String currency) {
        this.mContext = context;
        this.mExpenseList = objects;
        this.categories = categories;
        this.categioriesImageId = categioriesImageId;
        this.currency = currency;
    }

    public void setExpensesList(List<Expense>objects)
    {
        mExpenseList=objects;
    }

    public void setCurrency(String currency){this.currency=currency;}


    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_layout, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        final Expense item = mExpenseList.get(position);

       // viewHolder.Name.setText(item.getName() + " - Row Position " + position);
       // viewHolder.EmailId.setText(item.getEmailId());


        TextView expense=viewHolder.expense;
        TextView category=viewHolder.category;
        ImageView categoryImage=viewHolder.categoryImage;
        TextView price=viewHolder.price;
        TextView date=viewHolder.date;


        //SET PRICE

        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
        otherSymbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#0.00",otherSymbols);

        price.setText(String.valueOf(df.format(item.getPrice()))+" "+currency);
        if (item.getPrice()<0)
        {
            price.setTextColor(Color.parseColor("#8e0000"));
        }
        else
        {
            price.setTextColor(Color.parseColor("#1b5e20"));
        }

        //SET EXPENSE

        expense.setText(item.getName());


        date.setText(String.valueOf(item.getDate()));

        category.setText(item.getCategory());


        // SET IMAGE

        if (item.getCategory().equals("Income"))
        {
            categoryImage.setImageResource(R.drawable.income);
        }
        else
        {
            int index = 0;
            for (int i=0;i<categories.length;i++)
            {
                if (item.getCategory().equals(categories[i]))
                {
                    index = i;
                }
            }

            categoryImage.setImageResource(categioriesImageId[index]);    /////
        }



        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        //dari kiri
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayout.findViewById(R.id.bottom_wrapper1));

        //dari kanan
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayout.findViewById(R.id.bottom_wraper));



        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

        viewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, " Click : " + item.getName(), Toast.LENGTH_SHORT).show();
            }
        });

      /*  viewHolder.btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Clicked on Information " + item.getName(), Toast.LENGTH_SHORT).show();
            }
        });*/

       /* viewHolder.Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(view.getContext(), "Clicked on Share " + item.getName(), Toast.LENGTH_SHORT).show();
            }
        });*/

        viewHolder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                Intent editIntent = new Intent(mContext, EditExpenseActivity.class);

                Expense expense = mExpenseList.get(position);

                int pos = position;

                editIntent.putExtra("expense",expense);
                editIntent.putExtra("categories",categories);
                editIntent.putExtra("position",pos);

                ((Activity) mContext).startActivityForResult(editIntent,EDIT_EXPENSE_ACTIVITY_REQUEST_CODE);
                Log.d("aktywnosc", "Edit");
                mItemManger.closeItem(position);

                Toast.makeText(view.getContext(), "Clicked on Edit  " + item.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemManger.removeShownLayouts(viewHolder.swipeLayout);
                mExpenseList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mExpenseList.size());
                mItemManger.closeAllItems();
                Toast.makeText(v.getContext(), "Deleted " + item.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        mItemManger.bindView(viewHolder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return mExpenseList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder{
        public SwipeLayout swipeLayout;
       // public TextView Name;
       // public TextView EmailId;
        public ImageButton Delete;
        public ImageButton Edit;
       // public TextView Share;
       // public ImageButton btnLocation;



        TextView expense;
        TextView category;
        ImageView categoryImage;
        TextView price;
        TextView date;


        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
           // Name = (TextView) itemView.findViewById(R.id.Name);
           // EmailId = (TextView) itemView.findViewById(R.id.EmailId);
            Delete =  itemView.findViewById(R.id.Delete);
            Edit =  itemView.findViewById(R.id.Edit);
          //  Share = (TextView) itemView.findViewById(R.id.Share);
        //    btnLocation = (ImageButton) itemView.findViewById(R.id.btnLocation);



            expense = itemView.findViewById(R.id.expenseInfoTextView);
            category = itemView.findViewById(R.id.categoryTextView);
            categoryImage = itemView.findViewById(R.id.categoryImageView);
            price = itemView.findViewById(R.id.priceTextView);
            date = itemView.findViewById(R.id.dateTextView);

        }
    }
}