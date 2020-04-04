package com.example.covid19.service;

import com.example.covid19.model.MyPojo;
import com.example.covid19.model.SummaryHelth;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCovid {
    @GET("/api.php?code=external&fbclid=IwAR0u7jsBj49ZgbiLXk0B1rs1Fpy1rszp_WwCTwsBhhkpShp5hbI7haj_lYs")
    Call<MyPojo> getMoreCountry();
    @GET("/api/ncov-moh/data.json?fbclid=IwAR2wQLWZ71bbaCd3pqPo5w81vrmWVdWaasg22y8b3WosPXiS8tzO44mQMwg")
    Call<SummaryHelth> getSummary();
}
