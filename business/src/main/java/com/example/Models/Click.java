package com.example.Models;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public class Click {
    @JsonProperty("Click_Value")
    private int clickvalue;

    @JsonProperty("ClicksClicks")
    private double clickbonus;

    @JsonProperty("CritChange")
    private double critechange;

    @JsonProperty("Crit")
    private boolean crit;

    public Click() {}

    public Click(@JsonProperty("Click_Value") int clickvalue,
                 @JsonProperty("ClicksClicks") double clickbonus,
                 @JsonProperty("CritChange") double critechange,
                 @JsonProperty("Crit") boolean crit) {
        this.clickvalue = clickvalue;
        this.clickbonus = clickbonus;
        this.critechange = critechange;
        this.crit = crit;
    }

    @Async
    public CompletableFuture<Void> Clicked() {
        double randomValue = Math.random() * 100;
        if (randomValue < critechange) {
            crit = true;
            clickvalue += Math.floor(clickbonus * 2);
        } else {
            crit = false;
            clickvalue += clickbonus;
        }
        return CompletableFuture.completedFuture(null);
    }
    @Async
    public CompletableFuture<Void> Passive(int passiveincome)
    {
        clickvalue += passiveincome;
        return CompletableFuture.completedFuture(null);
    }

    public int getClickvalue() { return clickvalue; }
    public void setClickvalue(int clickvalue) { this.clickvalue = clickvalue; }

    public double getClickbonus() { return clickbonus; }
    public void setClickbonus(double clickbonus) { this.clickbonus = clickbonus; }

    public double getCritechange() { return critechange; }
    public void setCritechange(double critechange) { this.critechange = critechange; }

    public boolean isCrit() { return crit; }
    public void setCrit(boolean crit) { this.crit = crit; }

    @Override
    public String toString() {
        return "Click{" +
                "clickvalue=" + clickvalue +
                ", clickbonus=" + clickbonus +
                ", critechange=" + critechange +
                ", crit=" + crit +
                '}';
    }
}
