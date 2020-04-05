package com.example.covid19;


import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ablanco.zoomy.Zoomy;
import com.bumptech.glide.Glide;
import com.example.covid19.adapter.CountryAdapter;
import com.example.covid19.base.BaseActivity;
import com.example.covid19.databinding.ActivityMainBinding;
import com.example.covid19.fragment.about.AboutFragment;
import com.example.covid19.fragment.home.HomeFragment;
import com.example.covid19.fragment.notifi.NotificationFragment;
import com.example.covid19.fragment.protect.ProtectFragment;
import com.example.covid19.fragment.question.QuestionFragment;
import com.example.covid19.model.AlarmReceiver;
import com.example.covid19.model.Global;
import com.example.covid19.model.MyPojo;
import com.example.covid19.model.SummaryHelth;
import com.example.covid19.model.Vietnam;
import com.example.covid19.service.APIClient;
import com.example.covid19.service.ApiCovid;
import com.example.covid19.utils.Constant;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends BaseActivity<ActivityMainBinding,MainViewModel> {

    AlarmManager alarmMgr;
    PendingIntent alarmIntent;
    boolean statusAlarm;
    MediaPlayer player;
    @Override
    public Class<MainViewModel> getViewmodel() {
        return MainViewModel.class;
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    public void setBindingViewmodel() {
            // init ad main
            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {

                }
            });
           // get sharefrence
            boolean firstUse = Constant.isFirst;
            Constant.isFirst = false;
           // get status alarm
            statusAlarm = Constant.enableAlarm;
           // init alarm manager and alarm intent
            alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
            alarmIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
            event();

            // load default fragment
            addFragment(new HomeFragment());
    }
    private void addFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_frame,fragment);
        transaction.commit();
    }
    private void event() {

        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        addFragment(new HomeFragment());
                        binding.tvTitle.setText("Covid-19");
                        break;
                    case R.id.nav_question:
                        addFragment(new QuestionFragment(Constant.linkQuestion));
                        binding.tvTitle.setText("Hỏi Đáp");
                        break;
                    case R.id.nav_protectHelth:
                        addFragment(new QuestionFragment(Constant.linkProtect));
                        binding.tvTitle.setText("Bảo vệ sức khỏe");
                        break;
                    case R.id.nav_execute:
                        addFragment(new QuestionFragment(Constant.linkChidao));
                        binding.tvTitle.setText("Chỉ đạo");
                        break;
                    case R.id.nav_khuyencao:
                        addFragment(new QuestionFragment(Constant.linkKhuyencao));
                        binding.tvTitle.setText("Khuyến cáo");
                        break;
                    case R.id.nav_news:
                        addFragment(new QuestionFragment("https://ncov.moh.gov.vn/web/guest/tin-tuc"));
                        binding.tvTitle.setText("Tin tức Covid");
                        break;
                    case R.id.nav_aboutMe:
                        addFragment(new AboutFragment());
                        binding.tvTitle.setText("Nhà phát triển");
                        break;
                    case R.id.nav_call:
                        callBoyte();
                        break;
                    case R.id.nav_repply:
                        sendEmail(MainActivity.this,new String[]{Constant.mailFeedback},"Phản hồi ứng dụng","Chào bạn! hi");
                        break;
                }
                binding.drawlerLayout.closeDrawers();
                return true;
            }
        });
        binding.ivDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.ivDraw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        binding.drawlerLayout.openDrawer(GravityCompat.START);
                    }
                });
            }
        });
        binding.ivNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new NotificationFragment());
            }
        });
    }

    public static void sendEmail(Context context, String[] addresses, String subject,
                                 String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "You need install email!", Toast.LENGTH_SHORT).show();
        }
    }

    private void callBoyte() {
        String phone = "19003228";
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }

    public void createAlarm(){
        // Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,1);
        calendar.setTimeInMillis(System.currentTimeMillis());

        // setRepeating() lets you specify a precise custom interval--in this case,
        // 20 minutes..
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 1, alarmIntent);
    }
    public void cancelAlarm(){
        if (alarmMgr!= null) {
            alarmMgr.cancel(alarmIntent);
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 1) {
            fm.popBackStack();
        } else {
           finish();
        }
    }
}
