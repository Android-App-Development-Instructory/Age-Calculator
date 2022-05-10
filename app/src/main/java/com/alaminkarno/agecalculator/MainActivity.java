package com.alaminkarno.agecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button fromDateBTN,toDateBTN,calculateBTN;
    private TextView resultTV;

    DatePickerDialog.OnDateSetListener dateSetListenerFromDate,dateSetListenerToDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fromDateBTN = findViewById(R.id.fromAgeBTN);
        toDateBTN = findViewById(R.id.toAgeButton);
        calculateBTN = findViewById(R.id.calculateAgeBTN);
        resultTV = findViewById(R.id.resultTV);


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String todayDate = simpleDateFormat.format(calendar.getTime());
        toDateBTN.setText(todayDate);

        fromDateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListenerFromDate,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        dateSetListenerFromDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String birthDate = dayOfMonth+"/"+month+"/"+year;
                fromDateBTN.setText(birthDate);
            }
        };

        toDateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MainActivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListenerToDate,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        dateSetListenerToDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String todayDate = dayOfMonth+"/"+month+"/"+year;
                toDateBTN.setText(todayDate);
            }
        };


        calculateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String birthDate = fromDateBTN.getText().toString();
                String todayDate = toDateBTN.getText().toString();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

                try {
                    Date date1 = simpleDateFormat.parse(birthDate);
                    Date date2 = simpleDateFormat.parse(todayDate);

                    long startDate = date1.getTime();
                    long endDate = date2.getTime();

                    if(startDate <= endDate){

                        Period period = new Period(startDate,endDate, PeriodType.yearMonthDay());
                        int year = period.getYears();
                        int month = period.getMonths();
                        int day = period.getDays();

                        resultTV.setText("You are "+year+" Years, "+month+" Months, "+day+" Days Old.");
                    }
                    else{
                        resultTV.setText("Birthdate should not be Larger than today's Date.");
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        });


    }
}