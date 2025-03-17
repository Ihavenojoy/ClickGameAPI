package com.example.clickerapi.Model;
import com.example.clickerapi.DAL.Services.DragonFlyServices;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.CompletableFuture;

public class Click {
    @JsonProperty("Click_Value")
    public int clickvalue;
    @JsonProperty("ClicksClicks")
    public double clickbonus;
    @JsonProperty("CritChange")
    public double critechange;
    @JsonProperty("Crit")
    public boolean crit;

    public Click() {
    }

    @JsonCreator
    public Click(
            @JsonProperty("clicks") int Click_Value,
            @JsonProperty("ClicksClicks") double Click_Bonus,
            @JsonProperty("CritChange") double CritChange,
            @JsonProperty("Crit") boolean Crit) {
        clickvalue = Click_Value;
        clickbonus = Click_Bonus;
        critechange = CritChange;
        crit = Crit;
    }

    @Async
    public CompletableFuture<Void> Clicked() {
        double randomValue = Math.random() * 100;
        if (randomValue < critechange)
        {
            crit = true;
            clickvalue += Math.floor(clickbonus * 2);
        }
        else
        {
            crit = false;
            clickvalue += clickbonus;
        }
        return CompletableFuture.completedFuture(null);
    }



    public int clickvalueget()
    {
        return clickvalue;
    }
    public void clickvalueset(int value)
    {
        this.clickvalue = value;
    }
    public double getCritechange() {
        return critechange;
    }

    public boolean isCrit() {
        return crit;
    }
    public void setCritechange(double critechange) {
        this.critechange = critechange;
    }

    public void setCrit(boolean crit) {
        this.crit = crit;
    }
}
