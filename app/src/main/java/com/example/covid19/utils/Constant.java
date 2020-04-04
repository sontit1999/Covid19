package com.example.covid19.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.covid19.MainActivity;
import com.example.covid19.R;

import java.util.ArrayList;

public class Constant {
    public static String urlCountry = "https://ncovi.huynhhieu.com";
    public static String urlSumary = "https://code.junookyo.xyz";
    public static String urlMain = "https://ncov.moh.gov.vn/";
    public  static  String linkProtect  = "https://ncov.moh.gov.vn/web/guest/khuyen-cao";
    public  static  String linkQuestion  = "https://ncov.moh.gov.vn/web/guest/phan-anh-kien-nghi1";
    public static  String linkKhuyencao = "https://ncov.moh.gov.vn/web/guest/khuyen-cao";
    public static String linkNew = "ncov.moh.gov.vn/web/guest/tin-tuc";
    public static String linkChidao = "https://ncov.moh.gov.vn/web/guest/chi-dao-dieu-hanh";
    public static String linkDieucanbiet ="https://ncov.moh.gov.vn/web/guest/-ieu-can-biet";
    public static String mailFeedback ="tvson999@gmail.com";
    public static boolean isFirst = true;
    public static boolean enableAlarm = false;

    public static void showNotification(Context context, String title, String body, Intent intent) {
        Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"+ context.getApplicationContext().getPackageName() + "/" + R.raw.message);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);

            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            mChannel.setSound(soundUri, audioAttributes);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.corona)
                .setContentTitle(title)
                .setContentText(body)
                .setOngoing(true)
                .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND).setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.corona))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setAutoCancel(true);


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            mBuilder.setSound(soundUri);
        }
        mBuilder.setAutoCancel(true);

        mBuilder.setContentIntent(contentIntent);


        notificationManager.notify(notificationId, mBuilder.build());



    }
    public static ArrayList<Integer> getListNotification(){
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(R.raw.dieu1);
        arr.add(R.raw.dieu2);
        arr.add(R.raw.dieu3);
        arr.add(R.raw.dieu4);
        arr.add(R.raw.dieu5);
        return arr;
    }
    public static ArrayList<String> getListTextNotification(){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Hạn chế tối đa ra ngoài, chỉ ra ngoài khi thực sự cần thiết.");
        arrayList.add("Nếu buộc phải ra ngoài luôn luôn đeo khẩu trang, hãy giữ khoảng cách tiếp xúc, tốt nhất là 2m.");
        arrayList.add("Thường xuyên rửa tay bằng xà phòng hoặc dung dịch sát khuẩn.");
        arrayList.add("Thường xuyên vệ sinh nhà cửa, lau rửa thường xuyên, để thông thoáng, sinh hoạt lành mạnh.");
        arrayList.add("Thực hiện khai báo y tế, cập nhật tình hình sức khỏe hàng ngày, giữ liên hệ thường xuyên với cán bộ y tế, cơ sở y tế.");
        return arrayList;
    }
}
