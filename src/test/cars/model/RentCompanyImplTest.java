package test.cars.model;

import main.cars.dto.*;
import main.cars.model.AbstractRentCompany;
import main.cars.model.RentCompanyImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static main.cars.dto.CarsReturnCode.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RentCompanyImplTest {
    private AbstractRentCompany rentCompany;

    private static final String MODEL_NAME_1 = "BMW";
    private static final int GAS_TANK_1 = 10;
    private static final String COMPANY_1 = "X3";
    private static final String COUNTRY_1 = "Germany";
    private static final int PRICE_DAY_1 = 200;

    private static final String MODEL_NAME_2 = "Jeep";
    private static final int GAS_TANK_2 = 20;
    private static final String COMPANY_2 = "Q500";
    private static final String COUNTRY_2 = "USA";
    private static final int PRICE_DAY_2 = 500;

    private Model model1;
    private Model model2;

    private static final long LICENSE_ID_1 = 123456789;
    private static final String DRIVER_NAME_1 = "Vasya";
    private static final int BIRTH_YEAR_1 = 1960;
    private static final String PHONE_1 = "+97253-111-11-11";

    private static final long LICENSE_ID_2 = 987654321;
    private static final String DRIVER_NAME_2 = "Moshe";
    private static final int BIRTH_YEAR_2 = 1990;
    private static final String PHONE_2 = "+97253-222-22-22";

    private Driver driver1;
    private Driver driver2;

    private static final String REG_NUMBER_1 = "AE1234";
    private static final String COLOR_1 = "Red";

    private static final String REG_NUMBER_2 = "AE9876";
    private static final String COLOR_2 = "Black";

    private static final String REG_NUMBER_3 = "AE6543";

    private Car car1;
    private Car car2;
    private Car car3;

    private LocalDate RENT_DATE_1;
    private LocalDate RENT_DATE_2;
    private LocalDate RENT_DATE_3;

    private List<Car> CARS_DRIVER;

    private List<Driver> DRIVERS_CAR;

    private List<Car> CARS_MODEL;

    private RentRecord rentRecord1;
    private RentRecord rentRecord2;
    private List<RentRecord> RECORDS_RENT_DATES;

    @BeforeEach
    void setUp() {
        rentCompany = new RentCompanyImpl();

        model1 = new Model(MODEL_NAME_1, GAS_TANK_1, COMPANY_1, COUNTRY_1, PRICE_DAY_1);
        model2 = new Model(MODEL_NAME_2, GAS_TANK_2, COMPANY_2, COUNTRY_2, PRICE_DAY_2);
        rentCompany.addModel(model1);

        driver1 = new Driver(LICENSE_ID_1, DRIVER_NAME_1, BIRTH_YEAR_1, PHONE_1);
        driver2 = new Driver(LICENSE_ID_2, DRIVER_NAME_2, BIRTH_YEAR_2, PHONE_2);
        rentCompany.addDriver(driver1);

        car1 = new Car(REG_NUMBER_1, COLOR_1, MODEL_NAME_1);
        car2 = new Car(REG_NUMBER_2, COLOR_2, MODEL_NAME_2);
        car3 = new Car(REG_NUMBER_3, COLOR_2, MODEL_NAME_1);
        rentCompany.addCar(car1);

        RENT_DATE_1 = LocalDate.of(2018, 12,12);
        RENT_DATE_2 = LocalDate.of(2019, 1,1);
        RENT_DATE_3 = LocalDate.of(2019, 4,4);

        CARS_DRIVER = new ArrayList<>();

        CARS_DRIVER.add(car1);
        CARS_DRIVER.add(car2);

        DRIVERS_CAR = new ArrayList<>();

        DRIVERS_CAR.add(driver1);
        DRIVERS_CAR.add(driver2);

        CARS_MODEL = new ArrayList<>();

        CARS_MODEL.add(car1);
        CARS_MODEL.add(car3);

        rentRecord1 = new RentRecord(REG_NUMBER_1, LICENSE_ID_1, RENT_DATE_1);
        rentRecord2 = new RentRecord(REG_NUMBER_1, LICENSE_ID_1, RENT_DATE_2);
        RECORDS_RENT_DATES = new ArrayList<>();
        RECORDS_RENT_DATES.add(rentRecord1);
        RECORDS_RENT_DATES.add(rentRecord2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void restoreFromFile() {
    }

    @Test
    void save() {
    }

    @Test
    void addModel() {
        assertEquals(MODEL_EXISTS, rentCompany.addModel(model1));
        assertEquals(OK, rentCompany.addModel(model2));
        assertEquals(model2, rentCompany.getModel(model2.getModelName()));
    }

    @Test
    void addCar() {
        assertEquals(CAR_EXISTS, rentCompany.addCar(car1));
        assertEquals(NO_MODEL, rentCompany.addCar(car2));
        rentCompany.addModel(model2);
        assertEquals(OK, rentCompany.addCar(car2));
        assertEquals(car2, rentCompany.getCar(car2.getRegNumber()));
    }

    @Test
    void addDriver() {
        assertEquals(DRIVER_EXISTS, rentCompany.addDriver(driver1));
        assertEquals(OK, rentCompany.addDriver(driver2));
        assertEquals(driver2, rentCompany.getDriver(driver2.getLicenseId()));
    }

    @Test
    void getModel() {
        assertNull(rentCompany.getModel(MODEL_NAME_2));
        assertEquals(model1, rentCompany.getModel(model1.getModelName()));
    }

    @Test
    void getCar() {
        assertNull(rentCompany.getCar(REG_NUMBER_2));
        assertEquals(car1, rentCompany.getCar(car1.getRegNumber()));
    }

    @Test
    void getDriver() {
        assertNull(rentCompany.getDriver(LICENSE_ID_2));
        assertEquals(driver1, rentCompany.getDriver(driver1.getLicenseId()));
    }

    @Test
    void rentCar() {
        CarsReturnCode carsReturnCode = rentCompany.rentCar(REG_NUMBER_2, LICENSE_ID_1, RENT_DATE_1);
        assertEquals(NO_CAR, carsReturnCode);

        carsReturnCode = rentCompany.rentCar(REG_NUMBER_1, LICENSE_ID_2, RENT_DATE_1);
        assertEquals(NO_DRIVER, carsReturnCode);

        carsReturnCode = rentCompany.rentCar(REG_NUMBER_1, LICENSE_ID_1, RENT_DATE_1);
        assertEquals(OK, carsReturnCode);

        carsReturnCode = rentCompany.rentCar(REG_NUMBER_1, LICENSE_ID_1, RENT_DATE_1);
        assertEquals(OK, carsReturnCode);

        car1.setInUse(true);
        carsReturnCode = rentCompany.rentCar(REG_NUMBER_1, LICENSE_ID_1, RENT_DATE_1);
        assertEquals(CAR_IN_USE, carsReturnCode);

        car1.setInUse(false);
        car1.setIfRemoved(true);
        carsReturnCode = rentCompany.rentCar(REG_NUMBER_1, LICENSE_ID_1, RENT_DATE_1);
        assertEquals(CAR_REMOVED, carsReturnCode);
    }

    @Test
    void getCarsDriver() {
        assertEquals(0, rentCompany.getCarsDriver(LICENSE_ID_1).size());
        rentCompany.rentCar(REG_NUMBER_1, LICENSE_ID_1, RENT_DATE_1);
        rentCompany.addModel(model2);
        rentCompany.addCar(car2);
        rentCompany.rentCar(REG_NUMBER_2, LICENSE_ID_1, RENT_DATE_1);

        CARS_DRIVER.sort(Comparator.comparing(Car::getRegNumber));
        List<Car> actual = rentCompany.getCarsDriver(LICENSE_ID_1);
        actual.sort(Comparator.comparing(Car::getRegNumber));
        assertEquals(CARS_DRIVER, actual);
    }

    @Test
    void getDriversCar() {
        assertEquals(0, rentCompany.getDriversCar(REG_NUMBER_1).size());
        rentCompany.rentCar(REG_NUMBER_1, LICENSE_ID_1, RENT_DATE_1);
        rentCompany.addDriver(driver2);
        rentCompany.rentCar(REG_NUMBER_1, LICENSE_ID_2, RENT_DATE_1);

        DRIVERS_CAR.sort(Comparator.comparing(Driver::getLicenseId));
        List<Driver> actual = rentCompany.getDriversCar(REG_NUMBER_1);
        actual.sort(Comparator.comparing(Driver::getLicenseId));
        assertEquals(DRIVERS_CAR, actual);
    }

    @Test
    void getCarsModel() {
        assertEquals(0, rentCompany.getCarsModel(MODEL_NAME_1).size());
        rentCompany.rentCar(REG_NUMBER_1, LICENSE_ID_1, RENT_DATE_1);
        rentCompany.addCar(car3);
        rentCompany.rentCar(REG_NUMBER_3, LICENSE_ID_1, RENT_DATE_1);

        CARS_MODEL.sort(Comparator.comparing(Car::getRegNumber));
        List<Car> actual = rentCompany.getCarsModel(MODEL_NAME_1);
        actual.sort(Comparator.comparing(Car::getRegNumber));
        assertEquals(CARS_MODEL, actual);
    }

    @Test
    void getRentRecordsAtDates() {
        assertEquals(0, rentCompany.getRentRecordsAtDates(LocalDate.MIN, LocalDate.MAX).size());
        rentCompany.rentCar(REG_NUMBER_1, LICENSE_ID_1, RENT_DATE_1);
        rentCompany.rentCar(REG_NUMBER_1, LICENSE_ID_1, RENT_DATE_2);
        rentCompany.rentCar(REG_NUMBER_1, LICENSE_ID_1, RENT_DATE_3);

        RECORDS_RENT_DATES.sort(Comparator.comparing(RentRecord::getRegNumber));
        List<RentRecord> actual = rentCompany.getRentRecordsAtDates(RENT_DATE_1, RENT_DATE_2);
        actual.sort(Comparator.comparing(RentRecord::getRegNumber));
        assertEquals(RECORDS_RENT_DATES, actual);
    }

    @Test
    void removeCar() {
    }

    @Test
    void removeModel() {
    }

    @Test
    void returnCar() {
    }

    @Test
    void getMostPopularCarModels() {
    }

    @Test
    void getMostProfitableCarModels() {
    }

    @Test
    void getMostActiveDrivers() {
    }
}