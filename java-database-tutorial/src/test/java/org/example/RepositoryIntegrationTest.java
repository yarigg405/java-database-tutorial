package org.example;

import org.junit.jupiter.api.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryIntegrationTest {
    private static Repository repository;
    private CarDataModel[] testCars = new CarDataModel[]
            {
                    new CarDataModel(1989, "Nissan", "Skyline R32", 280),
                    new CarDataModel(1993, "Nissan", "Skyline R33", 305),
                    new CarDataModel(1998, "Nissan", "Skyline R34", 280),
                    new CarDataModel(1999, "Ferrari", "360 Modena", 395),
                    new CarDataModel(2000, "Spyker", "C8", 400),
                    new CarDataModel(2025, "Mercedes-AMG", "C63", 671),
                    new CarDataModel(2014, "BMW-AMG", "M4", 430),
            };

    private CarDataModel testCar = new CarDataModel(2025, "Test Mark", "Test Model", 405);

    @BeforeEach
    public void setup() {
        repository = new Repository();
        repository.save(Arrays.asList(testCars));
    }

    @AfterEach
    public void dropData() {
        repository.Dispose();
    }


    @Test
    void testSave() {
        var before = repository.findAll().size();
        repository.save(testCar);
        var after = repository.findAll().size();
        assertEquals(before + 1, after);
    }

    @Test
    void testSaveMultiply() {
        repository.save(Arrays.asList(testCars));
        assertEquals(repository.findAll().size(), testCars.length * 2);
    }

    @Test
    void testFindById() {
        var testResult = repository.findById(2L);
        assertEquals("Skyline R33", testResult.getModel());
    }

    @Test
    void testFindAll() {
        assertEquals(repository.findAll().size(), testCars.length);
    }

    @Test
    void testUpdate() {
        var testResult = repository.findById(2L);
        testResult.setModel("New model");
        repository.update(testResult);
        testResult = repository.findById(2L);
        assertEquals("New model", testResult.getModel());
    }

    @Test
    void testDelete() {
        repository.delete(2L);
        var testAll = repository.findAll();
        assertEquals(testCars.length - 1, testAll.size());
    }
}