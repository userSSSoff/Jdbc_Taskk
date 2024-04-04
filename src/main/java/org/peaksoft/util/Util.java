package org.peaksoft.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // TODO: 27.09.2023   реализуйте настройку соеденения с БД
public  static  final String URL = "jdbc:postgresql://localhost:5432/postgres";
public static final String USER_NAME = "postgres";
public static final String PASSWORD = "999777";
public static Connection getConnection(){
    Connection connection = null;
    try{
        connection = DriverManager.getConnection(URL,USER_NAME,PASSWORD);
    }catch (SQLException e){
        throw  new RuntimeException();
    }return connection;
}
}
