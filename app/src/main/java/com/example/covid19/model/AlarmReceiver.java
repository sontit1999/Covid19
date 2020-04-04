package com.example.covid19.model;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.PowerManager;
import android.util.Log;

import com.example.covid19.R;
import com.example.covid19.utils.Constant;

import java.util.Random;

public class AlarmReceiver extends BroadcastReceiver {
    MediaPlayer player;
    @Override
    public void onReceive(Context context, Intent intent) {
        int randomNotification = new Random().nextInt(Constant.getListNotification().size());
        int sound = Constant.getListNotification().get(randomNotification);
        if(player!=null){
            player.stop();
        }
        player = MediaPlayer.create(context,sound);
        player.start();
        //wakeUpLock(context);
        Constant.showNotification(context,"Chị google :D",Constant.getListTextNotification().get(randomNotification),new Intent());

    }
//    private void wakeUpLock(Context context)
//    {
//
//
//        PowerManager pm = (PowerManager)context.getSystemService(Context.POWER_SERVICE);
//
//        boolean isScreenOn = pm.isScreenOn();
//
//        if(isScreenOn==false)
//        {
//
//            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK |PowerManager.ACQUIRE_CAUSES_WAKEUP |PowerManager.ON_AFTER_RELEASE,"MyLock");
//
//            wl.acquire(10000);
//
//            @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wl_cpu = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"MyCpuLock");
//
//            wl_cpu.acquire(10000);
//        }
//    }
}
