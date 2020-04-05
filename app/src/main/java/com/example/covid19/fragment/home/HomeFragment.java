package com.example.covid19.fragment.home;

import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.covid19.MainActivity;
import com.example.covid19.R;
import com.example.covid19.adapter.CountryAdapter;
import com.example.covid19.base.BaseFragment;
import com.example.covid19.databinding.FragHomeBinding;
import com.example.covid19.model.Global;
import com.example.covid19.model.MyPojo;
import com.example.covid19.model.SummaryHelth;
import com.example.covid19.model.Vietnam;
import com.example.covid19.service.APIClient;
import com.example.covid19.service.ApiCovid;
import com.example.covid19.utils.Constant;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends BaseFragment<FragHomeBinding,HomeViewModel> {
    CountryAdapter adapterCountry;
    private InterstitialAd mInterstitialAd;
    String idADtest = "ca-app-pub-3940256099942544/1033173712";
    String idAdfullscreen = "ca-app-pub-3159028965186310/2865386932";
    String idADbanner = "ca-app-pub-3159028965186310/9231484739";
    String idbannerTest = "ca-app-pub-3940256099942544/6300978111";
    @Override
    public Class<HomeViewModel> getViewmodel() {
        return HomeViewModel.class;
    }

    @Override
    public int getLayoutID() {
        return R.layout.frag_home;
    }

    @Override
    public void setBindingViewmodel() {
       binding.setViewmodel(viewmodel);
    }

    @Override
    public void ViewCreated() {
        initRecyclerview();
        getdata();
        event();

    }
    private void event() {
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(idAdfullscreen);
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
            }
        });

        // init ad banner
        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);

        binding.adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                AdRequest adRequest = new AdRequest.Builder().build();
                binding.adView.loadAd(adRequest);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

        // show ad
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        },5000);

    }

    private void initRecyclerview() {
        adapterCountry = new CountryAdapter();
        binding.rvCountry.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        binding.rvCountry.setLayoutManager(manager);
        binding.rvCountry.setAdapter(adapterCountry);
    }

    private void getdata() {
        // init imageview main
        Glide.with(this).load("https://www.genco3.com/Data/Sites/1/media/2020/corona/poster/ap-corona-page-001.jpg").into(binding.ivMain2);
        Glide.with(this).load("https://doanhoi.vhu.edu.vn/Temp/ArticleImage/756edbb5-a1a8-4a4b-8426-577fb3748e27.jpg").into(binding.ivMain);
        // get more country
        ApiCovid apiCovid = APIClient.getClient(Constant.urlCountry).create(ApiCovid.class);
        Call<MyPojo> call = apiCovid.getMoreCountry();
        call.enqueue(new Callback<MyPojo>() {
            @Override
            public void onResponse(Call<MyPojo> call, Response<MyPojo> response) {
                Log.d("sondz",response.body().getUpdated());
                if(response.body().getData().size() <= 0){
                    binding.rvCountry.setVisibility(View.GONE);
                }else{
                    binding.rvCountry.setVisibility(View.VISIBLE);
                    adapterCountry.setList(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<MyPojo> call, Throwable t) {
                binding.spinKit.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Kiểm tra lại kết nối mạng của bạn.! Chưa load được dữ liệu, các bạn thông cảm nha ^^", Toast.LENGTH_SHORT).show();
            }
        });

        // get summary
        ApiCovid apiCovid1 = APIClient.getClient(Constant.urlSumary).create(ApiCovid.class);
        Call<SummaryHelth> call1 = apiCovid1.getSummary();
        call1.enqueue(new Callback<SummaryHelth>() {
            @Override
            public void onResponse(Call<SummaryHelth> call, Response<SummaryHelth> response) {
                binding.spinKit.setVisibility(View.GONE);
                Vietnam vietnam = response.body().getData().getVietnam();
                Global global = response.body().getData().getGlobal();
                if(vietnam==null){
                    vietnam = new Vietnam("0","0","0");
                }
                if(global==null){
                    global = new Global("0","0","0");
                }
                binding.setGlobal(global);
                binding.setVietnam(vietnam);
            }

            @Override
            public void onFailure(Call<SummaryHelth> call, Throwable t) {
                binding.spinKit.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Kiểm tra lại kết nối mạng của bạn.! Chưa load được dữ liệu, các bạn thông cảm nha ^^", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
