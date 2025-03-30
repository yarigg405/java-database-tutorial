package org.example;


import java.util.Arrays;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        var service = new Service();

        var models = new CarDataModel[]
                {
                        new CarDataModel(1989, "Nissan", "Skyline R32", 280),
                        new CarDataModel(1993, "Nissan", "Skyline R33", 305),
                        new CarDataModel(1998, "Nissan", "Skyline R34", 280),
                        new CarDataModel(1999, "Ferrari", "360 Modena", 395),
                        new CarDataModel(2000, "Spyker", "C8", 400),
                        new CarDataModel(2025, "Mercedes-AMG", "C63", 671),
                        new CarDataModel(2014, "BMW-AMG", "M4", 430),
                };


        service.save(Arrays.asList(models));

        service.save(new CarDataModel(2005, "Hummer", "H2", 450));


        var car = service.findById(1L);
        System.out.println(car.toString());

        car.setModel("New model");
        car.setHorsePower(1000);
        service.update(car);

        car = service.findById(1L);
        System.out.println(car.toString());

        service.delete(4L);

        System.out.println("\n\n\n\n");
        for (var c : service.findAll()) {
            System.out.println(c.toString());
        }

    }
}