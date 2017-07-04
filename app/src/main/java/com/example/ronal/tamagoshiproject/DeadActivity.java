package com.example.ronal.tamagoshiproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class DeadActivity extends AppCompatActivity
{
    Button makeOtherFacchinetti;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dead_layout);

        makeOtherFacchinetti = (Button) findViewById(R.id.MakeAnotherBtn);
        makeOtherFacchinetti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveData.SaveData("Water", 100, DeadActivity.this);
                SaveData.SaveData("Food", 100, DeadActivity.this);
                startService(new Intent(DeadActivity.this, CounterService.class));
                Intent i = new Intent(DeadActivity.this, DayvidActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
