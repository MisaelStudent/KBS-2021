package com.project.db;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;

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

    public class ObjectContextColumn {
        public static final int STATE_NEW_VALUE = 1;
        public static final int STATE_OLD_VALUE = 2;
        public static final int TYPE_STRING = 1;
        public static final int TYPE_INT    = 2;
        public static final int TYPE_PRICE  = 3;

        public Object  value;
        public int     state;
        public int     type;
    }

    public class ObjectContext {
        private String table;
        private ArrayList<String> columns;
        private HashMap<String, ObjectContextColumn> values;

        public ObjectContext(String table) {
            this.table = table;
            values = new HashMap<String, ObjectContextColumn>();
        }

        public boolean updateAttribute(String column, Object value) {
            ObjectContextColumn obj = new ObjectContextColumn();
            obj.value = value;
            obj.state = ObjectContextColumn.STATE_NEW_VALUE;
            if (value instanceof String) {
                obj.type = ObjectContextColumn.TYPE_STRING;
            } else if (value instanceof Integer) {
                obj.type = ObjectContextColumn.TYPE_INT;
            } else if (value instanceof Float) { // TODO: Replace with real another data type...
                obj.type = ObjectContextColumn.TYPE_PRICE;
            } else {
                return false;
            }
            values.put(column, obj);
            return true;
        }

        public boolean setSearchAttribute(String column, Object value) {
            ObjectContextColumn obj = new ObjectContextColumn();
            obj.value = value;
            obj.state = ObjectContextColumn.STATE_OLD_VALUE;
            if (value instanceof String) {
                obj.type = ObjectContextColumn.TYPE_STRING;
            } else if (value instanceof Integer) {
                obj.type = ObjectContextColumn.TYPE_INT;
            } else if (value instanceof Float) { // TODO: Replace with real another data type...
                obj.type = ObjectContextColumn.TYPE_PRICE;
            } else {
                return false;
            }
            values.put(column, obj);
            return true;
        }
    }

    public static void main(String args[]) {
        DBConnection dbc = new DBConnection("db.sqlite", "sqlite");
        dbc.connect(null, null);
        System.out.println("hello");
    }
}
