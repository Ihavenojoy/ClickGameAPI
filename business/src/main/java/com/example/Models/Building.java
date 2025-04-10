package com.example.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Building {
    @JsonProperty("ID")
    @Id
    public int id;
    @JsonProperty("Name")
    public String name; // building naam
    @JsonProperty("Ammount")
    public int ammount; //hoeveel in bezig
    @JsonProperty("Startvalue")
    public int startvalue; //eerste aankoopprijs
    @JsonProperty("Valueincrease")
    public double valueincrease; //prijs omhoog voor iedere aankoop
    @JsonProperty("Currentvalue")
    public int currentvalue; //huidige prijs
    @JsonProperty("Generate")
    public int generate; //huidige generatie
    @JsonProperty("Generateincrease")
    public double generateincrease; //generate bonuses (stats view)
    @JsonProperty("Imagestring")
    public String Imagestring; //generate bonuses (stats view)

        public Building(int id,String name, int ammount, int startvalue, double valueincrease, int currentvalue, int generate, double generateincrease, String Imagestring) {
            this.id = id;
            this.name = name;
            this.ammount = ammount;
            this.startvalue = startvalue;
            this.valueincrease = valueincrease;
            this.currentvalue = currentvalue;
            this.generate = generate;
            this.generateincrease = generateincrease;
            this.Imagestring = Imagestring;
        }

        public Building(int id , String name, int startvalue, double valueincrease, int currentvalue, int generate, String Imagestring)
        {
            this.id = id;
            this.name = name;
            this.startvalue = startvalue;
            this.valueincrease = valueincrease;
            this.currentvalue = currentvalue;
            this.generate = generate;
            this.Imagestring = Imagestring;
        }

        public Building()
        {

        }



        public int getId() {return id;}

        public void setId(int id) {this.id = id;}

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAmmount() {
            return ammount;
        }

        public void setAmmount(int ammount) {
            this.ammount = ammount;
        }

        public int getStartvalue() {
            return startvalue;
        }

        public void setStartvalue(int startvalue) {
            this.startvalue = startvalue;
        }

        public double getValueincrease() {
            return valueincrease;
        }

        public void setValueincrease(double valueincrease) {
            this.valueincrease = valueincrease;
        }

        public int getCurrentvalue() {
            return currentvalue;
        }

        public void setCurrentvalue(int currentvalue) {
            this.currentvalue = currentvalue;
        }

        public int getGenerate() {
            return generate;
        }

        public void setGenerate(int generate) {
            this.generate = generate;
        }

        public double getGenerateincrease() {
            return generateincrease;
        }

        public void setGenerateincrease(double generateincrease) {
            this.generateincrease = generateincrease;
        }
}