package org.example;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Repository {
    private static final String propertiesPath = "src/main/resources/db.properties";
    private final Properties properties;

    private static final String CREATE_TABLE_QUERY =
            "CREATE TABLE CARS "
                    + "(id INTEGER not NULL AUTO_INCREMENT, "
                    + "modelYear INTEGER, "
                    + "mark VARCHAR(32), "
                    + "model VARCHAR(32), "
                    + "horsePower INTEGER, "
                    + "PRIMARY KEY (id))";

    private static final String INSERT_QUERY =
            "INSERT INTO cars (modelYear, mark, model, horsePower) VALUES('?','?','?','?')";

    private static final String UPDATE_QUERY =
            "UPDATE cars SET modelYear =?, mark =?, model =?,  horsePower =? WHERE id =?";

    private static final String DELETE_QUERY =
            "DELETE FROM cars WHERE id =?";

    public Repository() {
        this.properties = getProperties();

        updateQuery(CREATE_TABLE_QUERY);
    }


    public void save(CarDataModel car) {
        updateQuery(carToInsertSqlString(car));
    }

    public void save(List<CarDataModel> cars) {
        ArrayList<String> sqls = new ArrayList<>(cars.size());
        for (var car : cars) {
            var sql = carToInsertSqlString(car);
            sqls.add(sql);
        }

        updateBatchQuery(sqls);
    }

    private String carToInsertSqlString(CarDataModel car) {
        var sb = new StringBuilder();
        sb.append("INSERT INTO cars (modelYear, mark, model, horsePower) ");
        sb.append("VALUES(");

        sb.append("'");
        sb.append(car.getYear());
        sb.append("',");

        sb.append("'");
        sb.append(car.getMark());
        sb.append("',");

        sb.append("'");
        sb.append(car.getModel());
        sb.append("',");

        sb.append("'");
        sb.append(car.getHorsePower());
        sb.append("'");

        sb.append(")\n");
        return sb.toString();
    }

    public CarDataModel findById(Long id) {
        try (var connection = getConnection();
             var statement = connection.createStatement()) {
            var query = "SELECT * FROM cars WHERE id = " + id;
            var result = statement.executeQuery(query);
            result.next();
            return buildModel(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<CarDataModel> findAll() {
        try (var connection = getConnection();
             var statement = connection.createStatement()) {
            ArrayList<CarDataModel> cars = new ArrayList<>();
            var query = "SELECT * FROM cars";
            var result = statement.executeQuery(query);
            while (result.next()) {
                var car = buildModel(result);
                cars.add(car);
            }
            return cars;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private CarDataModel buildModel(ResultSet resultSet) throws SQLException {
        var car = new CarDataModel();
        car.setId(resultSet.getLong("id"));
        car.setYear(resultSet.getInt("modelYear"));
        car.setMark(resultSet.getString("mark"));
        car.setModel(resultSet.getString("model"));
        car.setHorsePower(resultSet.getInt("horsePower"));

        return car;
    }


    public void update(CarDataModel car) {
        try (var connection = getConnection();
             var statement = connection.prepareStatement(UPDATE_QUERY)) {

            statement.setInt(1, car.getYear());
            statement.setString(2, car.getMark());
            statement.setString(3, car.getModel());
            statement.setInt(4, car.getHorsePower());
            statement.setLong(5, car.getId());

            statement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Long id) {
        try (var connection = getConnection();
             var statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setLong(1, id);

            statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void updateQuery(String query) {
        try (var connection = getConnection();
             var statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void updateBatchQuery(List<String> queries) {
        try (var connection = getConnection();
             var statement = connection.createStatement()) {

            for (var query : queries) {
                statement.addBatch(query);
            }
            var count = statement.executeBatch();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private Connection getConnection() throws SQLException {

        var url = properties.getProperty("url");
        var userName = properties.getProperty("username");
        var password = properties.getProperty("password");

        return DriverManager.getConnection(url, userName, password);
    }

    private Properties getProperties() {
        try {
            FileReader reader = new FileReader(propertiesPath);
            Properties p = new Properties();
            p.load(reader);
            return p;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
