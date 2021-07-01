package com.project.db;

import java.util.HashMap;
import java.util.ArrayList;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ObjectContext {
	private String table;
	private ArrayList<String> columns;
	private HashMap<String, ObjectContextValue> values;
	private PreparedStatement stmt;
	private ArrayList<ObjectContext> results; // Here we get the results...
	
	public ObjectContext(String table) {
		this.table = table;
		values = new HashMap<String, ObjectContextValue>();
	}

	public ResultSet execute() {
		try {
			return stmt.executeQuery();
		} catch (SQLException e) {
			return null;
		}
	}
	
	public boolean createStmt(DBConnection conn) {
		try {
			String query = getUpdateQuery();
			if (query != null) {
				stmt = conn.createStmt(query);
				int index = 0;
				for (String column : columns) {
					ObjectContextValue value = values.get(column);
					if (value.state == ObjectContextValue.STATE_NEW_VALUE) {
						switch(value.type) {
							case ObjectContextValue.TYPE_INT:
							stmt.setInt(index++, ((Integer)value.value).intValue());
							break;
							case ObjectContextValue.TYPE_PRICE:
							stmt.setString(index++, ((BigDecimal)value.value).toString());
							break;
							case ObjectContextValue.TYPE_STRING:
							stmt.setString(index++, (String)value.value);
							break;
						}
					}
				}
				ObjectContextValue id = values.get("id");
				stmt.setInt(index, ((Integer)id.value).intValue());
			} else {
				query = getInsertQuery();
				if (query != null) {
					stmt = conn.createStmt(query);
					int index = 0;
					for (String column : columns) {
						ObjectContextValue value = values.get(column);
						switch(value.type) {
							case ObjectContextValue.TYPE_INT:
							stmt.setInt(index++, ((Integer)value.value).intValue());
							break;
							case ObjectContextValue.TYPE_PRICE:
							stmt.setString(index++, ((BigDecimal)value.value).toString());
							break;
							case ObjectContextValue.TYPE_STRING:
							stmt.setString(index++, (String)value.value);
							break;
						}
						
					}
				} 
			}
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public boolean updateAttribute(String column, Object value) {
		ObjectContextValue obj = new ObjectContextValue();
		obj.value = value;
		obj.state = ObjectContextValue.STATE_NEW_VALUE;
		if (value instanceof String) {
			obj.type = ObjectContextValue.TYPE_STRING;
		} else if (value instanceof Integer) {
			obj.type = ObjectContextValue.TYPE_INT;
		} else if (value instanceof Float) { // TODO: Replace with real another data type...
			obj.type = ObjectContextValue.TYPE_PRICE;
		} else {
			return false;
		}
		values.put(column, obj);
		return true;
	}
	
	public boolean setSearchAttribute(String column, Object value) {
		ObjectContextValue obj = new ObjectContextValue();
		obj.value = value;
		obj.state = ObjectContextValue.STATE_OLD_VALUE;
		if (value instanceof String) {
			obj.type = ObjectContextValue.TYPE_STRING;
		} else if (value instanceof Integer) {
			obj.type = ObjectContextValue.TYPE_INT;
		} else if (value instanceof Float) { // TODO: Replace with real another data type...
			obj.type = ObjectContextValue.TYPE_PRICE;
		} else {
			return false;
		}
		values.put(column, obj);
		return true;
	}

	private String getSelectQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		boolean is_valid = false;
		for (String column : columns) {
			ObjectContextValue value = values.get(column);
			if (value.state == ObjectContextValue.STATE_OLD_VALUE) {
				sb.append(column);	
				sb.append(", ");	
				is_valid = true;
			}
		}
		if (is_valid == false) {
			return null;
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(" FROM ");
		sb.append(table);
		return sb.toString();
	}
	
	private String getUpdateQuery() {
		if (values.get("id") == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(table);
		sb.append("SET ");
		boolean is_valid = false;
		for (String column : columns) {
			ObjectContextValue value = values.get(column);
			if (value.state == ObjectContextValue.STATE_NEW_VALUE) {
				sb.append(column);	
				sb.append(" = ?,");
				is_valid = true;
			}
		}
		if (is_valid == false) {
			return null;
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(" WHERE id = ?");
		return sb.toString();
	}
	
	private String getInsertQuery() {
		if (values.get("id") != null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(table);
		sb.append("(");
		for (String column : columns) {
			sb.append(column);
			sb.append(",");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(") VALUES(");
		for (int i = 0; i < columns.size(); i++) {
			sb.append("?,");
		}
		sb.deleteCharAt(sb.length()-1);
		sb.append(")");
		return sb.toString();
	}


	public void search(DBConnection conn) {
		stmt = conn.createStmt(getSelectQuery());
		ResultSet set = execute();
		ResultSetMetaData metadata = null; 
		try {
			metadata = set.getMetaData();
			while (set.next()) {
				ObjectContext obj = new ObjectContext(table);
				for (int index = 1; index <= metadata.getColumnCount(); index++) {
					String column = metadata.getColumnName(index);
					int type = metadata.getColumnType(index);
				}
			}
		} catch (SQLException e) {
			return;
		}
	}
}
