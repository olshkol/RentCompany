package main.cars.dto;

import java.io.Serializable;

public class Model implements Serializable {
    private String modelName;
    private int gasTank; // in liters
    private String company;
    private String country;
    private int priceDay; // price per rent day no delay

    public Model() {
    }

    public Model(String modelName, int gasTank, String company, String country, int priceDay) {
        this.modelName = modelName;
        this.gasTank = gasTank;
        this.company = company;
        this.country = country;
        this.priceDay = priceDay;
    }

    public String getModelName() {
        return modelName;
    }

    public int getGasTank() {
        return gasTank;
    }

    public String getCompany() {
        return company;
    }

    public String getCountry() {
        return country;
    }

    public int getPriceDay() {
        return priceDay;
    }

    public void setPriceDay(int priceDay) {
        this.priceDay = priceDay;
    }

    @Override
    public String toString() {
        return "Model{" +
                "modelName='" + modelName + '\'' +
                ", gasTank=" + gasTank +
                ", company='" + company + '\'' +
                ", country='" + country + '\'' +
                ", priceDay=" + priceDay +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Model)) return false;

        Model model = (Model) o;

        return modelName != null ? modelName.equals(model.modelName) : model.modelName == null;
    }

    @Override
    public int hashCode() {
        return modelName != null ? modelName.hashCode() : 0;
    }
}
