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

            if(waterAmount == 25)
            {
                SendNotification(R.drawable.facchinetti_fome, "Sua água está acabando", "Vá dar água para o seu facchinetti, ele está com sede", new Intent(this, MainActivity.class));
            }
            else if(foodAmount == 25)
            {
                SendNotification(R.drawable.facchinetti_fome, "Sua comida está acabando", "Vá alimentar o seu facchinetti, ele está com fome ", new Intent(this, MainActivity.class));
            }
            else if(waterAmount <= 0 || foodAmount <= 0)
            {
                SendNotification(R.drawable.facchinetti_morto, "Seu facchinetti morreu", "Você não cuidou bem do seu facchinetti, tente novamente", new Intent(this, DeadActivity.class));
                stopSelf();
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

    private void SendNotification(int icon, String title, String text, Intent intent)
    {
        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        mBuilder.setContentIntent(pi);
        mBuilder.setVibrate(new long[] {100, 250, 100, 500});
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(R.string.app_name, mBuilder.build());
    }
}
