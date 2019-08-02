package main.cars.model;

import main.cars.dto.*;
import main.util.Persistable;

import java.time.LocalDate;
import java.util.*;

import static main.cars.dto.CarsReturnCode.*;

public class RentCompanyImpl extends AbstractRentCompany implements Persistable {
    private HashMap<String, Car> cars = new HashMap<>(); // key - carNumber, value - car
    private HashMap<Long, Driver> drivers = new HashMap<>(); // key - licenseId
    private HashMap<String, Model> models = new HashMap<>(); // key - modelName

    private HashMap<String, List<RentRecord>> carRecords = new HashMap<>();
    private HashMap<Long, List<RentRecord>> driverRecords = new HashMap<>();
    private HashMap<String, List<Car>> modelCars = new HashMap<>();
    private TreeMap<LocalDate, List<RentRecord>> records = new TreeMap<>();

    public static RentCompany restoreFromFile(String fileName) {
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
        RentRecord record;
        if (!cars.containsKey(regNumber))
            return NO_CAR;
        else if (!drivers.containsKey(licenseId))
            return NO_DRIVER;
        else {
            Car car = cars.get(regNumber);
            if (car.isInUse())
                return CAR_IN_USE;
            else if (car.isIfRemoved())
                return CAR_REMOVED;
            else {
                record = new RentRecord(regNumber, licenseId, rentDate);
                addRecord(carRecords, regNumber, record);
                addRecord(driverRecords, licenseId, record);
                addRecord(modelCars, car.getModelName(), car);
                addRecord(records, rentDate, record);
                return OK;
            }

        }
    }

    private static <K, V> void addRecord(Map<K, List<V>> records, K key, V record) {
        if (records.putIfAbsent(key, new ArrayList<>() {{
            add(record);
        }}) != null)
            records.get(key).add(record);
    }

    @Override
    public List<Car> getCarsDriver(long licenseId) {
        Set<Car> res = new HashSet<>();
        List<RentRecord> rentRecords = driverRecords.getOrDefault(licenseId, new ArrayList<>());
        for (RentRecord rentRecord : rentRecords) {
            String regNumber = rentRecord.getRegNumber();
            if (cars.containsKey(regNumber))
                res.add(cars.get(regNumber));
        }
        return new ArrayList<>(res);
    }

    @Override
    public List<Driver> getDriversCar(String regNumber) {
        Set<Driver> res = new HashSet<>();
        List<RentRecord> rentRecords = carRecords.getOrDefault(regNumber, new ArrayList<>());
        for (RentRecord rentRecord : rentRecords) {
            long licenseId = rentRecord.getLicenseId();
            if (drivers.containsKey(licenseId))
                res.add(drivers.get(licenseId));
        }
        return new ArrayList<>(res);
    }

    @Override
    public List<Car> getCarsModel(String modelName) {
        Set<Car> res = new HashSet<>();
        List<Car> rentRecords = modelCars.getOrDefault(modelName, new ArrayList<>());
        for (Car car : rentRecords) {
            String regNumber = car.getRegNumber();
            if (cars.containsKey(regNumber))
                res.add(cars.get(regNumber));
        }
        return new ArrayList<>(res);
    }

    @Override
    public List<RentRecord> getRentRecordsAtDates(LocalDate from, LocalDate to) {
        List<RentRecord> res = new ArrayList<>();
        for (Map.Entry<LocalDate, List<RentRecord>> pair : records.entrySet()) {
            LocalDate date = pair.getKey();
            if (date.isAfter(from.minusDays(1)) && date.isBefore(to.plusDays(1)))
                res.addAll(pair.getValue());
        }
        return res;
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
