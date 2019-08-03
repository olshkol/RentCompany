package main.cars.dto;

import java.io.Serializable;

public class Car implements Serializable {
    private String regNumber;
    private String color;
    private State state;
    private String modelName;
    private boolean inUse;
    private boolean removed;

    public Car() { }

    public Car(String regNumber, String color, String modelName) {
        this.regNumber = regNumber;
        this.color = color;
        this.modelName = modelName;
        this.state = State.EXCELLENT;
        this.inUse = false;
        this.removed = false;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public String getColor() {
        return color;
    }

    public State getState() {
        return state;
    }

    public String getModelName() {
        return modelName;
    }

    public boolean isInUse() {
        return inUse;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    @Override
    public String toString() {
        return "Car{" +
                "regNumber='" + regNumber + '\'' +
                ", color='" + color + '\'' +
                ", state=" + state +
                ", modelName='" + modelName + '\'' +
                ", inUse=" + inUse +
                ", removed=" + removed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;

        Car car = (Car) o;

        return regNumber != null ? regNumber.equals(car.regNumber) : car.regNumber == null;
    }

    @Override
    public int hashCode() {
        return regNumber != null ? regNumber.hashCode() : 0;
    }
}

