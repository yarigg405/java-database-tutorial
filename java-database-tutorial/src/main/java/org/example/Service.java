package org.example;

import java.util.ArrayList;
import java.util.List;

public class Service {
    private final Repository repository = new Repository();

    public void save(CarDataModel car) {
        repository.save(car);
    }

    public void save(List<CarDataModel> cars) {
        repository.save(cars);
    }

    public CarDataModel findById(Long id) {
        return repository.findById(id);
    }

    public List<CarDataModel> findAll() {
        return repository.findAll();
    }

    public void update(CarDataModel car) {
        repository.update(car);
    }

    public void delete(Long id) {
        repository.delete(id);
    }

}
