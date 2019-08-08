package test.cars.model;

import cars.model.*;
import main.cars.model.AbstractRentCompany;
import main.cars.model.RentCompanyImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static cars.model.CarsReturnCode.*;
import static main.cars.Config.FILENAME;
import static main.cars.Config.PATH_DATABASE;
import static org.junit.jupiter.api.Assertions.*;

class RentCompanyImplTest {
    private AbstractRentCompany rentCompany;

    private static final String MODEL_NAME_1 = "X3";
    private static final int GAS_TANK_1 = 10;
    private static final String COMPANY_1 = "BMW";
    private static final String COUNTRY_1 = "Germany";
    private static final int PRICE_DAY_1 = 200;

    private static final String MODEL_NAME_2 = "Q500";
    private static final int GAS_TANK_2 = 20;
    private static final String COMPANY_2 = "Jeep";
    private static final String COUNTRY_2 = "USA";
    private static final int PRICE_DAY_2 = 500;

    private static final String MODEL_NAME_3 = "2101";
    private static final int GAS_TANK_3 = 7;
    private static final String COMPANY_3 = "VAZ";
    private static final String COUNTRY_3 = "Russia";
    private static final int PRICE_DAY_3 = 100;

    private static final String MODEL_NAME_4 = "1100";

    private static final String MODEL_NAME_REMOVED = "21";

    private static final String MODEL_NAME_NEW = "2107";

    private Model model1;
    private Model model2;
    private Model model3;
    private Model model4;
    private Model modelNew;
    private Model modelRemoved;

    private static final long LICENSE_ID_1 = 123456789;
    private static final String DRIVER_NAME_1 = "Vasya";
    private static final int BIRTH_YEAR_1 = 1960;
    private static final String PHONE_1 = "+97253-111-11-11";

    private static final long LICENSE_ID_2 = 987654321;
    private static final String DRIVER_NAME_2 = "Moshe";
    private static final int BIRTH_YEAR_2 = 1990;
    private static final String PHONE_2 = "+97253-222-22-22";

    private static final long LICENSE_ID_3 = 876543210;
    private static final long LICENSE_ID_4 = 765432109;
    private static final long LICENSE_ID_5 = 654321098;
    private static final long LICENSE_ID_6 = 543210987;

    private static final long LICENSE_ID_NEW = 234567890;

    private Driver driver1;
    private Driver driver2;
    private Driver driver3;
    private Driver driver4;
    private Driver driver5;
    private Driver driver6;
    private Driver driverNew;

    private static final String REG_NUMBER_1 = "AE1234";
    private static final String COLOR_1 = "Red";

    private static final String REG_NUMBER_2 = "AE2345";
    private static final String COLOR_2 = "Black";

    private static final String REG_NUMBER_3 = "AE3456";
    private static final String REG_NUMBER_4 = "AE4567";
    private static final String REG_NUMBER_5 = "AE5678";
    private static final String REG_NUMBER_NEW = "AE6789";
    private static final String REG_NUMBER_REMOVED = "AE7890";

    private static final int COUNT_CARS = 5;

    private Car car1;
    private Car car2;
    private Car car3;
    private Car car4;
    private Car car5;
    private Car carNew;
    private Car carRemoved;

    private LocalDate rentDate1;
    private LocalDate rentDate2;
    private LocalDate rentDate3;

    private LocalDate returnedDate1;
    private LocalDate returnedDate2;
    private LocalDate returnedDate3;

    private static final int RENT_DAYS_1 = 5;
    private static final int RENT_DAYS_2 = 20;

    private static final int DAMAGES_1 = 30;

    private static final int TANK_PERCENT_1 = 20;

    private List<Car> carsDriver;

    private List<Driver> driversCar;

    private List<Car> carsModel;

    private RentRecord rentRecord1;
    private RentRecord rentRecord2;
    private RentRecord rentRecord3;
    private RentRecord rentRecord4;
    private List<RentRecord> recordsRentDates;


    private RentRecord rentRecordRemoved1;
    private RentRecord rentRecordRemoved2;
    private List<RentRecord> removedCars;

    private RentRecord returnRentRecord;

    private List<RemovedCarData> removedCarDataByModel;
    private List<RentRecord> rentRecordRemoved1ByModel;
    private List<RentRecord> rentRecordRemoved3ByModel;

    RentCompanyImplTest() {
    }

    @BeforeEach
    void setUp() {
        rentCompany = new RentCompanyImpl();

        model1 = new Model(MODEL_NAME_1, GAS_TANK_1, COMPANY_1, COUNTRY_1, PRICE_DAY_1);
        model2 = new Model(MODEL_NAME_2, GAS_TANK_2, COMPANY_2, COUNTRY_2, PRICE_DAY_2);
        model3 = new Model(MODEL_NAME_3, GAS_TANK_3, COMPANY_3, COUNTRY_3, PRICE_DAY_3);
        model4 = new Model(MODEL_NAME_4, GAS_TANK_3, COMPANY_1, COUNTRY_2, PRICE_DAY_3);
        modelNew = new Model(MODEL_NAME_NEW, GAS_TANK_3, COMPANY_3, COUNTRY_3, PRICE_DAY_3);
        modelRemoved = new Model(MODEL_NAME_REMOVED, GAS_TANK_3, COMPANY_3, COUNTRY_3, PRICE_DAY_3);

        rentCompany.addModel(model1);
        rentCompany.addModel(model2);
        rentCompany.addModel(model3);
        rentCompany.addModel(model4);
        rentCompany.addModel(modelRemoved);

        driver1 = new Driver(LICENSE_ID_1, DRIVER_NAME_1, BIRTH_YEAR_1, PHONE_1);
        driver2 = new Driver(LICENSE_ID_2, DRIVER_NAME_2, BIRTH_YEAR_2, PHONE_2);
        driver3 = new Driver(LICENSE_ID_3, DRIVER_NAME_2, BIRTH_YEAR_1, PHONE_1);
        driver4 = new Driver(LICENSE_ID_4, DRIVER_NAME_1, BIRTH_YEAR_2, PHONE_2);
        driver5 = new Driver(LICENSE_ID_5, DRIVER_NAME_2, BIRTH_YEAR_1, PHONE_1);
        driver6 = new Driver(LICENSE_ID_6, DRIVER_NAME_1, BIRTH_YEAR_2, PHONE_2);
        driverNew = new Driver(LICENSE_ID_NEW, DRIVER_NAME_2, BIRTH_YEAR_2, PHONE_2);
        rentCompany.addDriver(driver1);
        rentCompany.addDriver(driver2);
        rentCompany.addDriver(driver3);
        rentCompany.addDriver(driver4);
        rentCompany.addDriver(driver5);
        rentCompany.addDriver(driver6);

        car1 = new Car(REG_NUMBER_1, COLOR_1, MODEL_NAME_1);
        car2 = new Car(REG_NUMBER_2, COLOR_2, MODEL_NAME_2);
        car3 = new Car(REG_NUMBER_3, COLOR_2, MODEL_NAME_1);
        car4 = new Car(REG_NUMBER_4, COLOR_1, MODEL_NAME_3);
        car5 = new Car(REG_NUMBER_5, COLOR_2, MODEL_NAME_1);
        carNew = new Car(REG_NUMBER_NEW, COLOR_1, MODEL_NAME_NEW);
        carRemoved = new Car(REG_NUMBER_REMOVED, COLOR_1, MODEL_NAME_3);
        carRemoved.setRemoved(true);

        rentCompany.addCar(car1);
        rentCompany.addCar(car2);
        rentCompany.addCar(car3);
        rentCompany.addCar(car4);
        rentCompany.addCar(car5);
        rentCompany.addCar(carRemoved);

        rentDate1 = LocalDate.of(2018, 12,12);
        rentDate2 = LocalDate.of(2019, 1,1);
        rentDate3 = LocalDate.of(2019, 4,4);

        returnedDate1 = rentDate1.plusDays(7);
        returnedDate2 = rentDate2.plusDays(10);
        returnedDate3 = rentDate3.plusDays(5);

        rentCompany.rentCar(REG_NUMBER_1, LICENSE_ID_1, rentDate1, RENT_DAYS_1);
        rentCompany.rentCar(REG_NUMBER_3, LICENSE_ID_1, rentDate2, RENT_DAYS_2);
        rentCompany.rentCar(REG_NUMBER_4, LICENSE_ID_2, rentDate1, RENT_DAYS_2);

        rentCompany.returnCar(REG_NUMBER_1, LICENSE_ID_1, returnedDate1, DAMAGES_1, TANK_PERCENT_1);
        rentCompany.rentCar(REG_NUMBER_1, LICENSE_ID_2, rentDate2, RENT_DAYS_2);

        carsDriver = new ArrayList<>();
        carsDriver.add(car1);
        carsDriver.add(car3);
        carsDriver.add(car5);

        driversCar = new ArrayList<>();
        driversCar.add(driver1);
        driversCar.add(driver2);

        carsModel = new ArrayList<>();

        carsModel.add(car1);
        carsModel.add(car3);
        carsModel.add(car5);

        rentRecord1 = new RentRecord(REG_NUMBER_1, LICENSE_ID_1, rentDate1, RENT_DAYS_1);
        rentRecord2 = new RentRecord(REG_NUMBER_3, LICENSE_ID_1, rentDate2, RENT_DAYS_2);
        rentRecord3 = new RentRecord(REG_NUMBER_4, LICENSE_ID_2, rentDate1, RENT_DAYS_2);
        rentRecord4 = new RentRecord(REG_NUMBER_1, LICENSE_ID_2, rentDate2, RENT_DAYS_2);
        recordsRentDates = new ArrayList<>();
        recordsRentDates.add(rentRecord1);
        recordsRentDates.add(rentRecord2);
        recordsRentDates.add(rentRecord3);
        recordsRentDates.add(rentRecord4);

        removedCars = new ArrayList<>();
        rentCompany.rentCar(REG_NUMBER_5, LICENSE_ID_2, rentDate3, RENT_DAYS_1);
        rentCompany.returnCar(REG_NUMBER_5, LICENSE_ID_2, returnedDate3, DAMAGES_1, TANK_PERCENT_1);

        rentCompany.rentCar(REG_NUMBER_5, LICENSE_ID_1, rentDate3, RENT_DAYS_1);
        rentCompany.returnCar(REG_NUMBER_5, LICENSE_ID_1, returnedDate3, DAMAGES_1, TANK_PERCENT_1);

        rentRecordRemoved1 = new RentRecord(REG_NUMBER_5, LICENSE_ID_2, rentDate3, RENT_DAYS_1);
        rentRecordRemoved2 = new RentRecord(REG_NUMBER_5, LICENSE_ID_1, rentDate3, RENT_DAYS_1);

        removedCars.add(rentRecordRemoved1);
        removedCars.add(rentRecordRemoved2);

        returnRentRecord = new RentRecord(REG_NUMBER_2, LICENSE_ID_1, rentDate2, RENT_DAYS_1);

        //remove model
        removedCarDataByModel = new ArrayList<>();

        rentRecordRemoved1ByModel = new ArrayList<>();
        rentRecordRemoved1ByModel.add(rentRecord1);
        rentRecordRemoved1ByModel.add(rentRecord4);
        removedCarDataByModel.add(new RemovedCarData(car1, rentRecordRemoved1ByModel));

        rentRecordRemoved3ByModel = new ArrayList<>();
        rentRecordRemoved3ByModel.add(new RentRecord(REG_NUMBER_5, LICENSE_ID_2, rentDate3, RENT_DAYS_1));
        rentRecordRemoved3ByModel.add(new RentRecord(REG_NUMBER_5, LICENSE_ID_1, rentDate3, RENT_DAYS_1));
        removedCarDataByModel.add(new RemovedCarData(car5, rentRecordRemoved3ByModel));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void restoreFromFile() {
        RentCompanyImpl restoredRentCompany = assertDoesNotThrow(() ->
                (RentCompanyImpl) RentCompanyImpl.restoreFromFile(PATH_DATABASE + FILENAME));
        assertEquals(restoredRentCompany, rentCompany);
    }

    @Test
    void save() {
        assertDoesNotThrow(() -> ((RentCompanyImpl)rentCompany).save(PATH_DATABASE + FILENAME));
    }

    @Test
    void addModel() {
        assertEquals(MODEL_EXISTS, rentCompany.addModel(model1));
        assertEquals(OK, rentCompany.addModel(modelNew));
        assertEquals(modelNew, rentCompany.getModel(modelNew.getModelName()));
    }

    @Test
    void addCar() {
        assertEquals(CAR_EXISTS, rentCompany.addCar(car1));
        assertEquals(NO_MODEL, rentCompany.addCar(carNew));
        rentCompany.addModel(modelNew);
        assertEquals(OK, rentCompany.addCar(carNew));
        assertEquals(carNew, rentCompany.getCar(carNew.getRegNumber()));

        assertEquals(OK, rentCompany.addCar(carRemoved));
        assertEquals(carRemoved, rentCompany.getCar(carRemoved.getRegNumber()));
    }

    @Test
    void addDriver() {
        assertEquals(DRIVER_EXISTS, rentCompany.addDriver(driver1));
        assertEquals(OK, rentCompany.addDriver(driverNew));
        assertEquals(driverNew, rentCompany.getDriver(driverNew.getLicenseId()));
    }

    @Test
    void getModel() {
        assertNull(rentCompany.getModel(MODEL_NAME_NEW));
        assertEquals(model1, rentCompany.getModel(model1.getModelName()));
    }

    @Test
    void getCar() {
        assertNull(rentCompany.getCar(REG_NUMBER_NEW));
        assertEquals(car1, rentCompany.getCar(car1.getRegNumber()));
    }

    @Test
    void getDriver() {
        assertNull(rentCompany.getDriver(LICENSE_ID_NEW));
        assertEquals(driver1, rentCompany.getDriver(driver1.getLicenseId()));
    }

    @Test
    void rentCar() {
        CarsReturnCode carsReturnCode = rentCompany.rentCar(REG_NUMBER_NEW, LICENSE_ID_1, rentDate1, RENT_DAYS_1);
        assertEquals(NO_CAR, carsReturnCode);

        carsReturnCode = rentCompany.rentCar(REG_NUMBER_1, LICENSE_ID_NEW, rentDate1, RENT_DAYS_1);
        assertEquals(NO_DRIVER, carsReturnCode);

        carsReturnCode = rentCompany.rentCar(REG_NUMBER_2, LICENSE_ID_2, rentDate2, RENT_DAYS_2);
        assertEquals(OK, carsReturnCode);

        carsReturnCode = rentCompany.rentCar(REG_NUMBER_1, LICENSE_ID_1, rentDate1, RENT_DAYS_1);
        assertEquals(CAR_IN_USE, carsReturnCode);
        assertTrue(rentCompany.getCar(REG_NUMBER_1).isInUse());

        carsReturnCode = rentCompany.rentCar(REG_NUMBER_REMOVED, LICENSE_ID_1, rentDate1, RENT_DAYS_1);
        assertEquals(CAR_REMOVED, carsReturnCode);
    }

    @Test
    void getCarsDriver() {
        assertEquals(0, rentCompany.getCarsDriver(LICENSE_ID_NEW).size());
        carsDriver.sort(Comparator.comparing(Car::getRegNumber));
        List<Car> actual = rentCompany.getCarsDriver(LICENSE_ID_1);
        actual.sort(Comparator.comparing(Car::getRegNumber));
        assertEquals(carsDriver, actual);
    }

    @Test
    void getDriversCar() {
        assertEquals(0, rentCompany.getDriversCar(REG_NUMBER_NEW).size());
        driversCar.sort(Comparator.comparing(Driver::getLicenseId));
        List<Driver> actual = rentCompany.getDriversCar(REG_NUMBER_1);
        actual.sort(Comparator.comparing(Driver::getLicenseId));
        assertEquals(driversCar, actual);
    }

    @Test
    void getCarsModel() {
        assertEquals(0, rentCompany.getCarsModel(MODEL_NAME_NEW).size());
        carsModel.sort(Comparator.comparing(Car::getRegNumber));
        List<Car> actual = rentCompany.getCarsModel(MODEL_NAME_1);
        actual.sort(Comparator.comparing(Car::getRegNumber));
        assertEquals(carsModel, actual);
    }

    @Test
    void getRentRecordsAtDates() {
        recordsRentDates.sort(Comparator.comparing(RentRecord::getRegNumber));
        List<RentRecord> actual = rentCompany.getRentRecordsAtDates(rentDate1, rentDate2);
        actual.sort(Comparator.comparing(RentRecord::getRegNumber));
        assertEquals(recordsRentDates, actual);
    }

    @Test
    void removeCar() {
        assertNull(rentCompany.removeCar(REG_NUMBER_NEW));
        assertNull(rentCompany.removeCar(REG_NUMBER_1)); // in use
        assertNull(rentCompany.removeCar(REG_NUMBER_REMOVED)); // removed

        RemovedCarData removedCarData = rentCompany.removeCar(REG_NUMBER_5);
        assertTrue(car5.isRemoved());
        assertEquals(car5, removedCarData.getCar());
        assertEquals(removedCars, removedCarData.getRemovedRecords());
        assertEquals(COUNT_CARS-1, rentCompany.getCountCars());
    }

    @Test
    void removeModel() {
        assertEquals(new ArrayList<>(), rentCompany.removeModel(MODEL_NAME_NEW)); //no model

        rentCompany.returnCar(REG_NUMBER_1, LICENSE_ID_2, returnedDate2, DAMAGES_1, TANK_PERCENT_1); //return car1

        List<RemovedCarData> removedCarData = rentCompany.removeModel(MODEL_NAME_1);

        for (Car car : rentCompany.getCarsModel(MODEL_NAME_1)){
            if (!car.isInUse()){
                assertTrue(car.isRemoved());
            }
        }

        assertEquals(removedCarDataByModel, removedCarData); // ca1 + car2 records

        assertEquals(COUNT_CARS-2, rentCompany.getCountCars());
    }

    @Test
    void returnCar() {
        rentCompany.rentCar(REG_NUMBER_2, LICENSE_ID_1, rentDate1, RENT_DAYS_1);
        rentCompany.returnCar(REG_NUMBER_2, LICENSE_ID_1, returnedDate1, DAMAGES_1, TANK_PERCENT_1);
        rentCompany.rentCar(REG_NUMBER_2, LICENSE_ID_1, rentDate2, RENT_DAYS_1);

        RentRecord actual = rentCompany.returnCar(REG_NUMBER_2, LICENSE_ID_1, returnedDate2, DAMAGES_1, TANK_PERCENT_1);

        assertEquals(returnRentRecord, actual);
        assertFalse(rentCompany.getCar(REG_NUMBER_2).isInUse());
        assertEquals(COUNT_CARS,rentCompany.getCountCars());
    }

    @Test
    void getMostPopularCarModels() {
        rentCompany.returnCar(REG_NUMBER_3, LICENSE_ID_1, returnedDate2, DAMAGES_1, TANK_PERCENT_1);
        rentCompany.returnCar(REG_NUMBER_4, LICENSE_ID_2, returnedDate1, DAMAGES_1, TANK_PERCENT_1);
        rentCompany.returnCar(REG_NUMBER_1, LICENSE_ID_2, returnedDate2, DAMAGES_1, TANK_PERCENT_1);

        rentReturnCarByDriver(5, LICENSE_ID_1, REG_NUMBER_3);
        rentReturnCarByDriver(1, LICENSE_ID_2, REG_NUMBER_4);
        rentReturnCarByDriver(11, LICENSE_ID_2, REG_NUMBER_2);
        rentReturnCarByDriver(4, LICENSE_ID_2, REG_NUMBER_1);

        List<String> mostPopularCarModels = rentCompany.getMostPopularCarModels(rentDate1, rentDate2, 20, 30);
        List<String> threeMostPopularCarModels = new ArrayList<>();
        threeMostPopularCarModels.add(MODEL_NAME_2);
        threeMostPopularCarModels.add(MODEL_NAME_1);
        threeMostPopularCarModels.add(MODEL_NAME_3);

        assertEquals(threeMostPopularCarModels, mostPopularCarModels);
    }

    @Test
    void getMostProfitableCarModels() {
        rentCompany.returnCar(REG_NUMBER_3, LICENSE_ID_1, returnedDate2, DAMAGES_1, TANK_PERCENT_1);
        rentCompany.returnCar(REG_NUMBER_4, LICENSE_ID_2, returnedDate1, DAMAGES_1, TANK_PERCENT_1);
        rentCompany.returnCar(REG_NUMBER_1, LICENSE_ID_2, returnedDate2, DAMAGES_1, TANK_PERCENT_1);

        makeProfitableCarStatistics(3, REG_NUMBER_1);
        makeProfitableCarStatistics(7, REG_NUMBER_2);
        makeProfitableCarStatistics(2, REG_NUMBER_3);
        makeProfitableCarStatistics(3, REG_NUMBER_4);

        List<String> mostProfitableCarModels = rentCompany.getMostProfitableCarModels(rentDate1, rentDate2);

        List<String> threeMostProfitableModels = new ArrayList<>();
        threeMostProfitableModels.add(MODEL_NAME_2);
        threeMostProfitableModels.add(MODEL_NAME_1);
        threeMostProfitableModels.add(MODEL_NAME_3);

        assertEquals(threeMostProfitableModels, mostProfitableCarModels);
    }

    private void makeProfitableCarStatistics(int count, String regNumber){
        for (int i = 0; i < count; i++) {
            rentCompany.rentCar(regNumber, LICENSE_ID_2, rentDate1, RENT_DAYS_1);
            rentCompany.returnCar(regNumber, LICENSE_ID_2, returnedDate1, DAMAGES_1, TANK_PERCENT_1);
        }
    }

    @Test
    void getMostActiveDrivers() {
        rentReturnCarByDriver(5, LICENSE_ID_3, REG_NUMBER_2);
        rentReturnCarByDriver(1, LICENSE_ID_4, REG_NUMBER_2);
        rentReturnCarByDriver(11, LICENSE_ID_5, REG_NUMBER_2);
        rentReturnCarByDriver(4, LICENSE_ID_6, REG_NUMBER_2);

        List<Driver> mostActiveDrivers = rentCompany.getMostActiveDrivers();
        List<Driver> threeMostActiveDrivers = new ArrayList<>();
        threeMostActiveDrivers.add(driver5);
        threeMostActiveDrivers.add(driver3);
        threeMostActiveDrivers.add(driver6);

        assertEquals(threeMostActiveDrivers ,mostActiveDrivers);
    }

    private void rentReturnCarByDriver(int count, long license, String regNumber){
        for (int i = 0; i < count; i++) {
            rentCompany.rentCar(regNumber, license, rentDate1, RENT_DAYS_1);
            rentCompany.returnCar(regNumber, license, returnedDate1, DAMAGES_1,TANK_PERCENT_1);
        }
    }
}