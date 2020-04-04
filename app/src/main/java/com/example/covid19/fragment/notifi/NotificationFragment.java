package com.example.covid19.fragment.notifi;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.covid19.MainActivity;
import com.example.covid19.R;
import com.example.covid19.base.BaseFragment;
import com.example.covid19.databinding.FragNotificationBinding;
import com.example.covid19.model.AlarmReceiver;
import com.example.covid19.model.SitReciever;
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPickerListener;

import java.util.Calendar;


public class NotificationFragment extends BaseFragment<FragNotificationBinding,NotificationViewModel> implements CompoundButton.OnCheckedChangeListener {
    AlarmManager alarmMgr;
    PendingIntent alarmIntent;
    PendingIntent ngoiLauIntent;
    int minuteNgoilau = 0;
    int minuteCovid = 0;
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
        alarmMgr = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        Intent intent2 = new Intent(getContext(), SitReciever.class);
        alarmIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
        ngoiLauIntent = PendingIntent.getBroadcast(getContext(), 1, intent2, 0);
        binding.setViewmodel(viewmodel);
    }

    @Override
    public void ViewCreated() {
        event();

    }

    private void event() {
            binding.numberNgoilau.setListener(new ScrollableNumberPickerListener() {
                @Override
                public void onNumberPicked(int value) {
                    minuteNgoilau = value;
                }
            });
            binding.numberCovid.setListener(new ScrollableNumberPickerListener() {
                @Override
                public void onNumberPicked(int value) {
                    minuteCovid = value;
                }
            });
            binding.switchNgoilau.setOnCheckedChangeListener(this);
            binding.switchCovid.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.switchNgoilau:
                if(b){
                    createAlarmNgoiLau(binding.numberNgoilau.getValue());
                    Toast.makeText(getActivity(), "Bật thông báo nhắc nhở nếu ngồi lâu sau mỗi " + binding.numberNgoilau.getValue() + " phút!" , Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Tắt thông báo nhắc nhở nếu ngồi lâu", Toast.LENGTH_SHORT).show();
                    cancelAlarmNgoiLau();
                }
                break;
            case R.id.switchCovid:

                if(b){
                    createAlarmCovid(binding.numberCovid.getValue());
                    Toast.makeText(getActivity(), "Bật hông báo về khuyến cáo của bộ y tế mỗi  " + binding.numberCovid.getValue() + " phút!" , Toast.LENGTH_SHORT).show();
                }else{
                    cancelAlarmCovid();
                    Toast.makeText(getActivity(), "Tắt thông báo về khuyến cáo của bộ y tế", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    public void createAlarmNgoiLau(int minuteRepeat){
        // Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,1);
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
        calendar.add(Calendar.MINUTE,1);
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
