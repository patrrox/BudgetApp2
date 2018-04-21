package com.example.asus.budgetapp2;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


public class StatisticsActivity extends AppCompatActivity {
    String [] monthsName = {"January","February","March","April","May","June","July","August","September","October","November","December"};
    ArrayList<Month>months = new ArrayList<>();
    String currency;
    String[]categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_statistics);


        months = (ArrayList<Month>) getIntent().getSerializableExtra("monthsArrayList");
        categories = (String[]) getIntent().getSerializableExtra("categories");

        currency= (String) getIntent().getSerializableExtra("currency");
       // mainBarChart();
        setCardWithAvarage();
        setHalfPieChart();
        setExpensesBarChart();
    }


    public void setHalfPieChart()
    {

        String xValue[] = categories;
        double yValue[] = new double[categories.length];
        for(int i=0;i<yValue.length;i++)
        {
            yValue[i]=0;
        }


        List<PieEntry> pieEntries = new ArrayList<>();

       // values.add(new PieEntry((float) ((Math.random() * range) + range / 5), mParties[i % mParties.length]));


        for (int i=0;i<months.size();i++)
        {
            Log.d("aktywnosc","dodaje1");
            for (int j=0;j<months.get(i).getExpenses().size();j++)
            {
                Log.d("aktywnosc","dodaje2");
                for (int k=0;k<xValue.length;k++)
                {
                    Log.d("aktywnosc","dodaje3");
                    if(xValue[k].equals(months.get(i).getExpense(j).getCategory()))
                    {

                        Log.d("aktywnosc","dodaj4");
                        yValue[k] = yValue[k] + months.get(i).getExpense(j).getPrice();
                    }
                }
            }
        }

        for (int i=0;i<yValue.length;i++)
        {
          //  yValue[i]=i;
        }

        for (int i=0;i<yValue.length;i++)
        {
            if (yValue[i]!=0)
            {
                pieEntries.add(new PieEntry((float) yValue[i]*-1, xValue[i]));
              //  pieEntries.add(new PieEntry(3, "dsada"));

            }
            //else
            //{
         //       Log.d("aktywnosc","nie ma danych");
           // }
        }

        final int[] MY_COLORS = {Color.rgb(192,0,0), Color.rgb(255,0,0), Color.rgb(255,192,0),
                Color.rgb(127,127,127), Color.rgb(146,208,80), Color.rgb(0,176,80), Color.rgb(79,129,189)};

        PieDataSet dataSet = new PieDataSet(pieEntries,"title");
        //  dataSet.setColors(MY_COLORS);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        PieData data = new PieData(dataSet);




        PieChart chart = (PieChart)findViewById(R.id.pieChart1);

        Display display = getWindowManager().getDefaultDisplay();
        int height = display.getHeight();  // deprecated

        //int offset = (int)(height * 0.65); /* percent to move */
        int offset = (int)(height * 0.25);
        RelativeLayout.LayoutParams rlParams =
                (RelativeLayout.LayoutParams)chart.getLayoutParams();

        rlParams.setMargins(0, -offset, 0, 0);
        chart.setLayoutParams(rlParams);



        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);

       // mChart.setCenterTextTypeface(mTfLight);
        //chart.setCenterText(generateCenterSpannableText());

        //chart.setDrawHoleEnabled(true);
        //chart.setHoleColor(Color.WHITE);

        //chart.setTransparentCircleColor(Color.WHITE);
        //chart.setTransparentCircleAlpha(110);

        //chart.setHoleRadius(58f);
        //chart.setTransparentCircleRadius(61f);

        //chart.setDrawCenterText(true);

        chart.setRotationEnabled(false);
        chart.setHighlightPerTapEnabled(true);

        chart.setMaxAngle(180f); // HALF CHART
        chart.setRotationAngle(360f);

       // chart.setCenterTextOffset(0, -20);


        chart.setUsePercentValues(true);
        data.setValueFormatter(new PercentFormatter());


        chart.getLegend().setEnabled(false);
        chart.getDescription().setEnabled(false);

        //chart.setDrawHoleEnabled(true);
        // chart.setHoleColorTransparent(true);
        //chart.setDrawHoleEnabled(false);
       // chart.setHoleRadius(50);



        //chart.animateY(1000);
        chart.setData(data);
        chart.invalidate();
    }
   /* private void setPieChart() {

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
*/

    public void setCardWithAvarage()
    {
        TextView expensesAvg = findViewById(R.id.ExpensesAvarage);
        TextView incomesAvg = findViewById(R.id.IncomesAvarage);
        TextView balanceAvg = findViewById(R.id.balanceAvarage);

        double expenses=0;
        double incomes=0;
        double balance=0;

        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
        otherSymbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("#0.00",otherSymbols);

        if (months.size()<12)
        {
            for (int i=0;i<months.size();i++)
            {

                expenses = expenses + months.get(i).getBudget()[0];
                incomes = incomes + months.get(i).getBudget()[1];
                balance = balance + months.get(i).getBudget()[2];
            }
            expenses=expenses/months.size();
            incomes=incomes/months.size();
            balance=balance/months.size();


            expensesAvg.setText(df.format(expenses)+currency);
            incomesAvg.setText("+"+df.format(incomes)+currency);
            if (balance>=0)
            {
                balanceAvg.setText("+"+df.format(balance)+currency);
                balanceAvg.setTextColor(Color.parseColor("#1b5e20"));
            }
            if (balance<0)
            {
                balanceAvg.setText(df.format(balance)+currency);
                balanceAvg.setTextColor(Color.parseColor("#8e0000"));
            }

        }
        else
        {

            for (int i=0;i<12;i++)
            {

                expenses = expenses + months.get(i).getBudget()[0];
                incomes = incomes + months.get(i).getBudget()[1];
                balance = balance + months.get(i).getBudget()[2];
            }
            expenses=expenses/12;
            incomes=incomes/12;
            balance=balance/12;


            expensesAvg.setText(df.format(expenses)+currency);
            incomesAvg.setText("+"+df.format(incomes)+currency);
            if (balance>=0)
            {
                balanceAvg.setText("+"+df.format(balance)+currency);
                balanceAvg.setTextColor(Color.parseColor("#1b5e20"));
            }
            if (balance<0)
            {
                balanceAvg.setText(df.format(balance)+currency);
                balanceAvg.setTextColor(Color.parseColor("#8e0000"));
            }
        }
    }



    public void setExpensesBarChart()
    {
        final ArrayList<Month>months1=new ArrayList<>();
        BarChart mChart = findViewById(R.id.expensesBarChart);

        //check size of list (only 6 moths in chart)
        if (months.size()>=6)
        {
            for (int i=0;i<6;i++)
            {
                months1.add(months.get(i));
            }
        }
        else
        {
            months1.addAll(months);
        }

        Collections.reverse(months1);

        mChart.setBackgroundColor(Color.WHITE);
        mChart.setExtraTopOffset(-30f);
        mChart.setExtraBottomOffset(10f);
        mChart.setExtraLeftOffset(30f);
        mChart.setExtraRightOffset(40f);
//mChart.setContextClickable(false);
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);

        mChart.getDescription().setEnabled(false);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawGridBackground(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
       // xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextColor(Color.LTGRAY);
        xAxis.setTextSize(13f);
        xAxis.setAxisMaximum(6);

        xAxis.setLabelCount(5);
      //  xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f);

        YAxis left = mChart.getAxisLeft();
        left.setDrawLabels(false);
        left.setSpaceTop(25f);
        left.setSpaceBottom(25f);
        left.setDrawAxisLine(false);
        left.setDrawGridLines(false);
        left.setDrawZeroLine(true); // draw a zero line
        left.setZeroLineColor(Color.GRAY);
        //left.isDrawZeroLineEnabled();

        left.setDrawZeroLine(true);
        left.setZeroLineWidth(0.7f);
        mChart.getAxisRight().setEnabled(false);
        mChart.getLegend().setEnabled(false);




        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return monthsName[months1.get(Math.min(Math.max((int) value, 0), months1.size()-1)).getName()-1];
            }
        });











        //SETDATA
        ArrayList<BarEntry> values = new ArrayList<BarEntry>();
        List<Integer> colors = new ArrayList<Integer>();

        int green = Color.rgb(110, 190, 102);
        int red = Color.rgb(211, 74, 88);

       // BarEntry entry1 = new BarEntry(0, 0);
       // values.add(entry1);
        int k=0;
        for (int i = 0; i < months1.size(); i++) {

            Month month = months1.get(i);
            double monthsBilans = month.getBudget()[2];
            k=k+1;
            BarEntry entry = new BarEntry(k, (float)monthsBilans);
            values.add(entry);

            // specific colors
            if (monthsBilans >= 0)
                colors.add(green);
            else
                colors.add(red);
        }
      //  Collections.reverse(values);

        BarDataSet set;

        /*if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set = (BarDataSet)mChart.getData().getDataSetByIndex(0);
            set.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {*/
            set = new BarDataSet(values, "Values");
            set.setColors(colors);
            set.setValueTextColors(colors);
            //set.setFormSize(40);

            BarData data = new BarData(set);
            data.setValueTextSize(13f);

            //data.setValueTypeface(mTf);
            data.setValueFormatter(new ValueFormatter());
            data.setBarWidth(0.8f);

            mChart.setData(data);
            mChart.invalidate();


    /*}*/}




/*
    public void mainBarChart()
    {
       // mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        BarChart barChart = findViewById(R.id.barChart1);


        barChart.setBackgroundColor(Color.WHITE);
        barChart.setExtraTopOffset(-30f);
        barChart.setExtraBottomOffset(10f);
        barChart.setExtraLeftOffset(70f);
        barChart.setExtraRightOffset(70f);

        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);

        barChart.getDescription().setEnabled(false);

        // scaling can now only be done on x- and y-axis separately
        barChart.setPinchZoom(false);

        barChart.setDrawGridBackground(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
       // xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextColor(Color.LTGRAY);
        xAxis.setTextSize(13f);
        xAxis.setLabelCount(5);
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f);

        YAxis left = barChart.getAxisLeft();
        left.setDrawLabels(false);
        left.setSpaceTop(25f);
        left.setSpaceBottom(25f);
        left.setDrawAxisLine(false);
        left.setDrawGridLines(false);
        left.setDrawZeroLine(true); // draw a zero line
        left.setZeroLineColor(Color.GRAY);
        left.setZeroLineWidth(0.7f);
        barChart.getAxisRight().setEnabled(false);
        barChart.getLegend().setEnabled(false);



        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return "nazwa";
            }
        });






        ArrayList<BarEntry> values = new ArrayList<BarEntry>();
        List<Integer> colors = new ArrayList<Integer>();

        int green = Color.rgb(110, 190, 102);
        int red = Color.rgb(211, 74, 88);

        for (int i = 0; i < months.size(); i++) {

            Month month = months.get(i);
            double balance = month.getBudget()[2];

            BarEntry entry = new BarEntry(month.getName(), (float) balance);
            values.add(entry);

            // specific colors
            if (balance <= 0)
                colors.add(red);
            else
                colors.add(green);
        }

        BarDataSet set;

        if (barChart.getData() != null &&barChart.getData().getDataSetCount() > 0) {
            set = (BarDataSet)barChart.getData().getDataSetByIndex(0);
            set.setValues(values);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            set = new BarDataSet(values, "Values");
            set.setColors(colors);
            set.setValueTextColors(colors);

            BarData data = new BarData(set);
            data.setValueTextSize(13f);
           // data.setValueTypeface(mTf);
            data.setValueFormatter(new ValueFormatter());
            data.setBarWidth(0.8f);

            barChart.setData(data);
            barChart.invalidate();
        }

    }




*/
private void moveOffScreen() {


}

    private class ValueFormatter implements IValueFormatter
    {

        private DecimalFormat mFormat;

        public ValueFormatter() {
            mFormat = new DecimalFormat("#####0.00");
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return mFormat.format(value);
        }
    }
}




