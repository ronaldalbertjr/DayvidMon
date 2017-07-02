package com.example.ronal.tamagoshiproject;

import android.app.ActivityManager;
import android.content.Intent;
import android.support.v4.app.ActivityManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    Button createDayvidBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Intent dayvidActivity = new Intent(this, DayvidActivity.class);
        final Intent counterService = new Intent(this, CounterService.class);
        if(IsServiceRunning(CounterService.class))
        {
            startActivity(dayvidActivity);
        }
        else if(SaveData.GetData("Water", MainActivity.this) != 100 && SaveData.GetData("Food", MainActivity.this) != 100)
        {
            if(!IsServiceRunning(CounterService.class)) {
                startService(counterService);
            }
            startActivity(dayvidActivity);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createDayvidBtn = (Button) findViewById(R.id.create_btn);

        createDayvidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(!IsServiceRunning(CounterService.class)) {
                    startService(counterService);
                }
                startActivity(dayvidActivity);
            }
        });
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
