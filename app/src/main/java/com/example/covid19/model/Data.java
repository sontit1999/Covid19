package com.example.covid19.model;

public class Data
{
    private String country;

    private String recovered;

    private String cases;

    private String deaths;

    public String getCountry ()
    {
        return country;
    }

    public void setCountry (String country)
    {
        this.country = country;
    }

    public String getRecovered ()
    {
        return recovered;
    }

    public void setRecovered (String recovered)
    {
        this.recovered = recovered;
    }

    public String getCases ()
    {
        return cases;
    }

    public void setCases (String cases)
    {
        this.cases = cases;
    }

    public String getDeaths ()
    {
        return deaths;
    }

    public void setDeaths (String deaths)
    {
        this.deaths = deaths;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [country = "+country+", recovered = "+recovered+", cases = "+cases+", deaths = "+deaths+"]";
    }
}