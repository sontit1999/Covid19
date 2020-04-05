package com.example.covid19.model;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
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

}
