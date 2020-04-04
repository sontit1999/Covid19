package com.example.covid19.model;

import java.util.ArrayList;

public class MyPojo {
    private ArrayList<Data> data;

    private String updated;

    public ArrayList<Data> getData ()
    {
        return data;
    }

    public void setData (ArrayList<Data> data)
    {
        this.data = data;
    }

    public String getUpdated ()
    {
        return updated;
    }

    public void setUpdated (String updated)
    {
        this.updated = updated;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+", updated = "+updated+"]";
    }
}
