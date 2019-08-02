package main.cars.model;

public abstract class AbstractRentCompany implements RentCompany {
    protected int finePercent; // percent of fine per delay day
    protected int gasPrice; // liter price of rent company

    public AbstractRentCompany() {
        finePercent = 15;
        gasPrice = 10;
    }

    public int getFinePercent() {
        return finePercent;
    }

    public void setFinePercent(int finePercent) {
        this.finePercent = finePercent;
    }

    public int getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(int gasPrice) {
        this.gasPrice = gasPrice;
    }

    @Override
    public String toString() {
        return "AbstractRentCompany{" +
                "finePercent=" + finePercent +
                ", gasPrice=" + gasPrice +
                '}';
    }
}
