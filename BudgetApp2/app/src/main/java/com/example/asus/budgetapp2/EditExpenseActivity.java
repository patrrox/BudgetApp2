package com.example.asus.budgetapp2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditExpenseActivity extends AppCompatActivity {


    int day,month,year=0;

    TextView dateTextView;
    Expense expense;
    EditText nameEditText;
    Spinner categorySpinner;
    EditText priceEditText;

    int positionInCategorySpinner = 0;
    String [] categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);




        dateTextView = findViewById(R.id.dateTextView);
        expense = (Expense) getIntent().getSerializableExtra("expense");

        //EDYCJA DATY
        final Date date = expense.getDate(); // your date
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        year = cal.get(Calendar.YEAR)-1900;
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        dateTextView.setText(day+"/"+(month+1)+"/"+year);
        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditExpenseActivity.this, new DatePickerDialog.OnDateSetListener() {
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

        // NAME

        nameEditText = findViewById(R.id.ExpenseNameEditText);
        nameEditText.setText(expense.getName());

        //CATEGORY

        categorySpinner = findViewById(R.id.categorySpinner);
        categories = (String[]) getIntent().getSerializableExtra("categories");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,categories);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setSelection(getCategoryPosition());


        //PRICE
        priceEditText=findViewById(R.id.priceEditText);
        // priceEditText.addTextChangedListener(new NumberTextWatcher(priceEditText, "%.2f $"));


        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
        otherSymbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#.00",otherSymbols);


        if (expense.getPrice()<0)
        {
            priceEditText.setText(df.format(expense.getPrice()*-1));
        }
        else
        {
            priceEditText.setText(df.format(expense.getPrice()));
        }



        priceEditText.addTextChangedListener(new NumberTextWatcher(priceEditText));
       /* priceEditText.addTextChangedListener(new TextWatcher () {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable edt) {

                String temp = edt . toString ();
                int posDot = temp . indexOf (".");

                if (posDot <= 0) { return ; } if (temp.length() - posDot - 1 > 2) {
                    edt . delete(posDot + 3, posDot + 4);
                }
            }
        });
        */
       /* priceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().matches("^\\$(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$")) {
                    String userInput = "" + s.toString().replaceAll("[^\\d]", "");
                    StringBuilder cashAmountBuilder = new StringBuilder(userInput);

                    while (cashAmountBuilder.length() > 3 && cashAmountBuilder.charAt(0) == '0') {
                        cashAmountBuilder.deleteCharAt(0);
                    }
                    while (cashAmountBuilder.length() < 3) {
                        cashAmountBuilder.insert(0, '0');
                    }
                    cashAmountBuilder.insert(cashAmountBuilder.length() - 2, '.');

                    priceEditText.removeTextChangedListener(this);
                    priceEditText.setText(cashAmountBuilder.toString());

                    priceEditText.setTextKeepState("$" + cashAmountBuilder.toString());
                    Selection.setSelection(priceEditText.getText(), cashAmountBuilder.toString().length() + 1);

                    priceEditText.addTextChangedListener(this);
                }
            }
        });
*/
    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        Log.d("aktywnosc","destroy edytowanie");
        super.onDestroy();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        Log.d("aktywnosc","onBackPressed");
        Intent intent = new Intent();
        intent.putExtra("action","back");
        setResult(1,intent);
        finish();

       // Toast.makeText(this,"onBackPressed",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent();
        setResult(-11,intent);

        Log.d("aktywnosc","anulowano edytowanie");
        finish();

    }


    public void clickSave(View view) {



       /* Intent intent = new Intent();
        String n = getIntent().getStringExtra("name");
        String back = n+"NEW";
        intent.putExtra("newName",back);
        setResult(1,intent);
        finish();
        */

        Log.d("aktywnosc","zapis expense");
        //Double amount = Double.parseDouble(priceEditText.getText().toString());
        //Log.d("aktywnosc", String.valueOf(amount));

        //DATA
        Date date = new Date(year,month,day);
        Log.d("aktywnosc",String.valueOf(date.getDate()+"/")+String.valueOf(date.getMonth())+"/"+String.valueOf(date.getYear()));
        //NAME
        String name = nameEditText.getText().toString();

        //CATEGORY
        String category = categories[categorySpinner.getSelectedItemPosition()];


        //PRICE
        double price;
        if(TextUtils.isEmpty(priceEditText.getText().toString()))
        {
            price = expense.getPrice();
            Log.d("aktywnosc","pusty");
        }
        else
        {
            price= Double.parseDouble(priceEditText.getText().toString());
        }



        Intent intent = new Intent();
        intent.putExtra("action","save");
        intent.putExtra("newDate",date);
        intent.putExtra("newName",name);
        intent.putExtra("newCategory",category);
        intent.putExtra("newPrice",price);
        intent.putExtra("newPosition",getIntent().getSerializableExtra("position"));
        setResult(1,intent);
        finish();

    }

    public void clickBack(View view) {
        Intent intent = new Intent();
        setResult(-1,intent);
        finish();
    }


    public int getCategoryPosition(){
        int position =0;

        for (int i=0;i<categories.length;i++){
            if (categories[i].equals(expense.getCategory()))
            {
                position=i;
            }
        }
        return position;
    }





}
