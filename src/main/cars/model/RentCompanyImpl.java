package main.cars.model;

import cars.model.*;
import main.cars.model.annotations.Routing;
import main.util.Persistable;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static cars.model.CarsReturnCode.*;
import static cars.routing.Routing.*;
import static main.cars.Config.*;

public class RentCompanyImpl extends AbstractRentCompany implements Persistable {
    private static final long serialVersionUID = 6980361909005366533L;

    private HashMap<String, Car> cars = new HashMap<>(); // key - carNumber, value - car
    private HashMap<Long, Driver> drivers = new HashMap<>(); // key - licenseId
    private HashMap<String, Model> models = new HashMap<>(); // key - modelName

    private HashMap<String, List<Car>> modelCars = new HashMap<>();
    private HashMap<String, List<RentRecord>> carRecords = new HashMap<>();
    private HashMap<Long, List<RentRecord>> driverRecords = new HashMap<>();
    private TreeMap<LocalDate, List<RentRecord>> records = new TreeMap<>();

    public static RentCompany restoreFromFile(String fileName) {
        RentCompany rentCompany = null;
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            rentCompany = (RentCompany) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return rentCompany;
    }

    @Override
    public void save(String fileName) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Routing(ADD_MODEL)
    public CarsReturnCode addModel(Model model) {
        String modelName = model.getModelName();
        if (models.containsKey(modelName)) { // if model contains in database
            return MODEL_EXISTS;
        }
        models.put(modelName, model);
        modelCars.put(modelName, new ArrayList<>());
        return OK;
    }

    @Override
    @Routing(ADD_CAR)
    public CarsReturnCode addCar(Car car) {
        if (cars.containsKey(car.getRegNumber())) {
            if (!cars.get(car.getRegNumber()).isRemoved())
                return CAR_EXISTS;
            //TODO if car was removed, but adding new car with same RegNumber
            cars.get(car.getRegNumber()).setRemoved(false);
            return OK;

        }
        if (!models.containsKey(car.getModelName()))
            return NO_MODEL;

        cars.put(car.getRegNumber(), car);
        modelCars.get(car.getModelName()).add(car);
        return OK;
    }

    @Override
    @Routing(ADD_DRIVER)
    public CarsReturnCode addDriver(Driver driver) {
        if (drivers.containsKey(driver.getLicenseId()))
            return DRIVER_EXISTS;
        drivers.put(driver.getLicenseId(), driver);
        return OK;
    }

    @Override
    @Routing(GET_MODEL)
    public Model getModel(String modelName) {
        return models.get(modelName);
    }

    @Override
    @Routing(GET_CAR)
    public Car getCar(String regNumber) {
        return cars.get(regNumber);
    }

    @Override
    @Routing(GET_DRIVER)
    public Driver getDriver(long licenseId) {
        return drivers.get(licenseId);
    }

    @Override
    @Routing(RENT_CAR)
    public CarsReturnCode rentCar(String regNumber, long licenseId, LocalDate rentDate, int rentDays) {
        RentRecord record;
        if (!cars.containsKey(regNumber))
            return NO_CAR;
        else if (!drivers.containsKey(licenseId))
            return NO_DRIVER;
        else {
            Car car = cars.get(regNumber);
            if (car.isInUse())
                return CAR_IN_USE;
            else if (car.isRemoved())
                return CAR_REMOVED;
            else {
                car.setInUse(true);
                record = new RentRecord(regNumber, licenseId, rentDate, rentDays);
                addRecord(carRecords, regNumber, record);
                addRecord(driverRecords, licenseId, record);
                addRecord(records, rentDate, record);
                return OK;
            }
        }
    }

    private <K, V> void addRecord(Map<K, List<V>> records, K key, V record) {
        if (records.putIfAbsent(key, new ArrayList<>() {{
            add(record);
        }}) != null)
            records.get(key).add(record);
    }

    @Override
    @Routing(GET_DRIVER_CARS)
    public List<Car> getCarsDriver(long licenseId) {
        return getFilteredRecords(licenseId, cars, driverRecords, RentRecord::getRegNumber, Car::getRegNumber);
    }

    @Override
    @Routing(GET_CAR_DRIVERS)
    public List<Driver> getDriversCar(String regNumber) {
        return getFilteredRecords(regNumber, drivers, carRecords, RentRecord::getLicenseId, Driver::getLicenseId);
    }

    @Override
    @Routing(GET_MODEL_CARS)
    public List<Car> getCarsModel(String modelName) {
        return getFilteredRecords(modelName, cars, modelCars, Car::getRegNumber, Car::getRegNumber);
    }

    private <E, P, R, K, U> List<E> getFilteredRecords(P param, HashMap<K, E> database, HashMap<P, List<R>> records,
                                                       Function<R, U> getterParam1, Function<E, U> getterParam2) {
        return database.values().stream()
                .filter(
                        obj -> records.getOrDefault(param, new ArrayList<>()).stream()
                                .anyMatch(r -> getterParam1.apply(r).equals(getterParam2.apply(obj)))
                )
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    @Routing(GET_RECORDS)
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
    @Routing(REMOVE_CAR)
    public RemovedCarData removeCar(String regNumber) {
        if (!cars.containsKey(regNumber))
            return null;
        Car car = cars.get(regNumber);
        if (car.isInUse() || car.isRemoved())
            return null;
        if (!car.isRemoved())
            car.setRemoved(true);
        List<RentRecord> removedRecords = carRecords.getOrDefault(regNumber, new ArrayList<>());
        return new RemovedCarData(car, removedRecords);
    }

    @Override
    @Routing(REMOVE_MODEL)
    public List<RemovedCarData> removeModel(String modelName) {
        List<Car> carsByModel = modelCars.getOrDefault(modelName, new ArrayList<>());
        if (carsByModel.isEmpty()) {
            models.remove(modelName);
            modelCars.remove(modelName);
            return new ArrayList<>();
        }
        List<RemovedCarData> removedCarData = new ArrayList<>();
        for (Car car : carsByModel) {
            if (!car.isInUse()) {
                car.setRemoved(true);
                removedCarData.add(new RemovedCarData(car, carRecords.get(car.getRegNumber())));
            }
        }
        return removedCarData;
    }

    @Override
    @Routing(RETURN_CAR)
    public RentRecord returnCar(String regNumber, long licenseId, LocalDate returnDate, int damages, int tankPercent) {
        List<RentRecord> rentRecords = carRecords.get(regNumber);
        RentRecord rentRecord = rentRecords.get(rentRecords.size() - 1);

        Car car = cars.get(regNumber);
        rentRecord.setDamages(damages);
        setCarDamages(damages, car);

        rentRecord.setTankPercent(tankPercent);
        rentRecord.setReturnDate(returnDate);
        setProfit(rentRecord, car);

        car.setInUse(false);

        return rentRecord;
    }

    private void setProfit(RentRecord rentRecord, Car car) {
        Model model = models.get(car.getModelName());
        double cost = (model.getGasTank() * (rentRecord.getTankPercent() / 100.)) * gasPrice;

        int realDays = Period.between(rentRecord.getRentDate(), rentRecord.getReturnDate()).getDays();
        cost += model.getPriceDay() * rentRecord.getRentDays();

        int diffDays = realDays - rentRecord.getRentDays();
        if (diffDays > 0) {
            cost += diffDays * ((finePercent / 100.) * model.getPriceDay() + model.getPriceDay());
        }
        rentRecord.setCost(cost);
        rentRecord.setRentDays(realDays);
    }

    private void setCarDamages(int damages, Car car) {
        if (damages >= 80)
            car.setState(State.BAD);
        else if (damages <= 40)
            car.setState(State.EXCELLENT);
        else car.setState(State.GOOD);
    }

    private boolean isProperAge(RentRecord r, int fromAge, int toAge) {
        LocalDate rentDate = r.getRentDate();
        Driver driver = getDriver(r.getLicenseId());
        int driverAge = rentDate.getYear() - driver.getBirthYear();
        return driverAge >= fromAge && driverAge < toAge;
    }

    @Override
    @Routing(GET_MOST_POPULAR_MODELS)
    public List<String> getMostPopularCarModels(LocalDate dateFrom, LocalDate dateTo, int ageFrom, int ageTo) {
        return records.subMap(dateFrom, dateTo.plusDays(1)).values().stream()
                .flatMap(List::stream)
                .filter(rentRecord -> isProperAge(rentRecord, ageFrom, ageTo))
                .collect(Collectors.groupingBy(
                        rentRecord -> cars.get(rentRecord.getRegNumber()).getModelName(),
                        Collectors.summingInt(RentRecord::getRentDays)))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(COUNT_MOST_POPULAR_MODELS)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    @Routing(GET_MOST_PROFITABLE_MODELS)
    public List<String> getMostProfitableCarModels(LocalDate dateFrom, LocalDate dateTo) {
        return records.subMap(dateFrom, dateTo.plusDays(1)).values().stream()
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(
                        rentRecord -> cars.get(rentRecord.getRegNumber()).getModelName(),
                        Collectors.summingDouble(RentRecord::getCost)))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(COUNT_MOST_PROFITABLE_CAR_MODELS)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    @Routing(GET_MOST_ACTIVE_DRIVERS)
    public List<Driver> getMostActiveDrivers() {
        return drivers.values().stream().collect(Collectors.toMap(
                driver -> driver,
                driver -> driverRecords.getOrDefault(driver.getLicenseId(), new ArrayList<>()).size()
        ))
                .entrySet().stream()
                .sorted(Map.Entry.<Driver, Integer>comparingByValue().reversed())
                .limit(COUNT_MOST_ACTIVE_DRIVES)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public long getCountCars() {
        return cars.values().stream().filter(car -> !car.isRemoved()).count();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RentCompanyImpl)) return false;
        if (!super.equals(o)) return false;

        RentCompanyImpl that = (RentCompanyImpl) o;

        if (cars != null ? !cars.equals(that.cars) : that.cars != null) return false;
        if (drivers != null ? !drivers.equals(that.drivers) : that.drivers != null) return false;
        if (models != null ? !models.equals(that.models) : that.models != null) return false;
        if (modelCars != null ? !modelCars.equals(that.modelCars) : that.modelCars != null) return false;
        if (carRecords != null ? !carRecords.equals(that.carRecords) : that.carRecords != null) return false;
        if (driverRecords != null ? !driverRecords.equals(that.driverRecords) : that.driverRecords != null)
            return false;
        return records != null ? records.equals(that.records) : that.records == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (cars != null ? cars.hashCode() : 0);
        result = 31 * result + (drivers != null ? drivers.hashCode() : 0);
        result = 31 * result + (models != null ? models.hashCode() : 0);
        result = 31 * result + (modelCars != null ? modelCars.hashCode() : 0);
        result = 31 * result + (carRecords != null ? carRecords.hashCode() : 0);
        result = 31 * result + (driverRecords != null ? driverRecords.hashCode() : 0);
        result = 31 * result + (records != null ? records.hashCode() : 0);
        return result;
    }
}