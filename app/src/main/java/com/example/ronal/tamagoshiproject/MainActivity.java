package com.example.ronal.tamagoshiproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    Button createDayvidBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createDayvidBtn = (Button) findViewById(R.id.create_btn);

        createDayvidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = createIntent(DayvidActivity.class);
                startActivity(i);
            }
        });
    }

    Intent createIntent(Class classe)
    {
        return new Intent(this, classe);
    }
}
