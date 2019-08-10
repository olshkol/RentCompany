package main.cars.model;

import cars.dto.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public interface RentCompany extends Serializable {
    int getGasPrice(); //price for one liter of a company
    void setGasPrice(int price); //setting price of one liter
    int getFinePercent(); //fine of one delay day
    void setFinePercent(int finePercent); //setting fine
    CarsReturnCode addModel(Model model); //OK, MODEL_EXISTS
    CarsReturnCode addCar(Car car); //OK, CAR_EXISTS, NO_MODEL
    CarsReturnCode addDriver(Driver driver); //OK, DRIVER_EXISTS
    Model getModel(String modelName);
    Car getCar(String regNumber);
    Driver getDriver(long licenseId);

    CarsReturnCode rentCar(String regNumber, long licenseId, LocalDate rentDate, int rentDays); //OK, NO_CAR, NO_DRIVER, CAR_REMOVED, CAR_IN_USE
    List<Car> getCarsDriver(long licenseId);
    List<Driver> getDriversCar(String regNumber);
    List<Car> getCarsModel(String modelName);
    List<RentRecord> getRentRecordsAtDates(LocalDate from, LocalDate to);

    RemovedCarData removeCar(String regNumber);
    List<RemovedCarData> removeModel(String modelName);
    RentRecord returnCar(String regNumber, long licenseId, LocalDate returnDate, int damages, int tankPercent);

    List<String> getMostPopularCarModels(LocalDate dateFrom, LocalDate dateTo, int ageFrom, int ageTo);
    List<String> getMostProfitableCarModels(LocalDate dateFrom, LocalDate dateTo);
    List<Driver> getMostActiveDrivers();

    long getCountCars();
}
