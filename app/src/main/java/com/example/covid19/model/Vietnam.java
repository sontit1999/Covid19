package com.example.covid19.model;

public class Vietnam {
    private String recovered;

    private String cases;

    public Vietnam(String recovered, String cases, String deaths) {
        this.recovered = recovered;
        this.cases = cases;
        this.deaths = deaths;
    }

    public Vietnam() {
    }

    private String deaths;

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
        return "ClassPojo [recovered = "+recovered+", cases = "+cases+", deaths = "+deaths+"]";
    }
}
