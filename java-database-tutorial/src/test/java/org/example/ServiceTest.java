package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class ServiceTest {

    @Mock
    private Repository repository;

    @InjectMocks
    private Service service;

    CarDataModel[] testCars;

    @BeforeEach
    public void createTestData() {
        testCars = new CarDataModel[]
                {
                        new CarDataModel(1989, "Nissan", "Skyline R32", 280),
                        new CarDataModel(1993, "Nissan", "Skyline R33", 305),
                        new CarDataModel(1998, "Nissan", "Skyline R34", 280),
                        new CarDataModel(1999, "Ferrari", "360 Modena", 395),
                        new CarDataModel(2000, "Spyker", "C8", 400),
                        new CarDataModel(2025, "Mercedes-AMG", "C63", 671),
                        new CarDataModel(2014, "BMW-AMG", "M4", 430),
                };
    }


    @Test
    void testSave() {
        var model = testCars[0];
        service.save(model);
        Mockito.verify(repository, Mockito.times(1)).save(model);
    }

    @Test
    void testSaveMultiply() {
        service.save(Arrays.asList(testCars));
        Mockito.verify(repository, Mockito.times(1)).save(Arrays.asList(testCars));
    }

    @Test
    void testFindById() {
        long carId = 1L;
        Mockito.when(repository.findById(carId)).thenReturn(testCars[(int) carId]);

        var car = service.findById(carId);
        assertEquals(car, testCars[(int) carId]);
    }


    @Test
    void findAll() {
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(testCars));
        assertEquals(repository.findAll().size(), testCars.length);
    }

    @Test
    void update() {
        service.update(testCars[0]);
        Mockito.verify(repository, Mockito.times(1)).update(testCars[0]);
    }

    @Test
    void delete() {
        service.delete(testCars[0].getId());
        Mockito.verify(repository, Mockito.times(1)).delete(testCars[0].getId());
    }
}