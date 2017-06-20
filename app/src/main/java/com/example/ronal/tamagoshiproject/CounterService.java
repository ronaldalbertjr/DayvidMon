package com.example.ronal.tamagoshiproject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
public class CounterService extends Service implements Runnable
{
    protected int waterAmount;
    protected int foodAmount;
    private boolean active;
    private final Binder connection = new Binder();
    private Context context;

    public class LocalBinder extends Binder
    {
        public CounterService getService()
        {
            return CounterService.this;
        }
    }

    public void onCreate()
    {
        super.onCreate();
        waterAmount = SaveData.GetData("Water", CounterService.this);
        foodAmount = SaveData.GetData("Food", CounterService.this);

        active = true;
        new Thread(this).start();
    }

    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return super.onStartCommand(intent, flags, startId);
    }

    public IBinder onBind(Intent intent)
    {
        return connection;
    }

    public void run()
    {
        while(active)
        {
            waterAmount--;
            foodAmount--;

            SetInterval();
        }
        stopSelf();
    }

    private void SetInterval()
    {
        try{ Thread.sleep(1000); }
        catch (InterruptedException e) { e.printStackTrace(); }
    }
}
