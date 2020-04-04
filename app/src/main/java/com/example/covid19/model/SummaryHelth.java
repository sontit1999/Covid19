package com.example.covid19.model;

public class SummaryHelth {
    private DataSumary data;

    private String success;

    public DataSumary getData ()
    {
        return data;
    }

    public void setData (DataSumary data)
    {
        this.data = data;
    }

    public String getSuccess ()
    {
        return success;
    }

    public void setSuccess (String success)
    {
        this.success = success;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+", success = "+success+"]";
    }
}
