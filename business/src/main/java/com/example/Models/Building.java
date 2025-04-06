package com.example.Models;

public class Building {
    public int ammount;
    public int startvalue;
    public double valueincrease;
    public int currentvalue;
    public int generate;
    public double generateincrease;

        public Building(String name, int ammount, int startvalue, double valueincrease, int currentvalue, int generate, double generateincrease) {
            this.name = name;
            this.ammount = ammount;
            this.startvalue = startvalue;
            this.valueincrease = valueincrease;
            this.currentvalue = currentvalue;
            this.generate = generate;
            this.generateincrease = generateincrease;
        }

        public String name;

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