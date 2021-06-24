package com.project.db;

public class ObjectContextValue {
	public static final int STATE_NEW_VALUE = 1;
	public static final int STATE_OLD_VALUE = 2;
	public static final int TYPE_STRING = 1;
	public static final int TYPE_INT    = 2;
	public static final int TYPE_PRICE  = 3;
	
	public Object  value;
	public int     state;
	public int     type;
}
