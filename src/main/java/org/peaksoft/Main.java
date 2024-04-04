package org.peaksoft;

import org.peaksoft.model.Car;
import org.peaksoft.service.CarServiceImpl;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // TODO: 27.09.2023   реализуйте алгоритм здесь
      CarServiceImpl carService = new CarServiceImpl();
        carService.createTable();
//        carService.save(new Car("Mercedes",LocalDate.of(2001
//                ,01,1),"White"));
//        Car car = carService.removeById();
carService.removeById(2);
    }
}
