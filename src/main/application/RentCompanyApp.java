package main.application;

import main.cars.dto.Car;
import main.cars.dto.RentRecord;
import main.cars.model.RentCompany;
import main.cars.model.RentCompanyImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RentCompanyApp {
    private static HashMap<String, List<Integer>> carRecords = new HashMap<>();

    public static void main(String[] args) {
        List<Integer> old = new ArrayList<>();
        old.add(1);
        carRecords.put("Line1", old);

        int record = 1000;
        List<Integer> newRecords = new ArrayList<>();
        newRecords.add(record);

        if (carRecords.putIfAbsent("Line1", newRecords) != null){
            carRecords.get("Line1").add(record);
        }



    }
}
