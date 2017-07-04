package com.example.ronal.tamagoshiproject;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import java.util.logging.LogRecord;

public class DayvidActivity extends AppCompatActivity implements Runnable,ServiceConnection
{
    ProgressBar foodSlider;
    ProgressBar waterSlider;
    TextView numberOfFood;
    TextView numberOfWater;
    Button giveWater;
    Button giveFood;
    Handler handler;
    ImageView facchinettiImage;
    final ServiceConnection connection = this;
    private CounterService counter;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dayvid_layout);
        if(IsServiceRunning(CounterService.class) && counter == null)
        {
            bindService(new Intent(DayvidActivity.this, CounterService.class), connection, Context.BIND_AUTO_CREATE);
        }
        giveWater = (Button) findViewById(R.id.aguaBtn);
        giveFood = (Button) findViewById(R.id.comidaBtn);
        foodSlider = (ProgressBar) findViewById(R.id.food_slider);
        waterSlider = (ProgressBar) findViewById(R.id.water_slider);
        numberOfFood = (TextView) findViewById(R.id.number_of_food);
        numberOfWater = (TextView) findViewById(R.id.number_of_water);
        facchinettiImage = (ImageView) findViewById(R.id.facchinettiImage);

        giveWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter != null)
                {
                    if(counter.waterAmount <= 95)
                    {
                        counter.waterAmount += 5;
                    }
                    else
                    {
                        counter.waterAmount = 100;
                    }
                }
                else
                {
                    Toast.makeText(DayvidActivity.this, "Serviço não conectado", Toast.LENGTH_SHORT);
                }
            }
        });

        giveFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter != null)
                {
                    if(counter.foodAmount <= 95)
                    {
                        counter.foodAmount += 5;
                    }
                    else
                    {
                        counter.foodAmount = 100;
                    }
                }
                else
                {
                    Toast.makeText(DayvidActivity.this, "Serviço não conectado", Toast.LENGTH_SHORT);
                }
            }
        });

        handler = new Handler();
        handler.post(this);

    }

    protected void onStop()
    {
        super.onStop();
        if(counter != null)
        {
            counter = null;
            unbindService(connection);
        }
    }

    public void run()
    {
        handler.postDelayed(this, 1000);
        if(counter != null) {
            foodSlider.setProgress(counter.foodAmount);
            waterSlider.setProgress(counter.waterAmount);
            numberOfFood.setText(String.valueOf(counter.foodAmount));
            numberOfWater.setText(String.valueOf(counter.waterAmount));
            if(counter.foodAmount == 25 || counter.waterAmount == 25) {
                facchinettiImage.setImageResource(R.drawable.facchinetti_fome);
            }
            if(counter.foodAmount == 0 || counter.waterAmount == 0)
            {
                Intent i = new Intent(DayvidActivity.this, DeadActivity.class);
                startActivity(i);
                finish();
            }
        }


    }

    public void onServiceConnected(ComponentName name, IBinder service)
    {
        CounterService.LocalBinder binder = (CounterService.LocalBinder) service;
        counter = binder.getService();

    }

    public void onServiceDisconnected(ComponentName name)
    {
        counter = null;
    }

    private boolean IsServiceRunning(Class<?> serviceClass)
    {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

        for(ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if(serviceClass.getName().equals(service.service.getClassName())) return true;
        }
        return false;
    }
}
