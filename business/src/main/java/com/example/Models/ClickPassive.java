package com.example.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClickPassive {
    @JsonProperty("Ammount")
    private int ammount;

    public ClickPassive() {}

    public int getAmmount() {
        return ammount;
    }

    public void setAmmount(int ammount) {
        this.ammount = ammount;
    }


}
