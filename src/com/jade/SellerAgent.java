package com.jade;

import com.project.db.DBConnection;
import com.project.db.ObjectContext;

public class SellerAgent extends JadeAgent {
	private DBConnection db_connection;	
	private ObjectContext context;
	private String database;

	public SellerAgent() {
		database = String.format("%s.db", getClass().getName().toString());
	}

	protected void setup() {
		init("product-selling");
		db_connection = new DBConnection(database, "sqlite");
		if (db_connection.connect(null, null) == false) {
			System.out.println("Could not connect to database");
			this.takeDown();
		} 
		context = new ObjectContext("table_product");
		context.search(db_connection);
	} 
}
