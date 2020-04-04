package com.example.covid19.adapter;

import com.example.covid19.BR;
import com.example.covid19.R;
import com.example.covid19.base.BaseAdapter;
import com.example.covid19.base.CBAdapter;
import com.example.covid19.databinding.ItemCountryBinding;
import com.example.covid19.model.Data;

public class CountryAdapter  extends BaseAdapter<Data, ItemCountryBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.item_country;
    }

    @Override
    public int getIdVariable() {
        return BR.Data;
    }

    @Override
    public int getIdVariableOnclick() {
        return 0;
    }

    @Override
    public CBAdapter getOnclick() {
        return null;
    }
}
