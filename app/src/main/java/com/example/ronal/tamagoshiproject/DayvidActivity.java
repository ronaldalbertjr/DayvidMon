package com.example.ronal.tamagoshiproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class DayvidActivity extends AppCompatActivity implements Runnable
{
    ProgressBar foodSlider;
    ProgressBar waterSlider;
    TextView numberOfFood;
    TextView numberOfWater;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dayvid_layout);

        foodSlider = (ProgressBar) findViewById(R.id.food_slider);
        waterSlider = (ProgressBar) findViewById(R.id.water_slider);
        numberOfFood = (TextView) findViewById(R.id.number_of_food);
        numberOfWater = (TextView) findViewById(R.id.number_of_water);


    }

    public void run()
    {
        while(true)
        {
            SetInterval();
        }
    }


    private void SetInterval()
    {
        try{ Thread.sleep(1000); }
        catch (InterruptedException e) { e.printStackTrace(); }
    }
}
