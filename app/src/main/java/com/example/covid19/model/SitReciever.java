package com.example.covid19.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import com.example.covid19.R;
import com.example.covid19.utils.Constant;

import java.util.Random;

public class SitReciever extends BroadcastReceiver {
    MediaPlayer player;
    @Override
    public void onReceive(Context context, Intent intent) {

        int sound = R.raw.dungngoiqualau;
        if(player!=null){
            player.stop();
        }
        player = MediaPlayer.create(context,sound);
        player.start();
        //wakeUpLock(context);
        Constant.showNotification(context,"Chị google :D","Bạn ngồi một chỗ hơi lâu rồi đó.Hãy đứng lên đi lại chút đi cho thoải mái ",new Intent());
    }
}
