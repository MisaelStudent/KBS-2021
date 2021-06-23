package com.project.db;

import java.io.File;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection
{
    private Connection conn;
    private String     database;
    private String     user;
    private String     password;
    private String     driver;

    public DBConnection(String database, String driver) {
        this.database = database;
        this.driver   = driver;
    }

    public boolean connect(String user, String password) {
        this.user = user;
        this.password = password;
        try {
            String url = String.format("jdbc:%s:%s", driver, database);
            conn = DriverManager.getConnection(url);
            if (conn == null) {
                System.out.println("DB does not exists");
                return false;
            }
            System.out.println("Connection stablish");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ec) {
                    ec.printStackTrace();
                }
            }
            return false;
        }
    }

    public static void main(String args[]) {
        DBConnection dbc = new DBConnection("db.sqlite", "sqlite");
        dbc.connect(null, null);
        System.out.println("hello");
    }
}
