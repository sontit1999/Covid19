package com.example.covid19.fragment.notifi;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.covid19.MainActivity;
import com.example.covid19.R;
import com.example.covid19.base.BaseFragment;
import com.example.covid19.databinding.FragNotificationBinding;
import com.example.covid19.model.AlarmReceiver;
import com.example.covid19.model.SitReciever;
import com.example.covid19.utils.Constant;
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPickerListener;

import java.util.Calendar;


public class NotificationFragment extends BaseFragment<FragNotificationBinding,NotificationViewModel> implements CompoundButton.OnCheckedChangeListener {
    AlarmManager alarmMgr;
    PendingIntent alarmIntent;
    PendingIntent ngoiLauIntent;
    int minuteNgoilau = 0;
    int minuteCovid = 0;
    SharedPreferences sharedPreferences;
    String enableNgoiLau;
    String enableCovid;
    @Override
    public Class<NotificationViewModel> getViewmodel() {
        return NotificationViewModel.class;
    }

    @Override
    public int getLayoutID() {
        return R.layout.frag_notification;
    }

    @Override
    public void setBindingViewmodel() {
        sharedPreferences = getActivity().getSharedPreferences(Constant.namePrefrence,Context.MODE_PRIVATE);
        // get notification
        enableNgoiLau = sharedPreferences.getString(Constant.keyngoilau,"false");
        enableCovid = sharedPreferences.getString(Constant.keycovid,"false");
        alarmMgr = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        Intent intent2 = new Intent(getContext(), SitReciever.class);
        alarmIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
        ngoiLauIntent = PendingIntent.getBroadcast(getContext(), 1, intent2, 0);
        binding.setViewmodel(viewmodel);
    }

    @Override
    public void ViewCreated() {
        // set status notifi
        if(enableNgoiLau.equals("true")){
            binding.switchNgoilau.setChecked(true);
        }else{
            binding.switchNgoilau.setChecked(false);
        }
        if(enableCovid.equals("true")){
            binding.switchCovid.setChecked(true);
        }else{
            binding.switchCovid.setChecked(false);
        }
        event();

    }

    private void event() {
            binding.numberNgoilau.setListener(new ScrollableNumberPickerListener() {
                @Override
                public void onNumberPicked(int value) {
                    minuteNgoilau = value;
                    if(enableNgoiLau.equals("true")){
                        cancelAlarmNgoiLau();
                        createAlarmNgoiLau(value);
                        Toast.makeText(getActivity(), "Bật thông báo nhắc nhở nếu ngồi lâu sau mỗi " + value + " phút!" , Toast.LENGTH_SHORT).show();
                    }
                }
            });
            binding.numberCovid.setListener(new ScrollableNumberPickerListener() {
                @Override
                public void onNumberPicked(int value) {
                    minuteCovid = value;
                    if(enableCovid.equals("true")){
                        cancelAlarmCovid();
                        createAlarmCovid(value);
                        Toast.makeText(getActivity(), "Bật hông báo về khuyến cáo của bộ y tế mỗi  " + value + " phút!" , Toast.LENGTH_SHORT).show();
                    }
                }
            });
            binding.switchNgoilau.setOnCheckedChangeListener(this);
            binding.switchCovid.setOnCheckedChangeListener(this);
            binding.btnManagaNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openSetting();
                }
            });
    }

    private void openSetting() {
        Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, getContext().getPackageName());
        startActivity(intent);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.switchNgoilau:
                if(b){
                    enableNgoiLau = "true";
                    sharedPreferences.edit().putString(Constant.keyngoilau,"true").commit();
                    createAlarmNgoiLau(binding.numberNgoilau.getValue());
                    Toast.makeText(getActivity(), "Bật thông báo nhắc nhở nếu ngồi lâu sau mỗi " + binding.numberNgoilau.getValue() + " phút!" , Toast.LENGTH_SHORT).show();
                }else{
                    enableNgoiLau = "false";
                    sharedPreferences.edit().putString(Constant.keyngoilau,"false").commit();
                    Toast.makeText(getActivity(), "Tắt thông báo nhắc nhở nếu ngồi lâu", Toast.LENGTH_SHORT).show();
                    cancelAlarmNgoiLau();
                }
                break;
            case R.id.switchCovid:

                if(b){
                    enableCovid = "true";
                    sharedPreferences.edit().putString(Constant.keycovid,"true").commit();
                    createAlarmCovid(binding.numberCovid.getValue());
                    Toast.makeText(getActivity(), "Bật hông báo về khuyến cáo của bộ y tế mỗi  " + binding.numberCovid.getValue() + " phút!" , Toast.LENGTH_SHORT).show();
                }else{
                    enableCovid = "false";
                    sharedPreferences.edit().putString(Constant.keycovid,"false").commit();
                    cancelAlarmCovid();
                    Toast.makeText(getActivity(), "Tắt thông báo về khuyến cáo của bộ y tế", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    public void createAlarmNgoiLau(int minuteRepeat){
        // Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,minuteRepeat);
        calendar.setTimeInMillis(System.currentTimeMillis());

        // setRepeating() lets you specify a precise custom interval--in this case,
        // 20 minutes..

            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    1000 * 60 * minuteRepeat, ngoiLauIntent);

    }
    public void cancelAlarmNgoiLau(){
        if (alarmMgr!= null){
                alarmMgr.cancel(ngoiLauIntent);
        }
    }
    public void createAlarmCovid(int minuteRepeat){
        // Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,minuteRepeat);
        calendar.setTimeInMillis(System.currentTimeMillis());

        // setRepeating() lets you specify a precise custom interval--in this case,
        // 20 minutes..

        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * minuteRepeat, alarmIntent);

    }
    public void cancelAlarmCovid(){
        if (alarmMgr!= null){
            alarmMgr.cancel(alarmIntent);
        }
    }

}
