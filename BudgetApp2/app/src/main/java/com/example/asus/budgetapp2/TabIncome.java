package com.example.asus.budgetapp2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Asus on 26.03.2018.
 */

public class TabIncome extends Fragment {

    EditText nameEditText;
    TextView dateTextView;
    EditText priceEditText;
    int year;
    int month;
    int day;


    View rootView;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
             rootView = inflater.inflate(R.layout.fragment_income, container, false);




            //NAME
            nameEditText = rootView.findViewById(R.id.ExpenseNameEditText);
            //CATEGORY


            //DATE
            dateTextView = rootView.findViewById(R.id.dateTextView);

            Calendar cal = Calendar.getInstance();

            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            day = cal.get(Calendar.DAY_OF_MONTH);
            dateTextView.setText(day+"/"+(month+1)+"/"+year);
            dateTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int yearOnSet, int monthOnSet, int dayOnSet) {
                            dateTextView.setText(dayOnSet+"/"+(monthOnSet+1)+"/"+yearOnSet);
                            year = yearOnSet;
                            month = monthOnSet;
                            day=dayOnSet;



                        }
                    },year,month,day);
                    datePickerDialog.show();
                }

            });

            //PRICE
            priceEditText=rootView.findViewById(R.id.priceEditText);
            DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
            otherSymbols.setDecimalSeparator('.');
            DecimalFormat df = new DecimalFormat("#.00",otherSymbols);
            // double pr = 0;
            // priceEditText.setText(df.format(pr));
            priceEditText.addTextChangedListener(new NumberTextWatcher(priceEditText));






            addIncome();
            return rootView;
        }

    public void addIncome()
    {
        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.AddIncomefloatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();



                if (nameEditText.getText().toString().matches("")||priceEditText.getText().toString().matches(""))
                {
                    if (nameEditText.getText().toString().matches(""))
                    {
                        Toast.makeText(rootView.getContext(),"You have to set name",Toast.LENGTH_SHORT).show();
                    }
                    if (priceEditText.getText().toString().matches(""))
                    {
                        Toast.makeText(rootView.getContext(),"You have to set price",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Intent intent = new Intent();
                    intent.putExtra("action","income");
                    Expense expense = new Expense(nameEditText.getText().toString(),"Income",Double.parseDouble(priceEditText.getText().toString()),new Date(year,month-1,day));
                    intent.putExtra("income",expense);
                    getActivity().setResult(2,intent);
                    getActivity().finish();
                }




            }
        });

    }



}

