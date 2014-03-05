package com.example.moneymatters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class BankConnect {
	public static final String ROW_ID3="_id";
	public static final String ROW_NAME3="name";
	
	public static final String DATABASE_NAME3="bank_money";
	public static final String TABLE_NAME3="bank";
	public static final int VERSION=2;
	
	private MyOpenHelper openhelper;
	private SQLiteDatabase database;
	
	public BankConnect(Context context){
		openhelper= new MyOpenHelper(context, DATABASE_NAME3, null, VERSION);
		
	}
	
	public void open() throws SQLException{
		database=openhelper.getWritableDatabase();
		}
	
	public void close(){
		if(database!=null)
			database.close();
		
	}
	
	public void addAccount(String name,int balance){
		ContentValues newcon= new ContentValues();
		newcon.put(ROW_NAME3, name);
		newcon.put("balance", balance);
		open();
		database.insert(TABLE_NAME3, null, newcon);
		close();
	}
	public Cursor getAllAccounts(){
		return database.query(TABLE_NAME3, null, null, null, null, null, null);
		
	}
	public Cursor getOneAccount(long id){
		return database.query(TABLE_NAME3, null, "_id="+id, null,null,null,null);
		
	}
	public void updateAccount(long id,String name,int balance){
		ContentValues editcon= new ContentValues();
		editcon.put(ROW_NAME3, name);
		editcon.put("balance", balance);
		open();
		database.update(TABLE_NAME3, editcon, "_id="+id, null);
		
		close();
	}
	public void deleteAccount(long id){
		open();
		database.delete(TABLE_NAME3, "_id="+id, null);
		close();
		
	}
	
	


}
