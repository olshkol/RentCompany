package main.cars.model;

import main.cars.dto.*;
import main.util.Persistable;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static main.cars.dto.CarsReturnCode.*;

public class RentCompanyImpl extends AbstractRentCompany implements Persistable {
    private HashMap<String, Car> cars = new HashMap<>(); // key - carNumber, value - car
    private HashMap<Long, Driver> drivers = new HashMap<>(); // key - licenseId
    private HashMap<String, Model> models = new HashMap<>(); // key - modelName

    private HashMap<String, List<RentRecord>> carRecords = new HashMap<>();
    private HashMap<Long, List<RentRecord>> driverRecords = new HashMap<>();
    private HashMap<String, List<Car>> modelCars = new HashMap<>();
    private TreeMap<LocalDate, List<RentRecord>> records = new TreeMap<>();

    public static RentCompany restoreFromFile(String fileName){
        // TODO (31.07.2019) (restoreFromFile)
        return null;
    }

    @Override
    public void save(String fileName) {
        // TODO (31.07.2019) (save)
    }

    @Override
    public CarsReturnCode addModel(Model model) {
        if (models.containsKey(model.getModelName()))
            return MODEL_EXISTS;
        models.put(model.getModelName(), model);
        return OK;
    }

    @Override
    public CarsReturnCode addCar(Car car) {
        if (cars.containsKey(car.getRegNumber()))
            return CAR_EXISTS;
        else if (!models.containsKey(car.getModelName()))
            return NO_MODEL;

        cars.put(car.getRegNumber(), car);
        return OK;
    }

    @Override
    public CarsReturnCode addDriver(Driver driver) {
        if (drivers.containsKey(driver.getLicenseId()))
            return DRIVER_EXISTS;
        drivers.put(driver.getLicenseId(), driver);
        return OK;
    }

    @Override
    public Model getModel(String modelName) {
        return models.get(modelName);
    }

    @Override
    public Car getCar(String regNumber) {
        return cars.get(regNumber);
    }

    @Override
    public Driver getDriver(long licenseId) {
        return drivers.get(licenseId);
    }

    @Override
    public CarsReturnCode rentCar(String regNumber, long licenseId, LocalDate rentDate) {
        // TODO (02.08.2019) (rentCar)
        return null;
    }

    @Override
    public List<Car> getCarsDriver(long licenseId) {
        // TODO (02.08.2019) (getCarsDriver)
        return null;
    }

    @Override
    public List<Driver> getDriversCar(String regNumber) {
        // TODO (02.08.2019) (getDriversCar)
        return null;
    }

    @Override
    public List<Car> getCarsModel(String modelName) {
        // TODO (02.08.2019) (getCarsModel)
        return null;
    }

    @Override
    public List<RentRecord> getRentRecordsAtDates(LocalDate from, LocalDate to) {
        // TODO (02.08.2019) (getRentRecordsAtDates)
        return null;
    }

    @Override
    public RemovedCarData removeCar(String regNumber) {
        // TODO (02.08.2019) (removeCar)
        return null;
    }

    @Override
    public List<RemovedCarData> removeModel(String modelName) {
        // TODO (02.08.2019) (removeModel)
        return null;
    }

    @Override
    public RemovedCarData returnCar(String regNumber, long licenseId, LocalDate returnDate, int damages, int tankPercent) {
        // TODO (02.08.2019) (returnCar)
        return null;
    }

    @Override
    public List<String> getMostPopularCarModels(LocalDate dateFrom, LocalDate dateTo, int ageFrom, int ageTo) {
        // TODO (02.08.2019) (getMostPopularCarModels)
        return null;
    }

    @Override
    public List<String> getMostProfitableCarModels(LocalDate dateFrom, LocalDate dateTo) {
        // TODO (02.08.2019) (getMostProfitableCarModels)
        return null;
    }

    @Override
    public List<Driver> getMostActiveDrivers() {
        // TODO (02.08.2019) (getMostActiveDrivers)
        return null;
    }
}
