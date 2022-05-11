package com.alaminkarno.agecalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.animation.ValueAnimator;
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

    private CardView fromDateBTN,toDateBTN,calculateBTN;
    private TextView birthTV,todayTV,errorTv;
    private TextView yearTV,monthTV,dayTV;

    DatePickerDialog.OnDateSetListener dateSetListenerFromDate,dateSetListenerToDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fromDateBTN = findViewById(R.id.fromAgeBTN);
        toDateBTN = findViewById(R.id.toAgeButton);
        calculateBTN = findViewById(R.id.calculateAgeBTN);
        birthTV = findViewById(R.id.birthTV);
        todayTV = findViewById(R.id.todayTV);
        errorTv = findViewById(R.id.errorTV);
        yearTV = findViewById(R.id.yearTV);
        monthTV = findViewById(R.id.monthTV);
        dayTV = findViewById(R.id.dayTV);


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String todayDate = simpleDateFormat.format(calendar.getTime());
        birthTV.setText(todayDate);

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
                birthTV.setText(birthDate);
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
                todayTV.setText(todayDate);
            }
        };


        calculateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                errorTv.setText("");
                animateTextWithResult(0,yearTV);
                animateTextWithResult(0,monthTV);
                animateTextWithResult(0,dayTV);

                String birthDate = birthTV.getText().toString();
                String todayDate = todayTV.getText().toString();

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


                        animateTextWithResult(year,yearTV);
                        animateTextWithResult(month,monthTV);
                        animateTextWithResult(day,dayTV);


                    }
                    else{
                        errorTv.setText("Birthdate should not be Larger than today's Date.");
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        });


    }

    private void animateTextWithResult(int result, TextView textView) {

        ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(0,result);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                textView.setText(String.valueOf(animation.getAnimatedValue()));
            }
        });
        animator.setDuration(2000);
        animator.start();
    }
}