package com.example.moneymatters;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper{

	public MyOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) throws SQLException {
		String createquery="Create table borrow(_id integer primary key autoincrement, name text, daily_amount integer, dates text,phone text)";
		db.execSQL(createquery);
		
		String tablequery="Create table lender(_id integer primary key autoincrement, name text, daily_amount integer, dates text,phone text)";
		db.execSQL(tablequery);
		
		String query="Create table bank(_id integer primary key autoincrement, name text, balance integer)";
		db.execSQL(query);
		
		
		
		
				
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("Drop table if exists borrow");
		db.execSQL("Drop table if exists lend");
		onCreate(db);
		
		
		// TODO Auto-generated method stub
		
	}

}
