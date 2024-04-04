package org.peaksoft.service;


import org.peaksoft.model.User;
import org.peaksoft.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements Service<User> {
    UserServiceImpl userService = new UserServiceImpl();

    public void createTable() {
        String query = """
                CREATE TABLE IF NOT EXISTS users (
                    id SERIAL PRIMARY KEY,
                name VARCHAR NOT NULL,
                    last_name VARCHAR NOT NULL,
                    age SMALLINT NOT NULL,
                    cardId BIGINT NOT NULL
                                
                )
                """;
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(query);
            System.out.println("Users added)");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropTable() throws SQLException {
        Connection connection = Util.getConnection();
        Statement statement = connection.createStatement();
        statement.execute("DROP TABLE users ");
        statement.close();
        System.out.println("Udelano!");
    }

    public void save(User user) {
        String query = """
                INSERT INTO users(NAME, LAST_NAME, AGE, CARDID)
                VALUES (?,?,?,?);
                """;
        try (Connection connection = Util.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setByte(3, user.getAge());
            preparedStatement.setLong(4, user.getCarId());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeById(long id) {
        String query = """
                DELETE FROM users WHERE id = ?;
                """;
        try (Connection connection = Util.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<User> getAll() {
        String query = """
                SELECT * FROM users;
                """;
        List<User> usersList = new ArrayList<>();
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));
                user.setCarId(resultSet.getLong("cardId"));
                usersList.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return usersList;

    }

    public void cleanTable() {
try {
    userService.save(new User());
    userService.getAll();
    userService.dropTable();
    userService.cleanTable();
    if (!userService.getAll().isEmpty()){
        System.out.println("Здраствуйте)");
    }
}catch (Exception e){
    System.out.println(e.getMessage());
}
    }

    public User getById(long id) {
        String query = """
                SELECT * FROM users WHERE  id = ?
                """;
        User user = new User();
        try(Connection connection = Util.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery(query);
            while (resultSet.next()){
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));
                user.setCarId(resultSet.getLong("cardId"));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return user;
    }
}
