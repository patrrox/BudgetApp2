package com.example.asus.budgetapp2;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String path = Environment.getExternalStorageDirectory()+"/BudgetApp";
    private final int MEMORY_ACCESS = 5;
    private static final int EDIT_EXPENSE_ACTIVITY_REQUEST_CODE = 1;
    private static final int ADD_EXPENSE_ACTIVITY_REQUEST_CODE = 2;
    private static final int ADD_INCOME_ACTIVITY_REQUEST_CODE = 3;
    private static final int SPLASH_TIME_OUT_INTRO = 4000;
    String [] categories = {"Food","Home","Transport","Clothes","Entertainment","Beauty","Health","Shopping"};
    int [] categioriesImageId = {R.drawable.food,R.drawable.home,R.drawable.transport,R.drawable.clothes,R.drawable.enterteimant,R.drawable.beauty,R.drawable.health,R.drawable.shopping};

    String [] monthsName = {"January","February","March","April","May","June","July","August","September","October","November","December"};
    String currency = "zł";
    String [] allCurrency = {"zł","€","$"};

    private ArrayList<Month> monthArrayList = new ArrayList<>();
    List<Expense> expenses;
    RecyclerView recyclerView;
    ExpenseAdapterWithSwipe expenseAdapterWithSwipe;
    int indexOfMonthExpensesList=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //showIntro(SPLASH_TIME_OUT_INTRO);

                Intent intent = new Intent(MainActivity.this,IntroActivity.class);
                startActivity(intent);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerView = findViewById(R.id.recyclerViewOfExpense);
       // monthArrayList.add(new Month(5,2018,0));

        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {

        }
        else
        {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MEMORY_ACCESS);
        }

        getData();

       // addDataToList();

        addExpense();


        expenses=monthArrayList.get(indexOfMonthExpensesList).getExpenses();


        ExpenseAdapter expenseAdapter = new ExpenseAdapter(this,expenses);
        expenseAdapterWithSwipe = new ExpenseAdapterWithSwipe(this,expenses,categories,categioriesImageId,currency);


        //recyclerView.setAdapter(expenseAdapter);
        recyclerView.setAdapter(expenseAdapterWithSwipe);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


       // setPieChart();
        setCardInfo ();
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_statistics) {

            Intent intent = new Intent(this,StatisticsActivity.class);
            intent.putExtra("monthsArrayList",monthArrayList);
            intent.putExtra("categories",categories);
            intent.putExtra("currency",currency);
            startActivity(intent);

        } else if (id == R.id.nav_month) {


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose Month");


            String[] months= new String[monthArrayList.size()];


            for (int i=0;i<monthArrayList.size();i++)
            {
                months[i] =(monthArrayList.get(i).getName() + " " + monthArrayList.get(i).getYear());
            }


            int checkedItem = 0; // newest
            builder.setSingleChoiceItems(months, checkedItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // user checked an item
                }
            });

            // add OK and Cancel buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    ListView lw = ((AlertDialog)dialog).getListView();

                    indexOfMonthExpensesList=lw.getCheckedItemPosition();
                    expenses=monthArrayList.get(indexOfMonthExpensesList).getExpenses();

                   expenseAdapterWithSwipe.setExpensesList(expenses);


                    expenseAdapterWithSwipe.notifyDataSetChanged();

                   // expenseAdapterWithSwipe = new ExpenseAdapterWithSwipe(getApplicationContext(),expenses,categories);
                   // recyclerView.setAdapter(expenseAdapterWithSwipe);


                    setCardInfo();

                    // user clicked OK
                }
            });
            builder.setNegativeButton("Cancel", null);

// create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();


        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_currency) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose Currency");
            String[] months= allCurrency;

            int checkedItem = 0; // newest
            builder.setSingleChoiceItems(months, checkedItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // user checked an item
                }
            });

            // add OK and Cancel buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    ListView lw = ((AlertDialog)dialog).getListView();
                    int indexOfCurrency=lw.getCheckedItemPosition();

                    currency= (String) lw.getItemAtPosition(indexOfCurrency);

                    expenseAdapterWithSwipe.setCurrency(currency);
                    expenseAdapterWithSwipe.notifyDataSetChanged();




                    setCardInfo();

                    // user clicked OK
                }
            });
            builder.setNegativeButton("Cancel", null);

// create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();






        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void click(View view) {
        Intent intent;

        switch(view.getId())
        {
            case R.id.menuButton:
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(Gravity.START);
                break;


        }
    }

    public void addDataToList()
    {
        Log.d("aktywnosc","dodano dane");




        Month month1 = new Month(2, 2018, 0);

        Date date = new Date(2018, 2, 10);

        Expense expense1_1 = new Expense("shoping1", "Food", -21.32, date);
        Expense expense1_2 = new Expense("shoping2", "Home", -10.32, date);
        Expense expense1_3 = new Expense("shoping2", "Transport", -10.32, date);
        Expense expense1_4 = new Expense("shoping2", "Clothes", -10.32, date);
        Expense expense1_5 = new Expense("shoping2", "Entertainment", -10.32, date);
        Expense expense1_6 = new Expense("shoping2", "Beauty", -10.32, date);
        Expense expense1_7 = new Expense("shoping2", "Health", -10.32, date);
        Expense expense1_8 = new Expense("shoping2", "Shopping", -10.32, date);
        Expense expense1_9 = new Expense("czesne","Income",13123.23,date);
        month1.addExpense(expense1_1);
        month1.addExpense(expense1_2);
        month1.addExpense(expense1_3);
        month1.addExpense(expense1_4);
        month1.addExpense(expense1_5);
        month1.addExpense(expense1_6);
        month1.addExpense(expense1_7);
        month1.addExpense(expense1_8);
        month1.addExpense(expense1_9);

        monthArrayList.add(month1);


        Month month2 = new Month(3, 2018, 0);
        Expense expense2_1 = new Expense("shoping", "Food", -20, date);

        month2.addExpense(expense2_1);

        monthArrayList.add(month2);
        Month month3 = new Month(4, 2018, 0);
        Month month4 = new Month(5, 2018, 0);
        Month month5 = new Month(6, 2018, 0);
        monthArrayList.add(month3);
        monthArrayList.add(month4);
        monthArrayList.add(month5);
        monthArrayList.add(month3);
        monthArrayList.add(month4);
        monthArrayList.add(month5);
        monthArrayList.add(month3);
        monthArrayList.add(month4);
        monthArrayList.add(month5);
        monthArrayList.add(month3);
        monthArrayList.add(month4);
        monthArrayList.add(month5);
    }

    private void setPieChart() {

        //String xValue[] = {"Food","Transport","Home","Entertainment"};
        String xValue[] = categories;
        //double yValue[] = {12.3,34.2,54.2,54.8};
        double yValue[] = new double[categories.length];
        for(int i=0;i<yValue.length;i++)
        {
            yValue[i]=0;
        }

        List<PieEntry> pieEntries = new ArrayList<>();



        for(int i =0;i<expenses.size();i++)
        {
            for (int j=0;j<yValue.length;j++)
            {
                if(expenses.get(i).getCategory().equals(xValue[j]))
                {
                    yValue[j] = yValue[j] + expenses.get(i).getPrice();
                }
            }
        }


        for (int i=0;i<xValue.length;i++)
        {
            if (yValue[i]!=0)
            {
                pieEntries.add(new PieEntry((float) yValue[i]*-1, xValue[i]));
            }
        }

        final int[] MY_COLORS = {Color.rgb(192,0,0), Color.rgb(255,0,0), Color.rgb(255,192,0),
                Color.rgb(127,127,127), Color.rgb(146,208,80), Color.rgb(0,176,80), Color.rgb(79,129,189)};

        PieDataSet dataSet = new PieDataSet(pieEntries,"title");
      //  dataSet.setColors(MY_COLORS);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
        PieData data = new PieData(dataSet);




        PieChart chart = findViewById(R.id.pieChart);
        chart.getLegend().setEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.setDrawHoleEnabled(true);
       // chart.setHoleColorTransparent(true);
        //chart.setDrawHoleEnabled(false);
        chart.setHoleRadius(50);



        chart.animateY(1000);
        chart.setData(data);
        chart.invalidate();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Retrieve data in the intent

        Log.d("aktywnosc","result");
        if(resultCode ==EDIT_EXPENSE_ACTIVITY_REQUEST_CODE)
        {

            if (data.getSerializableExtra("action").equals("back"))
            {
                Log.d("aktywnosc","kliknieto back");
            }
                else
            {


                Log.d("aktywnosc", "result_inside");

                int position = (int) data.getSerializableExtra("newPosition");

                expenses.get(position).setDate((Date) data.getSerializableExtra("newDate"));
                expenses.get(position).setName((String) data.getSerializableExtra("newName"));
                expenses.get(position).setCategory((String) data.getSerializableExtra("newCategory"));
                expenses.get(position).setPrice((Double) data.getSerializableExtra("newPrice") * -1);
                Log.d("aktywnosc", expenses.get(position).getName());


                expenseAdapterWithSwipe.notifyDataSetChanged();
                //listView.invalidateViews();
            }
        }
        if(resultCode ==-1)
        {
            Log.d("aktywnosc","nie zrobiono nic");
        }

        if (requestCode == ADD_EXPENSE_ACTIVITY_REQUEST_CODE)
        {


            if (data.getSerializableExtra("action").equals("expense"))
            {

                Expense expense = (Expense) data.getSerializableExtra("expense");

                expenses.add(0,expense);
                expenseAdapterWithSwipe.notifyItemInserted(0);
                Log.d("aktywnosc","dodano expense");
            }
            if (data.getSerializableExtra("action").equals("income"))
            {

                Expense income = (Expense) data.getSerializableExtra("income");
                expenses.add(0,income);
                expenseAdapterWithSwipe.notifyItemInserted(0);
                Log.d("aktywnosc","dodano income");
            }

        }

        if (requestCode == -11)
        {
            Log.d("aktywnosc","anulowano edytowanie");
        }

    }


    public void addExpense()
    {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

               Intent intent;
                intent = new Intent(MainActivity.this,AddExpenseActivity.class);
                intent.putExtra("categories",categories);
                startActivityForResult(intent,ADD_EXPENSE_ACTIVITY_REQUEST_CODE);
            }
        });
    }


    public void setCardInfo ()
    {
        setPieChart();

        TextView monthNameTextView = findViewById(R.id.nameTextView);
        TextView expensesTextView = (TextView) findViewById(R.id.ExpensesAvarage);
        TextView incomesTextView = findViewById(R.id.IncomesAvarage);
        TextView balanceTextView = findViewById(R.id.balanceTextView);

        Double [] results = getBudget(expenses);

        monthNameTextView.setText(monthsName[monthArrayList.get(indexOfMonthExpensesList).getName()-1]);

        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
        otherSymbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#0.00",otherSymbols);



        expensesTextView.setText(df.format(results[0])+currency);
        incomesTextView.setText("+"+df.format(results[1])+currency);

        //SET COLOR FOR BALANCE
        if (results[2]>0)
        {
            balanceTextView.setText("+"+df.format(results[2])+currency);
            balanceTextView.setTextColor(Color.parseColor("#1b5e20"));
        }
        if (results[2]<0)
        {
            balanceTextView.setText(df.format(results[2])+currency);
            balanceTextView.setTextColor(Color.parseColor("#8e0000"));
        }

    }

    public Double[] getBudget(List<Expense> expenses) {

        double expense=0;
        double incomes=0;
        double balance;



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




    //ZAPIS ODCZYT

    //przeciona metoda do dostepow
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode)
        {
            case MEMORY_ACCESS:
                if (grantResults.length>0&& grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {

                }
                else
                {


                    Toast.makeText(getApplicationContext(),"jezeli nie zostanie wyrazona zgoda do pamieci nie bedzie mozna zapisac",Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MEMORY_ACCESS);
                }
                break;
        }
    }





    // zapisz stan aplikacji





    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putString("MyString", "hello");
        saveDate();
        // etc.

        Log.d("aktywnosc"," zapisanie stanu");
    }

    //odczytaj stan aplikacji

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        String h = savedInstanceState.getString("MyString");

        Log.d("aktywnosc"," odczytanie stanu"+h);
    }







    //metoda do zapisu Listy miesiecy

    public void createDir()
    {
        File folder = new File(path);
        if (!folder.exists())
        {
            try{
                folder.mkdir();
            }
            catch(Exception e)
            {
                Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
            }
        }
    }

    public void createFile()
    {
        File file = new File(path +"/"+"data");
        FileOutputStream fOut;
        ObjectOutputStream oos;
        try{
            fOut = new FileOutputStream(file);

            oos = new ObjectOutputStream(fOut);
            // myOutWriter.write(String.valueOf(monthArrayList));
            oos.writeObject(monthArrayList);
            fOut.close();
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }

    }

    public void saveDate()
    {
        createDir();
        createFile();
        Log.d("aktywnosc","zapisano do pliku");
    }

    private void getData()
    {
        FileInputStream fin;
        ObjectInputStream ois;

        try {
            fin = new FileInputStream(path +"/"+"data");
            ois = new ObjectInputStream(fin);
            monthArrayList = (ArrayList<Month>) ois.readObject();
            checkMonthArrayList();
            ois.close();
        }
        catch(Exception e)
        {
            Log.d("aktywnosc"," blad wczytywania pliku");
            monthArrayList = new ArrayList<>();
            checkMonthArrayList();
        }
        Log.d("aktywnosc","odczytano z pliku");
    }

    //dodaje miesiac biezacy jezeli jeszcze go nie ma
    private void checkMonthArrayList()
    {
        Calendar now = Calendar.getInstance();

        int month = now.get(Calendar.MONTH)+1;
        int year = now.get(Calendar.YEAR);

        boolean isInside = false;
        for (int i=0;i<monthArrayList.size();i++)
        {
            if (monthArrayList.get(i).getName()==month && monthArrayList.get(i).getYear()==year)
            {
                isInside = true;
            }
        }

        if (!isInside)
        {
            Month monthToAdd = new Month(month,year,0);
          //  monthToAdd.addExpense(new Expense("ds","zak",10,new Date(2018, 2, 10)));
            monthArrayList.add(0,monthToAdd);
        }

    }



    private void showIntro(int time)
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,IntroActivity.class);
                startActivity(intent);
                finish();
            }
        },time);
    }
}
