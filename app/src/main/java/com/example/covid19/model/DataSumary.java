package com.example.covid19.model;

public class DataSumary {
    private Vietnam vietnam;

    private Global global;

    public Vietnam getVietnam ()
    {
        return vietnam;
    }

    public void setVietnam (Vietnam vietnam)
    {
        this.vietnam = vietnam;
    }

    public Global getGlobal ()
    {
        return global;
    }

    public void setGlobal (Global global)
    {
        this.global = global;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [vietnam = "+vietnam+", global = "+global+"]";
    }
}
