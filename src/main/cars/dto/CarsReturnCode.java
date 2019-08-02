package main.cars.dto;

public enum CarsReturnCode {
    OK,

    MODEL_EXISTS,
    NO_MODEL,

    CAR_EXISTS,
    NO_CAR,
    CAR_REMOVED,
    CAR_IN_USE,

    DRIVER_EXISTS,
    NO_DRIVER
}
