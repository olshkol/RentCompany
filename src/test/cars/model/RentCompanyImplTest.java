package test.cars.model;

import main.cars.dto.Car;
import main.cars.dto.Driver;
import main.cars.dto.Model;
import main.cars.model.AbstractRentCompany;
import main.cars.model.RentCompanyImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
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

    private List<Car> CARS_DRIVER;

    private List<Driver> DRIVERS_CAR;

    private List<Car> CARS_MODEL;

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

        RENT_DATE_1 = LocalDate.of(2019, 4,4);

        CARS_DRIVER = new ArrayList<>();

        CARS_DRIVER.add(car1);
        CARS_DRIVER.add(car2);

        DRIVERS_CAR = new ArrayList<>();

        DRIVERS_CAR.add(driver1);
        DRIVERS_CAR.add(driver2);

        CARS_MODEL = new ArrayList<>();

        CARS_MODEL.add(car1);
        CARS_MODEL.add(car3);

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
    }

    @Test
    void getCarsDriver() {
    }

    @Test
    void getDriversCar() {
    }

    @Test
    void getCarsModel() {
    }

    @Test
    void getRentRecordsAtDates() {
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