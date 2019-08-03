package main.cars.model;

public abstract class AbstractRentCompany implements RentCompany {
    private static final long serialVersionUID = 6980361909005366533L;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractRentCompany)) return false;

        AbstractRentCompany that = (AbstractRentCompany) o;

        if (finePercent != that.finePercent) return false;
        return gasPrice == that.gasPrice;
    }

    @Override
    public int hashCode() {
        int result = finePercent;
        result = 31 * result + gasPrice;
        return result;
    }
}
