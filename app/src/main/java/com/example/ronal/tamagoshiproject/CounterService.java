package com.example.ronal.tamagoshiproject;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class CounterService extends Service implements Runnable
{
    public int waterAmount;
    public int foodAmount;
    public boolean active;
    private final LocalBinder connection = new LocalBinder();
    private Context context = CounterService.this;

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
        waterAmount = SaveData.GetData("Water", context);;
        foodAmount = SaveData.GetData("Food", context);

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

            SaveData.SaveData("Water", waterAmount, context);
            SaveData.SaveData("Food", foodAmount, context);

            if(waterAmount < 25)
            {
                SendNotification("Sua água está acabando", "Vá dar água para o seu dayvid, ele está com sede");
            }
            else if(foodAmount < 25)
            {
                SendNotification("Sua comida está acabando", "Vá alimentar o seu dayvid, ele está com fome ");
            }
            else if(waterAmount <= 0 || foodAmount <= 0)
            {
                SendNotification("Seu dayvid morreu", "Você não cuidou bem do seu dayvid, tente novamente");
            }
            SetInterval();
        }
        stopSelf();
    }

    private void SetInterval()
    {
        try{ Thread.sleep(1000); }
        catch (InterruptedException e) { e.printStackTrace(); }
    }

    private void SendNotification(String title, String text)
    {
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true);
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        mBuilder.setContentIntent(pi);
        mBuilder.setVibrate(new long[] {100, 250, 100, 500});
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(R.string.app_name, mBuilder.build());
    }
}
