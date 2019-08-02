package main.cars.dto;

import java.util.List;

public class RemovedCarData {
    private Car car;
    private List<RentRecord> removedRecords;

    public RemovedCarData() {
    }

    public RemovedCarData(Car car, List<RentRecord> removedRecords) {
        this.car = car;
        this.removedRecords = removedRecords;
    }

    public Car getCar() {
        return car;
    }

    public List<RentRecord> getRemovedRecords() {
        return removedRecords;
    }
}
