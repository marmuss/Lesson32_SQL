package com.tms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


abstract class DbConnection {
    protected Connection connection;

    public DbConnection() {
        try {
            this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/university");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
