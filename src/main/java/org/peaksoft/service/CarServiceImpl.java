package org.peaksoft.service;

import org.peaksoft.model.Car;
import org.peaksoft.util.Util;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.peaksoft.util.Util.getConnection;

public class CarServiceImpl implements Service<Car> {
    CarServiceImpl carService = new CarServiceImpl();

    public void createTable() {
        String query = """
                    CREATE TABLE IF NOT EXISTS cars(
                        id SERIAL  PRIMARY KEY ,
                        model VARCHAR NOT NULL,
                        yearOfRelease TIMESTAMP NOT NULL,
                        color VARCHAR not null
                    );
                """;
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(query);
            System.out.println("Table added");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropTable() throws SQLException {
        Connection connection = Util.getConnection();
        Statement statement = connection.createStatement();
        statement.execute("DROP TABLE car");
        connection.close();
        System.out.println("Udelano!");
    }


    public void save(Car car) {
        String query = """
                INSERT INTO cars(MODEL, YEAROFRELEASE, COLOR)
                VALUES (?,?,?)
                """;
        try (Connection connection = Util.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, car.getModel());
            preparedStatement.setDate(2, Date.valueOf(car.getYearOfRelease()));
            preparedStatement.setString(3, car.getColor());
            preparedStatement.execute();
            System.out.println("CARS added");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeById(long id) {
        String query = "DELETE FROM  cars WHERE id = ?";
        try (Connection connection = Util.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Car> getAll() {
        String query = """
                SELECT * FROM casrs;
                """;
        List<Car> carsList = new ArrayList<>();
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Car car = new Car();
                car.setId(resultSet.getLong("id"));
                car.setModel(resultSet.getString("model"));
                car.setYearOfRelease((LocalDate) resultSet.getObject("yearOfRelease"));
                carsList.add(car);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return carsList;
    }


    public void cleanTable() {
        try {
            carService.save(new Car());
            carService.getAll();
            carService.dropTable();
            carService.cleanTable();
            if (!carService.getAll().isEmpty()) {
                System.out.println("Здраствуйте)");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Car getById(long id) {
        String query = """
                SELECT * FROM cars WHERE  id = ?
                """;
        Car car = new Car();
        try (Connection connection = Util.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                car.setId(resultSet.getLong("id"));
                car.setModel(resultSet.getString("model"));
                car.setYearOfRelease((LocalDate) resultSet.getObject("yerarOfRelease"));
                car.setColor(resultSet.getString("color"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return car;
    }
}
