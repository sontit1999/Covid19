package com.example.covid19.fragment.home;

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
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends BaseFragment<FragHomeBinding,HomeViewModel> {
    CountryAdapter adapterCountry;
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
        Glide.with(this).load("https://tinhdoankhanhhoa.org.vn/wp-content/uploads/2020/02/apphich-corona-01-scaled.jpg").into(binding.ivMain2);

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
